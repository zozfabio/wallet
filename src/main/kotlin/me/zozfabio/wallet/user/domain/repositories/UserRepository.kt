package me.zozfabio.wallet.user.domain.repositories

import me.zozfabio.wallet.user.domain.entities.User
import org.springframework.data.repository.CrudRepository
import java.util.*

interface UserRepository : CrudRepository<User, String> {

    fun findFirstByEmail(email: String): Optional<User>

    fun findFirstByIsContactsContaining(contactId: String): Optional<User>
}
