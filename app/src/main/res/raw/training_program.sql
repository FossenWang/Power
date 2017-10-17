BEGIN TRANSACTION;
DROP TABLE IF EXISTS `tp1508060008888`;
CREATE TABLE IF NOT EXISTS `tp1508060008888` (
	`day`	INTEGER,
	`number`	INTEGER,
	`exercise`	TEXT,
	`sets`	INTEGER,
	`reps`	TEXT,
	`rest`	INTEGER,
	`structure`	TEXT
);
INSERT INTO `tp1508060008888` VALUES (5,1,'深蹲',3,'8~12',120,'normal');
INSERT INTO `tp1508060008888` VALUES (5,2,'硬拉',3,'8~12',120,'normal');
INSERT INTO `tp1508060008888` VALUES (5,3,'卧推',3,'8~12',120,'normal');
INSERT INTO `tp1508060008888` VALUES (5,4,'器械腿推',3,'8~12',120,'normal');
INSERT INTO `tp1508060008888` VALUES (5,5,'杠铃划船,阔背下拉',3,'8~12',120,'normal');
INSERT INTO `tp1508060008888` VALUES (5,6,'哑铃侧平举',3,'10~15',60,'normal');
INSERT INTO `tp1508060008888` VALUES (5,7,'站立小腿举',3,'10~15',60,'normal');
INSERT INTO `tp1508060008888` VALUES (5,8,'仰卧臂屈伸',3,'10~15',60,'normal');
INSERT INTO `tp1508060008888` VALUES (5,9,'哑铃弯举,杠铃弯举',3,'10~15',60,'normal');
INSERT INTO `tp1508060008888` VALUES (7,1,'深蹲',3,'8~12',120,'normal');
INSERT INTO `tp1508060008888` VALUES (7,2,'硬拉',3,'8~12',120,'normal');
INSERT INTO `tp1508060008888` VALUES (7,3,'杠铃肩推',3,'8~12',120,'normal');
INSERT INTO `tp1508060008888` VALUES (7,4,'哑铃弓步',3,'10~15',60,'normal');
INSERT INTO `tp1508060008888` VALUES (7,5,'杠铃划船',3,'8~12',120,'normal');
INSERT INTO `tp1508060008888` VALUES (7,6,'器械飞鸟',3,'10~15',60,'normal');
INSERT INTO `tp1508060008888` VALUES (7,7,'坐姿小腿举',3,'8~12',120,'normal');
INSERT INTO `tp1508060008888` VALUES (7,8,'绳索下压',3,'10~15',60,'normal');
INSERT INTO `tp1508060008888` VALUES (7,9,'锤式弯举',3,'10~15',60,'normal');
DROP TABLE IF EXISTS `tl20171013`;
CREATE TABLE IF NOT EXISTS `tl20171013` (
	`program_id`	TEXT,
	`number`	INTEGER,
	`exercise`	TEXT,
	`record`	TEXT,
	`structure`	TEXT
);
INSERT INTO `tl20171013` VALUES ('tp1508060008888',1,'深蹲','32.5×12,32.5×12,32.5×12,','normal');
INSERT INTO `tl20171013` VALUES ('tp1508060008888',2,'硬拉','32.5×12,32.5×12,32.5×12,','normal');
INSERT INTO `tl20171013` VALUES ('tp1508060008888',3,'卧推','32.5×12,32.5×12,32.5×12,','normal');
INSERT INTO `tl20171013` VALUES ('tp1508060008888',4,'器械腿推','32.5×12,32.5×12,32.5×12,','normal');
INSERT INTO `tl20171013` VALUES ('tp1508060008888',5,'杠铃划船','32.5×12,32.5×12,32.5×12,','normal');
INSERT INTO `tl20171013` VALUES ('tp1508060008888',6,'哑铃侧平举','32.5×12,32.5×12,32.5×12,','normal');
INSERT INTO `tl20171013` VALUES ('tp1508060008888',7,'站立小腿举','32.5×12,32.5×12,32.5×12,','normal');
INSERT INTO `tl20171013` VALUES ('tp1508060008888',8,'仰卧臂屈伸','32.5×12,32.5×12,32.5×12,','normal');
INSERT INTO `tl20171013` VALUES ('tp1508060008888',9,'哑铃弯举','32.5×12,32.5×12,32.5×12,','normal');
DROP TABLE IF EXISTS `program_list`;
CREATE TABLE IF NOT EXISTS `program_list` (
	`id`	TEXT,
	`name`	TEXT,
	`goal`	TEXT,
	`day_name`	TEXT,
	`start`	INTEGER,
	`date`	INTEGER,
	`note`	TEXT
);
INSERT INTO `program_list` VALUES ('tp1508060008888','全身式训练','保持','休息,休息,休息,休息,全身,休息,全身',0,20170101,'“全身式”训练设计，是针对忙碌健身者，最为高效省时间的训练方案。它的设计原理，是每天锻炼全身所有主要肌肉群 - 多动作，少组数。\n“全身式”训练设计，1天就是一个训练周期，之后需要休息1-2天。因此，对于忙碌的学生或白领，如果只有周末有空，参考方案为：\n- 周一：学校/工作\n- 周二：学校/工作\n- 周三：学校/工作\n- 周四：学校/工作\n- 周五：全身\n- 周六：休息\n- 周日：全身');
DROP TABLE IF EXISTS `log_catalog`;
CREATE TABLE IF NOT EXISTS `log_catalog` (
	`date`	INTEGER,
	`program_id`	TEXT,
	`program_name`	TEXT,
	`day_name`	TEXT
);
INSERT INTO `log_catalog` VALUES (20171013,'tp1508060008888','全身式训练','全身');
COMMIT;
