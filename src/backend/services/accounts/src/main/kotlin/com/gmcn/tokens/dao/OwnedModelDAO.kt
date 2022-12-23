package com.gmcn.tokens.dao

import com.gmcn.tokens.model.OwnedModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
object OwnedModelDAO {
    enum class OwnedModelTypes {
        ACCOUNT
    }

    lateinit var accountsRepository: AccountMongoRepository
        @Autowired
        set

    fun findOrNull(modelType: OwnedModelTypes, id: String): OwnedModel? {
        return when (modelType) {
            OwnedModelTypes.ACCOUNT -> accountsRepository.findByIdOrNull(id)
        }
    }
}
