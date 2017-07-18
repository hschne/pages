package at.hschroedl.pages.repository

import at.hschroedl.pages.domain.Document
import org.springframework.data.jpa.repository.JpaRepository


interface DocumentRepository : JpaRepository<Document, Long> {

    fun findByUserId(userId : Long) : List<Document>
}
