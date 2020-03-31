package me.zozfabio.wallet.transaction.domain.repositories

import me.zozfabio.wallet.transaction.domain.entities.Transaction
import org.springframework.data.repository.CrudRepository

interface TransactionRepository : CrudRepository<Transaction, String>
