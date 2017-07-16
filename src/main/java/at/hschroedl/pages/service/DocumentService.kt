package at.hschroedl.pages.service


import at.hschroedl.pages.domain.Document
import at.hschroedl.pages.repository.DocumentRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class DocumentService(var documentRepository: DocumentRepository) {

    private val log = LoggerFactory.getLogger(UserService::class.java)

    fun findAll() : List<Document>? {
        return documentRepository.findAll()
    }
}
