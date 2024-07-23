package ch.thgroup.greenfarm.controller;

import ch.thgroup.greenfarm.service.SMSService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/sms")
public class SMSController {

    private final SMSService smsService;

    @Autowired
    public SMSController(SMSService smsService) {
        this.smsService = smsService;
    }

    @GetMapping("/send-sms-get")
    public ResponseEntity<String> sendSMSGet() {
        try {
            String response = smsService.sendSMSGet();
            return ResponseEntity.ok(response);
        } catch (IOException | InterruptedException | URISyntaxException e) {
            log.error("Failed to send SMS via GET", e);
            return ResponseEntity.status(500).body("Failed to send SMS via GET");
        }
    }

    @PostMapping("/send-sms-post")
    public ResponseEntity<String> sendSMSPost() {
        try {
            String response = smsService.sendSMSPost();
            return ResponseEntity.ok(response);
        } catch (IOException | InterruptedException | URISyntaxException e) {
            log.error("Failed to send SMS via POST", e);
            return ResponseEntity.status(500).body("Failed to send SMS via POST");
        }
    }

    @GetMapping("/status")
    public ResponseEntity<String> getMessageStatus(@RequestParam UUID messageId) {
        try {
            String response = smsService.checkMessageStatus(messageId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Failed to get message status for messageId: {}", messageId, e);
            return ResponseEntity.status(500).body("Failed to get message status");
        }
    }
}
