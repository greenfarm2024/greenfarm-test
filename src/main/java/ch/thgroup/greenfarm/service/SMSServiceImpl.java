package ch.thgroup.greenfarm.service;

import ch.thgroup.greenfarm.model.CheckSMSEntity;
import ch.thgroup.greenfarm.model.SendSMSEntity;
import ch.thgroup.greenfarm.repository.CheckSMSRepository;
import ch.thgroup.greenfarm.repository.SendSMSRepository;
import ch.thgroup.greenfarm.service.dto.CheckSMSDTO;
import ch.thgroup.greenfarm.service.dto.SendSMSDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
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
import java.util.UUID;
import java.net.URLEncoder;

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
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final SendSMSRepository sendSMSRepository;
    private final CheckSMSRepository checkSMSRepository;

    @Autowired
    public SMSServiceImpl(ObjectMapper objectMapper, SendSMSRepository sendSMSRepository, CheckSMSRepository checkSMSRepository) {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = objectMapper;
        this.sendSMSRepository = sendSMSRepository;
        this.checkSMSRepository = checkSMSRepository;
    }

    @Override
    public String sendSMSGet() throws IOException, InterruptedException, URISyntaxException {
        HttpRequest request = buildRequest(HttpMethod.GET, buildUrl(null));
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        saveSendSMSData(response.body());
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
        saveSendSMSData(response.body());
        return response.body();
    }

    @Override
    public String getMessageStatus(UUID messageId) throws IOException, InterruptedException, URISyntaxException {
        String url = UriComponentsBuilder.fromHttpUrl(GET_MSG_STATUS_URL)
                .queryParam("ApiKey", API_KEY)
                .queryParam("ClientId", CLIENT_ID)
                .queryParam("MessageId", messageId)
                .toUriString();

        HttpRequest request = buildRequest(HttpMethod.GET, url);
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        saveCheckSMSData(response.body());
        return response.body();
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

    private void saveSendSMSData(String responseBody) throws IOException {
        SendSMSDTO sendSMSDTO = objectMapper.readValue(responseBody, SendSMSDTO.class);
        for (SendSMSDTO.DataDTO dataDTO : sendSMSDTO.getData()) {
            SendSMSEntity sendSMSEntity = new SendSMSEntity();
            sendSMSEntity.setErrorCode(sendSMSDTO.getErrorCode());
            sendSMSEntity.setErrorDescription(sendSMSDTO.getErrorDescription());
            sendSMSEntity.setMessageErrorCode(dataDTO.getMessageErrorCode());
            sendSMSEntity.setMessageErrorDescription(dataDTO.getMessageErrorDescription());
            sendSMSEntity.setMobileNumber(dataDTO.getMobileNumber());
            sendSMSEntity.setMessageId(dataDTO.getMessageId());
            sendSMSEntity.setCustom(dataDTO.getCustom());
            sendSMSRepository.save(sendSMSEntity);
        }
    }

    private void saveCheckSMSData(String responseBody) throws IOException {
        CheckSMSDTO checkSMSDTO = objectMapper.readValue(responseBody, CheckSMSDTO.class);
        CheckSMSEntity checkSMSEntity = new CheckSMSEntity();
        checkSMSEntity.setErrorCode(checkSMSDTO.getErrorCode());
        checkSMSEntity.setErrorDescription(checkSMSDTO.getErrorDescription());
        checkSMSEntity.setMobileNumber(checkSMSDTO.getData().getMobileNumber());
        checkSMSEntity.setSenderId(checkSMSDTO.getData().getSenderId());
        checkSMSEntity.setMessage(checkSMSDTO.getData().getMessage());
        checkSMSEntity.setSubmitDate(stringToLocalDateTime(checkSMSDTO.getData().getSubmitDate()));
        checkSMSEntity.setDoneDate(stringToLocalDateTime(checkSMSDTO.getData().getDoneDate()));
        checkSMSEntity.setMessageId(stringToUUID(checkSMSDTO.getData().getMessageId()));
        checkSMSEntity.setStatus(checkSMSDTO.getData().getStatus());
        checkSMSEntity.setErrorCodeMsg(checkSMSDTO.getData().getErrorCode());
        checkSMSRepository.save(checkSMSEntity);
    }

    private String buildUrl(String queryParams) throws URISyntaxException {
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

    private UUID stringToUUID(String uuidStr) {
        try {
            return UUID.fromString(uuidStr);
        } catch (IllegalArgumentException e) {
            // Handle parsing exception
            throw new IllegalArgumentException("Invalid UUID format: " + uuidStr, e);
        }
    }
}
