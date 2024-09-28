package dev.gooiman.server.page.application;

import dev.gooiman.server.common.exception.CommonException;
import dev.gooiman.server.common.exception.ErrorCode;
import dev.gooiman.server.memo.repository.MemoRepository;
import dev.gooiman.server.page.repository.PageRepository;
import dev.gooiman.server.page.repository.entity.Page;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PageService {

    private final PageRepository pageRepository;
    private final MemoRepository memoRepository;

    public Page getPageById(String id) {
        UUID uuid = UUID.fromString(id);
        return pageRepository.findById(uuid)
            .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_PAGE));
    }

//    public CreatePageResponseDto.Res create(CreatePageResponseDto createPageDto) {
//        String name = createPageDto.getName();
//        Page page = Page.builder().pageName(name).build();
//        Page savedPage = pageRepository.save(page);
//        return CreatePageResponseDto.Res.mapEntityToDto(savedPage);
//    }
//
//    public MemoSummariseResponseDto.Res memoSummarise(String pageId) {
//        Optional<Page> page = pageRepository.findById(UUID.fromString(pageId));
//        Page pageEntity = page.get();
//        String pageName = pageEntity.getPageName();
//        memoRepository.findById()
//
//    }
}
