package at.hschroedl.pages.service


import at.hschroedl.pages.domain.Page
import at.hschroedl.pages.domain.User
import at.hschroedl.pages.repository.PageRepository
import at.hschroedl.pages.service.dto.PageDTO
import at.hschroedl.pages.service.mapper.PageMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class PageService(private var pageRepository: PageRepository, private var pageMapper: PageMapper) {

    private val log = LoggerFactory.getLogger(PageService::class.java)

    @Transactional(readOnly = true)
    fun findAll(): List<PageDTO> {
        return pageRepository.findAll().map { pageMapper.toDto(it) }
    }

    @Transactional(readOnly = true)
    fun findByUser(user: User): List<Page> {
        return pageRepository.findByUserId(user.id)
    }

    fun createWith(currentUser: User, pageDTO: PageDTO): Page {
        val document = Page()
        document.name = pageDTO.name
        document.description = pageDTO.description
        document.content = pageDTO.content
        document.user = currentUser
        val result = pageRepository.save(document)
        log.debug("Created Information for Page: {}", document)
        return result
    }

    @Transactional(readOnly = true)
    fun findOne(id: Long?): PageDTO? {
        log.debug("Request to get Page with id : {}", id)
        val document = pageRepository.findOne(id) ?: null
        return pageMapper.toDto(document)
    }

    fun update(pageDTO: PageDTO): PageDTO {
        val document = pageRepository.findOne(pageDTO.id)
        document.name = pageDTO.name
        document.description = pageDTO.description
        document.content = pageDTO.content
        log.debug("Changed information for page {}", document)
        return PageDTO(document)
    }

    fun deleteDocument(id: Long?) {
        log.debug("Request to delete page with id: {}", id)
        pageRepository.delete(id)
    }
}
