package dev.gooiman.server.memo.application;


import dev.gooiman.server.common.exception.CommonException;
import dev.gooiman.server.common.exception.ErrorCode;
import dev.gooiman.server.common.dto.CommonSuccessDto;
import dev.gooiman.server.memo.application.dto.UpdateMemoRequestDto;
import dev.gooiman.server.memo.repository.MemoRepository;
import dev.gooiman.server.memo.repository.entity.Memo;
import dev.gooiman.server.page.repository.PageRepository;
import dev.gooiman.server.user.application.UserService;
import dev.gooiman.server.user.repository.UserRepository;
import dev.gooiman.server.user.repository.entity.User;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final PageRepository pageRepository;

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

    @Transactional
    public CommonSuccessDto updateMemo(String memoId, @RequestBody UpdateMemoRequestDto dto) {
        UUID uuid = UUID.fromString(memoId);
        Memo memo = memoRepository.findById(uuid)
            .orElseThrow(() -> new CommonException(ErrorCode.NOT_MATCH_AUTH_CODE));
        User user = userService.getUserByName(dto.author());
        memo.updateInfo(dto.title(), dto.content(), dto.category(), dto.subCategory(), user);

        return CommonSuccessDto.fromEntity(true);
    }

    @Transactional
    public CommonSuccessDto deleteMemo(String memoId) {
        UUID uuid = UUID.fromString(memoId);
        Memo memo = memoRepository.findById(uuid)
            .orElseThrow(() -> new CommonException(ErrorCode.NOT_MATCH_AUTH_CODE));
        memoRepository.delete(memo);

        return CommonSuccessDto.fromEntity(true);
    }
}



