package org.validation.shpp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.validation.shpp.mq.ReceiveMessage;
import org.validation.shpp.mq.SendMessage;
import org.validation.shpp.pojo.Person;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{

    Config config = new Config();

   static int i =0;
    public static void main( String[] args )
    {
        new SendMessage().sendMessage();
        new ReceiveMessage().receiveMessage();

//
//
//       IntStream.range(0,1001).mapToObj(value -> new Person().setName("Name" + value).setCount(value)
//               .setDateOfCreated(LocalDateTime.now().toString())).forEach(App::createJson);
//        System.out.println( "Hello World!" );
    }
    static void createJson(Person person){
        ObjectMapper objectMapper = new ObjectMapper();
        String json ;
        try {
            json = objectMapper.writeValueAsString(person);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        File yourFile = new File("json/example.json" + i++);
        try (FileWriter fileWriter = new FileWriter(yourFile)) {
            fileWriter.write(json);
            fileWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
