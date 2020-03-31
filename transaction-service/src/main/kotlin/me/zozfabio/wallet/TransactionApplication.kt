package me.zozfabio.wallet

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding

@EnableBinding(Channels::class)
class IntegrationConfig

@SpringBootApplication
class TransactionApplication

fun main(args: Array<String>) {
    runApplication<TransactionApplication>(*args)
}
