package me.zozfabio.wallet.user.domain.entities

data class UserViewContact(val id: String,
                           val email: String,
                           var name: String) {

    fun update(name: String) = apply {
        this.name = name
    }
}
