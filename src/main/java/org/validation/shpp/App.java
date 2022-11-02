package org.validation.shpp;

import org.validation.shpp.pojo.Person;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

       IntStream.range(0,1001).mapToObj(value -> new Person().setName("Name" + value).setCount(value)
               .setDateOfCreated(LocalDateTime.now())).forEach(App::createJson);


        System.out.println( "Hello World!" );
    }
    static void createJson(Person person){

    }


}
