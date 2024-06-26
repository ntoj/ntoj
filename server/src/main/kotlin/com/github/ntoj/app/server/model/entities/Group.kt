package com.github.ntoj.app.server.model.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne

@Entity(name = "t_groups")
class Group(
    var name: String,
    @ManyToMany(targetEntity = User::class)
    @JoinTable(
        name = "t_groups_users",
        joinColumns = [JoinColumn(name = "group_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")],
    )
    var users: List<User> = mutableListOf(),
    @ManyToOne(targetEntity = User::class)
    @JoinColumn(name = "creator_user_id", nullable = false)
    var creator: User,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    var groupId: Long? = null,
) : BaseEntity()
