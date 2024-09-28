package dev.gooiman.server.page.repository.entity;

import dev.gooiman.server.common.repository.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Timestamp;
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
@Table(name = "page")
public class Page extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID pageId;

    private String pageName;

    public Page(String pageName) {
        this.pageName = pageName;
    }

    public void updateTime() {
        this.updateTime = new Timestamp(System.currentTimeMillis());
    }
}
