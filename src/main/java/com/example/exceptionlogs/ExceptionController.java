package com.example.exceptionlogs;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ExceptionController {

    @GetMapping("/null-pointer")
    public String nullPointer() {
        log.info("Triggering NullPointerException - NOW WITH SAFETY CHECKS");
        
        // FIX: Previously, 'value' was null and calling .toString() caused NPE
        // Now we validate and handle the null case explicitly
        String value = null;
        
        if (value == null) {
            // FIX: Return safe default instead of crashing
            log.warn("Null value detected, returning safe default");
            return "NULL_VALUE";
        }
        
        return value.toString();  // Safe - value is never null here
    }

    @GetMapping("/arithmetic")
    public int arithmetic() {
        int divisor = 0;
        if (divisor == 0) {
            throw new IllegalArgumentException("Divisor cannot be zero");
        }
        return 10 / divisor;
    }

}