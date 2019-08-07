package com.saurabh.model.dao

import org.jetbrains.exposed.sql.*

object Users : Table(){
    val id = integer("id").primaryKey().autoIncrement()
    val name = varchar("name",50)
    val email = varchar("email",100)
}