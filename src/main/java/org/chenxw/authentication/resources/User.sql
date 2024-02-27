CREATE TABLE mes.`user` (
	id BIGINT auto_increment NOT NULL,
	username varchar(64) NOT NULL,
	password varchar(128) NOT NULL,
	nickname varchar(64) NULL,
	avatar varchar(256) NULL,
	age INT NULL,
	tel varchar(32) NULL,
	gender INT NULL,
	email varchar(64) NULL,
	status INT(64) NULL,
	create_ts DATETIME NOT NULL,
	last_login_ts DATETIME NULL,
	last_login_ip varchar(128) NULL,
	CONSTRAINT user_PK PRIMARY KEY (id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;



INSERT INTO mes.`user`
(id, username, password, nickname, avatar, age, tel, gender, email, status, create_ts, last_login_ts, last_login_ip)
VALUES(1, 'admin', '$2a$10$542GWT/AyjXBUjMV6L198.0.1sCHRiAERTLs5Uro/hjRjiccQdPc6', '管理员', '/avatar.png', 26, '13000000000', 1, 'admin@mes.com', 1, '2021-10-30 10:22:41', NULL, NULL);
