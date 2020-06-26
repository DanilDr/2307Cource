package ru.freegeek.catfacts.service

import com.sun.mail.smtp.SMTPTransport
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.net.URL
import java.util.*
import javax.activation.DataHandler
import javax.activation.DataSource
import javax.activation.FileDataSource
import javax.activation.URLDataSource
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart


@Component
class EmailServiceImpl: EmailService {

    @Value("\${email.server}")
    private val EMAIL_SERVER: String? = null

    @Value("\${email.login}")
    private val EMAIL_LOGIN: String? = null

    @Value("\${email.password}")
    private val EMAIL_PASSWORD: String? = null

    @Value("\${email.port}")
    private val EMAIL_PORT: Int? = null

    override fun sendImageWithAttachment(email: String, theme: String, catfact: String, imageUrl: String) {

        val prop: Properties = System.getProperties()

        prop.put("mail.smtp.host", EMAIL_SERVER)
        prop.put("mail.smtp.auth", "true")
        prop.put("mail.smtp.port", EMAIL_PORT)
        prop.put("mail.smtp.starttls.enable", "true")

        val session: Session = Session.getInstance(prop, null)

        val msg: Message = MimeMessage(session)

        try {
            msg.setFrom(InternetAddress(EMAIL_LOGIN));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email, false))
            msg.subject = theme

            var messageBodyPart: BodyPart = MimeBodyPart()
            messageBodyPart.setText(theme + catfact)

            val multipart: Multipart = MimeMultipart()
            multipart.addBodyPart(messageBodyPart)

            messageBodyPart = MimeBodyPart()
            val filename = "image.png"
            val source: DataSource = URLDataSource(URL(imageUrl))
            messageBodyPart.setDataHandler(DataHandler(source))
            messageBodyPart.setFileName(filename)
            messageBodyPart.setHeader("Content-ID", "image_id")
            multipart.addBodyPart(messageBodyPart)

            messageBodyPart = MimeBodyPart()
            messageBodyPart.setContent("<h1>Attached Image</h1>" +
                    "<img src='cid:image_id'>", "text/html")
            multipart.addBodyPart(messageBodyPart)

            msg.setContent(multipart)

            val t: SMTPTransport = session.getTransport("smtp") as SMTPTransport

            t.connect(EMAIL_SERVER, EMAIL_LOGIN, EMAIL_PASSWORD)

            t.sendMessage(msg, msg.allRecipients)

            t.close()
        } catch (e: MessagingException) {
            e.printStackTrace()
        }

    }
}
