package me.zozfabio.wallet.user.domain.repositories

import me.zozfabio.wallet.user.domain.entities.UserContact
import org.springframework.data.repository.CrudRepository
import java.util.stream.Stream

interface ContactRepository : CrudRepository<UserContact, String> {

    fun findAllByEmail(email: String): Stream<UserContact>
}
