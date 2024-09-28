package dev.gooiman.server.page.application;

import dev.gooiman.server.memo.repository.MemoRepository;
import dev.gooiman.server.page.repository.PageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PageService {

    private final PageRepository pageRepository;
    private final MemoRepository memoRepository;

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
