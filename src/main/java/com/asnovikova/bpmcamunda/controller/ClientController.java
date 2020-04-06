package com.asnovikova.bpmcamunda.controller;

import com.asnovikova.bpmcamunda.component.CamundaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Anna Novikova
 */
@RestController
@RequestMapping("/api")
public class ClientController {

    private final CamundaService camundaService;

    public ClientController(CamundaService camundaService) {
        this.camundaService = camundaService;
    }

    @GetMapping("/email/{email}")
    public String getEmail(@PathVariable String email) {
        return camundaService.startProcess(email) ? "ok" : "false";
    }
}
