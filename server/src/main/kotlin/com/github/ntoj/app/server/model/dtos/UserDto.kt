package com.github.ntoj.app.server.model.dtos

import com.github.ntoj.app.server.model.entities.User
import java.io.Serializable
import java.time.Instant

data class UserDto(
    val createdAt: Instant,
    val username: String,
    val email: String? = null,
    val displayName: String? = null,
    val bio: String? = null,
    val id: Long,
) : Serializable {
    companion object {
        fun from(user: User) =
            UserDto(
                user.createdAt!!,
                user.username,
                user.email,
                user.displayName,
                user.bio,
                user.userId!!,
            )
    }
}
