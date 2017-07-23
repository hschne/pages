package at.hschroedl.pages.web.rest

import at.hschroedl.pages.domain.Document
import at.hschroedl.pages.domain.User
import at.hschroedl.pages.service.DocumentService
import at.hschroedl.pages.service.UserService
import at.hschroedl.pages.service.dto.DocumentDTO
import at.hschroedl.pages.web.rest.util.HeaderUtil
import com.codahale.metrics.annotation.Timed
import io.github.jhipster.web.util.ResponseUtil
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class DocumentResource(val documentService: DocumentService, var userService: UserService) {

    private val ENTITY_NAME = "documentManagement"

    private val log = LoggerFactory.getLogger(DocumentResource::class.java)

    @GetMapping("/documents")
    @Timed
    fun getDocuments(): ResponseEntity<List<DocumentDTO>> {
        log.debug("Rest request to get documents")
        val currentUser: User = userService.userWithAuthorities ?: return ResponseEntity(
            HttpStatus.INTERNAL_SERVER_ERROR)
        return ResponseEntity.ok(documentService.findByUser(currentUser).map { DocumentDTO(it) })
    }

    @PostMapping("/documents")
    @Timed
    fun createDocument(@Valid @RequestBody documentDTO: DocumentDTO): ResponseEntity<*> {
        log.debug("REST request to create document {}", documentDTO)
        val currentUser: User? = userService.userWithAuthorities ?: return ResponseEntity<String>(
            HttpStatus.INTERNAL_SERVER_ERROR)
        if (documentDTO.id != null) {
            val result = ResponseEntity.badRequest()
                .headers(
                    HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new document cannot already have an ID"))
                .body<Any>(null)
            return result
        }
        val newDocument = documentService.createWith(currentUser!!, documentDTO)
        return ResponseEntity.created(URI("/api/documents/" + newDocument.id))
            .headers(
                HeaderUtil.createAlert("A document is created with name " + newDocument.name, newDocument.name))
            .body<Document>(newDocument)
    }

    @PutMapping("/documents")
    @Timed
    fun updateDocument(@Valid @RequestBody documentDTO: DocumentDTO): ResponseEntity<DocumentDTO> {
        log.debug("REST request to update document {}", documentDTO)
        val currentUser: User? = userService.userWithAuthorities ?: return ResponseEntity(
            HttpStatus.INTERNAL_SERVER_ERROR)
        val existingDocument = documentService.findOne(documentDTO.id) ?: return ResponseEntity(
            HttpStatus.NOT_FOUND)
        if (existingDocument.userId != currentUser!!.id) {
            return ResponseEntity.badRequest().headers(
                HeaderUtil.createFailureAlert(ENTITY_NAME, "invaliduser",
                    "User not authorized to modify this document")).body<DocumentDTO>(null)
        }
        return ResponseEntity.ok(documentService.update(documentDTO))
    }

    @DeleteMapping("/documents/delete/{id}")
    @Timed
    fun deleteDocument(@PathVariable id: Long?): ResponseEntity<Void> {
        log.debug("REST request to delete documen with idt {}", id)
        val currentUser: User = userService.userWithAuthorities ?: return ResponseEntity(
            HttpStatus.INTERNAL_SERVER_ERROR)
        val existingDocument = documentService.findOne(id) ?: return ResponseEntity(
            HttpStatus.NOT_FOUND)
        if (existingDocument.userId != currentUser.id) {
            var result = ResponseEntity.badRequest().headers(
                HeaderUtil.createFailureAlert(ENTITY_NAME, "invaliduser",
                    "User not authorized to delete this document")).body<Void>(null)
            return result
        }
        documentService.deleteDocument(id)
        return ResponseEntity.ok().headers(
            HeaderUtil.createAlert("A document is deleted with identifier " + id,
                id.toString())).build()
    }


    @GetMapping("/documents/{id}")
    @Timed
    fun getSample(@PathVariable id: Long?): ResponseEntity<DocumentDTO> {
        log.debug("REST request to get Sample : {}", id)
        val documentDto = documentService.findOne(id)
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(documentDto))
    }


}
