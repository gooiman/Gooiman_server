package dev.gooiman.server.repository;

import dev.gooiman.server.domain.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MemoRepository extends JpaRepository<Memo, UUID> {

}
