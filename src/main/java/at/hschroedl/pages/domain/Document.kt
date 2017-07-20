package at.hschroedl.pages.domain

import java.io.Serializable
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "document")
data class Document(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    var id: Long? = null,

    @Size(min = 1, max = 50)
    @NotNull
    var name: String,

    @Size(max = 100)
    var description: String,

    @Size(min = 1)
    @Lob
    var content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User

) : AbstractAuditingEntity(), Serializable {

    constructor() : this(null, "", "", "", User())

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }

        val document = other as Document?
        return !(document!!.id == null || id == null) && id == document.id
    }

    override fun toString(): String {
        return "Document(name='$name', description='$description', content='$content')"
    }

}
