package me.zozfabio.wallet.user.rest

import me.zozfabio.wallet.user.domain.entities.UserView
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.RepresentationModel
import java.math.BigDecimal

class UserModel(val name: String,
                val email: String,
                val balance: BigDecimal,
                val contacts: CollectionModel<UserContactModel>,
                val pendingMoneyRequests: CollectionModel<UserPendingMoneyRequestModel>)
    : RepresentationModel<UserModel>() {

    companion object Factory {
        fun of(user: UserView): UserModel {
            return UserModel(user.name, user.email, user.balance,
                UserContactModel.of(user, user.contacts),
                UserPendingMoneyRequestModel.of(user.pendingMoneyRequests)
            )
                .add(Link.of("/user/{userId}")
                    .withSelfRel()
                    .expand(user.id))
                .add(Link.of("/user/{userId}")
                    .withRel("update")
                    .expand(user.id))
                .add(Link.of("/user/{userId}/contact")
                    .withRel("addContact")
                    .expand(user.id))
                .add(Link.of("/user/{userId}/money/add")
                    .withRel("addMoney")
                    .expand(user.id))
                .add(Link.of("/user/{userId}/money/send")
                    .withRel("sendMoney")
                    .expand(user.id))
                .add(Link.of("/user/{userId}/money/request")
                    .withRel("requestMoney")
                    .expand(user.id))
        }

        fun of(users: Iterable<UserView>): CollectionModel<UserModel> {
            return CollectionModel.of(users.map { of(it) })
                .add(Link.of("/user")
                    .withSelfRel())
        }
    }
}
