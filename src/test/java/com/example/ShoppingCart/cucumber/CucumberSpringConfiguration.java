package com.example.ShoppingCart.cucumber;

import org.springframework.boot.test.context.SpringBootTest;
import com.example.ShoppingCart.ShoppingCartApplication;
import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = ShoppingCartApplication.class)
public class CucumberSpringConfiguration {
    // load full application context for Cucumber tests
}
