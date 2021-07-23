
drop table IF EXISTS "FWGL"."BUS_SVC_INFO_GEO" CASCADE;
drop table IF EXISTS "FWGL"."BUS_SVC_STYLE" CASCADE;
drop table IF EXISTS "FWGL"."BUS_SVC_SUBJECT" CASCADE;
drop table IF EXISTS "FWGL"."BUS_SVC_TYPE" CASCADE;
drop table IF EXISTS "FWGL"."SYS_DICT" CASCADE;
drop table IF EXISTS "FWGL"."SYS_LOG" CASCADE;
drop table IF EXISTS "FWGL"."SYS_ROLE_MENU" CASCADE;
drop table IF EXISTS "FWGL"."SYS_USER_ROLE" CASCADE;
drop table IF EXISTS "FWGL"."SYS_USER" CASCADE;
drop table IF EXISTS "FWGL"."SYS_LOGIN_LOG" CASCADE;
drop table IF EXISTS "FWGL"."SYS_ROLE_DEPT" CASCADE;
drop table IF EXISTS "FWGL"."SYS_MENU" CASCADE;
drop table IF EXISTS "FWGL"."SYS_ROLE" CASCADE;
drop table IF EXISTS "FWGL"."SYS_DEPT" CASCADE;



-- ----------------------------
-- Table structure for "FWGL"."SYS_DICT"
-- ----------------------------
CREATE TABLE "FWGL"."SYS_DICT"
(
"ID" BIGINT NOT NULL,
"CREATE_BY" VARCHAR(255),
"CREATE_TIME" DATETIME(6),
"LAST_UPDATE_BY" VARCHAR(255),
"LAST_UPDATE_TIME" DATETIME(6),
"DEL_FLAG" TINYINT,
"DESCRIPTION" VARCHAR(255),
"LABEL" VARCHAR(255),
"REMARKS" VARCHAR(255),
"SORT" BIGINT,
"TITLE" VARCHAR(255),
"TYPE" VARCHAR(255),
"VALUE" VARCHAR(255),
CLUSTER PRIMARY KEY("ID")) STORAGE(ON "MAIN", CLUSTERBTR) ;


-- ----------------------------
-- Table structure for "FWGL"."SYS_DEPT"
-- ----------------------------
CREATE TABLE "FWGL"."SYS_DEPT"
(
"ID" BIGINT NOT NULL,
"CREATE_BY" VARCHAR(255),
"CREATE_TIME" DATETIME(6),
"LAST_UPDATE_BY" VARCHAR(255),
"LAST_UPDATE_TIME" DATETIME(6),
"ADDRESS" VARCHAR(255),
"CONTACT" VARCHAR(255),
"DEL_FLAG" TINYINT,
"EMAIL" VARCHAR(255),
"NAME" VARCHAR(255),
"ORDER_NUM" INTEGER,
"PARENT_ID" BIGINT,
"PHONE_NUMBER" VARCHAR(255),
"WEBSITE" VARCHAR(255),
CLUSTER PRIMARY KEY("ID")) STORAGE(ON "MAIN", CLUSTERBTR) ;


-- ----------------------------
-- Table structure for "FWGL"."SYS_MENU"
-- ----------------------------
CREATE TABLE "FWGL"."SYS_MENU"
(
"ID" BIGINT NOT NULL,
"CREATE_BY" VARCHAR(255),
"CREATE_TIME" DATETIME(6),
"LAST_UPDATE_BY" VARCHAR(255),
"LAST_UPDATE_TIME" DATETIME(6),
"DEL_FLAG" TINYINT,
"ICON" VARCHAR(255),
"NAME" VARCHAR(255),
"ORDER_NUM" INTEGER,
"PARENT_ID" BIGINT,
"PERMS" VARCHAR(255),
"TYPE" INTEGER,
"URL" VARCHAR(255),
CLUSTER PRIMARY KEY("ID")) STORAGE(ON "MAIN", CLUSTERBTR) ;


-- ----------------------------
-- Table structure for "FWGL"."SYS_ROLE"
-- ----------------------------
CREATE TABLE "FWGL"."SYS_ROLE"
(
"ID" BIGINT NOT NULL,
"CREATE_BY" VARCHAR(255),
"CREATE_TIME" DATETIME(6),
"LAST_UPDATE_BY" VARCHAR(255),
"LAST_UPDATE_TIME" DATETIME(6),
"DEL_FLAG" TINYINT,
"FLAG" VARCHAR(255),
"NAME" VARCHAR(255),
"REMARK" VARCHAR(255),
CLUSTER PRIMARY KEY("ID")) STORAGE(ON "MAIN", CLUSTERBTR) ;


