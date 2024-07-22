package ch.thgroup.greenfarm.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.UUID;

public interface SMSService {
    String sendSMSGet() throws IOException, InterruptedException, URISyntaxException;
    String sendSMSPost() throws IOException, InterruptedException, URISyntaxException;
    String getMessageStatus(UUID messageId) throws IOException, InterruptedException, URISyntaxException;
}
