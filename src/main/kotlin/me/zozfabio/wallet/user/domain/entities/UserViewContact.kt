package me.zozfabio.wallet.user.domain.entities

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document

@Document("UserViewContact")
@TypeAlias("UserViewContact")
data class UserViewContact(@Id val id: String,
                           val email: String,
                           var name: String) {

    fun update(name: String) = apply {
        this.name = name
    }
}
