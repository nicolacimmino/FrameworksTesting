package com.gmcn.userassets.models

abstract class OwnedModel(
    var owner: String,
) {
    fun isOwnedBy(userId: String): Boolean {
        return owner == userId
    }
}