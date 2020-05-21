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
import ru.freegeek.catfacts.service.EmailService
import java.net.URL

@Component("sendImage")
class SendImageDelegate(val emailService: EmailService): JavaDelegate {

    override fun execute(execution: DelegateExecution) {

        val inputEmail: String = execution.getVariable("inputEmail").toString()
        val imageUrl: String = execution.getVariable("imageUrl").toString()
        val catfact: String = execution.getVariable("catfact").toString()

        emailService.sendImageWithAttachment(inputEmail, "2307cource", catfact, imageUrl)
    }

}
