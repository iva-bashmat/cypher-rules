package com.example.rules.controller;

import com.example.rules.service.PortService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/ports")
@AllArgsConstructor
public class PortsController {

    private final PortService portService;

    @GetMapping
    public List<String> getPorts() {
        return portService.getPorts();
    }
}
