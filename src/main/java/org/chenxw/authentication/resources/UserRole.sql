CREATE TABLE mes.`user_role` (
	id BIGINT auto_increment NOT NULL,
	user_id BIGINT NOT NULL,
	role_id BIGINT NOT NULL,
	CONSTRAINT user_role_PK PRIMARY KEY (id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;


INSERT INTO mes.user_role
(id, user_id, role_id)
VALUES(1, 1, 1);
