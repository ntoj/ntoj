package com.github.ntoj.app.server.repository

import com.github.ntoj.app.server.model.entities.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface ArticleRepository : JpaRepository<Article, Long>, JpaSpecificationExecutor<Article>