-- ----------------------------
-- Table structure for "FWGL"."SYS_USER"
-- ----------------------------
CREATE TABLE "FWGL"."SYS_USER"
(
"ID" BIGINT NOT NULL,
"CREATE_BY" VARCHAR(255),
"CREATE_TIME" DATETIME(6),
"LAST_UPDATE_BY" VARCHAR(255),
"LAST_UPDATE_TIME" DATETIME(6),
"AVATAR" VARCHAR(255),
"DEL_FLAG" TINYINT,
"DEPT_ID" BIGINT,
"EMAIL" VARCHAR(255),
"MOBILE" VARCHAR(255),
"NICK_NAME" VARCHAR(255),
"PASSWORD" VARCHAR(255),
"SALT" VARCHAR(255),
"SEX" TINYINT,
"STATUS" TINYINT,
"USERNAME" VARCHAR(255),
CLUSTER PRIMARY KEY("ID")) STORAGE(ON "MAIN", CLUSTERBTR) ;


-- ----------------------------
-- Table structure for "FWGL"."SYS_LOG"
-- ----------------------------
CREATE TABLE "FWGL"."SYS_LOG"
(
"ID" BIGINT NOT NULL,
"CREATE_BY" VARCHAR(255),
"CREATE_TIME" DATETIME(6),
"LAST_UPDATE_BY" VARCHAR(255),
"LAST_UPDATE_TIME" DATETIME(6),
"IP" VARCHAR(255),
"METHOD" VARCHAR(255),
"OPERATION" VARCHAR(255),
"PARAMS" VARCHAR(255),
"TIME" BIGINT,
"USER_NAME" VARCHAR(255),
CLUSTER PRIMARY KEY("ID")) STORAGE(ON "MAIN", CLUSTERBTR) ;


-- ----------------------------
-- Table structure for "FWGL"."SYS_LOGIN_LOG"
-- ----------------------------
CREATE TABLE "FWGL"."SYS_LOGIN_LOG"
(
"ID" BIGINT NOT NULL,
"CREATE_BY" VARCHAR(255),
"CREATE_TIME" DATETIME(6),
"LAST_UPDATE_BY" VARCHAR(255),
"LAST_UPDATE_TIME" DATETIME(6),
"IP" VARCHAR(255),
"STATUS" VARCHAR(255),
"USER_NAME" VARCHAR(255),
CLUSTER PRIMARY KEY("ID")) STORAGE(ON "MAIN", CLUSTERBTR) ;


-- ----------------------------
-- Table structure for "FWGL"."SYS_ROLE_DEPT"
-- ----------------------------
CREATE TABLE "FWGL"."SYS_ROLE_DEPT"
(
"ID" BIGINT NOT NULL,
"CREATE_BY" VARCHAR(255),
"CREATE_TIME" DATETIME(6),
"LAST_UPDATE_BY" VARCHAR(255),
"LAST_UPDATE_TIME" DATETIME(6),
"DEPT_ID" BIGINT,
"ROLE_ID" BIGINT,
CLUSTER PRIMARY KEY("ID")) STORAGE(ON "MAIN", CLUSTERBTR) ;


-- ----------------------------
-- Table structure for "FWGL"."SYS_ROLE_MENU"
-- ----------------------------
CREATE TABLE "FWGL"."SYS_ROLE_MENU"
(
"ID" BIGINT NOT NULL,
"CREATE_BY" VARCHAR(255),
"CREATE_TIME" DATETIME(6),
"LAST_UPDATE_BY" VARCHAR(255),
"LAST_UPDATE_TIME" DATETIME(6),
"MENU_ID" BIGINT,
"ROLE_ID" BIGINT,
CLUSTER PRIMARY KEY("ID")) STORAGE(ON "MAIN", CLUSTERBTR) ;


-- ----------------------------
-- Table structure for "FWGL"."SYS_USER_ROLE"
-- ----------------------------
CREATE TABLE "FWGL"."SYS_USER_ROLE"
(
"ID" BIGINT NOT NULL,
"CREATE_BY" VARCHAR(255),
"CREATE_TIME" DATETIME(6),
"LAST_UPDATE_BY" VARCHAR(255),
"LAST_UPDATE_TIME" DATETIME(6),
"ROLE_ID" BIGINT,
"USER_ID" BIGINT,
CLUSTER PRIMARY KEY("ID")) STORAGE(ON "MAIN", CLUSTERBTR) ;




-- ----------------------------
-- Table structure for "FWGL"."BUS_SVC_SUBJECT"
-- ----------------------------
CREATE TABLE "FWGL"."BUS_SVC_SUBJECT"
(
"SUBJECT_ID" BIGINT NOT NULL,
"CREATE_BY" VARCHAR(255),
"CREATE_TIME" DATETIME(6),
"DEL_FLAG" TINYINT,
"LAST_UPDATE_BY" VARCHAR(255),
"LAST_UPDATE_TIME" DATETIME(6),
"NAME" VARCHAR(255),
CLUSTER PRIMARY KEY("SUBJECT_ID")) STORAGE(ON "MAIN", CLUSTERBTR) ;


