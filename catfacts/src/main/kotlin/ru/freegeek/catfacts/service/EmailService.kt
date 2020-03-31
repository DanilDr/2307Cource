package ru.freegeek.catfacts.service

interface EmailService {

    /**
     * Отправка письма с приложением
     */
    fun sendImageWithAttachment(email: String, theme: String, imageUrl: String)
}
