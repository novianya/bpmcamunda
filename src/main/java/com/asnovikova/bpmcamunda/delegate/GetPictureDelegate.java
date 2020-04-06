package com.asnovikova.bpmcamunda.delegate;

import com.asnovikova.bpmcamunda.component.PictureService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Anna Novikova
 */
@Component("getPictureDelegate")
public class GetPictureDelegate  implements JavaDelegate {

    private final PictureService pictureService;
    @Autowired
    public GetPictureDelegate(PictureService pictureService) {
        this.pictureService = pictureService;

    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String fact = delegateExecution.getVariable("fact").toString();
        String pictureFile = pictureService.getPicture(fact);
        delegateExecution.setVariable("pictureFile", pictureFile);

    }
}