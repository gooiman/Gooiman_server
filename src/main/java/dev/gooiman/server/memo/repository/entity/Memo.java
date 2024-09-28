package dev.gooiman.server.memo.repository.entity;

import dev.gooiman.server.page.repository.entity.Page;
import dev.gooiman.server.auth.repository.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "memo")
@DynamicUpdate
public class Memo {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID memoId;
    private String category;
    private String subCategory;
    private String title;
    private String color;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PAGE_ID")
    private Page page;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "USER_ID", insertable = false, updatable = false)
    private UUID userID;

    public String getUsername() {
        return user.getName();
    }

    public Memo(String category, String subCategory, String title, String color,
        String content, Page page, User user) {
        this.category = category;
        this.subCategory = subCategory;
        this.title = title;
        this.color = color;
        this.content = content;
        this.page = page;
        this.user = user;
    }

    public void updateInfo(String title, String content, String category, String subCategory,
        String color, User user) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.subCategory = subCategory;
        this.color = color;
        this.user = user;
    }
}
