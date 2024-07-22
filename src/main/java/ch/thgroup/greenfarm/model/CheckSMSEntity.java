package ch.thgroup.greenfarm.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "check_sms")
public class CheckSMSEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "error_code", nullable = false)
    private int errorCode;

    @Column(name = "error_description", length = 255)
    private String errorDescription;

    @Column(name = "mobile_number", length = 15, nullable = false)
    private String mobileNumber;

    @Column(name = "sender_id", length = 50)
    private String senderId;

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    @Column(name = "submit_date")
    private LocalDateTime submitDate;

    @Column(name = "done_date")
    private LocalDateTime doneDate;

    @Column(name = "message_id", nullable = false)
    private UUID messageId;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "error_code_msg", length = 10)
    private String errorCodeMsg;

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

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(LocalDateTime submitDate) {
        this.submitDate = submitDate;
    }

    public LocalDateTime getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(LocalDateTime doneDate) {
        this.doneDate = doneDate;
    }

    public UUID getMessageId() {
        return messageId;
    }

    public void setMessageId(UUID messageId) {
        this.messageId = messageId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorCodeMsg() {
        return errorCodeMsg;
    }

    public void setErrorCodeMsg(String errorCodeMsg) {
        this.errorCodeMsg = errorCodeMsg;
    }
}
