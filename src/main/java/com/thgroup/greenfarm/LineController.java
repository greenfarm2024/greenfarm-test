package com.thgroup.greenfarm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/line")
public class LineController {

    private final LineService lineService;

    @Autowired
    public LineController(LineService lineService) {
        this.lineService = lineService;
    }

    @PostMapping("/send-line-post")
    public String sendLinePost() {
        try {
            return lineService.sendLinePost();
        } catch (IOException | InterruptedException | URISyntaxException e) {
            e.printStackTrace();
            return "Failed to send Line via POST";
        }
    }
}
