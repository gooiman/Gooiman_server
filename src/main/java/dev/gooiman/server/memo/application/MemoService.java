package dev.gooiman.server.memo.application;


import dev.gooiman.server.common.exception.CommonException;
import dev.gooiman.server.common.exception.ErrorCode;
import dev.gooiman.server.memo.application.dto.CreateMemoDto;
import dev.gooiman.server.memo.application.dto.GetMemoDto;
import dev.gooiman.server.memo.repository.MemoRepository;
import dev.gooiman.server.memo.repository.entity.Memo;
import dev.gooiman.server.page.repository.PageRepository;
import dev.gooiman.server.page.repository.entity.Page;
import dev.gooiman.server.user.repository.UserRepository;
import dev.gooiman.server.user.repository.entity.User;
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

    public CreateMemoDto.Res create(CreateMemoDto createMemoDto)
        throws CommonException {
        String pageId = createMemoDto.getPageId();
        String author = createMemoDto.getAuthor();
        String title = createMemoDto.getTitle();
        String category = createMemoDto.getCategory();
        String content = createMemoDto.getContent();
        String subCategory = createMemoDto.getSubCategory();

        Optional<User> user = userRepository.findByNameAndPage_PageId(author,UUID.fromString(pageId));
        Optional<Page> page = pageRepository.findById(UUID.fromString(pageId));

        if (!page.isPresent()) {
            throw new CommonException(ErrorCode.NOT_FOUND_RESOURCE);
        }
        if (!user.isPresent()) {
            throw new CommonException(ErrorCode.NOT_FOUND_RESOURCE);
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

    public GetMemoDto.Res getMemo(String memoId) {
        Optional<Memo> memo = memoRepository.findById(UUID.fromString(memoId));
        if (!memo.isPresent()) {
            throw new CommonException(ErrorCode.NOT_FOUND_RESOURCE);
        }
        Memo findMemo = memo.get();
        return GetMemoDto.Res.builder()
                .id(String.valueOf(findMemo.getMemoId()))
                .title(findMemo.getTitle())
                .content(findMemo.getContent())
                .author(findMemo.getUser().getName())
                .category(findMemo.getCategory())
                .sub_category(findMemo.getSubCategory())
                .build();
    }

}



