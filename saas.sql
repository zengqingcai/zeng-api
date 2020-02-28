/*
SQLyog Ultimate v11.33 (64 bit)
MySQL - 5.6.23-72.1-log : Database - saas
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`saas` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `saas`;

/*Table structure for table `city_popularity` */

DROP TABLE IF EXISTS `city_popularity`;

CREATE TABLE `city_popularity` (
  `region` int(10) NOT NULL COMMENT '1 国内 2 海外',
  `city_name` varchar(64) NOT NULL,
  `popularity` double(5,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `city_popularity` */

insert  into `city_popularity`(`region`,`city_name`,`popularity`) values (1,'北京',30.00),(1,'上海',30.00),(1,'南京',10.00),(2,'伦敦',20.00),(1,'张家界',8.00),(2,'纽约',35.00),(1,'三亚',25.00),(2,'新加坡',35.00);

/*Table structure for table `employees` */

DROP TABLE IF EXISTS `employees`;

CREATE TABLE `employees` (
  `employeeid` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `sempid` varchar(200) DEFAULT NULL COMMENT '上级用户账号',
  `empid` varchar(200) NOT NULL COMMENT '用户账号',
  `empname` varchar(200) NOT NULL COMMENT '用户姓名',
  `password` varchar(50) NOT NULL COMMENT '用户密码',
  `ztimes` int(11) DEFAULT '0' COMMENT '登陆错误次数 达到5次需次日登陆或后台处理',
  `emptype` int(11) NOT NULL COMMENT '用户类型 0平台 1景区 2供应商 3分销商',
  `icompanyinfoid` int(11) DEFAULT NULL COMMENT '企业id ',
  `companycode` varchar(50) NOT NULL COMMENT '企业编码 ',
  `companyname` varchar(200) NOT NULL COMMENT '企业名称 ',
  `mobile` varchar(20) NOT NULL COMMENT '手机号',
  `email` varchar(200) DEFAULT NULL COMMENT '邮箱地址',
  `addr` varchar(200) DEFAULT NULL COMMENT '地址',
  `byisuse` int(11) NOT NULL DEFAULT '0' COMMENT '用户状态 0禁用 1有效 2黑名单 3需审核',
  `shempid` int(11) DEFAULT NULL COMMENT '审核人ID',
  `shbyisuse` int(11) DEFAULT '0' COMMENT '审核状态 0待审核 1审核通过 2审核不通过',
  `shnote` varchar(500) DEFAULT NULL COMMENT '审核备注',
  `sources` varchar(20) NOT NULL DEFAULT '0' COMMENT '注册来源 0集成平台 1SAAS 2支付桥 3整合营销',
  `sznote` varchar(500) DEFAULT NULL COMMENT '备注',
  `dtmakedate` varchar(200) DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`employeeid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

/*Data for the table `employees` */

insert  into `employees`(`employeeid`,`sempid`,`empid`,`empname`,`password`,`ztimes`,`emptype`,`icompanyinfoid`,`companycode`,`companyname`,`mobile`,`email`,`addr`,`byisuse`,`shempid`,`shbyisuse`,`shnote`,`sources`,`sznote`,`dtmakedate`) values (1,NULL,'admin','系统管理员','029124EDC5E180631627E3AE44108724',0,0,1,'tCHD9F6z','深圳鼎游','15622980172','2564669556@qq.com','深圳南山',1,1,1,'系统自审','0','','2019-09-23 14:09:23'),(2,NULL,'abing','测试集成用户','029124EDC5E180631627E3AE44108724',0,1,2,'CUDlVYH9','深圳鼎游','15622980172','','深圳南山',1,1,1,'测试','0','','2019-09-23 14:13:49'),(10,'admin','test','张三-test','7B8B855FFA78A93B66EC3923135C1629',0,1,1,'IKS9IQrx','小黄单车','13544361981',NULL,NULL,1,NULL,0,NULL,'1','添加备注','2019-09-25 14:04:17'),(11,NULL,'admin11','zhangsan','7B8B855FFA78A93B66EC3923135C1629',0,0,171221,'LZaz3gnJ','小黄111','13544369801','','南山',1,1,1,'审核通过','0','123456','2019-09-25 14:23:01'),(13,'admin','centertest','张张','13788C0C13AD47C7BCBB3A792024CD1F',0,1,1,'kand','世界之窗','18813106459','ec@163.com',NULL,1,NULL,0,NULL,'1','132','2019-09-26 15:17:52'),(14,NULL,'test01','test01','029124EDC5E180631627E3AE44108724',0,1,37,'8L74qn7i','test01','13048008000','test01@qq.com','test01',3,NULL,0,NULL,'0','test01','2019-10-14 15:04:53'),(15,NULL,'test02','test02','1D9DCDE4D2032A05916DBAF782F7C645',0,1,38,'tLO24QUE','test02','13048808000','test02@qq.com','test02',3,NULL,0,NULL,'0','test02','2019-10-14 15:05:32'),(16,NULL,'test03','test03','029124EDC5E180631627E3AE44108724',0,1,39,'IPLIh6DY','test03','13048808400','test03@qq.com','test03',3,NULL,0,NULL,'0','test03','2019-10-14 15:06:18'),(17,NULL,'test04','test04','029124EDC5E180631627E3AE44108724',0,1,40,'W94vpqJ1','test04','13048008600','test04@qq.com','test04',3,NULL,0,NULL,'0','test04','2019-10-14 15:06:59'),(18,NULL,'test05','test05','9DCC0F6241AA538CE9337EA8EEE09090',0,1,42,'6i4s95zE','test05','13048008000','test05@QQ.COM','test05',3,NULL,0,NULL,'0','test05','2019-10-14 15:08:45'),(19,NULL,'test06','test06','FF95C0A90AAAAA3ADED0DE07E06BAEF6',0,1,43,'MN8642lm','test06','13048008000','test06@qq.com','test06',3,NULL,0,NULL,'0','test06','2019-10-14 15:09:53'),(20,NULL,'test07','test07','C77156CF81A42E4FBE89B4E3AEF23D69',0,1,44,'W981dKBu','test07','13048009000','test07@qq.com','test07',3,NULL,0,NULL,'0','test07','2019-10-14 15:10:23'),(21,NULL,'test08','test08','EC3D9E1E73417D9786B24AE6F278510C',0,1,47,'l7pr8i7Y','test08','13048000000','test08@qq.com','test08',3,NULL,0,NULL,'0','test08','2019-10-14 15:11:27'),(22,NULL,'cxh_pay','陈新浩','029124EDC5E180631627E3AE44108724',0,0,NULL,'CuvsLbXU','陈新浩测试','13200000000','1@qq.com',NULL,0,NULL,0,NULL,'2',NULL,NULL);

/*Table structure for table `emppush` */

DROP TABLE IF EXISTS `emppush`;

CREATE TABLE `emppush` (
  `pid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `employeeid` int(11) DEFAULT NULL COMMENT '用户表id',
  `pushstatus` int(11) DEFAULT '0' COMMENT '推送状态：0 未推送 1已经推送',
  `source` int(11) DEFAULT NULL COMMENT '1:saas 2:支付桥 3：整合营销',
  `byisuse` int(11) DEFAULT NULL COMMENT '0:禁用 1启用',
  `sznote` varchar(200) DEFAULT NULL COMMENT '备注',
  `dtmakedate` varchar(200) DEFAULT NULL COMMENT '操作日期',
  PRIMARY KEY (`pid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8 COMMENT='推送消息记录表';

/*Data for the table `emppush` */

insert  into `emppush`(`pid`,`employeeid`,`pushstatus`,`source`,`byisuse`,`sznote`,`dtmakedate`) values (53,11,1,1,1,'已推送111','2019-09-26 14:21:32'),(54,11,1,2,1,'推送','2019-09-25 14:37:07'),(55,11,1,3,1,'推送','2019-09-25 14:37:25');

/*Table structure for table `rsourcestab` */

DROP TABLE IF EXISTS `rsourcestab`;

CREATE TABLE `rsourcestab` (
  `rid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `employeeid` int(11) DEFAULT NULL COMMENT '用户表id',
  `byisuse` int(11) DEFAULT '1' COMMENT '系统状态：1:启用 0:禁用',
  `sourcename` varchar(200) DEFAULT NULL COMMENT '系统',
  `dtenddate` varchar(20) DEFAULT NULL COMMENT '截止日期',
  `dtmakedate` varchar(20) DEFAULT NULL COMMENT '操作日期',
  PRIMARY KEY (`rid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='用户系统通用表';

/*Data for the table `rsourcestab` */

insert  into `rsourcestab`(`rid`,`employeeid`,`byisuse`,`sourcename`,`dtenddate`,`dtmakedate`) values (1,2,0,'saas','2019-09-23','2019-09-23 17:40:20'),(2,2,1,'zfq','2019-09-23','2019-09-23 17:27:31'),(3,2,1,'zhyx','2019-09-23','2019-09-23 17:27:31'),(4,1,1,'saas','2019-09-23','2019-09-23 17:27:22'),(19,11,1,'saas','2019-09-25','2019-09-25 14:41:54'),(20,11,1,'zfq','2019-09-25','2019-09-25 14:33:01'),(21,11,1,'zhyx','2019-09-25','2019-09-25 14:33:03');

/*Table structure for table `sc` */

DROP TABLE IF EXISTS `sc`;

CREATE TABLE `sc` (
  `sid` varchar(4) NOT NULL,
  `cid` varchar(4) NOT NULL,
  `score` double(5,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sc` */

insert  into `sc`(`sid`,`cid`,`score`) values ('1','1',80.00),('1','2',90.00),('1','3',99.00),('2','1',70.00),('2','2',60.00),('2','3',80.00),('3','1',80.00),('3','2',80.00),('3','3',80.00),('4','1',50.00),('4','2',30.00),('4','3',20.00),('5','1',76.00),('5','2',87.00);

/*Table structure for table `score` */

DROP TABLE IF EXISTS `score`;

CREATE TABLE `score` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `memberId` varchar(64) NOT NULL,
  `score` double(5,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Data for the table `score` */

insert  into `score`(`id`,`memberId`,`score`) values (1,'100',50.50),(2,'101',30.60),(3,'102',20.00),(4,'103',60.30),(5,'104',80.80),(6,'105',50.70),(7,'106',70.90),(8,'107',20.00),(9,'108',80.80);

/*Table structure for table `sequence` */

DROP TABLE IF EXISTS `sequence`;

CREATE TABLE `sequence` (
  `seq_name` varchar(50) NOT NULL,
  `current_val` int(11) NOT NULL,
  `increment_val` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`seq_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sequence` */

insert  into `sequence`(`seq_name`,`current_val`,`increment_val`) values ('icompanyinfoid',60,1);

/*Table structure for table `syslog` */

DROP TABLE IF EXISTS `syslog`;

CREATE TABLE `syslog` (
  `logid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `employeeid` int(11) DEFAULT NULL COMMENT '操作人',
  `stlg` varchar(20) DEFAULT NULL COMMENT '操作类型',
  `brief` varchar(500) DEFAULT NULL COMMENT '操作前信息',
  `note` varchar(500) DEFAULT NULL COMMENT '操作后信息',
  `logdatetime` varchar(20) DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`logid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

/*Data for the table `syslog` */

insert  into `syslog`(`logid`,`employeeid`,`stlg`,`brief`,`note`,`logdatetime`) values (1,1,'0001','注册用户','用户:admin注册成功！','2019-09-23 14:09:23'),(2,1,'0001','注册用户','用户:abing注册成功！','2019-09-23 14:13:49'),(3,1,'0002','用户:2','用户:abing审核通过！','2019-09-23 14:35:24'),(4,1,'0001','注册用户','用户:admin14注册成功！','2019-09-24 08:46:14'),(5,1,'0002','审核用户:3','用户:admin14审核通过！','2019-09-24 08:47:40'),(6,1,'0001','注册用户','用户:admin15注册成功！','2019-09-24 08:56:04'),(7,1,'0002','审核用户:4','用户:admin15审核通过！','2019-09-24 08:57:37'),(8,1,'0001','注册用户','用户:admin16注册成功！','2019-09-24 09:05:31'),(9,1,'0002','审核用户:5','用户:admin16审核通过！','2019-09-24 09:05:52'),(10,1,'0001','注册用户','用户:admin17注册成功！','2019-09-24 09:36:20'),(11,1,'0002','审核用户:6','用户:admin17审核通过！','2019-09-24 09:48:08'),(12,1,'0001','注册用户','用户:admin18注册成功！','2019-09-24 10:02:28'),(13,1,'0002','审核用户:7','用户:admin18审核通过！','2019-09-24 10:02:45'),(14,1,'0001','注册用户','用户:admin19注册成功！','2019-09-24 10:06:59'),(15,1,'0002','审核用户:8','用户:admin19审核通过！','2019-09-24 10:07:13'),(16,1,'0001','注册用户姓名san','用户:aaaaa注册成功！','2019-09-25 11:32:45'),(17,NULL,'0001','saas用户注册。。','用户:test注册成功！','2019-09-25 14:04:31'),(18,1,'0001','注册用户姓名zhangsan','用户:admin1注册成功！','2019-09-25 14:24:05'),(19,1,'0002','审核用户:11','用户:admin1审核通过！','2019-09-25 14:32:48'),(20,NULL,'0005','用户：1','初始化用户：admin密码','2019-09-25 16:03:30'),(21,NULL,'0005','用户：1','初始化用户：admin密码','2019-09-25 16:08:46'),(22,NULL,'0001','saas用户注册。。','用户:centertest注册成功！','2019-09-26 15:16:23'),(23,NULL,'0001','saas用户注册。。','用户:centertest注册成功！','2019-09-26 15:17:52'),(24,NULL,'0005','用户：13','初始化用户：centertest密码','2019-09-26 15:56:40'),(25,NULL,'0001','注册用户姓名test01','用户:test01注册成功！','2019-10-14 15:04:53'),(26,NULL,'0001','注册用户姓名test02','用户:test02注册成功！','2019-10-14 15:05:32'),(27,NULL,'0001','注册用户姓名test03','用户:test03注册成功！','2019-10-14 15:06:18'),(28,NULL,'0001','注册用户姓名test04','用户:test04注册成功！','2019-10-14 15:06:59'),(29,NULL,'0001','注册用户姓名test05','用户:test05注册成功！','2019-10-14 15:08:45'),(30,NULL,'0001','注册用户姓名test06','用户:test06注册成功！','2019-10-14 15:09:53'),(31,NULL,'0001','注册用户姓名test07','用户:test07注册成功！','2019-10-14 15:10:23'),(32,NULL,'0001','注册用户姓名test08','用户:test08注册成功！','2019-10-14 15:11:27');

/*Table structure for table `sysparv5` */

DROP TABLE IF EXISTS `sysparv5`;

CREATE TABLE `sysparv5` (
  `pmky` varchar(10) NOT NULL COMMENT '参数key',
  `pmcd` varchar(10) NOT NULL COMMENT '参数码',
  `spmcd` varchar(10) DEFAULT NULL COMMENT '上级参数码',
  `systp` varchar(10) NOT NULL COMMENT '参数级别',
  `pmva` varchar(100) NOT NULL COMMENT '参数A',
  `pmvb` varchar(100) DEFAULT NULL COMMENT '参数B',
  `pmvc` varchar(100) DEFAULT NULL COMMENT '参数C',
  `pmvd` varchar(100) DEFAULT NULL COMMENT '参数D',
  `pmve` varchar(100) DEFAULT NULL COMMENT '参数E',
  `pmvf` varchar(100) DEFAULT NULL COMMENT '参数F',
  `isa` int(11) DEFAULT NULL COMMENT '整形备注A',
  `isb` int(11) DEFAULT NULL COMMENT '整形备注B',
  `isc` int(11) DEFAULT NULL COMMENT '整形备注C',
  `isd` int(11) DEFAULT NULL COMMENT '整形备注D',
  `ise` int(11) DEFAULT NULL COMMENT '整形备注E',
  `isf` int(11) DEFAULT NULL COMMENT '整形备注F',
  `isvalue` int(11) DEFAULT NULL COMMENT '是否启用',
  `note` varchar(1000) DEFAULT NULL COMMENT '备注',
  `dtmakedate` varchar(20) DEFAULT NULL COMMENT '操作日期',
  PRIMARY KEY (`pmky`,`pmcd`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sysparv5` */

insert  into `sysparv5`(`pmky`,`pmcd`,`spmcd`,`systp`,`pmva`,`pmvb`,`pmvc`,`pmvd`,`pmve`,`pmvf`,`isa`,`isb`,`isc`,`isd`,`ise`,`isf`,`isvalue`,`note`,`dtmakedate`) values ('DJXT','****',NULL,'0','对接系统','','','','','',1,NULL,NULL,NULL,NULL,NULL,1,'',NULL),('DJXT','saas','****','1','SAAS','','','','','',NULL,NULL,NULL,NULL,NULL,NULL,1,'',NULL),('DJXT','zfq','****','1','支付桥','','','','','',NULL,NULL,NULL,NULL,NULL,NULL,1,'',NULL),('DJXT','zhyx','****','1','整合营销','','','','','',NULL,NULL,NULL,NULL,NULL,NULL,1,'',NULL),('STLG','****',NULL,'0','系统日志','','','','','',NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL),('STLG','0001','****','1','用户注册','','','','','',NULL,NULL,NULL,NULL,NULL,NULL,1,'',NULL),('STLG','0002','****','1','用户审核','','','','','',NULL,NULL,NULL,NULL,NULL,NULL,1,'',NULL),('STLG','0003','','1','用户修改','','','','','',NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL),('STLG','0004','****','1','禁用用户','','','','','',NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL),('STLG','0005','****','1','用户密码初始化','','','','','',NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL),('STLG','0006','****','1','用户加入黑名单','','','','','',NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL),('STLG','0007','****','1','用户取消黑名单','','','','','',NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL);

/* Function  structure for function  `currval` */

/*!50003 DROP FUNCTION IF EXISTS `currval` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` FUNCTION `currval`(v_seq_name VARCHAR(50)) RETURNS int(11)
begin        
    declare value integer;         
    set value = 0;         
    select current_val into value  from sequence where seq_name = v_seq_name;   
   return value;   
end */$$
DELIMITER ;

/* Function  structure for function  `nextval` */

/*!50003 DROP FUNCTION IF EXISTS `nextval` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` FUNCTION `nextval`(v_seq_name VARCHAR(50)) RETURNS int(11)
begin  
    update sequence set current_val = current_val + increment_val  where seq_name = v_seq_name;  
    return currval(v_seq_name);  
end */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
