package me.zozfabio.wallet.user.rest

import me.zozfabio.wallet.user.domain.commands.*
import me.zozfabio.wallet.user.domain.entities.UserView
import me.zozfabio.wallet.user.domain.services.UserService
import me.zozfabio.wallet.user.domain.services.UserViewService
import org.springframework.web.bind.annotation.*
import java.time.Instant

@RestController
@RequestMapping("/user")
class UserRestController(val service: UserService, val users : UserViewService) {

    @GetMapping
    fun findAll(): MutableIterable<UserView> =
        users.findAll()

    @PostMapping
    fun create(@RequestBody cmd: CreateUser) =
        service.apply(cmd.apply {
            occuredAt = Instant.now()
        })

    @PutMapping(path = ["/{userId}"])
    fun update(@PathVariable userId: String, @RequestBody cmd: UpdateUser) =
        service.apply(cmd.apply {
            id = userId
            occuredAt = Instant.now()
        })

    @PostMapping(path = ["/{userId}/contact"])
    fun addContact(@PathVariable userId: String, @RequestBody cmd: AddContact) =
        service.apply(cmd.apply {
            this.userId = userId
            this.occuredAt = Instant.now()
        })

    @PutMapping(path = ["/{userId}/contact/{contactId}"])
    fun updateContact(@PathVariable userId: String, @PathVariable contactId: String, @RequestBody cmd: UpdateContact) =
        service.apply(cmd.apply {
            this.userId = userId
            this.contactId = contactId
            this.occuredAt = Instant.now()
        })

    @DeleteMapping(path = ["/{userId}/contact/{contactId}"])
    fun removeContact(@PathVariable userId: String, @PathVariable contactId: String) =
        service.apply(RemoveContact(userId, contactId))
}
