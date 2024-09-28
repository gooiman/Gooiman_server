package dev.gooiman.server.user.repository;

import dev.gooiman.server.user.repository.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("select u from User u"
        + " where u.name=:name")
    Optional<User> findByName(@Param("name") String name);

    Optional<User> findByNameAndPage_PageId(String name, UUID pageId);
}
