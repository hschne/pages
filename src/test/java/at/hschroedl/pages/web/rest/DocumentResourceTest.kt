package at.hschroedl.pages.web.rest

import at.hschroedl.pages.PagesApp
import at.hschroedl.pages.domain.Document
import at.hschroedl.pages.domain.User
import at.hschroedl.pages.repository.DocumentRepository
import at.hschroedl.pages.service.DocumentService
import at.hschroedl.pages.service.UserService
import at.hschroedl.pages.service.dto.DocumentDTO
import at.hschroedl.pages.web.rest.errors.ExceptionTranslator
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(PagesApp::class))
open class DocumentResourceTest {


    private val DEFAULT_NAME = "MyDocument"

    private val DEFAULT_DESCRIPTION = "MyDescription"

    private val DEFAULT_CONTENT = "DefaultContent"

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
    @Transactional
    open fun createDocument_withExistingId_returnsBadRequest() {
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

    private fun createCurrentUser(): User {
        val user = User()
        user.id = 1
        `when`<User>(mockUserService.userWithAuthorities).thenReturn(user)
        return user
    }

}
