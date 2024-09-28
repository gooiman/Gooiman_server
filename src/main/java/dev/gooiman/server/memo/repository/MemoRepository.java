package dev.gooiman.server.memo.repository;

import dev.gooiman.server.memo.repository.entity.Memo;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemoRepository extends JpaRepository<Memo, UUID> {

  List<Memo> findMemosByPage_PageIdAndCategory(UUID pageId, String category);

  List<Memo> findMemosByPage_PageId(UUID pageId);
}
