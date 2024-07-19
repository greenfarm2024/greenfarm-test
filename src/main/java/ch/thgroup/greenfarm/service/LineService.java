package ch.thgroup.greenfarm.service;

import java.io.IOException;
import java.net.URISyntaxException;

public interface LineService {
    String sendLinePost() throws IOException, InterruptedException, URISyntaxException;
}
