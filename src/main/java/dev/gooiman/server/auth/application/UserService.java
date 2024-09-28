package dev.gooiman.server.auth.application;

import dev.gooiman.server.auth.repository.UserRepository;
import dev.gooiman.server.auth.repository.entity.User;
import dev.gooiman.server.common.exception.CommonException;
import dev.gooiman.server.common.exception.ErrorCode;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserByNameAndPageId(String name, UUID pageId) {
        return userRepository.findByNameAndPage_PageId(name, pageId)
            .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
    }
}
