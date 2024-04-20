package pl.pja.s28201.tpo_07.service;

import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;
import org.springframework.stereotype.Service;

@Service
public class FormatterService {

    public String toFormattedString(String toFormat) throws FormatterException {
        return new Formatter().formatSource(toFormat);
    }
}
