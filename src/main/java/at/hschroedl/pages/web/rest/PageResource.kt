package at.hschroedl.pages.web.rest

import at.hschroedl.pages.domain.Page
import at.hschroedl.pages.domain.User
import at.hschroedl.pages.service.PageService
import at.hschroedl.pages.service.UserService
import at.hschroedl.pages.service.dto.PageDTO
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
class PageResource(val pageService: PageService, var userService: UserService) {

    private val ENTITY_NAME = "pageManagement"

    private val log = LoggerFactory.getLogger(PageResource::class.java)

    @GetMapping("/page")
    @Timed
    fun getDocuments(): ResponseEntity<List<PageDTO>> {
        log.debug("Rest request to get pages")
        val currentUser: User = userService.userWithAuthorities ?: return ResponseEntity(
            HttpStatus.INTERNAL_SERVER_ERROR)
        return ResponseEntity.ok(pageService.findByUser(currentUser).map { PageDTO(it) })
    }

    @PostMapping("/page")
    @Timed
    fun createDocument(@Valid @RequestBody pageDTO: PageDTO): ResponseEntity<*> {
        log.debug("REST request to create page {}", pageDTO)
        val currentUser: User? = userService.userWithAuthorities ?: return ResponseEntity<String>(
            HttpStatus.INTERNAL_SERVER_ERROR)
        if (pageDTO.id != null) {
            val result = ResponseEntity.badRequest()
                .headers(
                    HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new page cannot already have an ID"))
                .body<Any>(null)
            return result
        }
        val newDocument = pageService.createWith(currentUser!!, pageDTO)
        return ResponseEntity.created(URI("/api/pages/" + newDocument.id))
            .headers(
                HeaderUtil.createAlert("A page is created with name " + newDocument.name, newDocument.name))
            .body<Page>(newDocument)
    }

    @PutMapping("/page")
    @Timed
    fun updateDocument(@Valid @RequestBody pageDTO: PageDTO): ResponseEntity<PageDTO> {
        log.debug("REST request to update page {}", pageDTO)
        val currentUser: User? = userService.userWithAuthorities ?: return ResponseEntity(
            HttpStatus.INTERNAL_SERVER_ERROR)
        val existingDocument = pageService.findOne(pageDTO.id) ?: return ResponseEntity(
            HttpStatus.NOT_FOUND)
        if (existingDocument.userId != currentUser!!.id) {
            return ResponseEntity.badRequest().headers(
                HeaderUtil.createFailureAlert(ENTITY_NAME, "invaliduser",
                    "User not authorized to modify this page")).body<PageDTO>(null)
        }
        return ResponseEntity.ok(pageService.update(pageDTO))
    }

    @DeleteMapping("/page/{id}")
    @Timed
    fun deleteDocument(@PathVariable id: Long?): ResponseEntity<Void> {
        log.debug("REST request to delete page with idt {}", id)
        val currentUser: User = userService.userWithAuthorities ?: return ResponseEntity(
            HttpStatus.INTERNAL_SERVER_ERROR)
        val existingDocument = pageService.findOne(id) ?: return ResponseEntity(
            HttpStatus.NOT_FOUND)
        if (existingDocument.userId != currentUser.id) {
            var result = ResponseEntity.badRequest().headers(
                HeaderUtil.createFailureAlert(ENTITY_NAME, "invaliduser",
                    "User not authorized to delete this page")).body<Void>(null)
            return result
        }
        pageService.deleteDocument(id)
        return ResponseEntity.ok().headers(
            HeaderUtil.createAlert("Deleted "+ existingDocument.name,
                id.toString())).build()
    }


    @GetMapping("/page/{id}")
    @Timed
    fun getSample(@PathVariable id: Long?): ResponseEntity<PageDTO> {
        log.debug("REST request to get Sample : {}", id)
        val documentDto = pageService.findOne(id)
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(documentDto))
    }


}
