package dev.gooiman.server.memo.application;


import dev.gooiman.server.memo.application.dto.MemoDto;
import dev.gooiman.server.memo.repository.MemoRepository;
import dev.gooiman.server.page.repository.PageRepository;
import dev.gooiman.server.user.repository.UserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;
    private final UserRepository userRepository;
    private final PageRepository pageRepository;

    public MemoDto[] listMemo(UUID pageId, String category) {
        return null;
    }

//    public CreateMemoResponseDto.Res create(CreateMemoResponseDto createMemoDto)
//        throws BaseException {
//        String pageId = createMemoDto.getPageId();
//        String author = createMemoDto.getAuthor();
//        String title = createMemoDto.getTitle();
//        String category = createMemoDto.getCategory();
//        String content = createMemoDto.getContent();
//        String subCategory = createMemoDto.getSubCategory();
//
//        Optional<User> user = userRepository.findByPageAndName(pageId, author);
//        Optional<Page> page = pageRepository.findById(UUID.fromString(pageId));
//
//        if (!page.isPresent()) {
//            throw new BaseException();
//        }
//        if (!user.isPresent()) {
//            throw new BaseException();
//        }
//        User userEntity = user.get();
//        Page pageEntity = page.get();
//        Memo memo = Memo.builder().title(title)
//            .category(category)
//            .subCategory(subCategory)
//            .content(content)
//            .user(userEntity)
//            .page(pageEntity)
//            .build();
//        Memo savedMemo = memoRepository.save(memo);
//        CreateMemoResponseDto.Res res = CreateMemoResponseDto.Res.mapEntityToDto(savedMemo);
//        return res;
//    }
}



