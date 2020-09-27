package me.zozfabio.wallet

import me.zozfabio.wallet.user.domain.commands.AcceptPendingMoneyRequest
import me.zozfabio.wallet.user.domain.commands.AddMoney
import me.zozfabio.wallet.user.domain.commands.CreateUser
import me.zozfabio.wallet.user.domain.commands.RequestMoney
import me.zozfabio.wallet.user.domain.repositories.*
import me.zozfabio.wallet.user.domain.vo.TransactionAction.RECEIPT
import me.zozfabio.wallet.user.services.TransactionService
import me.zozfabio.wallet.user.services.UserService
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.math.BigDecimal
import java.time.Instant

@SpringBootApplication
class Application {
    @Bean
    fun sampleData(
        users: UserRepository,
        contacts: ContactRepository,

        usersView: UserViewRepository,

        usersBalance: UserBalanceRepository,
        transactions: TransactionRepository,

        usersService: UserService,
        transactionsService: TransactionService
    ) = ApplicationRunner {
        val now = Instant.now()

        users.deleteAll()
        contacts.deleteAll()

        usersView.deleteAll()

        usersBalance.deleteAll()
        transactions.deleteAll()

        usersService.apply(CreateUser("Teste 1", "teste1@gmail.com").apply {
            this.occuredAt = now
        })
        usersService.apply(CreateUser("Teste 2", "teste2@gmail.com").apply {
            this.occuredAt = now
        })

        val teste1 = users.findFirstByEmail("teste1@gmail.com")
            .orElseThrow { RuntimeException("User Teste 1 Not Created!") }
        val teste2 = users.findFirstByEmail("teste2@gmail.com")
            .orElseThrow { RuntimeException("User Teste 2 Not Created!") }

        transactionsService.apply(AddMoney(BigDecimal("2.00")).apply {
            this.userId = teste1.id
            this.occuredAt = now
        })
        transactionsService.apply(AddMoney(BigDecimal("2.00")).apply {
            this.userId = teste2.id
            this.occuredAt = now
        })
        transactionsService.apply(RequestMoney(teste2.id, BigDecimal("1.00")).apply {
            this.fromUserId = teste1.id
            this.occuredAt = now
        })

        val receipt = transactions.findFirstByActionEquals(RECEIPT)
            .orElseThrow { RuntimeException("Receipt Transaction Not Created!") }

        val acceptPendingMoneyRequest = AcceptPendingMoneyRequest(
            receipt.fromUserId,
            receipt.toUserId,
            receipt.id,
            receipt.value,
            Instant.now())

        transactionsService.apply(acceptPendingMoneyRequest)
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
