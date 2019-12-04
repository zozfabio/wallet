package me.zozfabio.wallet.user.domain.repository

import me.zozfabio.wallet.user.domain.entities.Contact
import org.springframework.data.repository.CrudRepository
import java.util.stream.Stream

interface ContactRepository : CrudRepository<Contact, String> {

    fun findAllByEmail(email: String): Stream<Contact>
}
