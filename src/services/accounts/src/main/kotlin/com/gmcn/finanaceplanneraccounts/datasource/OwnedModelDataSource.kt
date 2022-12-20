package com.gmcn.finanaceplanneraccounts.datasource

import com.gmcn.finanaceplanneraccounts.model.OwnedModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
object OwnedModelDataSource {
    const val TYPE_ACCOUNT = "account"

    lateinit var accountDataSource: AccountDataSource
        @Autowired
        set

    fun retrieveModel(model: String, id: String): OwnedModel? {
        return when (model) {
            TYPE_ACCOUNT -> accountDataSource.findByIdOrNull(id)
            else -> {
                throw Exception("Invalid owned model $model")
            }
        }
    }
}