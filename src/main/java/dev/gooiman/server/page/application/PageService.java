package dev.gooiman.server.page.application;

import dev.gooiman.server.common.dto.CommonIdResponseDto;
import dev.gooiman.server.common.exception.CommonException;
import dev.gooiman.server.common.exception.ErrorCode;
import dev.gooiman.server.memo.application.dto.MemoSummariesResponseDto;
import dev.gooiman.server.memo.repository.MemoRepository;
import dev.gooiman.server.page.application.dto.CreatePageRequestDto;
import dev.gooiman.server.page.repository.PageRepository;
import dev.gooiman.server.page.repository.entity.Page;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public MemoSummariesResponseDto.Res memoSummaries(UUID pageId) {
        Page page = getPageById(pageId);
        String name = page.getPageName();

        Map<String, Map<String, List<String>>> memoSummaries = new HashMap<>();
        List<Object[]> result = memoRepository.getMemoSummaries(pageId);
        for (Object[] row : result) {
            String title = (String) row[0];
            String category = (String) row[1];
            String subCategory = (String) row[2];

            memoSummaries.computeIfAbsent(category, k -> new HashMap<>())
                .computeIfAbsent(subCategory, k -> new ArrayList<>())
                .add(title);
        }
        return new MemoSummariesResponseDto.Res(name, memoSummaries);
    }

}
