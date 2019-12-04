package me.zozfabio.wallet.user.domain.repository

import me.zozfabio.wallet.user.domain.entities.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, String>
