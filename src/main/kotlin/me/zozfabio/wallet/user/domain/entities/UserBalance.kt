package me.zozfabio.wallet.user.domain.entities

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.math.BigDecimal.ZERO

@Document("UserBalance")
@TypeAlias("UserBalance")
data class UserBalance(@Id val userId: String,
                       var userBalance: BigDecimal = ZERO) {

    fun plus(value: BigDecimal): UserBalance {
        userBalance = userBalance.add(value)
        return this
    }

    fun minus(value: BigDecimal): UserBalance {
        userBalance = userBalance.minus(value)
        return this
    }
}
