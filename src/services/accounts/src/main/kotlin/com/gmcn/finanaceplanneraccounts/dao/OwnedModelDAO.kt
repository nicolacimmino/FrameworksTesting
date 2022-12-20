package com.gmcn.finanaceplanneraccounts.dao

import com.gmcn.finanaceplanneraccounts.model.OwnedModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
object OwnedModelDAO {
    const val TYPE_ACCOUNT = "account"

    lateinit var accountsRepository: AccountMongoRepository
        @Autowired
        set

    fun findOrNull(model: String, id: String): OwnedModel? {
        return when (model) {
            TYPE_ACCOUNT -> accountsRepository.findByIdOrNull(id)
            else -> {
                throw Exception("Invalid owned model $model")
            }
        }
    }
}