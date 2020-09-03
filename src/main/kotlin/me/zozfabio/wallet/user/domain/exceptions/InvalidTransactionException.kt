package me.zozfabio.wallet.user.domain.exceptions

import org.slf4j.helpers.MessageFormatter
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
class InvalidTransactionException(message: String, vararg args: Any)
    : RuntimeException(MessageFormatter.arrayFormat(message, args).message)
