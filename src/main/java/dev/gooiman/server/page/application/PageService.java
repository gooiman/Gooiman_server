package dev.gooiman.server.page.application;

import dev.gooiman.server.common.dto.CommonIdResponseDto;
import dev.gooiman.server.common.exception.CommonException;
import dev.gooiman.server.common.exception.ErrorCode;
import dev.gooiman.server.memo.application.dto.MemoSummariesResponseDto;
import dev.gooiman.server.memo.repository.MemoRepository;
import dev.gooiman.server.memo.repository.view.MemoSummariesView;
import dev.gooiman.server.page.application.dto.CreatePageRequestDto;
import dev.gooiman.server.page.application.dto.GetPageUpdatedTimeResponseDto;
import dev.gooiman.server.page.repository.PageRepository;
import dev.gooiman.server.page.repository.entity.Page;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PageService {

    private final PageRepository pageRepository;
    private final MemoRepository memoRepository;

    public Page getPageById(UUID id) {
        return pageRepository.findById(id)
            .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_PAGE));
    }

    @Transactional
    public CommonIdResponseDto create(CreatePageRequestDto dto) {
        Page page = new Page(dto.name());
        Page savedPage = pageRepository.save(page);
        return new CommonIdResponseDto(savedPage.getPageId());
    }

    public MemoSummariesResponseDto memoSummaries(UUID pageId) {
        Page page = getPageById(pageId);
        String name = page.getPageName();
        Map<String, Map<String, List<String>>> memoSummaries = memoRepository.getMemoSummaries(
                pageId)
            .stream().collect(
                Collectors.groupingBy(MemoSummariesView::getCategory,
                    Collectors.groupingBy(MemoSummariesView::getSubCategory,
                        Collectors.mapping(MemoSummariesView::getTitle, Collectors.toList()))));
        return new MemoSummariesResponseDto(name, memoSummaries);
    }

    public GetPageUpdatedTimeResponseDto getLastUpdatedPage(UUID pageId) {
        Page page = getPageById(pageId);
        return new GetPageUpdatedTimeResponseDto(page.getUpdateTime());
    }

    public void updatePageUpdateTime(Page page) {
        page.updateTime();
    }
}
