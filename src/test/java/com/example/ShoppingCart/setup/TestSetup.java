package com.example.ShoppingCart.setup;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

/**
 * Global test setup that runs before any tests start.
 * This is automatically discovered and executed by Spring.
 * Add your custom setup Java code in the beforeTestClass() method.
 */
public class TestSetup implements TestExecutionListener {

    private static boolean initialized = false;

    @Override
    public void beforeTestClass(TestContext testContext) {
        // Run only once
        if (!initialized) {
            System.out.println("========================================");
            System.out.println("Running global test setup...");
            System.out.println("========================================");
            
            clearLogs();
            initialized = true;
            System.out.println("Test setup completed!");
            System.out.println("========================================");
        }
    }

    static void clearLogs() {
        Path logPath = Paths.get("logs", "app.log");
        try {
            Files.deleteIfExists(logPath);
            System.out.println("✓ Cleared log file");
        } catch (IOException e) {
            // File may be locked by logging system, silently continue
            // This is normal during test runs
            System.out.println(e.getMessage());
        }
    }
}
