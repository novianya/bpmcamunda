package com.asnovikova.bpmcamunda;

import com.asnovikova.bpmcamunda.component.CamundaService;
import com.asnovikova.bpmcamunda.component.CatFactService;
import com.asnovikova.bpmcamunda.component.EmailSender;
import com.asnovikova.bpmcamunda.component.PictureService;
import com.asnovikova.bpmcamunda.delegate.GetFactDelegate;
import com.asnovikova.bpmcamunda.delegate.GetPictureDelegate;
import com.asnovikova.bpmcamunda.delegate.SendEmailDelegate;
import com.asnovikova.bpmcamunda.model.CatFact;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.spring.boot.starter.test.helper.StandaloneInMemoryTestConfiguration;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


/**
 * @author Anna Novikova
 */
@RunWith(SpringRunner.class)
@Deployment(resources = "BPMN/GetPicture.bpmn")
public class BpmCamundaTests {

    @Rule
    public final ProcessEngineRule camunda = new StandaloneInMemoryTestConfiguration().rule();

    @Mock
    private EmailSender emailSender;

    @Mock
    private CatFactService catFactService;

    @Mock
    private PictureService pictureService;

    private CamundaService camundaService;

    @Before
    public void setup() throws IOException {

        when(catFactService.getFact()).thenReturn(new CatFact("fact", 4));
        when(pictureService.getPicture(any())).thenReturn("picfile");
        Mocks.register("getFactDelegate", new GetFactDelegate(catFactService));
        Mocks.register("getPictureDelegate", new GetPictureDelegate(pictureService));
        Mocks.register("sendEmailDelegate", new SendEmailDelegate(emailSender));
        camundaService = new CamundaService( camunda.getRuntimeService());
    }

    @Test

    public void testHappyPath() {
        String email = "anchuta17@gmail.com";
        ProcessInstance pi = startProcess("email", email);
        assertThat(pi).isEnded().variables()
                .hasSize(3)
                .containsEntry("email", "anchuta17@gmail.com")
                .containsEntry("fact", "fact")
                .containsEntry("pictureFile", "picfile");
    }

    private ProcessInstance startProcess(String key, Object value, Object... furtherKeyValuePairs) {

        return camunda.getRuntimeService().startProcessInstanceByKey("GetPicture", withVariables(key, value, furtherKeyValuePairs));
    }

}
