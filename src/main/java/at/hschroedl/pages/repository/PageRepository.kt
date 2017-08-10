package at.hschroedl.pages.repository

import at.hschroedl.pages.domain.Page
import org.springframework.data.jpa.repository.JpaRepository


interface PageRepository : JpaRepository<Page, Long> {

    fun findByUserId(userId: Long): List<Page>
}
