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
class DocumentService(private var documentRepository: DocumentRepository) {

    private val log = LoggerFactory.getLogger(DocumentService::class.java)

    fun findAll(): List<Document> {
        return documentRepository.findAll()
    }

    fun findByUser(user: User): List<Document> {
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

    fun getById(documentDTO: DocumentDTO): Document? {
        return documentRepository.findOne(documentDTO.id)
    }

    fun update(documentDTO: DocumentDTO): DocumentDTO {
        val document = documentRepository.findOne(documentDTO.id)
        document.name = documentDTO.name
        document.description = documentDTO.description
        document.content = documentDTO.content
        log.debug("Changed information for document {}", document)
        return DocumentDTO(document)
    }

    fun deleteDocument(documentDTO: DocumentDTO) {
        val document = documentRepository.findOne(documentDTO.id) ?: return
        documentRepository.delete(document)
        log.debug("Deleted document: {}", document)
    }
}