-- ----------------------------
-- Table structure for "FWGL"."BUS_SVC_TYPE"
-- ----------------------------
CREATE TABLE "FWGL"."BUS_SVC_TYPE"
(
"TYPE_ID" BIGINT NOT NULL,
"CREATE_BY" VARCHAR(255),
"CREATE_TIME" DATETIME(6),
"DEL_FLAG" TINYINT,
"LAST_UPDATE_BY" VARCHAR(255),
"LAST_UPDATE_TIME" DATETIME(6),
"NAME" VARCHAR(255),
"SUBJECT_ID" BIGINT,
CLUSTER PRIMARY KEY("TYPE_ID"),
CONSTRAINT "FK1OWU18WGA8N4JC6EXMNRCNXQ9" FOREIGN KEY("SUBJECT_ID") REFERENCES "FWGL"."BUS_SVC_SUBJECT"("SUBJECT_ID")) STORAGE(ON "MAIN", CLUSTERBTR) ;


-- ----------------------------
-- Table structure for "FWGL"."BUS_SVC_STYLE"
-- ----------------------------
CREATE TABLE "FWGL"."BUS_SVC_STYLE"
(
"STYLE_ID" BIGINT NOT NULL,
"CREATE_BY" VARCHAR(255),
"CREATE_TIME" DATETIME(6),
"DEL_FLAG" TINYINT,
"LAST_UPDATE_BY" VARCHAR(255),
"LAST_UPDATE_TIME" DATETIME(6),
"NAME" VARCHAR(255),
CLUSTER PRIMARY KEY("STYLE_ID")) STORAGE(ON "MAIN", CLUSTERBTR) ;


-- ----------------------------
-- Table structure for "FWGL"."BUS_SVC_INFO_GEO"
-- ----------------------------
CREATE TABLE "FWGL"."BUS_SVC_INFO_GEO"
(
"SVC_ID" BIGINT NOT NULL,
"CREATE_BY" VARCHAR(255),
"CREATE_TIME" DATETIME(6),
"HOME_PAGE_URL" VARCHAR(255),
"INTERFACE_SITE_URL" VARCHAR(255),
"IS_GIS_SVC" INTEGER,
"LAST_UPDATE_BY" VARCHAR(255),
"LAST_UPDATE_TIME" DATETIME(6),
"PERMS" VARCHAR(255),
"PREVIEW_URL" VARCHAR(255),
"ROUTING_NAME" VARCHAR(255),
"SVC_ALIAS" VARCHAR(255),
"SVC_DESC" VARCHAR(255),
"SVC_KEYWORD" VARCHAR(255),
"SVC_NAME" VARCHAR(255),
"SVC_SUB_TYPE" VARCHAR(255),
"SVC_VERSION" VARCHAR(255),
"TERMINAL" INTEGER,
"THUMBNAILS" VARCHAR(255),
"AUDIT_OPINION" VARCHAR(255),
"AUDIT_STATE" TINYINT,
"AUDIT_TIME" DATETIME(6),
"INSTRO_DESC" VARCHAR(255),
"INSTRO_REQUEST_PARAM" TEXT,
"INSTRO_REQUEST_TYPE" VARCHAR(255),
"LAYER_COORDINATE_SYSTEM" VARCHAR(255),
"LAYER_COVERAGE" VARCHAR(255),
"LAYER_DESC" VARCHAR(255),
"LAYER_KEYWORD" VARCHAR(255),
"LAYER_NAME" VARCHAR(255),
"LAYER_PROJECTION_TYPE" VARCHAR(255),
"LAYER_SERVICE_AREA" VARCHAR(255),
"LAYER_UPDATE_CYCLE" VARCHAR(255),
"LAYER_UPDATE_TIME" DATETIME(6),
"RELEASE_STATE" TINYINT,
"RELEASE_TIME" DATETIME(6),
"SVC_COORDINATE_SYSTEM" VARCHAR(255),
"SVC_COVERAGE" VARCHAR(255),
"SVC_PROJECTION_TYPE" VARCHAR(255),
"SVC_SERVICE_AREA" VARCHAR(255),
"SVC_UPDATE_CYCLE" VARCHAR(255),
"TYPE_ID" BIGINT,
"ID" BIGINT,
"STYLE_ID" BIGINT,
NOT CLUSTER PRIMARY KEY("SVC_ID"),
CONSTRAINT "FKH518YKU3K0TYAAIVGEXMAWKR3" FOREIGN KEY("TYPE_ID") REFERENCES "FWGL"."BUS_SVC_TYPE"("TYPE_ID"),
CONSTRAINT "FK7YNKTUAMBNACSNHHMGXOESJ34" FOREIGN KEY("ID") REFERENCES "FWGL"."SYS_DEPT"("ID"),
CONSTRAINT "FKRE32Hl81LCWP6OW4GGQX6O5F" FOREIGN KEY("STYLE_ID") REFERENCES "FWGL"."BUS_SVC_STYLE"("STYLE_ID")) STORAGE(ON "MAIN", CLUSTERBTR);

CREATE SEQUENCE "FWGL"."HIBERNATE_SEQUENCE" INCREMENT BY 1 START WITH 1 MAXVALUE 9223372036854775807 MINVALUE 1;
