package me.zozfabio.wallet.user.domain.repositories

import me.zozfabio.wallet.user.domain.entities.UserView
import org.springframework.data.repository.CrudRepository

interface UserViewRepository : CrudRepository<UserView, String> {
}
