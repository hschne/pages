package at.hschroedl.pages.web.rest

import at.hschroedl.pages.service.DocumentService
import at.hschroedl.pages.service.dto.DocumentDTO
import at.hschroedl.pages.service.dto.UserDTO
import com.codahale.metrics.annotation.Timed
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class DocumentResource(var documentService: DocumentService) {


    @GetMapping("/documents")
    @Timed
    fun getDocuments(): ResponseEntity<List<DocumentDTO>> {
        return ResponseEntity.ok(documentService.findAll()!!.map { DocumentDTO(it) })
    }

    @GetMapping("/users/{userId}/documents")
    @Timed
    fun getDocumentsFor(@PathVariable(name = "userId") userId: Long): ResponseEntity<List<DocumentDTO>> {
        return ResponseEntity.ok(documentService.findByUser(userId)!!.map { DocumentDTO(it) })
    }

}
