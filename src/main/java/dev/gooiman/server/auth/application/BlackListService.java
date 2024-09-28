package dev.gooiman.server.auth.application;

import dev.gooiman.server.auth.repository.BlackListRepository;
import dev.gooiman.server.auth.repository.entity.BlackList;
import dev.gooiman.server.common.dto.CommonSuccessDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlackListService {

    private final BlackListRepository blackListRepository;

    public CommonSuccessDto saveBlackList(String token) {
        BlackList entity = new BlackList(token);
        blackListRepository.save(entity);
        return CommonSuccessDto.fromEntity(true);
    }
}
