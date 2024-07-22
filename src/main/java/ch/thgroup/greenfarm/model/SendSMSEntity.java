package ch.thgroup.greenfarm.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "send_sms", schema = "greenfarm")
public class SendSMSEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "error_code", nullable = false)
    private int errorCode;

    @Column(name = "error_description")
    private String errorDescription;

    @Column(name = "message_error_code", nullable = false)
    private int messageErrorCode;

    @Column(name = "message_error_description")
    private String messageErrorDescription;

    @Column(name = "mobile_number", nullable = false, length = 15)
    private String mobileNumber;

    @Column(name = "message_id", nullable = false)
    private UUID messageId;

    @Column(name = "custom")
    private String custom;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

