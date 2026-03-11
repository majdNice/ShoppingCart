package com.example.ShoppingCart;
import org.springframework.boot.test.context.SpringBootTest;

import io.cucumber.spring.CucumberContextConfiguration;
@CucumberContextConfiguration
@SpringBootTest(classes = ShoppingCartApplication.class)
public class CucumberSpringConfiguration {
    // load full application context for Cucumber tests
}
