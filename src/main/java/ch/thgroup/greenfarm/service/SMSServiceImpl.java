package ch.thgroup.greenfarm.service;

import ch.thgroup.greenfarm.model.SMSEntity;
import ch.thgroup.greenfarm.repository.SMSRepository;
import ch.thgroup.greenfarm.service.config.ScheduleConfig;
import ch.thgroup.greenfarm.service.dto.CheckSMSDTO;
import ch.thgroup.greenfarm.service.dto.SendSMSDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.net.URLEncoder;

@Slf4j
@Service
@Profile("!dev")  // This will be active in all profiles except "dev"
public class SMSServiceImpl implements SMSService {

    private static final String SMS_API_URL = "https://api.send-sms.in.th/api/v2/SendSMS";
    private static final String GET_MSG_STATUS_URL = "https://api.send-sms.in.th/api/v2/MessageStatus";
    private static final String USERNAME = "thgroup";
    private static final String PASSWORD = "ZPyq9urQxm!dvKtEx";
    private static final String API_KEY = "6mp8i/CWGzMSrSono5w/voqSMNnOF4RdZU9xOBo2mUw=";
    private static final String CLIENT_ID = "e09fb523-7aa6-4aa1-b482-eee2249b136c";
    private static final String SENDER_ID = "MAILBITTEST";
    private static final String MESSAGE = "XXX!";
    private static final String MOBILE_NUMBERS = "66996946359";
    private static final String IS_UNICODE = "true";
    private static final String IS_FLASH = "true";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss");


    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final SMSRepository smsRepository;

    private final ScheduleConfig scheduleConfig;

    @Autowired
    public SMSServiceImpl(ObjectMapper objectMapper, SMSRepository smsRepository,
                          ScheduleConfig scheduleConfig) {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = objectMapper;
        this.smsRepository = smsRepository;
        this.scheduleConfig = scheduleConfig;
    }

    @Override
    public String sendSMSGet() throws IOException, InterruptedException, URISyntaxException {
        HttpRequest request = buildRequest(HttpMethod.GET, buildUrl(null));
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        saveSMSData(response.body());
        return response.body();
    }

    @Override
    public String sendSMSPost() throws IOException, InterruptedException, URISyntaxException {
        JSONObject body = new JSONObject()
                .put("ApiKey", API_KEY)
                .put("ClientId", CLIENT_ID)
                .put("SenderId", SENDER_ID)
                .put("Message", MESSAGE)
                .put("MobileNumbers", MOBILE_NUMBERS)
                .put("Is_Unicode", IS_UNICODE)
                .put("Is_Flash", IS_FLASH);

        HttpRequest request = buildRequest(HttpMethod.POST, SMS_API_URL, body.toString());
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        saveSMSData(response.body());
        return response.body();
    }

    @Scheduled(cron = "#{scheduleConfig.getSchedule()}")
    public void scheduleGetMessageStatus() {
        List<SMSEntity> smsCheckList = smsRepository.findByCheckCountLessThan((short) scheduleConfig.getMaxRuns());
        smsCheckList.forEach(smsEntity -> {
            try {
                updateMessageStatus(smsEntity);
            } catch (IOException | InterruptedException | URISyntaxException e) {
                log.error("Error updating message status", e);
            }
        });
    }

