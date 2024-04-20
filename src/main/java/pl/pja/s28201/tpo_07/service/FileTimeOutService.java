package pl.pja.s28201.tpo_07.service;

import org.springframework.stereotype.Service;
import pl.pja.s28201.tpo_07.model.CodeFormat;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class FileTimeOutService {

    public boolean hasTimeOut(CodeFormat code) {
        Duration secondsElapsed = Duration.between(code.getTimeSerialized(), LocalDateTime.now());
        return secondsElapsed.getSeconds() > code.getSecondsToExpire();
    }
}
