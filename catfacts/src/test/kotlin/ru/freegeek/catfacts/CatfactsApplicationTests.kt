package ru.freegeek.catfacts

import org.camunda.bpm.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration
import org.camunda.bpm.engine.runtime.ProcessInstance
import org.camunda.bpm.engine.test.Deployment
import org.camunda.bpm.engine.test.ProcessEngineRule
import org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareAssertions
import org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests
import org.camunda.bpm.engine.test.mock.Mocks
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.test.context.ActiveProfiles
import ru.freegeek.catfacts.delegate.GenerateImageDelegate
import ru.freegeek.catfacts.delegate.GetCatFactDelegate
import ru.freegeek.catfacts.delegate.GetRandomImageDelegate
import ru.freegeek.catfacts.delegate.SendImageDelegate

@RunWith(MockitoJUnitRunner::class)
class CatfactsApplicationTests {

    @get:Rule
    var processEngineRule = ProcessEngineRule(ProcessTestConfig.processEngine)

	val BUSINESS_KEY = "TEST"
	val PRODUCTION_PROCESS_KEY = "SendCatFactProcess"

	@Before
	fun setup() {
		MockitoAnnotations.initMocks(this)
		Mocks.register("getCatFact", GetCatFactDelegate() )
		Mocks.register("getRandomImage", GetRandomImageDelegate() )
		Mocks.register("generateImage", GenerateImageDelegate() )
//		Mocks.register("sendImage", SendImageDelegate())
	}

	@After
	fun teardown() {
		Mocks.reset()
	}

	@Test
	@Deployment(resources = ["BPMN/2307process.bpmn"])
	fun happyPath() {
//		val processInstance  = startProcess()


	}

	private fun startProcess(): ProcessInstance {
		val pi = processEngineRule
				.runtimeService
				.startProcessInstanceByKey(PRODUCTION_PROCESS_KEY, BUSINESS_KEY,
						BpmnAwareTests.withVariables(
								"inputEmail", "dd@ptnl.moscow"
						))
		BpmnAwareAssertions.assertThat(pi).isNotNull()
		BpmnAwareAssertions.assertThat(pi).isStarted
		return  pi
	}
}

class ProcessTestConfig {
    companion object {
        val processEngine = StandaloneInMemProcessEngineConfiguration
                .createStandaloneInMemProcessEngineConfiguration()
                .buildProcessEngine()
    }
}
