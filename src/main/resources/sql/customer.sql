create table if not exists `customer` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`name` varchar(255) default null,
`contact` varchar(255) default null,
`telephone` varchar(255) default null,
`email` varchar(255) default null,
`remark` text,
primary key(`id`)	
)engine=InnoDB default charset=utf8;