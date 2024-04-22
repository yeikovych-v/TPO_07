package pl.pja.s28201.tpo_07.service;

import org.springframework.stereotype.Service;
import pl.pja.s28201.tpo_07.model.CodeFormat;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TimeOutService {

    public long getSecondsToExpire(CodeFormat code) {
        Duration secondsElapsed = Duration.between(code.getTimeSerialized(), LocalDateTime.now());
        return code.getSecondsToExpire() - secondsElapsed.getSeconds();
    }

    public boolean hasTimeOut(CodeFormat code) {
        Duration secondsElapsed = Duration.between(code.getTimeSerialized(), LocalDateTime.now());
        return secondsElapsed.getSeconds() > code.getSecondsToExpire();
    }

    public List<Long> getAllSecondsToExpire(List<CodeFormat> codes) {
        List<Long> result = new ArrayList<>();

        for (CodeFormat c : codes) {
            result.add(getSecondsToExpire(c));
        }

        return result;
    }
}
