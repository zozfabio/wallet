package me.zozfabio.wallet.user.entities

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document

@Document
@TypeAlias("Contact")
data class Contact(@Id val id: ObjectId,
                   val userId: ObjectId,
                   val email: String,
                   var name: String,
                   var contactUserId: ObjectId? = null) {

    fun update(name: String) = apply {
        this.name = name
    }

    fun isUser(contactUserId: ObjectId) = apply {
        this.contactUserId = contactUserId
    }

    fun isUser(contactUser: User) = apply {
        this.contactUserId = contactUser.id
    }
}
