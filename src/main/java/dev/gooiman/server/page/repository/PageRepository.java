package dev.gooiman.server.page.repository;

import dev.gooiman.server.page.repository.entity.Page;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageRepository extends JpaRepository<Page, UUID> {

}
