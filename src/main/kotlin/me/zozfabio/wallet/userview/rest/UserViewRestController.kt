package me.zozfabio.wallet.userview.rest

import me.zozfabio.wallet.userview.service.UserViewService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserViewRestController(val usersView: UserViewService) {

    @GetMapping
    fun search() =
        usersView.search()
}
