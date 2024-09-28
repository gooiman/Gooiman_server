package dev.gooiman.server.page.repository.entity;

import dev.gooiman.server.memo.repository.entity.Memo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Page {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID pageId;

    private String pageName;

    @OneToMany(mappedBy = "page")
    private List<Memo> memoList = new ArrayList<>();
}
