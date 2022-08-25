package com.nitinrane.learning.spring;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PersonForm {
    @NotNull
    @Min(18)
    private Integer age;
    @NotNull
    @Size(min = 2, max = 15)
    private String name;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PersonForm{" +
            "age=" + age +
            ", name='" + name + '\'' +
            '}';
    }
}
