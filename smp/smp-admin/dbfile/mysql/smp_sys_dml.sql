

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (1, 'admin', '2020-07-22 14:46:27', 'admin', '2020-07-22 14:46:27', 1, '性别男', '男', '性别', 0, NULL, 'sex', 'male');
INSERT INTO `sys_dict` VALUES (2, 'admin', '2020-07-22 14:46:27', 'admin', '2020-07-22 14:46:27', 1, '性别女', '女', '性别', 1, NULL, 'sex', 'female');
INSERT INTO `sys_dict` VALUES (3, 'admin', '2020-11-19 02:36:45', 'admin', '2020-11-19 02:37:53', 1, '终端类型-通用型', '通用', '终端类型', 0, NULL, 'terminal', '1');
INSERT INTO `sys_dict` VALUES (4, 'admin', '2020-11-19 02:39:10', 'admin', '2020-11-19 02:39:10', 1, '终端类型-Web端', 'Web端', '终端类型', 0, NULL, 'terminal', '2');
INSERT INTO `sys_dict` VALUES (5, 'admin', '2020-11-19 02:39:29', 'admin', '2020-11-19 02:39:29', 1, '终端类型-桌面端', '桌面端', '终端类型', 0, NULL, 'terminal', '3');
INSERT INTO `sys_dict` VALUES (6, 'admin', '2020-11-19 02:40:46', 'admin', '2020-11-19 02:40:46', 1, '审核状态-未审核', '未审核', '审核状态', 0, NULL, 'auditState', '0');
INSERT INTO `sys_dict` VALUES (7, 'admin', '2020-11-19 02:41:08', 'admin', '2020-11-19 02:41:08', 1, '审核状态-审核通过', '审核通过', '审核状态', 0, NULL, 'auditState', '1');
INSERT INTO `sys_dict` VALUES (8, 'admin', '2020-11-19 02:41:28', 'admin', '2020-11-19 02:41:28', 1, '审核状态-审核拒绝', '审核拒绝', '审核状态', 0, NULL, 'auditState', '2');
INSERT INTO `sys_dict` VALUES (9, 'admin', '2020-11-19 02:42:09', 'admin', '2020-11-19 02:42:09', 1, '发布状态-已发布', '已发布', '发布状态', 0, NULL, 'releaseState', '1');
INSERT INTO `sys_dict` VALUES (10, 'admin', '2020-11-19 02:42:28', 'admin', '2020-11-19 02:42:28', 1, '发布状态-未发布', '未发布', '发布状态', 0, NULL, 'releaseState', '0');



-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 'admin', '2020-07-22 14:45:42', 'admin', '2020-07-22 14:45:42', 1, NULL, 'admin', '超级管理员');
INSERT INTO `sys_role` VALUES (2, 'admin', '2020-07-22 14:45:42', 'admin', '2020-07-22 14:45:42', 1, NULL, 'mng', '项目经理');
INSERT INTO `sys_role` VALUES (3, 'admin', '2020-07-22 14:45:42', 'admin', '2020-07-22 14:45:42', 1, NULL, 'dev', '开发人员');
INSERT INTO `sys_role` VALUES (4, 'admin', '2020-07-22 14:45:42', 'admin', '2020-07-22 14:45:42', 1, NULL, 'test', '测试人员');


-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1, 'admin', '2020-07-22 15:11:41', 'admin', '2020-07-22 15:11:41', '北京市海淀区北四环中路211号（100083）', '工作人员', 1, 'xxhtjb@cetc.com.cn', '中国电子科技集团15所', 0, NULL, '010-89055775', 'http://www.nci.ac.cn/');
INSERT INTO `sys_dept` VALUES (2, 'admin', '2020-07-22 15:11:41', 'admin', '2020-07-22 15:11:41', '北京市朝阳区酒仙桥北路甲10号院电子城IT产业园107楼（100015）', '工作人员', 1, 'support@supermap.com', '星环科技', 1, NULL, '+86-10-59896655', 'https://www.supermap.com/');
INSERT INTO `sys_dept` VALUES (3, 'admin', '2020-07-22 15:11:41', 'admin', '2020-07-22 15:11:41', '北京市朝阳区酒仙桥北路甲10号院电子城IT产业园107楼（100015）', '工作人员', 1, 'support@supermap.com', '超图', 2, NULL, '+86-10-59896655', 'https://www.supermap.com/');


