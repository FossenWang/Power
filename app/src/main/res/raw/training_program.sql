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
INSERT INTO `tp1508060008888` VALUES (7,5,'杠铃划船,单臂哑铃划船',3,'8~12',120,'normal');
INSERT INTO `tp1508060008888` VALUES (7,6,'器械飞鸟',3,'10~15',60,'normal');
INSERT INTO `tp1508060008888` VALUES (7,7,'坐姿小腿举',3,'8~12',120,'normal');
INSERT INTO `tp1508060008888` VALUES (7,8,'绳索下压',3,'10~15',60,'normal');
INSERT INTO `tp1508060008888` VALUES (7,9,'锤式弯举',3,'10~15',60,'normal');
DROP TABLE IF EXISTS `tp1508060008887`;
CREATE TABLE IF NOT EXISTS `tp1508060008887` (
	`day`	INTEGER,
	`number`	INTEGER,
	`exercise`	TEXT,
	`sets`	INTEGER,
	`reps`	TEXT,
	`rest`	INTEGER,
	`structure`	TEXT
);
INSERT INTO `tp1508060008887` VALUES (1,1,'深蹲',5,'8~12',120,'normal');
INSERT INTO `tp1508060008887` VALUES (1,2,'器械腿推',3,'8~12',120,'normal');
INSERT INTO `tp1508060008887` VALUES (1,3,'哑铃弓步',3,'10~15',60,'normal');
INSERT INTO `tp1508060008887` VALUES (1,4,'卧姿腿弯举',3,'10~15',60,'normal');
INSERT INTO `tp1508060008887` VALUES (1,5,'站立小腿举',3,'10~15',60,'normal');
INSERT INTO `tp1508060008887` VALUES (2,1,'卧推,哑铃卧推',5,'8~12',120,'normal');
INSERT INTO `tp1508060008887` VALUES (2,2,'上斜哑铃卧推,上斜卧推',3,'8~12',120,'normal');
INSERT INTO `tp1508060008887` VALUES (2,3,'绳索夹胸,器械夹胸',3,'10~15',60,'normal');
INSERT INTO `tp1508060008887` VALUES (2,4,'杠铃肩推,哑铃肩推',3,'8~12',120,'normal');
INSERT INTO `tp1508060008887` VALUES (2,5,'哑铃侧平举',3,'10~15',60,'normal');
INSERT INTO `tp1508060008887` VALUES (2,6,'仰卧臂屈伸,俯身臂屈伸',3,'10~15',60,'normal');
INSERT INTO `tp1508060008887` VALUES (3,1,'硬拉',5,'8~12',120,'normal');
INSERT INTO `tp1508060008887` VALUES (3,2,'阔背下拉',3,'8~12',120,'normal');
INSERT INTO `tp1508060008887` VALUES (3,3,'杠铃划船',3,'8~12',120,'normal');
INSERT INTO `tp1508060008887` VALUES (3,4,'绳索面拉',3,'10~15',60,'normal');
INSERT INTO `tp1508060008887` VALUES (3,5,'哑铃弯举,杠铃弯举',3,'10~15',60,'normal');
INSERT INTO `tp1508060008887` VALUES (4,1,'深蹲',5,'8~12',120,'normal');
INSERT INTO `tp1508060008887` VALUES (4,2,'器械腿推',3,'8~12',120,'normal');
INSERT INTO `tp1508060008887` VALUES (4,3,'坐姿腿屈伸',3,'10~15',60,'normal');
INSERT INTO `tp1508060008887` VALUES (4,4,'卧姿腿弯举',3,'10~15',60,'normal');
INSERT INTO `tp1508060008887` VALUES (4,5,'坐姿小腿举',3,'10~15',60,'normal');
INSERT INTO `tp1508060008887` VALUES (5,1,'卧推,哑铃卧推',5,'8~12',120,'normal');
INSERT INTO `tp1508060008887` VALUES (5,2,'下斜哑铃卧推,下斜卧推',3,'8~12',120,'normal');
INSERT INTO `tp1508060008887` VALUES (5,3,'下斜绳索夹胸',3,'10~15',60,'normal');
INSERT INTO `tp1508060008887` VALUES (5,4,'哑铃肩推,杠铃肩推',3,'8~12',120,'normal');
INSERT INTO `tp1508060008887` VALUES (5,5,'哑铃侧平举',3,'10~15',60,'normal');
INSERT INTO `tp1508060008887` VALUES (5,6,'绳索下压,直杆下压',3,'10~15',60,'normal');
INSERT INTO `tp1508060008887` VALUES (6,1,'硬拉',5,'8~12',120,'normal');
INSERT INTO `tp1508060008887` VALUES (6,2,'阔背下拉',3,'8~12',120,'normal');
INSERT INTO `tp1508060008887` VALUES (6,3,'单臂哑铃划船',3,'8~12',120,'normal');
INSERT INTO `tp1508060008887` VALUES (6,4,'绳索面拉',3,'10~15',60,'normal');
INSERT INTO `tp1508060008887` VALUES (6,5,'锤式弯举',3,'10~15',60,'normal');
DROP TABLE IF EXISTS `tp1508060008886`;
CREATE TABLE IF NOT EXISTS `tp1508060008886` (
	`day`	INTEGER,
	`number`	INTEGER,
	`exercise`	TEXT,
	`sets`	INTEGER,
	`reps`	TEXT,
	`rest`	INTEGER,
	`structure`	TEXT
);
INSERT INTO `tp1508060008886` VALUES (1,1,'深蹲',5,'8~12',120,'normal');
INSERT INTO `tp1508060008886` VALUES (1,2,'器械腿推',4,'8~12',120,'normal');
INSERT INTO `tp1508060008886` VALUES (1,3,'哑铃弓步',3,'10~15',60,'normal');
INSERT INTO `tp1508060008886` VALUES (1,4,'坐姿腿屈伸',3,'10~15',60,'normal');
INSERT INTO `tp1508060008886` VALUES (1,5,'卧姿腿弯举',3,'10~15',60,'normal');
INSERT INTO `tp1508060008886` VALUES (1,6,'站立小腿举,坐姿小腿举',4,'10~15',60,'normal');
INSERT INTO `tp1508060008886` VALUES (2,1,'卧推,哑铃卧推',5,'8~12',120,'normal');
INSERT INTO `tp1508060008886` VALUES (2,2,'上斜哑铃卧推,上斜卧推',4,'8~12',120,'normal');
INSERT INTO `tp1508060008886` VALUES (2,3,'绳索夹胸,器械夹胸',3,'10~15',60,'normal');
INSERT INTO `tp1508060008886` VALUES (2,4,'下斜绳索夹胸',3,'10~15',60,'normal');
INSERT INTO `tp1508060008886` VALUES (2,5,'上斜绳索夹胸',3,'10~15',60,'normal');
INSERT INTO `tp1508060008886` VALUES (2,6,'仰卧臂屈伸,俯身臂屈伸',4,'10~15',60,'normal');
INSERT INTO `tp1508060008886` VALUES (3,1,'硬拉',5,'8~12',120,'normal');
INSERT INTO `tp1508060008886` VALUES (3,2,'阔背下拉',4,'8~12',120,'normal');
INSERT INTO `tp1508060008886` VALUES (3,3,'杠铃划船',3,'8~12',120,'normal');
INSERT INTO `tp1508060008886` VALUES (3,4,'单臂哑铃划船',3,'10~15',60,'normal');
INSERT INTO `tp1508060008886` VALUES (3,5,'绳索面拉',3,'10~15',60,'normal');
INSERT INTO `tp1508060008886` VALUES (3,6,'哑铃弯举,杠铃弯举',4,'10~15',60,'normal');
INSERT INTO `tp1508060008886` VALUES (4,1,'杠铃肩推',5,'8~12',120,'normal');
INSERT INTO `tp1508060008886` VALUES (4,2,'哑铃肩推',4,'8~12',120,'normal');
INSERT INTO `tp1508060008886` VALUES (4,3,'哑铃侧平举',3,'10~15',60,'normal');
INSERT INTO `tp1508060008886` VALUES (4,4,'哑铃前平举',3,'10~15',60,'normal');
INSERT INTO `tp1508060008886` VALUES (4,5,'俯身哑铃侧平举',3,'10~15',60,'normal');
INSERT INTO `tp1508060008886` VALUES (4,6,'绳索面拉',4,'10~15',60,'normal');
INSERT INTO `tp1508060008886` VALUES (5,1,'仰卧臂屈伸',4,'10~15',60,'normal');
INSERT INTO `tp1508060008886` VALUES (5,2,'哑铃弯举,杠铃弯举',4,'10~15',60,'normal');
INSERT INTO `tp1508060008886` VALUES (5,3,'绳索下压,直杆下压',3,'10~15',60,'normal');
INSERT INTO `tp1508060008886` VALUES (5,4,'锤式弯举',3,'10~15',60,'normal');
INSERT INTO `tp1508060008886` VALUES (5,5,'俯身臂屈伸',3,'10~15',60,'normal');
INSERT INTO `tp1508060008886` VALUES (5,6,'集中哑铃弯举',3,'10~15',60,'normal');
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
INSERT INTO `program_list` VALUES ('tp1508060008886','五分化训练','增肌','胸,背,腿,肩,臂,休息,休息',0,20170101,'五分化训练的设计原理，是每天针对5大肌肉群 - 腿、胸、背、肩、臂 - 中的一项，以最大化地施加超负荷刺激。以5天为一训练周期，之后休息1-2天。因此，当以周为单位时，参考方案为：\n- 周一：腿\n- 周二：胸\n- 周三：背\n- 周四：肩\n- 周五：臂\n- 周六：休息\n- 周日：休息');
INSERT INTO `program_list` VALUES ('tp1508060008887','腿推拉训练','增肌','腿,推,拉,腿,推,拉,休息',0,20170101,'腿推拉训练设计的设计原理，是以肌肉的三大功能 - 即腿、推、拉 - 进行训练内容划分。以3天为一训练周期，之后休息1天，或两周期循环后再休息。因此，当以周为单位时，参考方案为：\n- 周一：腿（A）\n- 周二：推（A）\n- 周三：拉（A）\n- 周四：腿（B）\n- 周五：推（B）\n- 周六：拉（B）\n- 周日：休息');
INSERT INTO `program_list` VALUES ('tp1508060008888','全身式训练','保持','休息,休息,休息,休息,全身,休息,全身',0,20170101,'全身式训练设计，是针对忙碌健身者，最为高效省时间的训练方案。它的设计原理，是每天锻炼全身所有主要肌肉群 - 多动作，少组数。\n全身式训练设计，1天就是一个训练周期，之后需要休息1-2天。因此，对于忙碌的学生或白领，如果只有周末有空，参考方案为：\n- 周一：学校/工作\n- 周二：学校/工作\n- 周三：学校/工作\n- 周四：学校/工作\n- 周五：全身\n- 周六：休息\n- 周日：全身');
DROP TABLE IF EXISTS `log_catalog`;
CREATE TABLE IF NOT EXISTS `log_catalog` (
	`date`	INTEGER,
	`program_id`	TEXT,
	`program_name`	TEXT,
	`day_name`	TEXT
);
INSERT INTO `log_catalog` VALUES (20171013,'tp1508060008888','全身式训练','全身');
COMMIT;
