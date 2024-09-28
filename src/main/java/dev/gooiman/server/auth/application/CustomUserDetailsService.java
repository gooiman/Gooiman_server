package dev.gooiman.server.auth.application;


import dev.gooiman.server.common.exception.CommonException;
import dev.gooiman.server.common.exception.ErrorCode;
import dev.gooiman.server.auth.application.domain.CustomUserDetails;
import dev.gooiman.server.auth.repository.UserRepository;
import dev.gooiman.server.auth.repository.entity.User;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService {

    private final UserRepository userRepository;

    public Optional<CustomUserDetails> loadUserByUsername(UUID pageId, String name)
        throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByNameAndPage_PageId(name, pageId);

        return user.map(CustomUserDetails::new);
    }

    public CustomUserDetails loadUserByUserId(String userId) {
        UUID uuid = UUID.fromString(userId);
        User user = userRepository.findById(uuid)
            .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        return new CustomUserDetails(user);
    }
}
