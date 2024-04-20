package pl.pja.s28201.tpo_07.controller;

import com.google.googlejavaformat.java.FormatterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.pja.s28201.tpo_07.exception.CodeIdAlreadyExistsException;
import pl.pja.s28201.tpo_07.model.CodeFormat;
import pl.pja.s28201.tpo_07.repository.InMemoryCodeFormatRepository;
import pl.pja.s28201.tpo_07.service.FormatterService;
import pl.pja.s28201.tpo_07.service.SerializationService;

@Controller
@RequestMapping("/")
public class MainPageController {

    private final FormatterService formatterService;
    private final InMemoryCodeFormatRepository inMemoryRepository;
    private final SerializationService serializationService;

    @Autowired
    public MainPageController(FormatterService formatterService, InMemoryCodeFormatRepository inMemoryRepository, SerializationService serializationService) {
        this.formatterService = formatterService;
        this.inMemoryRepository = inMemoryRepository;
        this.serializationService = serializationService;
    }

    @GetMapping
    public String getMainPage(Model model){
        System.out.println("GET::");

        model.addAttribute("code", new CodeFormat());
        return "main";
    }

    @PostMapping
    public String postMainPage(Model model,
                               @ModelAttribute CodeFormat format) {
        System.out.println("POST::");
        try {
            String formattedCode = formatterService.toFormattedString(format.getBody());

            model.addAttribute("formattedCode", formattedCode);
            model.addAttribute("code", format);

            format.setBody(formattedCode);
            inMemoryRepository.add(format);
            serializationService.serialize(format);

            return "main";
        } catch (FormatterException e) {
            System.out.println("Info:: Invalid java code format.");

            model.addAttribute("code", new CodeFormat());
            model.addAttribute("formatErrMsg", "Your code has compilation errors or is written in different language, impossible to format.");
            return "main";
        } catch (CodeIdAlreadyExistsException e) {
            System.out.println("Info:: Serialization Id Already Exists.");

            model.addAttribute(format);
            model.addAttribute("serializationErrMsg", e.getMessage());
            return "main";
        }
    }
}
