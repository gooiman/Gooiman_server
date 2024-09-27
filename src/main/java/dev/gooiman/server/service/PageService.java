package dev.gooiman.server.service;

import dev.gooiman.server.domain.Memo;
import dev.gooiman.server.domain.Page;
import dev.gooiman.server.dto.CreatePageDto;
import dev.gooiman.server.dto.MemoSummariseDto;
import dev.gooiman.server.repository.MemoRepository;
import dev.gooiman.server.repository.PageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PageService {

    private final PageRepository pageRepository;
    private final MemoRepository memoRepository;

    public CreatePageDto.Res create(CreatePageDto createPageDto) {
        String name = createPageDto.getName();
        Page page = Page.builder().pageName(name).build();
        Page savedPage = pageRepository.save(page);
        return CreatePageDto.Res.mapEntityToDto(savedPage);
    }

    public MemoSummariseDto.Res memoSummarise(String pageId) {
        Optional<Page> page = pageRepository.findById(UUID.fromString(pageId));
        Page pageEntity = page.get();
        String pageName = pageEntity.getPageName();
        memoRepository.findById()

    }
}
