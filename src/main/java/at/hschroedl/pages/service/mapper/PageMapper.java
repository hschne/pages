package at.hschroedl.pages.service.mapper;

import at.hschroedl.pages.domain.Page;
import at.hschroedl.pages.service.dto.PageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * This garbage needs to be in Java, because of this issue: https://discuss.kotlinlang.org/t/issue-with-repeated-java-8-annotations/1667/5
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class,})
public interface PageMapper extends EntityMapper<PageDTO, Page> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    PageDTO toDto(Page page);

    @Mapping(source = "userId", target = "user")
    Page toEntity(PageDTO pageDTO);

    default Page fromId(Long id) {
        if (id == null) {
            return null;
        }
        Page page = new Page();
        page.setId(id);
        return page;
    }
}
