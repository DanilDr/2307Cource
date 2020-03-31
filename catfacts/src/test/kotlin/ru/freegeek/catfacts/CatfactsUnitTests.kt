package ru.freegeek.catfacts

import org.apache.http.client.HttpClient
import org.apache.http.impl.client.HttpClientBuilder
import org.camunda.bpm.engine.test.mock.Mocks
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.client.RestTemplate
import ru.freegeek.catfacts.delegate.GenerateImageDelegate
import ru.freegeek.catfacts.delegate.GetRandomImageDelegate
import ru.freegeek.catfacts.delegate.SendImageDelegate
import java.net.URI


@RunWith(MockitoJUnitRunner::class)
@ActiveProfiles("test")
class CatfactsUnitTests {

    var headers = HttpHeaders().also {}

    private fun createHTTPEntity(json: String?): HttpEntity<String> {
        return HttpEntity(json, headers)
    }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Mocks.register("getCatFact", ru.freegeek.catfacts.delegate.GetCatFactDelegate())
        Mocks.register("getRandomImage", GetRandomImageDelegate() )
        Mocks.register("generateImage", GenerateImageDelegate() )
//        Mocks.register("sendImage", SendImageDelegate())
    }

    @After
    fun teardown() {
        Mocks.reset()
    }

    @Test
    fun testGetRamdomImageDelegate() {

    }

    val serviceURL: String = "https://picsum.photos/800/600.jpg"

    @Test
    fun testAvailableImageService() {
        val httpClient: HttpClient = HttpClientBuilder.create().disableRedirectHandling().build()
        val rest = RestTemplate(HttpComponentsClientHttpRequestFactory(httpClient))


        val responseEntity = rest.exchange(serviceURL, HttpMethod.GET, createHTTPEntity(null), String::class.java)
        val headersOut: HttpHeaders = responseEntity.headers
        val location: URI? = headersOut.location


/*
        val result = rest
                .exchange(
                        serviceURL,
                        HttpMethod.GET, createHTTPEntity(null),
                        String::class.java)
*/

        println(location.toString())
    }

}


