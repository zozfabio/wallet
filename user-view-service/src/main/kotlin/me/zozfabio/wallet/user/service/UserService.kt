package me.zozfabio.wallet.user.service

import me.zozfabio.wallet.user.domain.entities.User
import me.zozfabio.wallet.user.domain.events.UserEvent
import me.zozfabio.wallet.user.domain.repository.UserRepository
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.messaging.Sink
import org.springframework.stereotype.Service

@Service
class UserService(val users: UserRepository) {

    fun search() =
        users.findAll()

    @StreamListener(Sink.INPUT)
    fun handle(e: UserEvent) {
        users.save(users.findById(e.userId)
            .orElseGet { User(e.userId) }
            .handle(e))
    }
}
