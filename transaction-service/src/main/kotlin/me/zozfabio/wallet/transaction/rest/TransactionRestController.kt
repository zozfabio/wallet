package me.zozfabio.wallet.transaction.rest

import me.zozfabio.wallet.transaction.domain.commands.PayContact
import me.zozfabio.wallet.transaction.service.TransactionService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/transaction")
class TransactionRestController(val service: TransactionService) {

    @PostMapping
    fun pay(@RequestBody payContact: PayContact) {
        service.apply(payContact)
    }
}
