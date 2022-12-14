package com.gmcn.finanaceplanneraccounts.dao

import org.springframework.data.mongodb.repository.MongoRepository

interface EntityDao<T, TKEY> : MongoRepository<T, TKEY> {
}