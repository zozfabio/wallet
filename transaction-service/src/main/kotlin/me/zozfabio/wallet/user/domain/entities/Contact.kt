package me.zozfabio.wallet.user.domain.entities

import org.springframework.data.annotation.Id

data class Contact(@Id val id: String,
                   var name: String,
                   var email: String)
