package ru.kubsu.telegrambot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {
    @GetMapping(value = "/{name}", produces = "application/json")
    public String hello(@PathVariable String name){
        return "Hello, " + name + "!\r\n";
    }
}
