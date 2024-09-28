package dev.gooiman.server.user;

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
        return userRepository.findByName(name).orElseThrow(IllegalArgumentException::new);
    }
}
