package me.zozfabio.wallet.user.domain.repositories

import me.zozfabio.wallet.user.domain.entities.UserBalance
import org.springframework.data.repository.CrudRepository

interface UserBalanceRepository : CrudRepository<UserBalance, String>
