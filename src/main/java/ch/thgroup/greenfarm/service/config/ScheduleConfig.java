package ch.thgroup.greenfarm.service.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class ScheduleConfig {

    @Value("${checkMessageStatus.schedule}")
    private String schedule;

    @Value("${checkMessageStatus.maxRuns}")
    private int maxRuns;

}
