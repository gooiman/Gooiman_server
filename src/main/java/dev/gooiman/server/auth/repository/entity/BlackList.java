package dev.gooiman.server.auth.repository.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@RedisHash(value = "token")
public class BlackList {

    @Id
    private final String token;

    @TimeToLive
    private final Long expiration;

    public BlackList(String token, Long expiration) {
        this.token = token;
        this.expiration = expiration;
    }
}
