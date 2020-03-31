package me.zozfabio.wallet

import me.zozfabio.wallet.user.service.UserService
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Source
import org.springframework.context.annotation.Bean

@EnableBinding(Source::class)
class IntegrationConfig

@SpringBootApplication
class UserApplication {

    @Bean
    fun appRunner(users: UserService) =
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

//            Mono.just("1")
//                .delayElement(Duration.ofSeconds(1))
//                .or(Mono.just("2"))
//                .log()
//                .subscribe()

//            users.save(User.create("User 1", "people1@test.com"))
//                .doOnNext { println("User Created: $it") }
//                .log()
//                .then(users.findFirstByEmail("people1@test.com")
//                    .doOnNext { println("User Finded: $it") }
//                    .log())
//                .subscribe()

//            users.default()
        }
}

fun main(args: Array<String>) {
    runApplication<UserApplication>(*args)
}
