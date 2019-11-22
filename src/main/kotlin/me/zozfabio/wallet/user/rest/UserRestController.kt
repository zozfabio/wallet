package me.zozfabio.wallet.user.rest

import me.zozfabio.wallet.user.commands.*
import me.zozfabio.wallet.user.service.UserService
import org.bson.types.ObjectId
import org.springframework.web.bind.annotation.*
import java.time.Instant

@RestController
@RequestMapping("/user")
class UserRestController(val service: UserService) {

    @PostMapping
    fun create(@RequestBody cmd: CreateUser) =
        service.apply(cmd.apply {
            occuredAt = Instant.now()
        })

    @PutMapping(path = ["/{userId}"])
    fun update(@PathVariable userId: ObjectId, @RequestBody cmd: UpdateUser) =
        service.apply(cmd.apply {
            id = userId
            occuredAt = Instant.now()
        })

    @PostMapping(path = ["/{userId}/contact"])
    fun addContact(@PathVariable userId: ObjectId, @RequestBody cmd: AddContact) =
        service.apply(cmd.apply {
            this.userId = userId
            this.occuredAt = Instant.now()
        })

    @PutMapping(path = ["/{userId}/contact/{contactId}"])
    fun updateContact(@PathVariable userId: ObjectId, @PathVariable contactId: ObjectId, @RequestBody cmd: UpdateContact) =
        service.apply(cmd.apply {
            this.userId = userId
            this.contactId = contactId
            this.occuredAt = Instant.now()
        })

    @DeleteMapping(path = ["/{userId}/contact/{contactId}"])
    fun removeContact(@PathVariable userId: ObjectId, @PathVariable contactId: ObjectId) =
        service.apply(RemoveContact(userId, contactId))
}
