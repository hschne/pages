package at.hschroedl.pages.service


import at.hschroedl.pages.domain.Document
import at.hschroedl.pages.domain.User
import at.hschroedl.pages.repository.DocumentRepository
import at.hschroedl.pages.service.dto.DocumentDTO
import at.hschroedl.pages.service.mapper.DocumentMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class DocumentService(private var documentRepository: DocumentRepository, private var documentMapper: DocumentMapper) {

    private val log = LoggerFactory.getLogger(DocumentService::class.java)

    @Transactional(readOnly = true)
    fun findAll(): List<DocumentDTO> {
        return documentRepository.findAll().map { documentMapper.toDto(it) }
    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    fun findOne(id: Long?): DocumentDTO? {
        log.debug("Request to get Document with id : {}", id)
        val document = documentRepository.findOne(id) ?: null
        return documentMapper.toDto(document)
    }

    fun update(documentDTO: DocumentDTO): DocumentDTO {
        val document = documentRepository.findOne(documentDTO.id)
        document.name = documentDTO.name
        document.description = documentDTO.description
        document.content = documentDTO.content
        log.debug("Changed information for document {}", document)
        return DocumentDTO(document)
    }

    fun deleteDocument(id: Long?) {
        log.debug("Request to delete document with id: {}", id)
        documentRepository.delete(id)
    }
}
