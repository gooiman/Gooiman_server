CREATE DATABASE gooiman;

drop table if exists memo;
drop table if exists users;
drop table if exists page;

CREATE TABLE page
(
    page_id   BINARY(16)   NOT NULL PRIMARY KEY,
    page_name VARCHAR(255) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;


CREATE TABLE users
(
    user_id  BINARY(16)   NOT NULL PRIMARY KEY,
    name     VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    page_id  BINARY(16),
    CONSTRAINT fk_users_page FOREIGN KEY (page_id) REFERENCES page (page_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;


CREATE TABLE memo
(
    memo_id      BINARY(16)   NOT NULL PRIMARY KEY,
    category     VARCHAR(255),
    sub_category VARCHAR(255),
    title        VARCHAR(255) NOT NULL,
    content      TEXT,
    page_id      BINARY(16),
    user_id      BINARY(16),
    CONSTRAINT fk_memo_page FOREIGN KEY (page_id) REFERENCES page (page_id),
    CONSTRAINT fk_memo_user FOREIGN KEY (user_id) REFERENCES users (user_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- page 더미
INSERT INTO page (page_id, page_name)
VALUES (UUID_TO_BIN('dbdfa00c-4292-48c2-92b4-97c643e6dd5a'), 'page1');
-- (UUID_TO_BIN('dbee3cdf-20e1-4ddb-a510-3cf5909f875c'), 'page2'),
-- (UUID_TO_BIN('1cf08322-da12-4838-a1ea-3e45ffc701a6'), 'page3');

-- user1 더미 추가
INSERT INTO users (user_id, name, password, page_id)
VALUES (UUID_TO_BIN('382afa8a-79d2-4fe8-9f61-2c4db816ee3a'), 'user1', '1234',
        UUID_TO_BIN('dbdfa00c-4292-48c2-92b4-97c643e6dd5a'));
-- (UUID_TO_BIN('082e940e-cd05-49b1-b5e5-e62eeb3a478b'), 'Bob', 'password456', NULL),
-- (UUID_TO_BIN('41f69597-45d2-4732-8c4b-68775cbf7468'), 'Charlie', 'password789', NULL);

-- user1의 memo 추가
INSERT INTO memo (memo_id, category, sub_category, title, content, page_id, user_id)
VALUES (UUID_TO_BIN('24a4aea3-eb63-475c-b9e3-212d58986980'), 'category1', 'sub_category1', 'title1',
        'content1.', UUID_TO_BIN('dbdfa00c-4292-48c2-92b4-97c643e6dd5a'),
        UUID_TO_BIN('382afa8a-79d2-4fe8-9f61-2c4db816ee3a')),
       (UUID_TO_BIN('4bb960d6-4dc1-4b31-a3c7-333c5bd589d2'), 'category2', 'sub_category2', 'title2',
        'content2', UUID_TO_BIN('dbdfa00c-4292-48c2-92b4-97c643e6dd5a'),
        UUID_TO_BIN('382afa8a-79d2-4fe8-9f61-2c4db816ee3a')),
       (UUID_TO_BIN('cfda99e4-2d9f-4188-9498-4dea0bdb9011'), 'category3', 'sub_category3', 'title3',
        '3', UUID_TO_BIN('dbdfa00c-4292-48c2-92b4-97c643e6dd5a'),
        UUID_TO_BIN('382afa8a-79d2-4fe8-9f61-2c4db816ee3a'));

select *
from page;
select *
from users;
select *
from memo;