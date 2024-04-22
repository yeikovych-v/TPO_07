package pl.pja.s28201.tpo_07.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.pja.s28201.tpo_07.dto.CodeFormatDto;
import pl.pja.s28201.tpo_07.model.CodeFormat;
import pl.pja.s28201.tpo_07.repository.InMemoryCodeFormatRepository;
import pl.pja.s28201.tpo_07.service.TimeOutService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/codes")
public class RecordsViewController {

    private final InMemoryCodeFormatRepository repository;
    private final TimeOutService timeOutService;

    @Autowired
    public RecordsViewController(InMemoryCodeFormatRepository repository, TimeOutService timeOutService) {
        this.repository = repository;
        this.timeOutService = timeOutService;
    }

    @GetMapping
    public String displaySavedRecords(Model model) {
        var codes = repository.findAllAsList();
        var seconds = timeOutService.getAllSecondsToExpire(codes);
        var dtos = parseToDto(codes, seconds);

        model.addAttribute("dtos", dtos);


        return "dbDisplay";
    }

    private List<CodeFormatDto> parseToDto(List<CodeFormat> codes, List<Long> secondsToExpire) {
        List<CodeFormatDto> dtos = new ArrayList<>();

        int i = 0;
        for (CodeFormat c : codes) {
            var toExpire = secondsToExpire.get(i);
            if (toExpire >= 0) {
                dtos.add(new CodeFormatDto(c.getId(), c.getBody(), c.getTimeSerialized(), toExpire));
            }
            i++;
        }

        return dtos;
    }
}
