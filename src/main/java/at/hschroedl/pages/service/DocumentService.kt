package at.hschroedl.pages.service


import at.hschroedl.pages.domain.Document
import at.hschroedl.pages.domain.User
import at.hschroedl.pages.repository.DocumentRepository
import at.hschroedl.pages.service.dto.DocumentDTO
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class DocumentService(var documentRepository: DocumentRepository) {

    private val log = LoggerFactory.getLogger(DocumentService::class.java)

    fun findAll() : List<Document> {
        return documentRepository.findAll()
    }

    fun findByUser(user : User) : List<Document> {
        return documentRepository.findByUserId(user.id)
    }

    fun createWith(currentUser: User, documentDTO: DocumentDTO): Document {
        val document = Document()
        document.name = documentDTO.name
        document.description = documentDTO.description
        document.content = documentDTO.content
        document.user = currentUser
        val result = documentRepository.save(document)
        log.debug("Created Information for Document: {}", document)
        return result
    }
}
