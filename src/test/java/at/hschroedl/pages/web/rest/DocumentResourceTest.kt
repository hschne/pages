package at.hschroedl.pages.web.rest

import at.hschroedl.pages.PagesApp
import at.hschroedl.pages.domain.Document
import at.hschroedl.pages.domain.User
import at.hschroedl.pages.repository.DocumentRepository
import at.hschroedl.pages.service.DocumentService
import at.hschroedl.pages.service.UserService
import at.hschroedl.pages.service.dto.DocumentDTO
import at.hschroedl.pages.web.rest.errors.ExceptionTranslator
import at.hschroedl.pages.web.rest.util.HeaderUtil
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.web.PageableHandlerMethodArgumentResolver
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(PagesApp::class))
open class DocumentResourceTest {


    private val DEFAULT_NAME = "DefaultDocument"

    private val DEFAULT_DESCRIPTION = "DefaultDescription"

    private val DEFAULT_CONTENT = "DefaultContent"

    private val UPDATED_NAME = "UpdatedDocument"

    private val UPDATED_DESCRIPTION = "UpdatedDescription"

    private val UPDATED_CONTENT = "UpdatedContent"

    @Autowired
    private lateinit var documentRespository: DocumentRepository

    private lateinit var restUserMockMvc: MockMvc

    @Autowired
    private lateinit var documentService: DocumentService

    @Autowired
    private val jacksonMessageConverter: MappingJackson2HttpMessageConverter? = null

    @Autowired
    private val pageableArgumentResolver: PageableHandlerMethodArgumentResolver? = null

    @Autowired
    private val exceptionTranslator: ExceptionTranslator? = null

    @Mock
    private lateinit var mockUserService: UserService

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        val documentResource = DocumentResource(documentService, mockUserService)
        this.restUserMockMvc = MockMvcBuilders.standaloneSetup(documentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver!!)
            .setControllerAdvice(exceptionTranslator!!)
            .setMessageConverters(jacksonMessageConverter!!)
            .build()
    }

    @Test
    @Transactional
    open fun createDocument_withValidUserAndDocument_createsDocument() {
        val databaseSizeBeforeCreate = documentRespository.findAll().size
        val documentDto = DocumentDTO(null, DEFAULT_NAME, DEFAULT_DESCRIPTION, DEFAULT_CONTENT, Instant.now())
        val user = createCurrentUser()

        restUserMockMvc.perform(post("/api/documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentDto)))
            .andExpect(status().isCreated)

        val documentList = documentRespository.findAll()
        assertThat<Document>(documentList).hasSize(databaseSizeBeforeCreate + 1)
        val testDocument = documentList[documentList.size - 1]
        assertThat(testDocument.name).isEqualTo(DEFAULT_NAME)
        assertThat(testDocument.content).isEqualTo(DEFAULT_CONTENT)
        assertThat(testDocument.description).isEqualTo(DEFAULT_DESCRIPTION)
        assertThat(testDocument.user).isEqualToComparingFieldByField(user)
    }

    @Test
    fun createDocument_withExistingId_returnsBadRequest() {
        val documentDto = DocumentDTO(1, DEFAULT_NAME, DEFAULT_DESCRIPTION, DEFAULT_CONTENT, Instant.now())
        createCurrentUser()

        restUserMockMvc.perform(post("/api/documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentDto)))
            .andExpect(status().isBadRequest)
            .andExpect(header().string("X-pagesApp-error", "A new document cannot already have an ID"))
    }

    @Test
    fun createDocument_withInvalidSession_returnsInternalServerError() {
        val documentDto = DocumentDTO(null, DEFAULT_NAME, DEFAULT_DESCRIPTION, DEFAULT_CONTENT, Instant.now())

        restUserMockMvc.perform(post("/api/documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentDto)))
            .andExpect(status().isInternalServerError)
    }

    @Test
    @Transactional
    open fun updateDocument_withValidDocument_updatesDocument() {
        val documentDto = DocumentDTO(1, UPDATED_NAME, UPDATED_DESCRIPTION, UPDATED_CONTENT, Instant.now())
        createCurrentUser()

        restUserMockMvc.perform(put("/api/documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentDto)))
            .andExpect(status().isOk)

        val document = documentRespository.findOne(1)
        assertThat(document.name).isEqualTo(UPDATED_NAME)
        assertThat(document.content).isEqualTo(UPDATED_CONTENT)
        assertThat(document.description).isEqualTo(UPDATED_DESCRIPTION)
    }

    @Test
    fun updateDocument_withBadUser_returnsInvalidServerError() {
        val documentDto = DocumentDTO(2, UPDATED_NAME, UPDATED_DESCRIPTION, UPDATED_CONTENT, Instant.now())
        createCurrentUser()

        restUserMockMvc.perform(put("/api/documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentDto)))
            .andExpect(status().isInternalServerError)
    }

    @Test
    fun updateDocument_withInvalidDocument_returnsNotFound() {
        val documentDto = DocumentDTO(-1, UPDATED_NAME, UPDATED_DESCRIPTION, UPDATED_CONTENT, Instant.now())
        createCurrentUser()

        restUserMockMvc.perform(put("/api/documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentDto)))
            .andExpect(status().isNotFound)
    }

    @Test
    @Transactional
    open fun deleteDocument_withValidDocument_deletesDocument() {
        val databaseSizeBeforeCreate = documentRespository.findAll().size
        val document = documentRespository.findOne(1)
        val documentDto = DocumentDTO(1, UPDATED_NAME, UPDATED_DESCRIPTION, UPDATED_CONTENT, Instant.now())
        createCurrentUser()

        restUserMockMvc.perform(delete("/api/documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentDto)))
            .andExpect(status().isOk)

        val documentList = documentRespository.findAll()
        assertThat<Document>(documentList).hasSize(databaseSizeBeforeCreate - 1)
        assertThat<Document>(documentList).doesNotContain(document)
    }

    @Test
    fun deleteDocument_withInvalidDocument_returnsNotFoundError() {
        val documentDto = DocumentDTO(-1, UPDATED_NAME, UPDATED_DESCRIPTION, UPDATED_CONTENT, Instant.now())
        createCurrentUser()

        restUserMockMvc.perform(delete("/api/documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentDto)))
            .andExpect(status().isNotFound)
    }

    @Test
    @Transactional
    open fun deleteDocument_withBadUser_returnsBadRequest() {
        val documentDto = DocumentDTO(2, UPDATED_NAME, UPDATED_DESCRIPTION, UPDATED_CONTENT, Instant.now())
        createCurrentUser()

        restUserMockMvc.perform(delete("/api/documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentDto)))
            .andExpect(status().isBadRequest)
            .andExpect(header().string(HeaderUtil.ERROR_HEADER, "User not authorized to delete this document"))
    }


    private fun createCurrentUser(): User {
        val user = User()
        user.id = 3
        `when`<User>(mockUserService.userWithAuthorities).thenReturn(user)
        return user
    }

}
