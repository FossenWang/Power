BEGIN TRANSACTION;
DROP TABLE IF EXISTS `programs`;
CREATE TABLE IF NOT EXISTS `programs` (
	`id`	INTEGER,
	`day`	INTEGER DEFAULT 1,
	`number`	INTEGER DEFAULT 1,
	`exercise`	TEXT DEFAULT '',
	`sets`	INTEGER DEFAULT 0,
	`reps`	TEXT DEFAULT '',
	`rest`	INTEGER DEFAULT 0,
	`structure`	TEXT DEFAULT '',
	FOREIGN KEY(`id`) REFERENCES `program_list`(`id`) ON DELETE CASCADE
);
INSERT INTO `programs` VALUES (1,1,1,'深蹲',5,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (1,1,2,'器械腿推',4,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (1,1,3,'哑铃弓步',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (1,1,4,'坐姿腿屈伸',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (1,1,5,'卧姿腿弯举',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (1,1,6,'站立小腿举,坐姿小腿举',4,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (1,2,1,'卧推,哑铃卧推',5,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (1,2,2,'上斜卧推,上斜哑铃卧推',4,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (1,2,3,'绳索夹胸,器械夹胸',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (1,2,4,'下斜绳索夹胸',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (1,2,5,'上斜绳索夹胸',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (1,2,6,'仰卧臂屈伸,俯身臂屈伸',4,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (1,3,1,'硬拉',5,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (1,3,2,'阔背下拉',4,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (1,3,3,'杠铃划船',3,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (1,3,4,'单臂哑铃划船',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (1,3,5,'绳索面拉',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (1,3,6,'哑铃弯举,杠铃弯举',4,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (1,4,1,'杠铃肩推',5,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (1,4,2,'哑铃肩推',4,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (1,4,3,'哑铃侧平举',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (1,4,4,'哑铃前平举',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (1,4,5,'哑铃俯身飞鸟',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (1,4,6,'绳索面拉',4,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (1,5,1,'仰卧臂屈伸',4,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (1,5,2,'哑铃弯举,杠铃弯举',4,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (1,5,3,'绳索下压,直杆下压',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (1,5,4,'哑铃锤式弯举',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (1,5,5,'俯身臂屈伸',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (1,5,6,'哑铃集中弯举',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (2,1,1,'深蹲',5,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (2,1,2,'器械腿推',3,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (2,1,3,'哑铃弓步',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (2,1,4,'卧姿腿弯举',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (2,1,5,'站立小腿举',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (2,2,1,'卧推,哑铃卧推',5,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (2,2,2,'上斜哑铃卧推,上斜卧推',3,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (2,2,3,'绳索夹胸,器械夹胸',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (2,2,4,'杠铃肩推,哑铃肩推',3,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (2,2,5,'哑铃侧平举',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (2,2,6,'仰卧臂屈伸,俯身臂屈伸',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (2,3,1,'硬拉',5,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (2,3,2,'阔背下拉',3,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (2,3,3,'杠铃划船',3,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (2,3,4,'绳索面拉',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (2,3,5,'哑铃弯举,杠铃弯举',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (2,4,1,'深蹲',5,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (2,4,2,'器械腿推',3,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (2,4,3,'坐姿腿屈伸',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (2,4,4,'卧姿腿弯举',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (2,4,5,'坐姿小腿举',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (2,5,1,'卧推,哑铃卧推',5,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (2,5,2,'下斜哑铃卧推,下斜卧推',3,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (2,5,3,'下斜绳索夹胸',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (2,5,4,'哑铃肩推,杠铃肩推',3,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (2,5,5,'哑铃侧平举',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (2,5,6,'绳索下压,直杆下压',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (2,6,1,'硬拉',5,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (2,6,2,'阔背下拉',3,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (2,6,3,'单臂哑铃划船',3,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (2,6,4,'绳索面拉',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (2,6,5,'哑铃锤式弯举',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (3,5,1,'深蹲',3,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (3,5,2,'硬拉',3,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (3,5,3,'卧推',3,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (3,5,4,'器械腿推',3,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (3,5,5,'杠铃划船,阔背下拉',3,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (3,5,6,'哑铃侧平举',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (3,5,7,'站立小腿举',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (3,5,8,'仰卧臂屈伸',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (3,5,9,'哑铃弯举,杠铃弯举',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (3,7,1,'深蹲',3,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (3,7,2,'硬拉',3,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (3,7,3,'杠铃肩推',3,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (3,7,4,'哑铃弓步',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (3,7,5,'杠铃划船,单臂哑铃划船',3,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (3,7,6,'器械飞鸟',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (3,7,7,'坐姿小腿举',3,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (3,7,8,'绳索下压',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (3,7,9,'哑铃锤式弯举',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (4,1,1,'卧推,哑铃卧推',5,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (4,1,2,'上斜卧推,上斜哑铃卧推',3,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (4,1,3,'上斜绳索夹胸,上斜单臂夹胸',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (4,1,4,'绳索下压,直杆下压,窄距卧推',5,'8~12',60,'equipment');
INSERT INTO `programs` VALUES (4,1,5,'俯身臂屈伸,哑铃仰卧臂屈伸',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (4,1,6,'哑铃侧平举,拉力器侧平举',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (4,2,1,'硬拉',5,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (4,2,2,'杠铃划船,拉力器划船,器械划船',3,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (4,2,3,'单臂哑铃划船,直臂下压,单手直臂下拉',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (4,2,4,'哑铃弯举,哑铃集中弯举,杠铃弯举,拉力器弯举',5,'8~12',60,'equipment');
INSERT INTO `programs` VALUES (4,2,5,'绳索锤式弯举,哑铃锤式弯举',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (4,2,6,'绳索面拉,开肘哑铃划船,哑铃俯身飞鸟',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (4,3,1,'深蹲',5,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (4,3,2,'器械腿推',5,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (4,3,3,'哑铃弓步,保加利亚分腿蹲',3,'8~12',60,'equipment');
INSERT INTO `programs` VALUES (4,3,4,'杠铃肩推,哑铃肩推',5,'8~12',60,'equipment');
INSERT INTO `programs` VALUES (4,3,5,'哑铃侧平举,拉力器侧平举',5,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (4,4,1,'卧推,哑铃卧推',5,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (4,4,2,'下斜卧推,下斜哑铃卧推',3,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (4,4,3,'下斜绳索夹胸,下斜单臂夹胸',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (4,4,4,'窄距卧推,绳索下压,直杆下压',5,'8~12',60,'equipment');
INSERT INTO `programs` VALUES (4,4,5,'俯身臂屈伸,哑铃仰卧臂屈伸',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (4,4,6,'哑铃侧平举,拉力器侧平举',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (4,5,1,'硬拉',5,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (4,5,2,'负重引体向上,阔背下拉',3,'8~12',120,'bodyweight');
INSERT INTO `programs` VALUES (4,5,3,'直臂下压,单手直臂下拉,单臂哑铃划船',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (4,5,4,'杠铃弯举,哑铃弯举,拉力器弯举,哑铃集中弯举',5,'8~12',60,'equipment');
INSERT INTO `programs` VALUES (4,5,5,'哑铃锤式弯举,绳索锤式弯举',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (4,5,6,'开肘哑铃划船,绳索面拉,哑铃俯身飞鸟',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (4,6,1,'深蹲',5,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (4,6,2,'保加利亚分腿蹲,哑铃弓步',3,'8~12',60,'equipment');
INSERT INTO `programs` VALUES (4,6,3,'哑铃卧推,卧推',5,'8~12',120,'equipment');
INSERT INTO `programs` VALUES (4,6,4,'绳索夹胸,单臂夹胸,哑铃飞鸟,仰卧直臂上拉',3,'10~15',60,'equipment');
INSERT INTO `programs` VALUES (4,6,5,'哑铃肩推,杠铃肩推',3,'8~12',60,'equipment');
INSERT INTO `programs` VALUES (4,6,6,'哑铃侧平举,拉力器侧平举',3,'10~15',60,'equipment');
DROP TABLE IF EXISTS `program_list`;
CREATE TABLE IF NOT EXISTS `program_list` (
	`id`	INTEGER DEFAULT '' PRIMARY KEY AUTOINCREMENT,
	`name`	TEXT DEFAULT '',
	`goal`	TEXT DEFAULT '',
	`day_name`	TEXT DEFAULT '',
	`start`	INTEGER DEFAULT 0,
	`date`	INTEGER DEFAULT 20170101,
	`note`	TEXT DEFAULT ''
);
INSERT INTO `program_list` VALUES (1,'五分化训练','增肌','腿,胸,背,肩,臂,休息,休息',0,20170101,'五分化训练的设计原理，是每天针对5大肌肉群 - 腿、胸、背、肩、臂 - 中的一项，以最大化地施加超负荷刺激。以5天为一训练周期，之后休息1-2天。因此，当以周为单位时，参考方案为：\n- 周一：腿\n- 周二：胸\n- 周三：背\n- 周四：肩\n- 周五：臂\n- 周六：休息\n- 周日：休息');
INSERT INTO `program_list` VALUES (2,'腿推拉训练','增肌','腿,推,拉,腿,推,拉,休息',0,20170101,'腿推拉训练设计的设计原理，是以肌肉的三大功能 - 即腿、推、拉 - 进行训练内容划分。以3天为一训练周期，之后休息1天，或两周期循环后再休息。因此，当以周为单位时，参考方案为：\n- 周一：腿\n- 周二：推\n- 周三：拉\n- 周四：腿\n- 周五：推\n- 周六：拉\n- 周日：休息');
INSERT INTO `program_list` VALUES (3,'全身式训练','保持','休息,休息,休息,休息,全身,休息,全身',0,20170101,'全身式训练设计，是针对忙碌健身者，最为高效省时间的训练方案。它的设计原理，是每天锻炼全身所有主要肌肉群 - 多动作，少组数。\n全身式训练设计，1天就是一个训练周期，之后需要休息1-2天。因此，对于忙碌的学生或白领，如果只有周末有空，参考方案为：\n- 周一：学校/工作\n- 周二：学校/工作\n- 周三：学校/工作\n- 周四：学校/工作\n- 周五：全身\n- 周六：休息\n- 周日：全身');
INSERT INTO `program_list` VALUES (4,'Fossen的胸背腿训练','增肌','胸,背,腿,胸,背,腿,休息',0,20170101,'以推拉腿三分化训练为基本模板，针对我自身的训练需求做出调整，制定了我个人的训练方案。该方案主要按胸、背、腿三个大肌群划分，再辅以锻炼小肌群的动作，三天一小循环，两个小循环休息一天。\n现阶段为了更好的锻炼胸部，我在每周最后一天练腿日中也加入了两组胸部训练动作。每日的训练部位如下：\n第一天练胸、肱三、肩；\n第二天练背、肱二、三角肌后束；\n第三天练下肢、肩；\n第四天练胸、肱三、肩；\n第五天练背、肱二、三角肌后束；\n第六天练下肢、胸、肩；');
DROP TABLE IF EXISTS `logs`;
CREATE TABLE IF NOT EXISTS `logs` (
	`id`	INTEGER,
	`number`	INTEGER DEFAULT 1,
	`exercise`	TEXT DEFAULT '',
	`record`	TEXT DEFAULT '',
	`structure`	TEXT DEFAULT '',
	FOREIGN KEY(`id`) REFERENCES `log_catalog`(`id`) ON DELETE CASCADE
);
INSERT INTO `logs` VALUES (1,1,'深蹲','32.5×12,32.5×12,32.5×12,','equipment');
INSERT INTO `logs` VALUES (1,2,'硬拉','32.5×12,32.5×12,32.5×12,','equipment');
INSERT INTO `logs` VALUES (1,3,'卧推','32.5×12,32.5×12,32.5×12,','equipment');
INSERT INTO `logs` VALUES (1,4,'器械腿推','32.5×12,32.5×12,32.5×12,','equipment');
INSERT INTO `logs` VALUES (1,5,'杠铃划船','32.5×12,32.5×12,32.5×12,','equipment');
INSERT INTO `logs` VALUES (1,6,'哑铃侧平举','32.5×12,32.5×12,32.5×12,','equipment');
INSERT INTO `logs` VALUES (1,7,'站立小腿举','32.5×12,32.5×12,32.5×12,','equipment');
INSERT INTO `logs` VALUES (1,8,'仰卧臂屈伸','32.5×12,32.5×12,32.5×12,','equipment');
INSERT INTO `logs` VALUES (1,9,'哑铃弯举','32.5×12,32.5×12,32.5×12,','equipment');
DROP TABLE IF EXISTS `log_catalog`;
CREATE TABLE IF NOT EXISTS `log_catalog` (
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`date`	INTEGER,
	`program_id`	INTEGER,
	`program_name`	TEXT DEFAULT '',
	`day_name`	TEXT DEFAULT ''
);
INSERT INTO `log_catalog` VALUES (1,20171013,3,'全身式训练','全身');
DROP TABLE IF EXISTS `exercises`;
CREATE TABLE IF NOT EXISTS `exercises` (
	`id`	INTEGER,
	`name`	TEXT DEFAULT '',
	`sort`	TEXT DEFAULT '',
	`type`	TEXT DEFAULT '',
	`record`	TEXT DEFAULT '',
	FOREIGN KEY(`type`) REFERENCES `exercise_types`(`type_name`) ON DELETE CASCADE
);
INSERT INTO `exercises` VALUES (110101,'卧推','胸部','equipment','50×10,50×10,50×10,50×10,50×10');
INSERT INTO `exercises` VALUES (110102,'上斜卧推','胸部','equipment','40×8,40×8,40×6');
INSERT INTO `exercises` VALUES (110103,'下斜卧推','胸部','equipment','40×15,45×10,40×10');
INSERT INTO `exercises` VALUES (110104,'哑铃卧推','胸部','equipment','45×10,45×8,45×7,45×8,45×6');
INSERT INTO `exercises` VALUES (110105,'上斜哑铃卧推','胸部','equipment','40×8,40×8,40×6');
INSERT INTO `exercises` VALUES (110106,'下斜哑铃卧推','胸部','equipment','');
INSERT INTO `exercises` VALUES (110107,'绳索夹胸','胸部','equipment','30×12,40×8,40×8');
INSERT INTO `exercises` VALUES (110108,'上斜绳索夹胸','胸部','equipment','30×8,20×12,20×10');
INSERT INTO `exercises` VALUES (110109,'下斜绳索夹胸','胸部','equipment','30×12,40×8,40×8');
INSERT INTO `exercises` VALUES (110110,'器械夹胸','胸部','equipment','');
INSERT INTO `exercises` VALUES (110111,'哑铃飞鸟','胸部','equipment','');
INSERT INTO `exercises` VALUES (110112,'仰卧直臂上拉','胸部','equipment','');
INSERT INTO `exercises` VALUES (110113,'单臂夹胸','胸部','equipment','40×8,40×8,40×6');
INSERT INTO `exercises` VALUES (110114,'上斜单臂夹胸','胸部','equipment','20×12,20×12,20×10');
INSERT INTO `exercises` VALUES (110115,'下斜单臂夹胸','胸部','equipment','40×8,40×8,40×6');
INSERT INTO `exercises` VALUES (110201,'杠铃划船','背部','equipment','40×10,40×8,40×8');
INSERT INTO `exercises` VALUES (110202,'宽握杠铃划船','背部','equipment','');
INSERT INTO `exercises` VALUES (110203,'单臂哑铃划船','背部','equipment','40×10,40×10,40×8,');
INSERT INTO `exercises` VALUES (110204,'拉力器划船','背部','equipment','');
INSERT INTO `exercises` VALUES (110205,'器械划船','背部','equipment','');
INSERT INTO `exercises` VALUES (110206,'阔背下拉','背部','equipment','');
INSERT INTO `exercises` VALUES (110207,'负重引体向上','背部','equipment','2.5×8,2.5×6,2.5×6,');
INSERT INTO `exercises` VALUES (110208,'直臂下压','背部','equipment','30×10,30×10,30×10');
INSERT INTO `exercises` VALUES (110209,'单手直臂下拉','背部','equipment','30×10,30×10,30×10');
INSERT INTO `exercises` VALUES (110301,'深蹲','下肢','equipment','50×10,60×6,50×6,50×6,50×6');
INSERT INTO `exercises` VALUES (110302,'颈前深蹲','下肢','equipment','');
INSERT INTO `exercises` VALUES (110303,'硬拉','下肢','equipment','60×12,70×9,70×8,70×8,70×7,');
INSERT INTO `exercises` VALUES (110304,'直腿硬拉','下肢','equipment','');
INSERT INTO `exercises` VALUES (110305,'罗马尼亚硬拉','下肢','equipment','');
INSERT INTO `exercises` VALUES (110306,'相扑硬拉','下肢','equipment','');
INSERT INTO `exercises` VALUES (110307,'器械腿推','下肢','equipment','100×8,100×8,100×8,');
INSERT INTO `exercises` VALUES (110308,'哑铃弓步','下肢','equipment','30×8,30×8,30×6,');
INSERT INTO `exercises` VALUES (110309,'保加利亚分腿蹲','下肢','equipment','30×12,30×12,30×12,');
INSERT INTO `exercises` VALUES (110310,'坐姿腿屈伸','下肢','equipment','');
INSERT INTO `exercises` VALUES (110311,'卧姿腿弯举','下肢','equipment','');
INSERT INTO `exercises` VALUES (110350,'站立小腿举','下肢','equipment','');
INSERT INTO `exercises` VALUES (110351,'坐姿小腿举','下肢','equipment','');
INSERT INTO `exercises` VALUES (110352,'负重提踵','下肢','equipment','');
INSERT INTO `exercises` VALUES (110401,'哑铃侧平举','肩部','equipment','15×12,15×12,15×12');
INSERT INTO `exercises` VALUES (110402,'拉力器侧平举','肩部','equipment','10×12,10×10,10×8,');
INSERT INTO `exercises` VALUES (110403,'杠铃肩推','肩部','equipment','25×12,25×12,25×12,25×12,25×10');
INSERT INTO `exercises` VALUES (110404,'哑铃肩推','肩部','equipment','25×5,20×6,20×6,');
INSERT INTO `exercises` VALUES (110405,'前平举','肩部','equipment','');
INSERT INTO `exercises` VALUES (110451,'绳索面拉','肩部','equipment','25×12,25×12,25×12');
INSERT INTO `exercises` VALUES (110452,'哑铃俯身飞鸟','肩部','equipment','');
INSERT INTO `exercises` VALUES (110453,'绳索俯身飞鸟','肩部','equipment','');
INSERT INTO `exercises` VALUES (110454,'器械反向飞鸟','肩部','equipment','');
INSERT INTO `exercises` VALUES (110455,'开肘哑铃划船','肩部','equipment','30×10,30×10,30×10,');
INSERT INTO `exercises` VALUES (110501,'哑铃弯举','肱二头肌','equipment','20×10,20×8,20×8,20×7,20×7,');
INSERT INTO `exercises` VALUES (110502,'杠铃弯举','肱二头肌','equipment','25×10,25×10,25×9,25×8,25×8');
INSERT INTO `exercises` VALUES (110503,'拉力器弯举','肱二头肌','equipment','');
INSERT INTO `exercises` VALUES (110504,'哑铃锤式弯举','肱二头肌','equipment','20×8,20×8,20×8');
INSERT INTO `exercises` VALUES (110505,'绳索锤式弯举','肱二头肌','equipment','25×10,25×10,25×10');
INSERT INTO `exercises` VALUES (110506,'哑铃集中弯举','肱二头肌','equipment','');
INSERT INTO `exercises` VALUES (110507,'斜板杠铃弯举','肱二头肌','equipment','');
INSERT INTO `exercises` VALUES (110508,'斜板哑铃弯举','肱二头肌','equipment','');
INSERT INTO `exercises` VALUES (110601,'仰卧臂屈伸','肱三头肌','equipment','');
INSERT INTO `exercises` VALUES (110602,'哑铃仰卧臂屈伸','肱三头肌','equipment','');
INSERT INTO `exercises` VALUES (110603,'拉力器仰卧臂屈伸','肱三头肌','equipment','');
INSERT INTO `exercises` VALUES (110604,'俯身臂屈伸','肱三头肌','equipment','15×15,15×12,15×12');
INSERT INTO `exercises` VALUES (110605,'哑铃颈后臂屈伸','肱三头肌','equipment','');
INSERT INTO `exercises` VALUES (110606,'拉力器颈后臂屈伸','肱三头肌','equipment','');
INSERT INTO `exercises` VALUES (110607,'直杆下压','肱三头肌','equipment','40×8,40×8,40×6');
INSERT INTO `exercises` VALUES (110608,'绳索下压','肱三头肌','equipment','40×8,40×8,40×6');
INSERT INTO `exercises` VALUES (110609,'窄距卧推','肱三头肌','equipment','40×12,40×12,40×10,40×8,40×8');
INSERT INTO `exercises` VALUES (120101,'引体向上','拉','bodyweight','');
INSERT INTO `exercises` VALUES (120201,'俯卧撑','推','bodyweight','');
INSERT INTO `exercises` VALUES (120301,'徒手深蹲','下肢','bodyweight','');
INSERT INTO `exercises` VALUES (120701,'仰卧起坐','核心','bodyweight','');
INSERT INTO `exercises` VALUES (130101,'胸部拉伸','胸部','stretching','');
INSERT INTO `exercises` VALUES (130201,'背部拉伸','背部','stretching','');
INSERT INTO `exercises` VALUES (130301,'下肢拉伸','下肢','stretching','');
INSERT INTO `exercises` VALUES (130401,'肩部拉伸','肩部','stretching','');
INSERT INTO `exercises` VALUES (130501,'肱二头拉伸','肱二头肌','stretching','');
INSERT INTO `exercises` VALUES (130601,'肱三头拉伸','肱三头肌','stretching','');
INSERT INTO `exercises` VALUES (130701,'核心拉伸','核心','stretching','');
DROP TABLE IF EXISTS `exercise_types`;
CREATE TABLE IF NOT EXISTS `exercise_types` (
	`number`	INTEGER,
	`type_name`	TEXT,
	`type_chinese`	TEXT,
	`sort`	TEXT,
	PRIMARY KEY(`type_name`)
);
INSERT INTO `exercise_types` VALUES (1,'equipment','负重','胸部,背部,下肢,肩部,肱二头肌,肱三头肌,核心');
INSERT INTO `exercise_types` VALUES (2,'bodyweight','自重','推,拉,核心,下肢');
INSERT INTO `exercise_types` VALUES (3,'stretching','拉伸','胸部,背部,下肢,肩部,肱二头肌,肱三头肌,核心');
COMMIT;
