package dev.gooiman.server.service;


import dev.gooiman.server.domain.Memo;
import dev.gooiman.server.domain.Page;
import dev.gooiman.server.domain.User;
import dev.gooiman.server.dto.CreateMemoDto;
import dev.gooiman.server.exception.BaseException;
import dev.gooiman.server.repository.MemoRepository;
import dev.gooiman.server.repository.PageRepository;
import dev.gooiman.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;
    private final UserRepository userRepository;
    private final PageRepository pageRepository;

    public CreateMemoDto.Res create(CreateMemoDto createMemoDto) throws BaseException {
        String pageId = createMemoDto.getPageId();
        String author = createMemoDto.getAuthor();
        String title = createMemoDto.getTitle();
        String category = createMemoDto.getCategory();
        String content = createMemoDto.getContent();
        String subCategory = createMemoDto.getSubCategory();

        Optional<User> user = userRepository.findByPageAndName(pageId, author);
        Optional<Page> page = pageRepository.findById(UUID.fromString(pageId));

        if (!page.isPresent()) {
            throw new BaseException();
        }
        if (!user.isPresent()) {
            throw new BaseException();
        }
        User userEntity = user.get();
        Page pageEntity = page.get();
        Memo memo = Memo.builder().title(title)
                .category(category)
                .subCategory(subCategory)
                .content(content)
                .user(userEntity)
                .page(pageEntity)
                .build();
        Memo savedMemo = memoRepository.save(memo);
        CreateMemoDto.Res res = CreateMemoDto.Res.mapEntityToDto(savedMemo);
        return res;
    }
}



