package at.hschroedl.pages.service.dto

import at.hschroedl.pages.domain.Document
import org.hibernate.validator.constraints.NotBlank
import java.time.Instant
import javax.validation.constraints.Size

data class DocumentDTO(
    var id: Long? = null,

    @NotBlank
    @Size(min = 1, max = 50)
    var name: String,

    @Size(max = 100)
    var description: String,

    @Size(min = 1)
    var content: String,

    var createdDate: Instant,

    var userId: Long? = null,

    var userLogin: String? = null) {

    constructor() : this(null, "", "", "", Instant.now())

    constructor(document: Document) : this(document.id, document.name, document.description, document.content,
        document.createdDate)

    override fun toString(): String {
        val shortContent = content.substring(0, Math.min(content.length, 30))
        return "DocumentDTO(name='$name', description='$description', content='$shortContent...', createdDate=$createdDate)"
    }

}
