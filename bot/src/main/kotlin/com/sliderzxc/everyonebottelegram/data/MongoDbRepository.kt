package com.sliderzxc.everyonebottelegram.data

import com.mongodb.client.MongoClient
import org.bson.Document

class MongoDbRepository(private val client: MongoClient) {

    fun addUser(user: User, groupTitle: String) {
        client.getDatabase(BOT_DATABASE).getCollection(groupTitle).insertOne(userToDocument(user))
    }

    fun deleteUser(user: User, groupTitle: String) {
        client.getDatabase(BOT_DATABASE).getCollection(groupTitle).deleteOne(Document("user", user.id))
    }

    fun getAllUsers(groupTitle: String): List<User> {
        val users = mutableListOf<User>()
        val cursor = client.getDatabase(BOT_DATABASE).getCollection(groupTitle).find()

        for (document in cursor) {
            users.add(documentToUser(document))
        }

        return users
    }

    private fun userToDocument(user: User): Document {
        return Document("user", user.id)
    }

    private fun documentToUser(document: Document): User {
        return User(document.getLong("user"))
    }

    companion object {
        const val BOT_DATABASE = "everyone_bot_database"
    }
}
