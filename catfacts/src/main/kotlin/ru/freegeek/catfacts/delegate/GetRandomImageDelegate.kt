package ru.freegeek.catfacts.delegate

import org.apache.http.client.HttpClient
import org.apache.http.impl.client.HttpClientBuilder
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.net.URI
import java.net.URL

@Component("getRandomImage")
public class GetRandomImageDelegate: JavaDelegate {

    @Value("\${picsum.photos.service.url}")
    private val picSumUrl: String? = null

    override fun execute(execution: DelegateExecution) {

        /**
         * Нужно убирать редирект, т.к. это единственны способ получить путь к изображению (location)
         */
        val httpClient: HttpClient = HttpClientBuilder.create().disableRedirectHandling().build()
        val rest = RestTemplate(HttpComponentsClientHttpRequestFactory(httpClient))

        val responseEntity = rest.exchange(picSumUrl.toString(), HttpMethod.GET, null, String::class.java)
        val headersOut: HttpHeaders = responseEntity.headers

        execution.setVariable("randomImageUrl", headersOut.location)
    }

}
