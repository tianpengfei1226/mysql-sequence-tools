CREATE TABLE `t_sequence` (
  `name` VARCHAR(32) NOT NULL,
  `current_value` INT(11) NOT NULL,
  `increment` SMALLINT(6) NOT NULL,
  `total` SMALLINT(8) NOT NULL,
  `threshold` SMALLINT(4) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='sequence'