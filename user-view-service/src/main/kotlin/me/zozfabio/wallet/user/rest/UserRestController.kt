package me.zozfabio.wallet.user.rest

import me.zozfabio.wallet.user.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserRestController(val users: UserService) {

    @GetMapping
    fun search() =
        users.search()
}
