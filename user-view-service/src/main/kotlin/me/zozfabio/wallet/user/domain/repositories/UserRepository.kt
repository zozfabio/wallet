package me.zozfabio.wallet.user.domain.repositories

import me.zozfabio.wallet.user.domain.entities.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, String>
