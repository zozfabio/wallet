package me.zozfabio.wallet.user.domain.repositories

import me.zozfabio.wallet.user.domain.entities.Transaction
import me.zozfabio.wallet.user.domain.vo.TransactionAction
import org.springframework.data.repository.CrudRepository
import java.util.*

interface TransactionRepository : CrudRepository<Transaction, String> {

    fun findFirstByActionEquals(action: TransactionAction): Optional<Transaction>
}
