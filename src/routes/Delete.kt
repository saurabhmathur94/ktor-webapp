package com.saurabh.routes

import com.saurabh.dao
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get

const val DELETE = "/delete"

fun Route.delete(){

    get(DELETE) {

        val id = call.request.queryParameters["id"]
        if(id != null){
            dao.deleteUser(id.toInt())
            call.respond(FreeMarkerContent("index.ftl", mapOf("users" to dao.getAllUsers())))
        }
    }

}