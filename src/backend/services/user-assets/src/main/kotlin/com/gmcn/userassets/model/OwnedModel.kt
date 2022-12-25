package com.gmcn.userassets.model

abstract class OwnedModel(
    var owner: String,
) {
    fun isOwnedBy(userId: String): Boolean {
        return owner == userId
    }
}