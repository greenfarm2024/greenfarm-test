package ch.thgroup.greenfarm.controller;

import ch.thgroup.greenfarm.service.SMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.UUID;

@RestController
@RequestMapping("/sms")
public class SMSController {

    private final SMSService smsService;

    @Autowired
    public SMSController(SMSService smsService) {
        this.smsService = smsService;
    }

    @GetMapping("/send-sms-get")
    public String sendSMSGet() {
        try {
            return smsService.sendSMSGet();
        } catch (IOException | InterruptedException | URISyntaxException e) {
            e.printStackTrace();
            return "Failed to send SMS via GET";
        }
    }

    @PostMapping("/send-sms-post")
    public String sendSMSPost() {
        try {
            return smsService.sendSMSPost();
        } catch (IOException | InterruptedException | URISyntaxException e) {
            e.printStackTrace();
            return "Failed to send SMS via POST";
        }
    }

    @GetMapping("/status")
    public ResponseEntity<String> getMessageStatus(@RequestParam UUID messageId) {
        try {
            String response = smsService.getMessageStatus(messageId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
