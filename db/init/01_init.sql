-- Docker 環境用 DB 初期化 SQL
-- コンテナ初回起動時に自動実行される

USE user;

CREATE TABLE IF NOT EXISTS tb_example (
    id int(11) NOT NULL,
    name varchar(255) NOT NULL,
    created_at datetime NOT NULL,
    updated_at datetime NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- サンプルデータの投入
INSERT INTO tb_example VALUES(1, 'name1', NOW(), NOW());
INSERT INTO tb_example VALUES(2, 'name2', NOW(), NOW());
INSERT INTO tb_example VALUES(3, 'name3', NOW(), NOW());
INSERT INTO tb_example VALUES(4, 'name4', NOW(), NOW());
INSERT INTO tb_example VALUES(5, 'name5', NOW(), NOW());
