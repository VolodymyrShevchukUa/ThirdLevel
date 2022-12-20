package org.validation.shpp;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.google.gson.Gson;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.validation.shpp.mq.MessageReader;
import org.validation.shpp.mq.MessageSender;
import org.validation.shpp.pojo.Person;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Hello world!
 */
public class Main implements Configurable {
    List<Person> invalid = new LinkedList<>();
    List<Person> valid = new LinkedList<>();
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    CsvSchema validSchema = CsvSchema.builder().addColumn("name").addColumn("count").build();
    CsvSchema invalidSchema = CsvSchema.builder().addColumn("name").addColumn("count").
            addColumn("errors").build();
    private final CsvMapper csvMapper = new CsvMapper();


    public static void main(String[] args) {
        Main main = new Main();
        main.generateMessage();
        main.validationObject();
        main.createValidCSV();
        main.createInvalidFile();
    }

    private void generateMessage() {
        List<String> names = getNames();
        Random random = new Random();
        int timer = LocalDateTime.now().getSecond() + time;
        MessageSender messageSender = new MessageSender();

        List<String> message = IntStream.range(0, 2000).takeWhile(s -> timer > LocalDateTime.now().getSecond()).
                mapToObj(value -> new Person()
                        .setName(names.get(random.nextInt(names.size() - 1)))
                        .setCount(random.nextInt(1000))
                        .setDateOfCreated(LocalDateTime.now().toString())).map(this::jsonMapper)
                .collect(Collectors.toList());

        message.add(POISON_PILL);
        messageSender.sendMessages(message);
    }

    private void validationObject() {
        MessageReader messageReader = new MessageReader();
        List<String> list = messageReader.receiveMessage();
        Gson gson = new Gson();
        valid = list.stream().map(p -> gson.fromJson(p, Person.class)).filter(this::sort)
                .collect(Collectors.toList());
    }

    private void createValidCSV() {
        csvMapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
        File yourFile = new File("validPerson.csv");
        String text = "";
        for (Person person : valid) {
            try {
                ObjectWriter writer = csvMapper.writer(validSchema);
                String s = writer.writeValueAsString(person);
                text = text.concat(s);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try (FileWriter fileWriter = new FileWriter(yourFile)) {
            fileWriter.write(text);
            fileWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createInvalidFile() {
        csvMapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
        File yourFile = new File("invalidPerson.csv");
        String text = "";
        for (Person person : invalid) {
            try {
                ObjectWriter writer = csvMapper.writer(invalidSchema);
                String s = writer.writeValueAsString(person);
                text = text.concat(s);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try (FileWriter fileWriter = new FileWriter(yourFile)) {
            fileWriter.write(text);
            fileWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean sort(Person person) {
        Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);
        Velosiped velosiped = new Velosiped();
        String[] errors = new String[constraintViolations.size()];
        boolean check = true;
        if (!constraintViolations.isEmpty()) {
            invalid.add(person);
            check = false;
            int i = 0;
            for (ConstraintViolation<Person> personConstraintViolation : constraintViolations) {
                errors[i++] = personConstraintViolation.getMessage();
            }
            velosiped.errors = errors;
            try {
                person.errors = new ObjectMapper().writeValueAsString(velosiped);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

        }
        return check;
    }

    private List<String> getNames() {
        List<String> names = new ArrayList<>();

        try (FileReader fileReader = new FileReader(new File("names.txt"))
             ; BufferedReader bufferedReader = new BufferedReader(fileReader);) {
            String line = bufferedReader.readLine();
            while (line != null) {
                names.add(line.split("\\s+")[1]);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return names;
    }

    String jsonMapper(Person person) {
        ObjectMapper objectMapper = new ObjectMapper();
        String json;
        try {
            json = objectMapper.writeValueAsString(person);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }
}
