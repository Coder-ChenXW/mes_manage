CREATE TABLE mes.`role` (
	id BIGINT auto_increment NOT NULL,
	name varchar(64) NOT NULL,
	description varchar(64) NOT NULL,
	CONSTRAINT role_PK PRIMARY KEY (id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;



INSERT INTO mes.`role`(id, name, `description`) VALUES(1, 'admin', '管理员');
INSERT INTO mes.`role`(id, name, `description`) VALUES(2, 'hr', '人事');
INSERT INTO mes.`role`(id, name, `description`) VALUES(3, 'steper', '裁床');
INSERT INTO mes.`role`(id, name, `description`) VALUES(4, 'normal', '员工');
