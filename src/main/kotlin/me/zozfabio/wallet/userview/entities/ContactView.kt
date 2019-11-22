package me.zozfabio.wallet.userview.entities

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document

@Document
@TypeAlias("ContactView")
data class ContactView(@Id val id: ObjectId,
                       var name: String,
                       var email: String) {
}
