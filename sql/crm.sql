CREATE TABLE `table` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `table_name` VARCHAR(50) NOT NULL COMMENT '表名',
  `name` VARCHAR(50) NOT NULL COMMENT '中文名',
  `can_edit` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '0可编辑，1不可编辑',
  `is_delete` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '0未删除，1已删除',
  `type` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '类型 0主键自增表 1关联表 2标准表 3附件表',
  `remark` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '备注',
  `create_at` INT(11) NOT NULL DEFAULT '0',
  `create_id` INT(11) NOT NULL DEFAULT '0',
  `create_name` VARCHAR(32) NOT NULL DEFAULT '',
  `modify_at` INT(11) NOT NULL DEFAULT '0',
  `modify_id` INT(11) NOT NULL DEFAULT '0',
  `modify_name` VARCHAR(32) NOT NULL DEFAULT '',
  `modify_actionid` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '业务动作标识',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `field` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `table_id` INT(11) NOT NULL DEFAULT '0' COMMENT '表id',
  `field_name` VARCHAR(50) NOT NULL COMMENT '字段名',
  `name` VARCHAR(50) NOT NULL COMMENT '中文名',
  `is_relative` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '是否关联表 0否，1是',
  `relative_table_name` VARCHAR(50) NOT NULL COMMENT '关联表名',
  `relative_field_id` INT(11) NOT NULL DEFAULT '0' COMMENT '关联字段id',
  `can_edit` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '0可编辑，1不可编辑',
  `is_delete` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '0未删除，1已删除',
  `type` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '字段类型 0整形 1浮点型 2文本 3富文本 4日期类型',
  `field_length` INT(11) NOT NULL DEFAULT '0' COMMENT '字段长度',
  `is_not_null` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '是否必填 0非必填，1必填',
  `create_at` INT(11) NOT NULL DEFAULT '0',
  `create_id` INT(11) NOT NULL DEFAULT '0',
  `create_name` VARCHAR(32) NOT NULL DEFAULT '',
  `modify_at` INT(11) NOT NULL DEFAULT '0',
  `modify_id` INT(11) NOT NULL DEFAULT '0',
  `modify_name` VARCHAR(32) NOT NULL DEFAULT '',
  `modify_actionid` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '业务动作标识',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `file` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `filename` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '文件正常名称',
  `onlyname` VARCHAR(200) NOT NULL DEFAULT '' COMMENT '文件唯一名称',
  `filepath` VARBINARY(200) NOT NULL DEFAULT '' COMMENT '文件路径',
  `create_id` INT(11) NOT NULL DEFAULT '0' COMMENT '创建人id',
  `create_name` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_at` INT(11) NOT NULL DEFAULT '0' COMMENT '创建时间',
  `modify_id` INT(11) NOT NULL DEFAULT '0' COMMENT '修改人',
  `modify_name` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '修改人姓名',
  `modify_at` INT(11) NOT NULL DEFAULT '0' COMMENT '修改时间',
  `modify_actionid` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '业务动作标识',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='附件';



CREATE TABLE `form` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `table_name` VARCHAR(50) NOT NULL COMMENT '表名',
  `name` VARCHAR(50) NOT NULL COMMENT '中文名',
  `can_edit` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '0可编辑，1不可编辑',
  `is_delete` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '0未删除，1已删除',
  `type` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '类型 0主键自增表 1关联表 2标准表 3附件表',
  `remark` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '备注',
  `create_at` INT(11) NOT NULL DEFAULT '0',
  `create_id` INT(11) NOT NULL DEFAULT '0',
  `create_name` VARCHAR(32) NOT NULL DEFAULT '',
  `modify_at` INT(11) NOT NULL DEFAULT '0',
  `modify_id` INT(11) NOT NULL DEFAULT '0',
  `modify_name` VARCHAR(32) NOT NULL DEFAULT '',
  `modify_actionid` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '业务动作标识',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;











