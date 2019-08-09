package com.saurabh.routes

import com.saurabh.dao
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.Parameters
import io.ktor.request.receiveParameters
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post

const val USER = "/user"

fun Route.user(){

    get(USER) {
        when(val action = (call.request.queryParameters["action"] ?: "new")){

            "new" -> call.respond(FreeMarkerContent("user.ftl", mapOf("action" to action)))

            "edit" -> {

                val id = call.request.queryParameters["id"]
                if(id != null){
                    call.respond(FreeMarkerContent("user.ftl", mapOf("user" to dao.getUser(id.toInt()), "action" to action)))
                }
            }

        }
    }

    post(USER) {
        val postParameters: Parameters = call.receiveParameters()
        val action = postParameters["action"] ?: "new"
        when(action){

            "new" -> dao.createUser(postParameters["name"] ?: "", postParameters["email"] ?: "")

            "edit" ->{
                val id = postParameters["id"]
                if(id != null)
                    dao.updateUser(id.toInt(),postParameters["name"] ?: "", postParameters["email"] ?: "")
            }
        }

        call.respond(FreeMarkerContent("index.ftl", mapOf("users" to dao.getAllUsers())))

    }


}