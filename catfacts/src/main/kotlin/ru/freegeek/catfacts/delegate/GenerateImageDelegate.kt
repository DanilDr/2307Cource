package ru.freegeek.catfacts.delegate

import com.amazonaws.util.Base16;
import org.apache.commons.codec.binary.Base64
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


@Component("generateImage")
public class GenerateImageDelegate: JavaDelegate {

    @Value("\${imgproxy.service.url}")
    private val imgproxyUrl: String? = null

    @Value("\${imgproxy.key}")
    private val imgproxyKey: String? = null

    @Value("\${imgproxy.salt}")
    private val imgproxySalt: String? = null

    override fun execute(execution: DelegateExecution) {

        val randomImageUrl: String = execution.getVariable("randomImageUrl").toString()
        val catfact: String = execution.getVariable("catfact").toString()
        val inputEmail: String = execution.getVariable("inputEmail").toString()

        val generatedUrl: String? = randomImageUrl

        execution.setVariable("imageUrl", generatedUrl)
    }

    @Throws(Exception::class)
    fun generateSignedUrlImgProxy(key: String?, salt: String?, url: String, resize: String, width: Int, height: Int, gravity: String, enlarge: Int, extension: String): String? {
        val HMACSHA256 = "HmacSHA256"
        val keybin: ByteArray = Base16.decode(key)
        val saltBin: ByteArray = Base16.decode(salt)
        val encodedUrl: String = Base64.encodeBase64URLSafeString(url.toByteArray())
        val path = "/$resize/$width/$height/$gravity/$enlarge/$encodedUrl.$extension"
        val sha256_HMAC: Mac = Mac.getInstance(HMACSHA256)
        val secret_key = SecretKeySpec(keybin, HMACSHA256)
        sha256_HMAC.init(secret_key)
        sha256_HMAC.update(saltBin)
        val hash: String = Base64.encodeBase64URLSafeString(sha256_HMAC.doFinal(path.toByteArray()))
        return "/$hash$path"
    }
}
