package me.zozfabio.wallet

import com.commercehub.jackson.datatype.mongo.MongoModule
import com.fasterxml.jackson.databind.ObjectMapper
import me.zozfabio.wallet.user.repository.ContactRepository
import me.zozfabio.wallet.user.repository.UserEventRepository
import me.zozfabio.wallet.user.repository.UserRepository
import me.zozfabio.wallet.userview.repository.UserViewRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class WalletApplication {

    @Autowired
    fun runner(objectMapper: ObjectMapper) {
        objectMapper.registerModule(MongoModule())
    }

    @Bean
    fun appRunner(users: UserRepository, contacts: ContactRepository, userEvents: UserEventRepository, usersView: UserViewRepository) =
        ApplicationRunner {
//            println("inicio")
//                Flux.just("a", "b")
//                    .delayElements(Duration.ofSeconds(2))
//                    .subscribe(::println)
//            println("fim")

//            Mono.zip(users.findById("5dc56a451110c82f5b856f58"), contacts.findAllByEmail("user3@test.com").collectList())
//                .doOnNext {
//                    println(it.t1)
//                    println(it.t2)
//                }
//                .doOnNext {
//                    println(it.t1.getIsContacts())
//                }
//                .map { it.t1.isContacts(it.t2) }
//                .doOnNext {
//                    println(it.getIsContacts())
//                }
//                .flatMap { users.save(it) }
//                .subscribe()

//            Mono.zip(Mono.just("a"), Flux.empty<String>().collectList())
//                .doOnNext {
//                    println(it.t1)
//                    println(it.t2)
//                }
//                .subscribe()

//            users.findAllByIsContactsContaining(ObjectId("5dc569ec1110c82f5b856f57"))
//                .doOnNext {
//                    println(it)
//                }
//                .subscribe()

//            Mono.zip(Mono.defer { Mono.just(ObjectId.get()) }, Mono.defer { Mono.just(ObjectId.get()) })
//                .log()
//                .subscribe()

//            userEvents.findAll()
//                .groupBy { it.userId }
//                .flatMap { Mono.zip(Mono.justOrEmpty(it.key()), it.collectList()) }
//                .map { UserView.recreate(it.t1, it.t2) }
//                .flatMap { usersView.save(it) }
//                .subscribe()

//            Mono.just("1")
//                .delayElement(Duration.ofSeconds(1))
//                .or(Mono.just("2"))
//                .log()
//                .subscribe()
        }
}

fun main(args: Array<String>) {
    runApplication<WalletApplication>(*args)
}
