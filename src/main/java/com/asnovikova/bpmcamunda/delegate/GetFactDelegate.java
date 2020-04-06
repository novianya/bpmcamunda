package com.asnovikova.bpmcamunda.delegate;

import com.asnovikova.bpmcamunda.component.CatFactService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Anna Novikova
 */
@Component("getFactDelegate")
public class GetFactDelegate implements JavaDelegate {

    private final CatFactService catFactService;
    Logger logger  = LoggerFactory.getLogger(getClass().getName());

    public GetFactDelegate(CatFactService catFactService) {
        this.catFactService = catFactService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String email = delegateExecution.getVariable("email").toString();
        String fact = catFactService.getFact().getFact();

        logger.info("email {}, fact {}", email, fact);
        delegateExecution.setVariable("fact", fact);
    }
}
