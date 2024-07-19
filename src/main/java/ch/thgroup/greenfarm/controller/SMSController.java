package ch.thgroup.greenfarm.controller;

import ch.thgroup.greenfarm.service.SMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;

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
}
