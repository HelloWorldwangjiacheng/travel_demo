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
```

