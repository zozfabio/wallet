package me.zozfabio.wallet.user.domain.repositories

import me.zozfabio.wallet.user.domain.entities.Transaction
import org.springframework.data.repository.CrudRepository

interface TransactionRepository : CrudRepository<Transaction, String>
