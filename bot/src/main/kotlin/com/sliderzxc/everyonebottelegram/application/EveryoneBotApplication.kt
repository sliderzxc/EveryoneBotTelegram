package com.sliderzxc.everyonebottelegram.application

import com.sliderzxc.everyonebottelegram.bot.Bot
import com.sliderzxc.everyonebottelegram.data.Config
import com.sliderzxc.everyonebottelegram.di.AppModule
import com.sliderzxc.everyonebottelegram.utils.Constants.BOT_API_KEY
import com.sliderzxc.everyonebottelegram.utils.Constants.MONGO_CONNECTION
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun main() {
    val botApiKey = System.getenv(BOT_API_KEY) ?: error("Provide $BOT_API_KEY")
    val mongoConnection = System.getenv(MONGO_CONNECTION) ?: error("Provide $MONGO_CONNECTION")

    val dynamicModule = module {
        single {
            Config(
                mongoConnectionString = mongoConnection,
                botApiKey = botApiKey
            )
        }
    }

    val koin = startKoin {
        modules(AppModule, dynamicModule)
    }.koin

    val bot = Bot(koin.get()).bot(botApiKey)

    bot.startPolling()
}