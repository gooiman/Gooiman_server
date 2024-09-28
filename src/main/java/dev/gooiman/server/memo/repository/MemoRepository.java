package dev.gooiman.server.memo.repository;

import dev.gooiman.server.memo.repository.entity.Memo;
import dev.gooiman.server.memo.repository.view.MemoSummariesView;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemoRepository extends JpaRepository<Memo, UUID> {

    List<Memo> findMemosByPage_PageIdAndCategory(UUID pageId, String category);

    List<Memo> findMemosByPage_PageId(UUID pageId);

    @Query("select m.title as title, m.category as category, m.subCategory as subCategory from Memo m where m.page.pageId = :pageId")
    List<MemoSummariesView> getMemoSummaries(@Param("pageId") UUID pageId);

}
