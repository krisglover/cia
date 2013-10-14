CREATE SCHEMA `Configs` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;

delimiter $$

CREATE TABLE `Configs`.`Global` (
  `name` varchar(45) NOT NULL,
  `value` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$

CREATE TABLE `Configs`.`Environment` (
  `name` varchar(45) NOT NULL,
  `value` varchar(45) DEFAULT NULL,
  `environment` varchar(45) NOT NULL DEFAULT '',
  PRIMARY KEY (`name`,`environment`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$

CREATE TABLE `Configs`.`Application` (
  `name` varchar(45) NOT NULL,
  `value` varchar(45) DEFAULT NULL,
  `environment` varchar(45) DEFAULT NULL,
  `application` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`name`,`environment`,`application`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$

CREATE TABLE `Configs`.`History` (
  `name` varchar(32) NOT NULL,
  `value` varchar(45) DEFAULT NULL,
  `environment` varchar(45) NOT NULL DEFAULT '',
  `application` varchar(45) NOT NULL DEFAULT '',
  `version` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`version`,`name`,`environment`,`application`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
