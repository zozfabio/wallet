package me.zozfabio.wallet.user.rest

import me.zozfabio.wallet.user.domain.commands.AcceptPendingMoneyRequest
import me.zozfabio.wallet.user.domain.exceptions.EntityNotFoundException
import me.zozfabio.wallet.user.domain.repositories.TransactionRepository
import me.zozfabio.wallet.user.domain.repositories.UserBalanceRepository
import me.zozfabio.wallet.user.services.TransactionService
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.noContent
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
@RequestMapping("/transaction")
class TransactionRestController(val transactions: TransactionRepository,
                                val users: UserBalanceRepository,
                                val service: TransactionService) {

    @PostMapping(path = ["/{transactionId}/acceptpendingrequest"])
    fun acceptPendingMoneyRequest(@PathVariable transactionId: String): ResponseEntity<Unit> {
        val transaction = transactions.findById(transactionId)
            .orElseThrow { EntityNotFoundException("Transaction not found!") }

        val cmd = AcceptPendingMoneyRequest(
            transaction.fromUserId,
            transaction.toUserId,
            transactionId,
            transaction.value,
            Instant.now())

        service.apply(cmd)

        return noContent()
            .build<Unit>()
    }
}
