package com.saurabh.routes

import io.ktor.application.call
import io.ktor.routing.Route

const val HOME = "/"

fun Route.home() {
    get(HOME) {
        call.respondText("Home Page")

    }
}
