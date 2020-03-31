package me.zozfabio.wallet.user.service

import me.zozfabio.wallet.USER_EVENTS
import me.zozfabio.wallet.user.domain.entities.User
import me.zozfabio.wallet.user.domain.events.UserEvent
import me.zozfabio.wallet.user.domain.repositories.UserRepository
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.stereotype.Service

@Service
class UserService(val users: UserRepository) {

    @StreamListener(USER_EVENTS)
    fun handle(e: UserEvent) {
        users.save(users.findById(e.userId)
            .orElseGet { User(e.userId) }
            .handle(e))
    }
}
