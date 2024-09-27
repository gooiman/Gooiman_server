package dev.gooiman.server.repository;

import dev.gooiman.server.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PageRepository extends JpaRepository<Page, UUID> {
}