-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, 'el-icon-setting', '权限管理', 0, 0, NULL, 0, NULL, NULL);
INSERT INTO `sys_menu` VALUES (2, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, 'el-icon-service', '用户管理', 1, 1, NULL, 1, '/sys/user', NULL);
INSERT INTO `sys_menu` VALUES (3, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '查看', 0, 2, 'sys:user:view', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (4, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '新增', 0, 2, 'sys:user:add', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (5, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '修改', 0, 2, 'sys:user:edit', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (6, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '删除', 0, 2, 'sys:user:delete', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (7, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, 'el-icon-news', '机构管理', 2, 1, NULL, 1, '/sys/dept', NULL);
INSERT INTO `sys_menu` VALUES (8, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '查看', 0, 7, 'sys:dept:view', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (9, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '新增', 0, 7, 'sys:dept:add', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (10, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '修改', 0, 7, 'sys:dept:edit', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (11, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '删除', 0, 7, 'sys:dept:delete', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (12, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, 'el-icon-view', '角色管理', 3, 1, NULL, 1, '/sys/role', NULL);
INSERT INTO `sys_menu` VALUES (13, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '查看', 0, 12, 'sys:role:view', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (14, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '新增', 0, 12, 'sys:role:add', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (15, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '修改', 0, 12, 'sys:role:edit', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (16, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '删除', 0, 12, 'sys:role:delete', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (17, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, 'el-icon-menu', '菜单管理', 4, 1, NULL, 1, '/sys/menu', NULL);
INSERT INTO `sys_menu` VALUES (18, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '查看', 0, 17, 'sys:menu:view', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (19, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '新增', 0, 17, 'sys:menu:add', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (20, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '修改', 0, 17, 'sys:menu:edit', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (21, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '删除', 0, 17, 'sys:menu:delete', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (22, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, 'el-icon-setting', '系统配置', 1, 0, NULL, 0, NULL, NULL);
INSERT INTO `sys_menu` VALUES (23, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, 'el-icon-edit-outline', '字典管理', 1, 22, NULL, 1, '/sys/dict', NULL);
INSERT INTO `sys_menu` VALUES (24, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '查看', 0, 23, 'sys:dict:view', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (25, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '新增', 0, 23, 'sys:dict:add', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (26, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '修改', 0, 23, 'sys:dict:edit', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (27, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '删除', 0, 23, 'sys:dict:delete', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (28, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, 'el-icon-setting', '系统监控', 2, 0, NULL, 0, NULL, NULL);
INSERT INTO `sys_menu` VALUES (29, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, 'el-icon-info', '操作日志', 1, 28, NULL, 1, '/sys/log', NULL);
INSERT INTO `sys_menu` VALUES (30, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '查看', 0, 29, 'sys:log:view', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (31, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '删除', 0, 29, 'sys:log:delete', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (32, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, 'el-icon-info', '主机监控', 2, 28, NULL, 1, '/sys/monitor/mainFrame', NULL);
INSERT INTO `sys_menu` VALUES (33, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '查看', 0, 32, 'sys:monitor:mainframe:view', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (34, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, 'el-icon-info', '在线用户', 3, 28, NULL, 1, '/sys/online', NULL);
INSERT INTO `sys_menu` VALUES (35, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '查看', 0, 34, 'sys:online:view', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (36, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, 'el-icon-setting', '服务管理', 3, 0, NULL, 0, NULL, NULL);
INSERT INTO `sys_menu` VALUES (37, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, 'el-icon-document', '服务风格', 1, 36, NULL, 1, '/bus/svc/style', NULL);
INSERT INTO `sys_menu` VALUES (38, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '查看', 0, 37, 'svc:style:view', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (39, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '新增', 0, 37, 'svc:style:add', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (40, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '修改', 0, 37, 'svc:style:edit', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (41, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '删除', 0, 37, 'svc:style:delete', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (42, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, 'el-icon-document', '服务主题', 1, 36, NULL, 1, '/bus/svc/subject', NULL);
INSERT INTO `sys_menu` VALUES (43, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '查看', 0, 42, 'svc:subject:view', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (44, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '新增', 0, 42, 'svc:subject:add', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (45, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '修改', 0, 42, 'svc:subject:edit', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (46, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '删除', 0, 42, 'svc:subject:delete', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (47, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, 'el-icon-document', '服务类型', 2, 36, NULL, 1, '/bus/svc/type', NULL);
INSERT INTO `sys_menu` VALUES (48, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '查看', 0, 47, 'svc:type:view', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (49, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '新增', 0, 47, 'svc:type:add', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (50, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '修改', 0, 47, 'svc:type:edit', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (51, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '删除', 0, 47, 'svc:type:delete', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (52, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, 'el-icon-document', '服务注册', 3, 36, NULL, 1, '/bus/svc/register', NULL);
INSERT INTO `sys_menu` VALUES (53, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '注册', 0, 52, 'svc:register:regist', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (54, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, 'el-icon-document', '服务审核', 4, 36, NULL, 1, '/bus/svc/audit', NULL);
INSERT INTO `sys_menu` VALUES (55, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '查看', 0, 54, 'svc:audit:view', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (56, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '审核', 0, 54, 'svc:audit:audit', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (57, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '删除', 0, 54, 'svc:audit:delete', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (58, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, 'el-icon-document', '服务发布', 5, 36, NULL, 1, '/bus/svc/release', NULL);
INSERT INTO `sys_menu` VALUES (59, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '查看', 0, 58, 'svc:release:view', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (60, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '发布', 0, 58, 'svc:release:release', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (61, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '删除', 0, 58, 'svc:release:delete', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (62, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, 'el-icon-files', '服务浏览', 6, 36, NULL, 1, '/bus/svc/view', NULL);
INSERT INTO `sys_menu` VALUES (63, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '浏览', 0, 62, 'svc:view:view', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (64, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '修改', 0, 62, 'svc:view:edit', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (65, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '删除', 0, 62, 'svc:view:delete', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (66, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, 'el-icon-setting', '统计分析', 4, 0, NULL, 0, NULL, NULL);
INSERT INTO `sys_menu` VALUES (67, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, 'el-icon-pie-chart', '服务种类统计', 1, 66, NULL, 1, '/statistics/svc/kind', NULL);
INSERT INTO `sys_menu` VALUES (68, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '查看', 0, 67, 'stat:svc:kind:view', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (69, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, 'el-icon-data-analysis', '服务访问量统计', 2, 66, NULL, 1, '/statistics/svc/visit', NULL);
INSERT INTO `sys_menu` VALUES (70, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '查看', 0, 69, 'stat:svc:visit:view', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (71, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, 'el-icon-setting', '开放平台', 5, 0, NULL, 0, NULL, NULL);
INSERT INTO `sys_menu` VALUES (72, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, 'el-icon-document', '接口文档', 1, 71, NULL, 1, 'http://192.168.56.222:8998/swagger-ui.html', 'interface_swagger');
INSERT INTO `sys_menu` VALUES (73, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, NULL, '查看', 0, 72, 'open:interface:view', 2, NULL, NULL);
INSERT INTO `sys_menu` VALUES (74, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, 'el-icon-info', '登录日志', 0, 28, '', 1, '/sys/loginlog', NULL);
INSERT INTO `sys_menu` VALUES (75, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, '', '查看', 0, 74, 'sys:loginlog:view', 2, '', NULL);
INSERT INTO `sys_menu` VALUES (76, 'admin', '2020-07-22 14:50:18', 'admin', '2020-07-22 14:50:18', 1, '', '删除', 0, 74, 'sys:loginlog:delete', 2, '', NULL);


-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '2020-07-22 14:39:25', 'admin', '2020-07-22 07:14:15', NULL, 1, 1, 'admin@qq.com', '13612345678', '超管', 'bd1718f058d8a02468134432b8656a86', 'YzcmCZNvbXocrsz9dm8e', 1, 1, 'admin');
INSERT INTO `sys_user` VALUES (2, 'admin', '2020-07-22 14:40:57', 'liubei', '2020-10-19 10:12:45', NULL, 1, 1, 'test@qq.com', '13889700023', '刘备', 'c81d2b03bad872229ac88a50fe3d6bca', '1d00dc66ed0e41d8ba9a', 2, 1, 'liubei');
INSERT INTO `sys_user` VALUES (3, 'admin', '2020-07-22 14:41:13', 'admin', '2021-04-20 09:33:14', NULL, 1, 1, 'test@qq.com', '13889700023', '赵云', '6d0f906cddc23248ae4541cc0b4cf379', '1ed6d768348046afa324', 1, 1, 'zhaoyun');
INSERT INTO `sys_user` VALUES (4, 'admin', '2020-07-22 14:42:57', 'admin', '2020-07-22 14:42:45', NULL, 1, 1, 'test@qq.com', '13889700023', '诸葛亮', 'c4a18dc00032bd46619ce716a8dac74a', '3ae5bb7e44ce4b28987a', 1, 1, 'zhugeliang');
INSERT INTO `sys_user` VALUES (5, 'admin', '2020-07-22 14:43:00', 'admin', '2020-10-19 09:58:05', NULL, 1, 1, 'test@qq.com', '13889700023', '曹操', '17c463246f09a430c0dcef02f4a1a913', '53224106d0c84b7ebd0a', 2, 1, 'caocao');
INSERT INTO `sys_user` VALUES (6, 'admin', '2020-07-22 14:44:40', 'admin', '2020-07-22 14:44:32', NULL, 1, 1, 'test@qq.com', '13889700023', '夏侯惇', '6d604e035c517c665613c76c94f9d39f', 'c623563af6234bbd864b', 1, 1, 'xiahoudun');


-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 'admin', '2020-07-22 07:14:15', 'admin', '2020-07-22 07:14:15', 1, 1);
INSERT INTO `sys_user_role` VALUES (2, 'admin', '2020-07-22 07:14:15', 'admin', '2020-07-22 07:14:15', 2, 1);
INSERT INTO `sys_user_role` VALUES (3, 'admin', '2020-07-22 07:14:15', 'admin', '2020-07-22 07:14:15', 3, 1);
INSERT INTO `sys_user_role` VALUES (4, 'admin', '2020-07-22 07:14:15', 'admin', '2020-07-22 07:14:15', 4, 1);
INSERT INTO `sys_user_role` VALUES (5, 'admin', '2020-10-19 10:12:45', 'admin', '2020-10-19 10:12:45', 2, 2);
INSERT INTO `sys_user_role` VALUES (6, 'admin', '2021-04-20 09:33:14', 'admin', '2021-04-20 09:33:14', 2, 3);
INSERT INTO `sys_user_role` VALUES (7, 'admin', '2020-10-19 09:58:05', 'admin', '2020-10-19 09:58:05', 4, 5);


-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------
INSERT INTO `sys_role_dept` VALUES (1, 'admin', '2020-07-22 14:48:31', 'admin', '2020-07-22 14:48:31', 1, 1);
INSERT INTO `sys_role_dept` VALUES (2, 'admin', '2020-07-22 14:48:31', 'admin', '2020-07-22 14:48:31', 1, 2);
INSERT INTO `sys_role_dept` VALUES (3, 'admin', '2020-07-22 14:48:31', 'admin', '2020-07-22 14:48:31', 1, 3);


-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, NULL, NULL, NULL, NULL, 1, 5);
INSERT INTO `sys_role_menu` VALUES (2, NULL, NULL, NULL, NULL, 1, 3);
INSERT INTO `sys_role_menu` VALUES (3, NULL, NULL, NULL, NULL, 2, 3);
INSERT INTO `sys_role_menu` VALUES (4, NULL, NULL, NULL, NULL, 3, 3);
INSERT INTO `sys_role_menu` VALUES (5, NULL, NULL, NULL, NULL, 4, 3);
INSERT INTO `sys_role_menu` VALUES (6, NULL, NULL, NULL, NULL, 5, 3);
INSERT INTO `sys_role_menu` VALUES (7, NULL, NULL, NULL, NULL, 6, 3);
INSERT INTO `sys_role_menu` VALUES (8, NULL, NULL, NULL, NULL, 7, 3);
INSERT INTO `sys_role_menu` VALUES (9, NULL, NULL, NULL, NULL, 8, 3);
INSERT INTO `sys_role_menu` VALUES (10, NULL, NULL, NULL, NULL, 9, 3);
INSERT INTO `sys_role_menu` VALUES (11, NULL, NULL, NULL, NULL, 10, 3);
INSERT INTO `sys_role_menu` VALUES (12, NULL, NULL, NULL, NULL, 11, 3);
INSERT INTO `sys_role_menu` VALUES (13, NULL, NULL, NULL, NULL, 12, 3);
INSERT INTO `sys_role_menu` VALUES (14, NULL, NULL, NULL, NULL, 13, 3);
INSERT INTO `sys_role_menu` VALUES (15, NULL, NULL, NULL, NULL, 14, 3);
INSERT INTO `sys_role_menu` VALUES (16, NULL, NULL, NULL, NULL, 15, 3);
INSERT INTO `sys_role_menu` VALUES (17, NULL, NULL, NULL, NULL, 16, 3);
INSERT INTO `sys_role_menu` VALUES (18, NULL, NULL, NULL, NULL, 17, 3);
INSERT INTO `sys_role_menu` VALUES (19, NULL, NULL, NULL, NULL, 18, 3);
INSERT INTO `sys_role_menu` VALUES (20, NULL, NULL, NULL, NULL, 19, 3);
INSERT INTO `sys_role_menu` VALUES (21, NULL, NULL, NULL, NULL, 20, 3);
INSERT INTO `sys_role_menu` VALUES (22, NULL, NULL, NULL, NULL, 21, 3);
INSERT INTO `sys_role_menu` VALUES (23, NULL, NULL, NULL, NULL, 22, 3);
INSERT INTO `sys_role_menu` VALUES (24, NULL, NULL, NULL, NULL, 23, 3);
INSERT INTO `sys_role_menu` VALUES (25, NULL, NULL, NULL, NULL, 24, 3);
INSERT INTO `sys_role_menu` VALUES (26, NULL, NULL, NULL, NULL, 25, 3);
INSERT INTO `sys_role_menu` VALUES (27, NULL, NULL, NULL, NULL, 26, 3);
INSERT INTO `sys_role_menu` VALUES (28, NULL, NULL, NULL, NULL, 27, 3);
INSERT INTO `sys_role_menu` VALUES (29, NULL, NULL, NULL, NULL, 28, 3);
INSERT INTO `sys_role_menu` VALUES (30, NULL, NULL, NULL, NULL, 29, 3);
INSERT INTO `sys_role_menu` VALUES (31, NULL, NULL, NULL, NULL, 30, 3);
INSERT INTO `sys_role_menu` VALUES (32, NULL, NULL, NULL, NULL, 31, 3);
INSERT INTO `sys_role_menu` VALUES (33, NULL, NULL, NULL, NULL, 32, 3);
INSERT INTO `sys_role_menu` VALUES (34, NULL, NULL, NULL, NULL, 33, 3);
INSERT INTO `sys_role_menu` VALUES (35, NULL, NULL, NULL, NULL, 34, 3);
INSERT INTO `sys_role_menu` VALUES (36, NULL, NULL, NULL, NULL, 35, 3);
INSERT INTO `sys_role_menu` VALUES (37, NULL, NULL, NULL, NULL, 36, 3);
INSERT INTO `sys_role_menu` VALUES (38, NULL, NULL, NULL, NULL, 37, 3);
INSERT INTO `sys_role_menu` VALUES (39, NULL, NULL, NULL, NULL, 38, 3);
INSERT INTO `sys_role_menu` VALUES (40, NULL, NULL, NULL, NULL, 39, 3);
INSERT INTO `sys_role_menu` VALUES (41, NULL, NULL, NULL, NULL, 40, 3);
INSERT INTO `sys_role_menu` VALUES (42, NULL, NULL, NULL, NULL, 41, 3);
INSERT INTO `sys_role_menu` VALUES (43, NULL, NULL, NULL, NULL, 42, 3);
INSERT INTO `sys_role_menu` VALUES (44, NULL, NULL, NULL, NULL, 43, 3);
INSERT INTO `sys_role_menu` VALUES (45, NULL, NULL, NULL, NULL, 44, 3);
INSERT INTO `sys_role_menu` VALUES (46, NULL, NULL, NULL, NULL, 45, 3);
INSERT INTO `sys_role_menu` VALUES (47, NULL, NULL, NULL, NULL, 46, 3);
INSERT INTO `sys_role_menu` VALUES (48, NULL, NULL, NULL, NULL, 47, 3);
INSERT INTO `sys_role_menu` VALUES (49, NULL, NULL, NULL, NULL, 48, 3);
INSERT INTO `sys_role_menu` VALUES (50, NULL, NULL, NULL, NULL, 49, 3);
INSERT INTO `sys_role_menu` VALUES (51, NULL, NULL, NULL, NULL, 50, 3);
INSERT INTO `sys_role_menu` VALUES (52, NULL, NULL, NULL, NULL, 51, 3);
INSERT INTO `sys_role_menu` VALUES (53, NULL, NULL, NULL, NULL, 52, 3);
INSERT INTO `sys_role_menu` VALUES (54, NULL, NULL, NULL, NULL, 53, 3);
INSERT INTO `sys_role_menu` VALUES (55, NULL, NULL, NULL, NULL, 54, 3);
INSERT INTO `sys_role_menu` VALUES (56, NULL, NULL, NULL, NULL, 55, 3);
INSERT INTO `sys_role_menu` VALUES (57, NULL, NULL, NULL, NULL, 56, 3);
INSERT INTO `sys_role_menu` VALUES (58, NULL, NULL, NULL, NULL, 57, 3);
INSERT INTO `sys_role_menu` VALUES (59, NULL, NULL, NULL, NULL, 58, 3);
INSERT INTO `sys_role_menu` VALUES (60, NULL, NULL, NULL, NULL, 59, 3);
INSERT INTO `sys_role_menu` VALUES (61, NULL, NULL, NULL, NULL, 60, 3);
INSERT INTO `sys_role_menu` VALUES (62, NULL, NULL, NULL, NULL, 61, 3);
INSERT INTO `sys_role_menu` VALUES (63, NULL, NULL, NULL, NULL, 62, 3);
INSERT INTO `sys_role_menu` VALUES (64, NULL, NULL, NULL, NULL, 63, 3);
INSERT INTO `sys_role_menu` VALUES (65, NULL, NULL, NULL, NULL, 64, 3);
INSERT INTO `sys_role_menu` VALUES (66, NULL, NULL, NULL, NULL, 65, 3);
INSERT INTO `sys_role_menu` VALUES (67, NULL, NULL, NULL, NULL, 66, 3);
INSERT INTO `sys_role_menu` VALUES (68, NULL, NULL, NULL, NULL, 1, 2);
INSERT INTO `sys_role_menu` VALUES (69, NULL, NULL, NULL, NULL, 2, 2);
INSERT INTO `sys_role_menu` VALUES (70, NULL, NULL, NULL, NULL, 3, 2);
INSERT INTO `sys_role_menu` VALUES (71, NULL, NULL, NULL, NULL, 4, 2);
INSERT INTO `sys_role_menu` VALUES (72, NULL, NULL, NULL, NULL, 5, 2);
INSERT INTO `sys_role_menu` VALUES (73, NULL, NULL, NULL, NULL, 6, 2);
INSERT INTO `sys_role_menu` VALUES (74, NULL, NULL, NULL, NULL, 7, 2);
INSERT INTO `sys_role_menu` VALUES (75, NULL, NULL, NULL, NULL, 8, 2);
INSERT INTO `sys_role_menu` VALUES (76, NULL, NULL, NULL, NULL, 12, 2);
INSERT INTO `sys_role_menu` VALUES (77, NULL, NULL, NULL, NULL, 13, 2);
INSERT INTO `sys_role_menu` VALUES (78, NULL, NULL, NULL, NULL, 17, 2);
INSERT INTO `sys_role_menu` VALUES (79, NULL, NULL, NULL, NULL, 18, 2);
INSERT INTO `sys_role_menu` VALUES (80, NULL, NULL, NULL, NULL, 1, 4);
INSERT INTO `sys_role_menu` VALUES (81, NULL, NULL, NULL, NULL, 2, 4);
INSERT INTO `sys_role_menu` VALUES (82, NULL, NULL, NULL, NULL, 3, 4);
INSERT INTO `sys_role_menu` VALUES (83, NULL, NULL, NULL, NULL, 7, 4);
INSERT INTO `sys_role_menu` VALUES (84, NULL, NULL, NULL, NULL, 8, 4);
INSERT INTO `sys_role_menu` VALUES (85, NULL, NULL, NULL, NULL, 12, 4);
INSERT INTO `sys_role_menu` VALUES (86, NULL, NULL, NULL, NULL, 13, 4);
INSERT INTO `sys_role_menu` VALUES (87, NULL, NULL, NULL, NULL, 17, 4);
INSERT INTO `sys_role_menu` VALUES (88, NULL, NULL, NULL, NULL, 18, 4);



