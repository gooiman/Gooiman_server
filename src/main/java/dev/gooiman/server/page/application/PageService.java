package dev.gooiman.server.page.application;

import dev.gooiman.server.common.exception.BaseException;
import dev.gooiman.server.memo.application.dto.MemoSummariseResponseDto;
import dev.gooiman.server.memo.repository.MemoRepository;
import dev.gooiman.server.memo.repository.entity.Memo;
import dev.gooiman.server.page.application.dto.CreatePageResponseDto;
import dev.gooiman.server.page.repository.PageRepository;
import dev.gooiman.server.page.repository.entity.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PageService {

    private final PageRepository pageRepository;
    private final MemoRepository memoRepository;

    @Transactional
    public CreatePageResponseDto.Res create(CreatePageResponseDto createPageDto) {
        String name = createPageDto.getName();
        Page page = Page.builder().pageName(name).build();
        Page savedPage = pageRepository.save(page);
        return CreatePageResponseDto.Res.mapEntityToDto(savedPage);
    }

    public MemoSummariseResponseDto.Res memoSummarise(UUID pageId) {
        System.out.println(pageId);
        System.out.println("+++++++++++++++++++++++++");
        Optional<Page> page = pageRepository.findById(pageId);
        System.out.println("+++++++++++++++++++++++++");
        System.out.println(pageId);
        Page pageEntity = page.get();
        String pageName = pageEntity.getPageName();
        Map<String, Map<String, List<String>>> memoSummarise = new HashMap<>();
        List<Object[]> result = memoRepository.getMemoSummarise(pageId);
        for (Object[] row : result) {
            String title = (String) row[0];
            String category = (String) row[1];
            String subCategory = (String) row[2];

            memoSummarise.computeIfAbsent(category, k -> new HashMap<>())
                    .computeIfAbsent(subCategory, k -> new ArrayList<>())
                    .add(title);
        }
        return new MemoSummariseResponseDto.Res(pageName, memoSummarise);
    }

}
