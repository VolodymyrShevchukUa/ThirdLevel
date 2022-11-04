package org.validation.shpp.pojo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

public class Person {

    @Length(min = 7)
    @Pattern(regexp = "[\\w]*a[\\w]*",message = "must contains \"a\"")
    String name ;

    @NotNull(message = "must be not null")
    String dateOfCreated;

    @Min(value = 10,message = "value should be more than 10")
    int count;


    public String getName() {
        return name;
    }

    public Person setName(String name) {
        this.name = name;
        return this;
    }

    public String getDateOfCreated() {
        return dateOfCreated;
    }

    public Person setDateOfCreated(String dateOfCreated) {
        this.dateOfCreated = dateOfCreated;
        return this;
    }

    public int getCount() {
        return count;
    }

    public Person setCount(int count) {
        this.count = count;
        return this;
    }





}
