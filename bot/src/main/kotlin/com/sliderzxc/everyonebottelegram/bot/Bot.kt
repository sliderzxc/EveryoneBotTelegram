package com.sliderzxc.everyonebottelegram.bot

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.entities.ChatId
import com.sliderzxc.everyonebottelegram.data.MongoDbRepository
import com.sliderzxc.everyonebottelegram.data.User

class Bot(private val mongoDbRepository: MongoDbRepository) {
    fun bot(botApiKey: String): Bot {
        return bot {
            token = botApiKey
            dispatch {
                command("start") {
                    sendStartMessage(bot, message.chat.id)
                }

                command("in") {
                    mongoDbRepository.addUser(User(id = message.from?.id ?: 0), message.chat.title ?: "mafia")
                    bot.sendMessage(ChatId.fromId(message.chat.id), text = "Ви були додані до списку отримувачів.")
                }

                command("out") {
                    mongoDbRepository.deleteUser(User(id = message.from?.id ?: 0), message.chat.title ?: "mafia")
                    bot.sendMessage(ChatId.fromId(message.chat.id), text = "Вас видалено зі списку отримувачів.")
                }

                command("everyone") {
                    val users = mongoDbRepository.getAllUsers(message.chat.title ?: "mafia")
                    notifyEveryone(bot, message.chat.id, users)
                }
            }
        }
    }
}

private fun notifyEveryone(bot: Bot, chatId: Long, users: List<User>) {
    val notificationText = "Увага, всім! Це сповіщення для всіх користувачів."

    users.forEach { user ->
        bot.sendMessage(ChatId.fromId(user.id), text = notificationText)
    }

    bot.sendMessage(ChatId.fromId(chatId), text = "Сповіщення надіслані всім у списку отримувачів.")
}

private fun sendStartMessage(bot: Bot, chatId: Long) {
    val startMessage = """
        Привіт! Я бот, який може вас сповіщати.
        
        Доступні команди:
        /start - Показати це повідомлення
        /in - Додати себе до списку отримувачів
        /out - Видалити себе зі списку отримувачів
        /everyone - Відправити сповіщення усім в списку отримувачів
    """.trimIndent()

    bot.sendMessage(ChatId.fromId(chatId), text = startMessage)
}
