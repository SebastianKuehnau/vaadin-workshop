package com.vaadin.workshop.views.helloworld;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MyRestService {

    public record ExampleData(String name, String surname, Double rating, BigDecimal price) {}

    public List<ExampleData> listData() {
        return List.of(new ExampleData("John", "Doe", 4.5, BigDecimal.valueOf(100)));
    }
}
