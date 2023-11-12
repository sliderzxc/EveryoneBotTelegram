package com.sliderzxc.everyonebottelegram.di

import com.mongodb.client.MongoClient
import com.sliderzxc.everyonebottelegram.data.Config
import com.sliderzxc.everyonebottelegram.data.MongoDbRepository
import org.koin.dsl.module
import org.litote.kmongo.KMongo

val AppModule = module {
    single<MongoClient> {
        KMongo.createClient(get<Config>().mongoConnectionString)
    }
    single {
        MongoDbRepository(get())
    }
}