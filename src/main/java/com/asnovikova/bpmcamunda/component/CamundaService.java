package com.asnovikova.bpmcamunda.component;

import org.camunda.bpm.engine.RuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author Anna Novikova
 */
@Service
public class CamundaService {

    Logger logger = LoggerFactory.getLogger(getClass().getName());

    private final RuntimeService runtimeService;

    @Autowired
    public CamundaService(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    public boolean startProcess(String email) {
        logger.info("Start proccess for email = {}", email);
        int countActiveProcesses = runtimeService.createProcessInstanceQuery().active()
                .processInstanceBusinessKey(email).list().size();
        logger.info("count of active process =  {}", countActiveProcesses);
        if (countActiveProcesses == 0) {
            runtimeService.createMessageCorrelation("EmailWasGot")
                    .processInstanceBusinessKey(email).setVariable("email", email).correlateStartMessage();

            logger.info("process started");
            return true;
        } else {
            return false;
        }
    }
}
