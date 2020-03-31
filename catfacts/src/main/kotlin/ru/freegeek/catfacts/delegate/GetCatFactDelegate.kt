package ru.freegeek.catfacts.delegate

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import net.minidev.json.JSONObject
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import ru.freegeek.catfacts.model.CatFact
import java.net.URL

@Component("getCatFact")
public class GetCatFactDelegate: JavaDelegate {

    @Value("\${catfact.ninja.service.url}")
    private val catfactUrl: String? = null

    val mapper = ObjectMapper().registerModule(KotlinModule())

    override fun execute(execution: DelegateExecution) {

        // Получение факта о кошке
        val factInput = URL(catfactUrl).readText()

        // Сохранение факта о кошке в контекст
        val catFact: CatFact = mapper.readValue(factInput)
        execution.setVariable("catfact", catFact.fact)

    }

}
