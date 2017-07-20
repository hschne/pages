package at.hschroedl.pages.web.rest

import at.hschroedl.pages.domain.Document
import at.hschroedl.pages.domain.User
import at.hschroedl.pages.service.DocumentService
import at.hschroedl.pages.service.UserService
import at.hschroedl.pages.service.dto.DocumentDTO
import at.hschroedl.pages.web.rest.util.HeaderUtil
import com.codahale.metrics.annotation.Timed
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class DocumentResource(val documentService: DocumentService, var userService: UserService) {

    private val ENTITY_NAME = "documentManagement"

    @GetMapping("/documents")
    @Timed
    fun getDocuments(): ResponseEntity<List<DocumentDTO>> {
        val currentUser: User? = userService.userWithAuthorities ?: return ResponseEntity(
            HttpStatus.INTERNAL_SERVER_ERROR)
        return ResponseEntity.ok(documentService.findByUser(currentUser!!).map { DocumentDTO(it) })
    }

    @PostMapping("/documents")
    @Timed
    fun createDocument(@Valid @RequestBody documentDTO: DocumentDTO): ResponseEntity<*> {
        val currentUser: User? = userService.userWithAuthorities ?: return ResponseEntity<String>(
            HttpStatus.INTERNAL_SERVER_ERROR)
        if (documentDTO.id != null) {
            val result =ResponseEntity.badRequest()
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


}
