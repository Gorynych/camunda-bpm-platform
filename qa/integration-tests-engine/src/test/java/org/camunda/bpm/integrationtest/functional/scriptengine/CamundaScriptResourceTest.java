/*
 * Copyright © 2013-2018 camunda services GmbH and various authors (info@camunda.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.integrationtest.functional.scriptengine;

import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.integrationtest.util.AbstractFoxPlatformIntegrationTest;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * @author Daniel Meyer
 *
 */
@RunWith(Arquillian.class)
public class CamundaScriptResourceTest extends AbstractFoxPlatformIntegrationTest {

  @Deployment
  public static WebArchive processArchive() {

    return initWebArchiveDeployment()
      .addAsResource("org/camunda/bpm/integrationtest/functional/scriptengine/CamundaScriptResourceTest.examplescript.js", "CamundaScriptResourceTest.examplescript.js")
      .addAsResource("org/camunda/bpm/integrationtest/functional/scriptengine/CamundaScriptResourceTest.exampleprocess.bpmn", "CamundaScriptResourceTest.exampleprocess.bpmn");
  }

  @Test
  public void testDeployProcessArchive() {

    // the process can successfully be executed
    ProcessInstance pi = runtimeService.startProcessInstanceByKey("testProcess");

    HistoricVariableInstance variable = historyService.createHistoricVariableInstanceQuery()
      .processInstanceId(pi.getId())
      .singleResult();

    assertNotNull(variable);
    assertEquals("executed", variable.getName());
    assertEquals(true, variable.getValue());
  }

}