    @Override
    public String checkMessageStatus(UUID messageId) throws IOException, InterruptedException, URISyntaxException {
        String url = UriComponentsBuilder.fromHttpUrl(GET_MSG_STATUS_URL)
                .queryParam("ApiKey", API_KEY)
                .queryParam("ClientId", CLIENT_ID)
                .queryParam("MessageId", messageId)
                .toUriString();

        HttpRequest request = buildRequest(HttpMethod.GET, url);
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public void updateMessageStatus(SMSEntity smsEntity) throws IOException, InterruptedException, URISyntaxException {
        String url = UriComponentsBuilder.fromHttpUrl(GET_MSG_STATUS_URL)
                .queryParam("ApiKey", API_KEY)
                .queryParam("ClientId", CLIENT_ID)
                .queryParam("MessageId", smsEntity.getMessageId())
                .toUriString();

        HttpRequest request = buildRequest(HttpMethod.GET, url);
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        updateMessageStatus(smsEntity, response.body());
    }

    private HttpRequest buildRequest(HttpMethod method, String url) throws URISyntaxException {
        return buildRequest(method, url, null);
    }

    private HttpRequest buildRequest(HttpMethod method, String url, String body) throws URISyntaxException {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(new URI(url))
                .header("Authorization", createAuthHeader(USERNAME, PASSWORD))
                .header("Content-Type", "application/json");

        if (method == HttpMethod.POST && body != null) {
            requestBuilder.POST(HttpRequest.BodyPublishers.ofString(body));
        } else {
            requestBuilder.GET();
        }

        return requestBuilder.build();
    }

    private String createAuthHeader(String username, String password) {
        String auth = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
    }

    private void saveSMSData(String responseBody) throws IOException {
        SendSMSDTO sendSMSDTO = objectMapper.readValue(responseBody, SendSMSDTO.class);
        for (SendSMSDTO.DataDTO dataDTO : sendSMSDTO.getData()) {
            SMSEntity smsEntity = new SMSEntity();
            smsEntity.setErrorCode(sendSMSDTO.getErrorCode());
            smsEntity.setErrorDescription(sendSMSDTO.getErrorDescription());
            smsEntity.setMessageErrorCode(dataDTO.getMessageErrorCode());
            smsEntity.setMessageErrorDescription(dataDTO.getMessageErrorDescription());
            smsEntity.setMobileNumber(dataDTO.getMobileNumber());
            smsEntity.setMessageId(dataDTO.getMessageId());
            smsEntity.setCustom(dataDTO.getCustom());
            smsEntity.setCheckCount((short) 0);
            smsRepository.save(smsEntity);
        }
    }

    private void updateMessageStatus(SMSEntity smsEntity, String responseBody) throws IOException {
        CheckSMSDTO checkSMSDTO = objectMapper.readValue(responseBody, CheckSMSDTO.class);
        smsEntity.setErrorCode(checkSMSDTO.getErrorCode());
        smsEntity.setErrorDescription(checkSMSDTO.getErrorDescription());
        smsEntity.setMobileNumber(checkSMSDTO.getData().getMobileNumber());
        smsEntity.setSenderId(checkSMSDTO.getData().getSenderId());
        smsEntity.setMessage(checkSMSDTO.getData().getMessage());
        smsEntity.setSubmitDate(stringToLocalDateTime(checkSMSDTO.getData().getSubmitDate()));
        smsEntity.setDoneDate(stringToLocalDateTime(checkSMSDTO.getData().getDoneDate()));
        smsEntity.setStatus(checkSMSDTO.getData().getStatus());
        smsEntity.setErrorCodeMsg(checkSMSDTO.getData().getErrorCode());

        // Increment checkCount
        short currentCheckCount = (smsEntity.getCheckCount() != null) ? smsEntity.getCheckCount() : 0;
        smsEntity.setCheckCount((short) (currentCheckCount + 1));

        smsRepository.save(smsEntity);
    }

    private String buildUrl(String queryParams) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(SMS_API_URL)
                .queryParam("ApiKey", API_KEY)
                .queryParam("ClientId", CLIENT_ID)
                .queryParam("SenderId", SENDER_ID)
                .queryParam("Message", URLEncoder.encode(MESSAGE, StandardCharsets.UTF_8))
                .queryParam("MobileNumbers", MOBILE_NUMBERS)
                .queryParam("Is_Unicode", IS_UNICODE)
                .queryParam("Is_Flash", IS_FLASH);

        if (queryParams != null) {
            uriBuilder.query(queryParams);
        }

        return uriBuilder.toUriString();
    }

    private LocalDateTime stringToLocalDateTime(String dateTimeStr) {
        try {
            return LocalDateTime.parse(dateTimeStr, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            // Handle parsing exception
            throw new IllegalArgumentException("Invalid date-time format: " + dateTimeStr, e);
        }
    }

}
