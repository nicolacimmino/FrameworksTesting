package com.gmcn.finanaceplanneraccounts.dao

import com.gmcn.finanaceplanneraccounts.model.User

interface UserDao : EntityDao<User, String> {
    fun findByEmail(email: String): User?
}