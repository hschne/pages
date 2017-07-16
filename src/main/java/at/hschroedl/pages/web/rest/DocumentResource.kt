package at.hschroedl.pages.web.rest

import at.hschroedl.pages.service.DocumentService
import at.hschroedl.pages.service.dto.DocumentDTO
import at.hschroedl.pages.service.dto.UserDTO
import com.codahale.metrics.annotation.Timed
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class DocumentResource(var documentService: DocumentService){


    @GetMapping("/documents")
    @Timed
    fun getDocuments() : ResponseEntity<List<DocumentDTO>> {
        return ResponseEntity.ok(documentService.findAll()!!.map { DocumentDTO(it) })
    }

}
