package com.saurabh.model.dao

import com.saurabh.model.User
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.Closeable

interface DAOFacade: Closeable {
    fun init()
    fun createUser(name: String, email: String)
    fun updateUser(id: Int,name: String, email: String)
    fun deleteUser(id: Int)
    fun getUser(id: Int): User?
    fun getAllUsers(): List<User>
}

class UserDatabase(val db: Database): DAOFacade{

    override fun init(): Unit = transaction(db) {
        SchemaUtils.create(Users)

        val users = listOf(
            User(1, "Neo","neo@doe.com"),
            User(2,"Trinity","trinity@doe.com"),
            User(3, "Morpheus","morpheus@doe.com")
        )

        Users.batchInsert(users){ user ->

            this[Users.id] = user.id
            this[Users.name] = user.name
            this[Users.email] = user.email
        }
        Unit
    }

    override fun createUser(name: String, email: String) = transaction(db) {
        Users.insert {
            it[Users.name] = name
            it[Users.email] = email
        }
        Unit
    }

    override fun updateUser(id: Int, name: String, email: String) = transaction(db) {
        Users.update({Users.id eq id}){
            it[Users.name] = name
            it[Users.email] = email
        }
        Unit
    }

    override fun deleteUser(id: Int) = transaction(db) {
        Users.deleteWhere { Users.id eq id }
        Unit
    }

    override fun getUser(id: Int) = transaction(db) {
        Users.select { Users.id eq id }.map {
            User(it[Users.id], it[Users.name], it[Users.email]
            )
        }.singleOrNull()
    }

    override fun getAllUsers()  = transaction(db){
        Users.selectAll().map {
            User(it[Users.id], it[Users.name], it[Users.email]
            )
        }
    }

    override fun close() {
    }


}