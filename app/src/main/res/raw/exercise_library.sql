BEGIN TRANSACTION;
DROP TABLE IF EXISTS `stretching`;
CREATE TABLE IF NOT EXISTS `stretching` (
	`id`	INTEGER,
	`name`	TEXT,
	`muscle`	TEXT
);
INSERT INTO `stretching` VALUES (130101,'胸部拉伸','胸部');
INSERT INTO `stretching` VALUES (130201,'背部拉伸','背部');
INSERT INTO `stretching` VALUES (130301,'下肢拉伸','下肢');
INSERT INTO `stretching` VALUES (130401,'肩部拉伸','肩部');
INSERT INTO `stretching` VALUES (130501,'肱二头拉伸','肱二头肌');
INSERT INTO `stretching` VALUES (130601,'肱三头拉伸','肱三头肌');
DROP TABLE IF EXISTS `sort`;
CREATE TABLE IF NOT EXISTS `sort` (
	`number`	INTEGER,
	`sort_name`	TEXT,
	`sort_chinese`	TEXT
);
INSERT INTO `sort` VALUES (2,'equipment','负重');
INSERT INTO `sort` VALUES (1,'bodyweight','自重');
INSERT INTO `sort` VALUES (3,'stretching','拉伸');
DROP TABLE IF EXISTS `equipment`;
CREATE TABLE IF NOT EXISTS `equipment` (
	`id`	INTEGER,
	`name`	TEXT,
	`muscle`	TEXT
);
INSERT INTO `equipment` VALUES (110101,'卧推','胸部');
INSERT INTO `equipment` VALUES (110102,'上斜卧推','胸部');
INSERT INTO `equipment` VALUES (110103,'下斜卧推','胸部');
INSERT INTO `equipment` VALUES (110104,'哑铃卧推','胸部');
INSERT INTO `equipment` VALUES (110105,'上斜哑铃卧推','胸部');
INSERT INTO `equipment` VALUES (110106,'下斜哑铃卧推','胸部');
INSERT INTO `equipment` VALUES (110107,'绳索夹胸','胸部');
INSERT INTO `equipment` VALUES (110108,'上斜绳索夹胸','胸部');
INSERT INTO `equipment` VALUES (110109,'下斜绳索夹胸','胸部');
INSERT INTO `equipment` VALUES (110110,'器械夹胸','胸部');
INSERT INTO `equipment` VALUES (110111,'哑铃飞鸟','胸部');
INSERT INTO `equipment` VALUES (110112,'仰卧直臂上拉','胸部');
INSERT INTO `equipment` VALUES (110113,'单臂夹胸','胸部');
INSERT INTO `equipment` VALUES (110114,'上斜单臂夹胸','胸部');
INSERT INTO `equipment` VALUES (110115,'下斜单臂夹胸','胸部');
INSERT INTO `equipment` VALUES (110201,'杠铃划船','背部');
INSERT INTO `equipment` VALUES (110202,'宽握杠铃划船','背部');
INSERT INTO `equipment` VALUES (110203,'单臂哑铃划船','背部');
INSERT INTO `equipment` VALUES (110204,'拉力器划船','背部');
INSERT INTO `equipment` VALUES (110205,'器械划船','背部');
INSERT INTO `equipment` VALUES (110206,'阔背下拉','背部');
INSERT INTO `equipment` VALUES (110207,'负重引体向上','背部');
INSERT INTO `equipment` VALUES (110208,'直臂下压','背部');
INSERT INTO `equipment` VALUES (110209,'单手直臂下拉','背部');
INSERT INTO `equipment` VALUES (110301,'深蹲','大腿');
INSERT INTO `equipment` VALUES (110302,'颈前深蹲','大腿');
INSERT INTO `equipment` VALUES (110303,'硬拉','臀腿');
INSERT INTO `equipment` VALUES (110304,'直腿硬拉','臀腿');
INSERT INTO `equipment` VALUES (110305,'罗马尼亚硬拉','臀腿');
INSERT INTO `equipment` VALUES (110306,'相扑硬拉','臀腿');
INSERT INTO `equipment` VALUES (110307,'器械腿推','大腿');
INSERT INTO `equipment` VALUES (110308,'哑铃弓步','大腿');
INSERT INTO `equipment` VALUES (110309,'保加利亚分腿蹲','大腿');
INSERT INTO `equipment` VALUES (110310,'坐姿腿屈伸','大腿');
INSERT INTO `equipment` VALUES (110311,'卧姿腿弯举','大腿');
INSERT INTO `equipment` VALUES (110350,'站立小腿举','小腿');
INSERT INTO `equipment` VALUES (110351,'坐姿小腿举','小腿');
INSERT INTO `equipment` VALUES (110352,'负重提踵','小腿');
INSERT INTO `equipment` VALUES (110401,'哑铃侧平举','肩部');
INSERT INTO `equipment` VALUES (110402,'拉力器侧平举','肩部');
INSERT INTO `equipment` VALUES (110403,'杠铃肩推','肩部');
INSERT INTO `equipment` VALUES (110404,'哑铃肩推','肩部');
INSERT INTO `equipment` VALUES (110405,'前平举','肩部');
INSERT INTO `equipment` VALUES (110451,'绳索面拉','肩部');
INSERT INTO `equipment` VALUES (110452,'哑铃俯身飞鸟','肩部');
INSERT INTO `equipment` VALUES (110453,'绳索俯身飞鸟','肩部');
INSERT INTO `equipment` VALUES (110454,'器械反向飞鸟','肩部');
INSERT INTO `equipment` VALUES (110455,'开肘哑铃划船','肩部');
INSERT INTO `equipment` VALUES (110501,'哑铃弯举','肱二头肌');
INSERT INTO `equipment` VALUES (110502,'杠铃弯举','肱二头肌');
INSERT INTO `equipment` VALUES (110503,'拉力器弯举','肱二头肌');
INSERT INTO `equipment` VALUES (110504,'锤式弯举','肱二头肌');
INSERT INTO `equipment` VALUES (110505,'绳索锤式弯举','肱二头肌');
INSERT INTO `equipment` VALUES (110506,'哑铃集中弯举','肱二头肌');
INSERT INTO `equipment` VALUES (110507,'斜板杠铃弯举','肱二头肌');
INSERT INTO `equipment` VALUES (110508,'斜板哑铃弯举','肱二头肌');
INSERT INTO `equipment` VALUES (110601,'仰卧臂屈伸','肱三头肌');
INSERT INTO `equipment` VALUES (110602,'哑铃仰卧臂屈伸','肱三头肌');
INSERT INTO `equipment` VALUES (110603,'拉力器仰卧臂屈伸','肱三头肌');
INSERT INTO `equipment` VALUES (110604,'俯身臂屈伸','肱三头肌');
INSERT INTO `equipment` VALUES (110605,'哑铃颈后臂屈伸','肱三头肌');
INSERT INTO `equipment` VALUES (110606,'拉力器颈后臂屈伸','肱三头肌');
INSERT INTO `equipment` VALUES (110607,'直杆下压','肱三头肌');
INSERT INTO `equipment` VALUES (110608,'绳索下压','肱三头肌');
INSERT INTO `equipment` VALUES (110609,'窄距卧推','肱三头肌');
DROP TABLE IF EXISTS `bodyweight`;
CREATE TABLE IF NOT EXISTS `bodyweight` (
	`id`	INTEGER,
	`name`	TEXT,
	`muscle`	TEXT
);
INSERT INTO `bodyweight` VALUES (120101,'引体向上','背部');
INSERT INTO `bodyweight` VALUES (120201,'俯卧撑','胸部');
INSERT INTO `bodyweight` VALUES (120303,'徒手深蹲','臀腿');
COMMIT;
