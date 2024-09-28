package dev.gooiman.server.memo.repository;

import dev.gooiman.server.memo.repository.entity.Memo;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemoRepository extends JpaRepository<Memo, UUID> {

    @Query("select m.title, m.category, m.subCategory from Memo m where m.page = :pageId")
    List<Object[]> getMemoSummarise(@Param("pageId") UUID pageId);

}
