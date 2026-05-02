package com.project.logging.controller;

import com.project.logging.service.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/log")
public class LogController {

    @Autowired
    private LoggingService loggingService;

    @PostMapping
    public String createLog(@RequestParam String stack,
                           @RequestParam String level,
                           @RequestParam String pkg,
                           @RequestParam String message) {

        return loggingService.log(stack, level, pkg, message);
    }

    @GetMapping("/test")
    public String test() {

        loggingService.log("backend", "info", "controller", "Test API called");

        try {
            int x = 10 / 0;
        } catch (Exception e) {
            loggingService.log("backend", "error", "handler", "Division by zero");
        }

        return "Test logs sent!";
    }
}