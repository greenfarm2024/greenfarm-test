package ch.thgroup.greenfarm.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SendSMSDTO {

    @JsonProperty("ErrorCode")
    private int errorCode;

    @JsonProperty("ErrorDescription")
    private String errorDescription;

    @JsonProperty("Data")
    private List<DataDTO> data;

    // Getters and Setters

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataDTO {

        @JsonProperty("MessageErrorCode")
        private int messageErrorCode;

        @JsonProperty("MessageErrorDescription")
        private String messageErrorDescription;

        @JsonProperty("MobileNumber")
        private String mobileNumber;

        @JsonProperty("MessageId")
        private UUID messageId;

        @JsonProperty("Custom")
        private String custom;

        // Getters and Setters

        public int getMessageErrorCode() {
            return messageErrorCode;
        }

        public void setMessageErrorCode(int messageErrorCode) {
            this.messageErrorCode = messageErrorCode;
        }

        public String getMessageErrorDescription() {
            return messageErrorDescription;
        }

        public void setMessageErrorDescription(String messageErrorDescription) {
            this.messageErrorDescription = messageErrorDescription;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

        public UUID getMessageId() {
            return messageId;
        }

        public void setMessageId(UUID messageId) {
            this.messageId = messageId;
        }

        public String getCustom() {
            return custom;
        }

        public void setCustom(String custom) {
            this.custom = custom;
        }
    }
}
