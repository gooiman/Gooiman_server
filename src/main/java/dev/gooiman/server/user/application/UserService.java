package dev.gooiman.server.user.application;

import dev.gooiman.server.common.exception.CommonException;
import dev.gooiman.server.common.exception.ErrorCode;
import dev.gooiman.server.user.repository.UserRepository;
import dev.gooiman.server.user.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserByName(String name) {
        return userRepository.findByName(name)
            .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
    }
}
