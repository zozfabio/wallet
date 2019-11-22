package me.zozfabio.wallet.userview.service

import me.zozfabio.wallet.user.events.UserEvent
import me.zozfabio.wallet.userview.entities.UserView
import me.zozfabio.wallet.userview.repository.UserViewRepository
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class UserViewService(val usersView: UserViewRepository) {

    fun search() =
        usersView.findAll()

    @EventListener
    fun handle(e: UserEvent) {
        usersView.findById(e.userId)
            .defaultIfEmpty(UserView(e.userId))
            .map { it.handle(e) }
            .flatMap { usersView.save(it) }
            .subscribe()
    }
}
