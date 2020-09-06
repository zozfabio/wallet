package me.zozfabio.wallet.user.rest

import me.zozfabio.wallet.user.domain.commands.*
import me.zozfabio.wallet.user.domain.entities.UserView
import me.zozfabio.wallet.user.domain.exceptions.EntityNotFoundException
import me.zozfabio.wallet.user.services.TransactionService
import me.zozfabio.wallet.user.services.UserService
import me.zozfabio.wallet.user.services.UserViewService
import org.springframework.web.bind.annotation.*
import java.time.Instant

@RestController
@RequestMapping("/user")
class UserRestController(val users: UserService,
                         val usersView : UserViewService,
                         val transactions : TransactionService) {

    @GetMapping
    fun findAll(): Iterable<UserView> =
        usersView.findAll()

    @PostMapping
    fun create(@RequestBody cmd: CreateUser) =
        users.apply(cmd.apply {
            occuredAt = Instant.now()
        })

    @GetMapping(path = ["/{userId}"])
    fun findOne(@PathVariable userId: String): UserView =
        usersView.findOne(userId)
            .orElseThrow { EntityNotFoundException("User not found!") }

    @PutMapping(path = ["/{userId}"])
    fun update(@PathVariable userId: String, @RequestBody cmd: UpdateUser) =
        users.apply(cmd.apply {
            id = userId
            occuredAt = Instant.now()
        })

    @PostMapping(path = ["/{userId}/contact"])
    fun addContact(@PathVariable userId: String, @RequestBody cmd: AddContact) =
        users.apply(cmd.apply {
            this.userId = userId
            this.occuredAt = Instant.now()
        })

    @PutMapping(path = ["/{userId}/contact/{contactId}"])
    fun updateContact(@PathVariable userId: String, @PathVariable contactId: String, @RequestBody cmd: UpdateContact) =
        users.apply(cmd.apply {
            this.userId = userId
            this.contactId = contactId
            this.occuredAt = Instant.now()
        })

    @DeleteMapping(path = ["/{userId}/contact/{contactId}"])
    fun removeContact(@PathVariable userId: String, @PathVariable contactId: String) =
        users.apply(RemoveContact(userId, contactId))

    @PostMapping(path = ["/{userId}/money/add"])
    fun addMoney(@PathVariable userId: String, @RequestBody cmd: AddMoney) =
        transactions.apply(cmd.apply {
            this.userId = userId
            this.occuredAt = Instant.now()
        })

    @PostMapping(path = ["/{userId}/money/send"])
    fun sendMoney(@PathVariable userId: String, @RequestBody cmd: SendMoney) =
        transactions.apply(cmd.apply {
            this.fromUserId = userId
            this.occuredAt = Instant.now()
        })

    @PostMapping(path = ["/{userId}/money/request"])
    fun requestMoney(@PathVariable userId: String, @RequestBody cmd: RequestMoney) =
        transactions.apply(cmd.apply {
            this.fromUserId = userId
            this.occuredAt = Instant.now()
        })
}
