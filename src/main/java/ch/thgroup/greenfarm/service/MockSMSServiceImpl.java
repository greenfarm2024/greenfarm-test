package ch.thgroup.greenfarm.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Profile("dev")  // This will only be active in the "dev" profile
public class MockSMSServiceImpl implements SMSService {

    @Override
    public String sendSMSGet() {
        // Mock response for sendSMSGet method
        return """
        {
            "ErrorCode": 0,
            "ErrorDescription": null,
            "Data": [
                {
                    "MessageErrorCode": 0,
                    "MessageErrorDescription": "Success",
                    "MobileNumber": "66996946372XXXX",
                    "MessageId": "a382577d-e4d8-420f-b4c8-5169ff673fc0",
                    "Custom": null
                }
            ]
        }
        """;
    }

    @Override
    public String sendSMSPost() {
        // Mock response for sendSMSPost method
        return """
        {
            "ErrorCode": 0,
            "ErrorDescription": null,
            "Data": [
                {
                    "MessageErrorCode": 0,
                    "MessageErrorDescription": "Success",
                    "MobileNumber": "66996946359",
                    "MessageId": "25e6727b-bc7d-494d-80f5-d0b1b74b8961",
                    "Custom": null
                }
            ]
        }
        """;
    }

    @Override
    public String checkMessageStatus(UUID messageId) {
        return """
    {
        "ErrorCode": 0,
        "ErrorDescription": "Success",
        "Data": {
            "MobileNumber": "66996946359",
            "SenderId": "MAILBITTEST",
            "Message": "XXX!",
            "SubmitDate": "17 Jul 2024 22:11:38",
            "DoneDate": "17 Jul 2024 22:11:00",
            "MessageId": "bd788c4e-a6cb-46cf-855a-40f4788509ae",
            "Status": "DELIVRD",
            "ErrorCode": "000"
        }
    }
    """;
    }
}
