package dev.gooiman.server.page.application;

import dev.gooiman.server.common.dto.CommonIdResponseDto;
import dev.gooiman.server.common.exception.CommonException;
import dev.gooiman.server.common.exception.ErrorCode;
import dev.gooiman.server.memo.application.domain.MemoSummaryList;
import dev.gooiman.server.memo.application.dto.MemoSummariesResponseDto;
import dev.gooiman.server.memo.repository.MemoRepository;
import dev.gooiman.server.page.application.dto.CreatePageRequestDto;
import dev.gooiman.server.page.repository.PageRepository;
import dev.gooiman.server.page.repository.entity.Page;
import java.util.UUID;
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
        MemoSummaryList memoList = new MemoSummaryList(memoRepository.getMemoSummaries(pageId));

        return new MemoSummariesResponseDto(name, memoList);
    }
}
