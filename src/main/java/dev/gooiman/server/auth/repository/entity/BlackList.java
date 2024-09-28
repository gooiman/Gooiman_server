package dev.gooiman.server.auth.repository.entity;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@RedisHash(value = "token")
public class BlackList {

    @Id
    private String token;

    @TimeToLive
    @Value("${spring.security.blacklist-validity-time}")
    private Long expiration;

    public BlackList(String token) {
        this.token = token;
    }
}
