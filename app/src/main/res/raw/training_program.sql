BEGIN TRANSACTION;
DROP TABLE IF EXISTS `tp3`;
CREATE TABLE IF NOT EXISTS `tp3` (
	`day`	INTEGER,
	`number`	INTEGER,
	`exercise`	TEXT,
	`sets`	INTEGER,
	`reps`	TEXT,
	`rest`	INTEGER,
	`structure`	INTEGER
);
INSERT INTO `tp3` VALUES (1,0,'','','','','');
INSERT INTO `tp3` VALUES (2,0,'','','','','');
INSERT INTO `tp3` VALUES (3,0,'','','','','');
INSERT INTO `tp3` VALUES (4,0,'','','','','');
INSERT INTO `tp3` VALUES (5,1,'1010301',3,'8-12',120,'normal');
INSERT INTO `tp3` VALUES (5,2,'1010302',3,'8-12',120,'normal');
INSERT INTO `tp3` VALUES (5,3,'1010101',3,'8-12',120,'normal');
INSERT INTO `tp3` VALUES (5,4,'1010303',3,'8-12',120,'normal');
INSERT INTO `tp3` VALUES (5,5,'1020201,1010205',3,'8-12',120,'normal');
INSERT INTO `tp3` VALUES (5,6,'1010401',3,'10-15',60,'normal');
INSERT INTO `tp3` VALUES (5,7,'1010701',3,'10-15',60,'normal');
INSERT INTO `tp3` VALUES (5,8,'1010601',3,'10-15',60,'normal');
INSERT INTO `tp3` VALUES (5,9,'1010501,1010502',3,'10-15',60,'normal');
INSERT INTO `tp3` VALUES (6,0,'','','','','');
INSERT INTO `tp3` VALUES (7,1,'1010301',3,'8-12',120,'normal');
INSERT INTO `tp3` VALUES (7,2,'1010302',3,'8-12',120,'normal');
INSERT INTO `tp3` VALUES (7,3,'1010402',3,'8-12',120,'normal');
INSERT INTO `tp3` VALUES (7,4,'1010304',3,'10-15',60,'normal');
INSERT INTO `tp3` VALUES (7,5,'1010201',3,'8-12',120,'normal');
INSERT INTO `tp3` VALUES (7,6,'1010111',3,'10-15',60,'normal');
INSERT INTO `tp3` VALUES (7,7,'1010702',3,'8-12',120,'normal');
INSERT INTO `tp3` VALUES (7,8,'1010604',3,'10-15',60,'normal');
INSERT INTO `tp3` VALUES (7,9,'1010503',3,'10-15',60,'normal');
DROP TABLE IF EXISTS `program_list`;
CREATE TABLE IF NOT EXISTS `program_list` (
	`id`	TEXT,
	`name`	TEXT,
	`goal`	TEXT,
	`day_name`	TEXT,
	`year`	INTEGER,
	`month`	INTEGER,
	`day`	INTEGER,
	`start`	INTEGER,
	`note`	TEXT
);
INSERT INTO `program_list` VALUES ('tp3','全身式训练','保持','休息,休息,休息,休息,全身,休息,全身','','','',0,'“全身式”训练设计，是针对忙碌健身者，最为高效省时间的训练方案。它的设计原理，是每天锻炼全身所有主要肌肉群 - 多动作，少组数。\n“全身式”训练设计，1天就是一个训练周期，之后需要休息1-2天。因此，对于忙碌的学生或白领，如果只有周末有空，参考方案为：\n- 周一：学校/工作\n- 周二：学校/工作\n- 周三：学校/工作\n- 周四：学校/工作\n- 周五：全身\n- 周六：休息\n- 周日：全身');
COMMIT;