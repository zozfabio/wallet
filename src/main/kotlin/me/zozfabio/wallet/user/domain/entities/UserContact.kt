package me.zozfabio.wallet.user.domain.entities

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document

@Document("Contact")
@TypeAlias("Contact")
data class UserContact(@Id val id: String,
                       val userId: String,
                       val email: String,
                       var name: String,
                       var contactUserId: String? = null) {

    fun update(name: String) = apply {
        this.name = name
    }

    fun isUser(contactUserId: String) = apply {
        this.contactUserId = contactUserId
    }

    fun isUser(contactUser: User) = apply {
        this.contactUserId = contactUser.id
    }
}
