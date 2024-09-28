package dev.gooiman.server.memo.application;


import dev.gooiman.server.common.dto.CommonIdResponseDto;
import dev.gooiman.server.common.dto.CommonSuccessDto;
import dev.gooiman.server.common.exception.CommonException;
import dev.gooiman.server.common.exception.ErrorCode;
import dev.gooiman.server.memo.application.dto.CreateMemoRequestDto;
import dev.gooiman.server.memo.application.dto.GetMemoResponseDto;
import dev.gooiman.server.memo.application.dto.MemoDto;
import dev.gooiman.server.memo.application.dto.UpdateMemoRequestDto;
import dev.gooiman.server.memo.repository.MemoRepository;
import dev.gooiman.server.memo.repository.entity.Memo;
import dev.gooiman.server.page.application.PageService;
import dev.gooiman.server.page.repository.entity.Page;
import dev.gooiman.server.user.application.UserService;
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
    private final PageService pageService;

    public MemoDto[] listMemo(UUID pageId, String category) {
        if (category != null) {
            return memoRepository.findMemosByPage_PageIdAndCategory(pageId, category)
                .stream()
                .map(MemoDto::fromEntity)
                .toArray(MemoDto[]::new);
        }
        return memoRepository.findMemosByPage_PageId(pageId)
            .stream()
            .map(MemoDto::fromEntity)
            .toArray(MemoDto[]::new);
    }

    @Transactional
    public CommonSuccessDto updateMemo(UUID memoId, @RequestBody UpdateMemoRequestDto dto) {
        Memo memo = memoRepository.findById(memoId)
            .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_MEMO));
        User user = userService.getUserByName(dto.author());
        memo.updateInfo(dto.title(), dto.content(), dto.category(), dto.subCategory(), dto.color(),
            user);

        return CommonSuccessDto.fromEntity(true);
    }

    @Transactional
    public CommonSuccessDto deleteMemo(UUID memoId) {
        Memo memo = memoRepository.findById(memoId)
            .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_MEMO));
        memoRepository.delete(memo);

        return CommonSuccessDto.fromEntity(true);
    }

    @Transactional
    public CommonIdResponseDto createMemo(CreateMemoRequestDto dto) {
        User user = userService.getUserByName(dto.author());
        Page page = pageService.getPageById(dto.pageId());
        Memo memo = new Memo(dto.category(), dto.subCategory(), dto.title(), dto.color(),
            dto.content(), page, user);
        Memo savedMemo = memoRepository.save(memo);

        return new CommonIdResponseDto(savedMemo.getMemoId());
    }

    public GetMemoResponseDto getMemo(UUID memoId) {
        Memo memo = memoRepository.findById(memoId)
            .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_MEMO));

        return new GetMemoResponseDto(memo.getMemoId(), memo.getTitle(), memo.getContent(),
            memo.getUsername(), memo.getCategory(), memo.getSubCategory());
    }
}
