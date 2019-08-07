package com.saurabh

import com.saurabh.model.dao.UserDatabase
import com.saurabh.routes.home
import io.ktor.application.*
import io.ktor.routing.*
import freemarker.cache.*
import io.ktor.freemarker.*
import org.jetbrains.exposed.sql.Database

//Instantiate UserDatabase by passing H2 in-memory database instance
val dao = UserDatabase(Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver"))

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    //Integrate Dao layer with Ktor
    dao.init()

    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }

    routing {
        home()
    }
}

data class IndexData(val items: List<Int>)

