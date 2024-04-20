package pl.pja.s28201.tpo_07.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.pja.s28201.tpo_07.model.CodeFormat;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class SerializationService {

    @Value("${serialization.src}")
    private String SERIALIZATION_DIR;
    private final FileTimeOutService timeOutService;

    @Autowired
    public SerializationService(FileTimeOutService timeOutService) {
        this.timeOutService = timeOutService;
    }

    public void serialize(CodeFormat code) {
        var fileName = "code-ser-" + code.getId() + ".ser";
        try {
            Path directoryPath = Paths.get(SERIALIZATION_DIR);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            try (FileOutputStream fos = new FileOutputStream(SERIALIZATION_DIR + fileName);
                 ObjectOutputStream out = new ObjectOutputStream(fos)) {

                code.setTimeSerialized(LocalDateTime.now());

                out.writeObject(code);

                System.out.printf("Info:: Serialized code object with id[%d]. %n", code.getId());
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public CodeFormat deserialize(File file) {
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (CodeFormat) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Map<Integer, CodeFormat> deserializeAll() {
        Map<Integer, CodeFormat> resultMap = new HashMap<>();

        File[] files = new File(SERIALIZATION_DIR).listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".ser")) {
                    var toSave = deserialize(file);
                    if (timeOutService.hasTimeOut(toSave)) {
                        deleteFile(file);
                    } else {
                        resultMap.put(toSave.getId(), toSave);
                    }
                }
            }
        }

        return resultMap;
    }

    private void deleteFile(File file) {
        if (file.delete()) {
            System.out.printf("Info:: serialization file with name [%s] has expired and was deleted. %n", file.getName());
        }
    }
}
