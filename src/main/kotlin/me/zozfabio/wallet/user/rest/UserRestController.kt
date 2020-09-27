package me.zozfabio.wallet.user.rest

import me.zozfabio.wallet.user.domain.commands.*
import me.zozfabio.wallet.user.domain.exceptions.EntityNotFoundException
import me.zozfabio.wallet.user.services.TransactionService
import me.zozfabio.wallet.user.services.UserService
import me.zozfabio.wallet.user.services.UserViewService
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.*
import org.springframework.web.bind.annotation.*
import java.time.Instant

@RestController
@RequestMapping("/user")
class UserRestController(val users: UserService,
                         val usersView: UserViewService,
                         val transactions: TransactionService) {

    @GetMapping
    fun findAll(): ResponseEntity<CollectionModel<UserModel>> {
        val users = UserModel.of(usersView.findAll())

        return ok(users)
    }

    @PostMapping
    fun create(@RequestBody cmd: CreateUser): ResponseEntity<Unit> {
        cmd.occuredAt = Instant.now()

        val user = users.apply(cmd)

        return created(linkTo(methodOn(UserRestController::class.java).findOne(user.id)).toUri())
            .build<Unit>()
    }

    @GetMapping(path = ["/{userId}"])
    fun findOne(@PathVariable userId: String): ResponseEntity<UserModel> {
        val user = usersView.findOne(userId)
            .map { UserModel.of(it) }
            .orElseThrow { EntityNotFoundException("User not found!") }

        return ok(user)
    }

    @PutMapping(path = ["/{userId}"])
    fun update(@PathVariable userId: String,
               @RequestBody cmd: UpdateUser): ResponseEntity<Unit> {
        cmd.id = userId
        cmd.occuredAt = Instant.now()

        users.apply(cmd)

        return noContent()
            .build<Unit>()
    }

    @PostMapping(path = ["/{userId}/contact"])
    fun addContact(@PathVariable userId: String,
                   @RequestBody cmd: AddContact): ResponseEntity<Unit> {
        cmd.userId = userId
        cmd.occuredAt = Instant.now()

        users.apply(cmd)

        return noContent()
            .build<Unit>()
    }

    @PutMapping(path = ["/{userId}/contact/{contactId}"])
    fun updateContact(@PathVariable userId: String,
                      @PathVariable contactId: String,
                      @RequestBody cmd: UpdateContact): ResponseEntity<Unit> {
        cmd.userId = userId
        cmd.contactId = contactId
        cmd.occuredAt = Instant.now()

        users.apply(cmd)

        return noContent()
            .build<Unit>()
    }

    @DeleteMapping(path = ["/{userId}/contact/{contactId}"])
    fun removeContact(@PathVariable userId: String,
                      @PathVariable contactId: String): ResponseEntity<Unit> {
        users.apply(RemoveContact(userId, contactId))

        return noContent()
            .build<Unit>()
    }

    @PostMapping(path = ["/{userId}/money/add"])
    fun addMoney(@PathVariable userId: String,
                 @RequestBody cmd: AddMoney): ResponseEntity<Unit> {
        cmd.userId = userId
        cmd.occuredAt = Instant.now()

        transactions.apply(cmd)

        return noContent()
            .build<Unit>()
    }

    @PostMapping(path = ["/{userId}/money/send"])
    fun sendMoney(@PathVariable userId: String,
                  @RequestBody cmd: SendMoney): ResponseEntity<Unit> {
        cmd.fromUserId = userId
        cmd.occuredAt = Instant.now()

        transactions.apply(cmd)

        return noContent()
            .build<Unit>()
    }

    @PostMapping(path = ["/{userId}/money/request"])
    fun requestMoney(@PathVariable userId: String,
                     @RequestBody cmd: RequestMoney): ResponseEntity<Unit> {
        cmd.fromUserId = userId
        cmd.occuredAt = Instant.now()

        transactions.apply(cmd)

        return noContent()
            .build<Unit>()
    }
}
