package ch.thgroup.greenfarm.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "sms", schema = "greenfarm")
public class SMSEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "error_code", nullable = false)
    private Integer errorCode;

    @Column(name = "error_description", columnDefinition = "TEXT")
    private String errorDescription;

    @Column(name = "message_error_code")
    private Integer messageErrorCode;

    @Column(name = "message_error_description", columnDefinition = "TEXT")
    private String messageErrorDescription;

    @Column(name = "mobile_number", length = 15, nullable = false)
    private String mobileNumber;

    @Column(name = "message_id", nullable = false)
    private UUID messageId;

    @Column(name = "custom", columnDefinition = "TEXT")
    private String custom;

    @Column(name = "sender_id", length = 50)
    private String senderId;

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    @Column(name = "submit_date")
    private LocalDateTime submitDate;

    @Column(name = "done_date")
    private LocalDateTime doneDate;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "error_code_msg", length = 10)
    private String errorCodeMsg;

    @Column(name = "check_count")
    private Short checkCount;

    @Column(name = "created_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdDate;
}

