package me.zozfabio.wallet.user.rest

import me.zozfabio.wallet.user.domain.entities.UserView
import me.zozfabio.wallet.user.domain.entities.UserViewContact
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.RepresentationModel

class UserContactModel(val name: String,
                       val email: String)
    : RepresentationModel<UserContactModel>() {

    companion object Factory {
        fun of(user: UserView, contact: UserViewContact): UserContactModel {
            return UserContactModel(contact.name, contact.email)
                .add(Link.of("/user/{userId}/contact/{contactId}")
                    .withRel("update")
                    .expand(user.id, contact.id))
                .add(Link.of("/user/{userId}/contact/{contactId}")
                    .withRel("remove")
                    .expand(user.id, contact.id))
        }

        fun of(user: UserView, contacts: Iterable<UserViewContact>): CollectionModel<UserContactModel> {
            return CollectionModel.of(contacts.map { of(user, it) })
        }
    }
}
