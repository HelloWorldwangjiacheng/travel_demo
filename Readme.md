创建SQL
```sql
CREATE TABLE `user` (
	`id` BIGINT PRIMARY KEY AUTO_INCREMENT,
	`accountId` VARCHAR(128) ,
	`userName` VARCHAR(64) ,
	`token` CHAR(36) ,
	`gmtCreate` BIGINT ,
	`userPassword` VARCHAR(128),
	`userType` INT,
	`avatarUrl` VARCHAR(128),
    `userTelephone` VARCHAR(11),
    `userEmail` VARCHAR(32) 
);

CREATE TABLE question (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	title VARCHAR(50),
	description TEXT,
	gmtCreate BIGINT,
	creator BIGINT,
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

CREATE TABLE `sys_prov_city_dist` (
`id` INT(11) NOT NULL,
`province` VARCHAR(50) DEFAULT NULL,
`type` CHAR(1) DEFAULT NULL,
`city` VARCHAR(80) DEFAULT NULL,
`province_id` INT(6) DEFAULT NULL,
`district` VARCHAR(80) DEFAULT NULL,
`city_id` INT(6) DEFAULT NULL,
PRIMARY KEY (`id`)
);

CREATE TABLE `notification` (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	notifier BIGINT,
	notifierName VARCHAR(128),
	receiver BIGINT,
	outerId BIGINT,
	`type` INT,
	`status` INT,
	gmtCreate BIGINT,
	outerTitle VARCHAR(256)
);
```

mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate

