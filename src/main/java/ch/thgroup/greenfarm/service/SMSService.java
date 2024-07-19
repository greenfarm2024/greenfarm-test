package ch.thgroup.greenfarm.service;

import java.io.IOException;
import java.net.URISyntaxException;

public interface SMSService {
    String sendSMSGet() throws IOException, InterruptedException, URISyntaxException;
    String sendSMSPost() throws IOException, InterruptedException, URISyntaxException;
}
