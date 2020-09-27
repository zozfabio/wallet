package me.zozfabio.wallet.user.rest

import me.zozfabio.wallet.user.domain.entities.UserViewPendingMoneyRequest
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.RepresentationModel
import java.math.BigDecimal

class UserPendingMoneyRequestModel(val transactionId: String,
                                   val userName: String,
                                   val userMail: String,
                                   val value: BigDecimal)
    : RepresentationModel<UserPendingMoneyRequestModel>() {

    companion object Factory {
        fun of(pendingMoneyRequest: UserViewPendingMoneyRequest): UserPendingMoneyRequestModel {
            return UserPendingMoneyRequestModel(
                pendingMoneyRequest.transactionId,
                pendingMoneyRequest.fromUserName,
                pendingMoneyRequest.fromUserEmail,
                pendingMoneyRequest.value
            )
                .add(Link.of("/transaction/{transactionId}/acceptpendingrequest")
                    .withRel("acceptPendingRequest")
                    .expand(pendingMoneyRequest.transactionId))
        }

        fun of(pendingMoneyRequests: Iterable<UserViewPendingMoneyRequest>): CollectionModel<UserPendingMoneyRequestModel> {
            return CollectionModel.of(pendingMoneyRequests.map { of(it) })
        }
    }
}
