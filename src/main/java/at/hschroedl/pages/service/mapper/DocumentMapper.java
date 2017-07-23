package at.hschroedl.pages.service.mapper;

import at.hschroedl.pages.domain.Document;
import at.hschroedl.pages.service.dto.DocumentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;

/**
 * This garbage needs to be in Java, because of this issue: https://discuss.kotlinlang.org/t/issue-with-repeated-java-8-annotations/1667/5
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class,})
public interface DocumentMapper extends EntityMapper<DocumentDTO, Document> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    DocumentDTO toDto(Document document);

    @Mapping(source = "userId", target = "user")
    Document toEntity(DocumentDTO documentDTO);

    default Document fromId(Long id) {
        if (id == null) {
            return null;
        }
        Document document = new Document();
        document.setId(id);
        return document;
    }
}
