package dev.gooiman.server.user.repository;

import dev.gooiman.server.user.repository.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByPageAndName(String pageId, String name);
}
