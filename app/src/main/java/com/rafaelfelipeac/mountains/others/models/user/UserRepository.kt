package com.rafaelfelipeac.mountains.others.models.user

import androidx.lifecycle.LiveData
import com.rafaelfelipeac.mountains.others.models.User
import javax.inject.Inject

class UserRepository @Inject constructor(private val userDAO: UserDAO) {

    fun getUsers(): LiveData<List<User>> {
        return userDAO.getAll()
    }

    fun getUser(userId: Long): LiveData<User> {
        return userDAO.get(userId)
    }

    fun save(user: User): Long {
        return userDAO.save(user)
    }

    fun delete(user: User) {
        return userDAO.delete(user)
    }

    fun getUserByUUI(uui: String): User? {
        return userDAO.getByUUI(uui)
    }
}