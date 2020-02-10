创建SQL
```sql
CREATE TABLE `user` (
	`id` INT PRIMARY KEY AUTO_INCREMENT,
	`account_id` VARCHAR(128) ,
	`name` VARCHAR(64) ,
	`token` CHAR(36) ,
	`gmt_create` BIGINT ,
	`password` VARCHAR(128),
	`user_type` INT,
	`avatar_url` VARCHAR(128)
);

CREATE TABLE question (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	title VARCHAR(50),
	description TEXT,
	gmtCreate BIGINT,
	createor BIGINT,
	commentCount INT DEFAULT 0,
	viewCount INT DEFAULT 0,
	likeCount INT DEFAULT 0,
	tag VARCHAR(256)
);

CREATE TABLE `comment` (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	parentId BIGINT,
	`type` INT,
	commentator BIGINT,
	gmtCreate BIGINT,
	content VARCHAR(1024),
	commentCount INT DEFAULT 0,
	likeCount INT DEFAULT 0
);
```

mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate

