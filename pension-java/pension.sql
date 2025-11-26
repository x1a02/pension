/*
 * 社区养老管理系统数据库全量脚本
 * 包含：数据库创建、表结构定义、基础配置数据、演示用Mock数据
 * 数据库: MySQL 5.7 / 8.0+
 */

-- ==========================================
-- 1. 数据库初始化
-- ==========================================
DROP DATABASE IF EXISTS pension_management;
CREATE DATABASE pension_management CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE pension_management;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0; -- 临时关闭外键检查以避免建表顺序问题

-- ==========================================
-- 2. 表结构定义 (DDL)
-- ==========================================

-- 1. 用户表 (系统用户：管理员、员工、家属)
CREATE TABLE tb_user (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
                         password VARCHAR(255) NOT NULL COMMENT '密码',
                         real_name VARCHAR(50) NOT NULL COMMENT '真实姓名',
                         phone VARCHAR(20) COMMENT '联系电话',
                         user_type TINYINT NOT NULL COMMENT '1:管理员 2:员工 3:家属',
                         status TINYINT DEFAULT 1 COMMENT '1:正常 0:禁用',
                         create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
                         update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 2. 角色表
CREATE TABLE tb_role (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         role_name VARCHAR(50) NOT NULL UNIQUE COMMENT '角色名称',
                         role_desc VARCHAR(200) COMMENT '角色描述',
                         status TINYINT DEFAULT 1 COMMENT '1:正常 0:禁用',
                         create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
                         update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 3. 权限表
CREATE TABLE tb_permission (
                               id BIGINT PRIMARY KEY AUTO_INCREMENT,
                               permission_name VARCHAR(50) NOT NULL UNIQUE COMMENT '权限名称',
                               permission_code VARCHAR(50) NOT NULL UNIQUE COMMENT '权限标识',
                               permission_desc VARCHAR(200) COMMENT '权限描述',
                               create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

-- 4. 用户-角色关联表
CREATE TABLE tb_user_role (
                              id BIGINT PRIMARY KEY AUTO_INCREMENT,
                              user_id BIGINT NOT NULL,
                              role_id BIGINT NOT NULL,
                              create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
                              UNIQUE KEY uk_user_role (user_id, role_id),
                              FOREIGN KEY (user_id) REFERENCES tb_user(id) ON DELETE CASCADE,
                              FOREIGN KEY (role_id) REFERENCES tb_role(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- 5. 角色-权限关联表
CREATE TABLE tb_role_permission (
                                    id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                    role_id BIGINT NOT NULL,
                                    permission_id BIGINT NOT NULL,
                                    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
                                    UNIQUE KEY uk_role_permission (role_id, permission_id),
                                    FOREIGN KEY (role_id) REFERENCES tb_role(id) ON DELETE CASCADE,
                                    FOREIGN KEY (permission_id) REFERENCES tb_permission(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联表';

-- 6. 老人信息表
CREATE TABLE tb_elderly (
                            id BIGINT PRIMARY KEY AUTO_INCREMENT,
                            name VARCHAR(50) NOT NULL COMMENT '姓名',
                            gender TINYINT NOT NULL COMMENT '1:男 2:女',
                            id_card VARCHAR(18) UNIQUE COMMENT '身份证号',
                            birth_date DATE COMMENT '出生日期',
                            age INT COMMENT '年龄',
                            phone VARCHAR(20) COMMENT '联系电话',
                            address VARCHAR(255) COMMENT '家庭住址',
                            emergency_contact VARCHAR(50) COMMENT '紧急联系人',
                            emergency_phone VARCHAR(20) COMMENT '紧急联系电话',
                            health_status VARCHAR(500) COMMENT '健康状况描述',
                            photo_url VARCHAR(255) COMMENT '照片URL',
                            status TINYINT DEFAULT 1 COMMENT '1:在住 0:离院',
                            create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
                            update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='老人信息表';

-- 7. 健康档案/记录表
CREATE TABLE tb_health_record (
                                  id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                  elderly_id BIGINT NOT NULL COMMENT '老人ID',
                                  record_date DATE NOT NULL COMMENT '记录日期',
                                  blood_pressure VARCHAR(20) COMMENT '血压(mmHg)',
                                  heart_rate INT COMMENT '心率(bpm)',
                                  blood_sugar DECIMAL(5,2) COMMENT '血糖(mmol/L)',
                                  temperature DECIMAL(4,2) COMMENT '体温(℃)',
                                  weight DECIMAL(5,2) COMMENT '体重(kg)',
                                  notes VARCHAR(500) COMMENT '备注',
                                  data_source TINYINT DEFAULT 1 COMMENT '1:手工录入 2:设备采集',
                                  create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
                                  FOREIGN KEY (elderly_id) REFERENCES tb_elderly(id) ON DELETE CASCADE,
                                  INDEX idx_elderly_date (elderly_id, record_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='健康记录表';

-- 8. 健康预警配置表
CREATE TABLE tb_health_alert (
                                 id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                 elderly_id BIGINT NOT NULL COMMENT '老人ID',
                                 alert_type VARCHAR(50) NOT NULL COMMENT '预警类型: blood_pressure, heart_rate, blood_sugar, temperature',
                                 min_value DECIMAL(10,2) COMMENT '最小值阈值',
                                 max_value DECIMAL(10,2) COMMENT '最大值阈值',
                                 status TINYINT DEFAULT 1 COMMENT '1:启用 0:禁用',
                                 create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
                                 update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                 FOREIGN KEY (elderly_id) REFERENCES tb_elderly(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='健康预警配置表';

-- 9. 服务分类表
CREATE TABLE tb_service_category (
                                     id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                     category_name VARCHAR(50) NOT NULL UNIQUE COMMENT '分类名称',
                                     category_desc VARCHAR(200) COMMENT '分类描述',
                                     status TINYINT DEFAULT 1 COMMENT '1:启用 0:禁用',
                                     create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务分类表';

-- 10. 服务项目表
CREATE TABLE tb_service_project (
                                    id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                    category_id BIGINT NOT NULL COMMENT '分类ID',
                                    service_name VARCHAR(100) NOT NULL COMMENT '服务名称',
                                    service_desc VARCHAR(500) COMMENT '服务介绍',
                                    duration INT COMMENT '服务时长(分钟)',
                                    price DECIMAL(10,2) COMMENT '价格',
                                    status TINYINT DEFAULT 1 COMMENT '1:上架 0:下架',
                                    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
                                    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                    FOREIGN KEY (category_id) REFERENCES tb_service_category(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务项目表';

-- 11. 服务订单表
CREATE TABLE tb_service_order (
                                  id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                  order_no VARCHAR(50) NOT NULL UNIQUE COMMENT '订单号',
                                  elderly_id BIGINT NOT NULL COMMENT '老人ID',
                                  service_id BIGINT NOT NULL COMMENT '服务项目ID',
                                  staff_id BIGINT COMMENT '服务人员ID',
                                  appointment_time DATETIME NOT NULL COMMENT '预约时间',
                                  order_status TINYINT DEFAULT 1 COMMENT '1:待接单 2:已接单 3:服务中 4:已完成 5:已取消',
                                  completion_time DATETIME COMMENT '完成时间',
                                  completion_notes VARCHAR(500) COMMENT '完成备注',
                                  rating TINYINT COMMENT '评分 1-5',
                                  feedback VARCHAR(500) COMMENT '评价反馈',
                                  create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
                                  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                  FOREIGN KEY (elderly_id) REFERENCES tb_elderly(id) ON DELETE CASCADE,
                                  FOREIGN KEY (service_id) REFERENCES tb_service_project(id) ON DELETE CASCADE,
                                  FOREIGN KEY (staff_id) REFERENCES tb_user(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务订单表';

-- 12. 社区活动表
CREATE TABLE tb_activity (
                             id BIGINT PRIMARY KEY AUTO_INCREMENT,
                             activity_name VARCHAR(100) NOT NULL COMMENT '活动名称',
                             activity_desc VARCHAR(1000) COMMENT '活动详情',
                             activity_date DATETIME NOT NULL COMMENT '活动时间',
                             location VARCHAR(200) COMMENT '活动地点',
                             max_participants INT COMMENT '最大人数',
                             current_participants INT DEFAULT 0 COMMENT '当前报名人数',
                             organizer_id BIGINT COMMENT '组织者(员工ID)',
                             cover_image VARCHAR(255) COMMENT '封面图',
                             status TINYINT DEFAULT 1 COMMENT '1:报名中 2:进行中 3:已结束 4:已取消',
                             create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
                             update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                             FOREIGN KEY (organizer_id) REFERENCES tb_user(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='社区活动表';

-- 13. 活动报名表
CREATE TABLE tb_activity_registration (
                                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                          activity_id BIGINT NOT NULL COMMENT '活动ID',
                                          elderly_id BIGINT NOT NULL COMMENT '老人ID',
                                          registration_status TINYINT DEFAULT 1 COMMENT '1:已报名 2:已签到 3:已取消',
                                          check_in_time DATETIME COMMENT '签到时间',
                                          create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
                                          UNIQUE KEY uk_activity_elderly (activity_id, elderly_id),
                                          FOREIGN KEY (activity_id) REFERENCES tb_activity(id) ON DELETE CASCADE,
                                          FOREIGN KEY (elderly_id) REFERENCES tb_elderly(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='活动报名表';

-- 14. 家属关联表
CREATE TABLE tb_family_relation (
                                    id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                    elderly_id BIGINT NOT NULL COMMENT '老人ID',
                                    family_user_id BIGINT NOT NULL COMMENT '家属用户ID',
                                    relation_type VARCHAR(20) COMMENT '关系: 子女, 配偶等',
                                    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
                                    UNIQUE KEY uk_elderly_family (elderly_id, family_user_id),
                                    FOREIGN KEY (elderly_id) REFERENCES tb_elderly(id) ON DELETE CASCADE,
                                    FOREIGN KEY (family_user_id) REFERENCES tb_user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='家属关联表';

-- 15. 消息通知表
CREATE TABLE tb_notification (
                                 id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                 user_id BIGINT NOT NULL COMMENT '接收用户ID',
                                 title VARCHAR(100) NOT NULL COMMENT '标题',
                                 content VARCHAR(500) NOT NULL COMMENT '内容',
                                 notification_type TINYINT NOT NULL COMMENT '1:系统 2:服务 3:健康预警 4:活动',
                                 is_read TINYINT DEFAULT 0 COMMENT '0:未读 1:已读',
                                 read_time DATETIME COMMENT '阅读时间',
                                 create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
                                 FOREIGN KEY (user_id) REFERENCES tb_user(id) ON DELETE CASCADE,
                                 INDEX idx_user_read (user_id, is_read)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息通知表';

SET FOREIGN_KEY_CHECKS = 1; -- 恢复外键检查

-- ==========================================
-- 3. 基础数据插入 (系统运行必要数据)
-- ==========================================

-- 3.1 基础用户
INSERT INTO tb_user (username, password, real_name, phone, user_type) VALUES
                                                                          ('admin', 'admin123', '系统管理员', '13800000000', 1),
                                                                          ('staff01', 'staff123', '张护理', '13800000001', 2),
                                                                          ('family01', 'family123', '李家属', '13800000002', 3);

-- 3.2 角色定义
INSERT INTO tb_role (role_name, role_desc) VALUES
                                               ('系统管理员', '拥有系统所有权限'),
                                               ('服务人员', '负责日常服务执行与记录'),
                                               ('家属用户', '查看老人信息与接收通知');

-- 3.3 权限定义
INSERT INTO tb_permission (permission_name, permission_code, permission_desc) VALUES
                                                                                  ('用户管理', 'user:manage', '管理系统用户'),
                                                                                  ('老人档案', 'elderly:manage', '管理老人信息'),
                                                                                  ('服务管理', 'service:manage', '管理服务项目与订单'),
                                                                                  ('活动管理', 'activity:manage', '发布与管理社区活动'),
                                                                                  ('查看档案', 'elderly:view', '仅查看老人信息'),
                                                                                  ('查看通知', 'notification:view', '接收和查看系统通知');

-- 3.4 用户-角色绑定
INSERT INTO tb_user_role (user_id, role_id) VALUES
                                                (1, 1),  -- admin -> 系统管理员
                                                (2, 2),  -- staff01 -> 服务人员
                                                (3, 3);  -- family01 -> 家属成员

-- 3.5 角色-权限绑定
INSERT INTO tb_role_permission (role_id, permission_id) VALUES
                                                            (1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6),  -- 管理员: 全权
                                                            (2, 2), (2, 3), (2, 4), (2, 5),  -- 服务人员: 老人、服务、活动管理，查看档案
                                                            (3, 5), (3, 6);  -- 家属: 查看档案、查看通知

-- 3.6 服务分类基础数据
INSERT INTO tb_service_category (category_name, category_desc) VALUES
                                                                   ('医疗护理', '专业的医疗健康护理服务'),
                                                                   ('生活照料', '日常生活起居协助'),
                                                                   ('文娱社交', '精神文化生活与社交活动'),
                                                                   ('紧急响应', '突发情况的紧急处理服务');


-- ==========================================
-- 4. 演示Mock数据 (业务测试数据)
-- ==========================================

-- 4.1 插入老人信息（12位老人）
INSERT INTO tb_elderly (name, gender, id_card, birth_date, age, phone, address, emergency_contact, emergency_phone, health_status, status) VALUES
                                                                                                                                               ('张大爷', 1, '110101194501011234', '1945-01-01', 80, '13800001001', '北京市朝阳区建国路88号', '张小明', '13900001001', '高血压，需定期监测', 1),
                                                                                                                                               ('李奶奶', 2, '110101194802152345', '1948-02-15', 77, '13800001002', '北京市朝阳区建国路90号', '李娟', '13900001002', '糖尿病，饮食需注意', 1),
                                                                                                                                               ('王大爷', 1, '110101195003203456', '1950-03-20', 75, '13800001003', '北京市朝阳区建国路92号', '王强', '13900001003', '身体健康，偶有腰痛', 1),
                                                                                                                                               ('刘奶奶', 2, '110101195204254567', '1952-04-25', 73, '13800001004', '北京市朝阳区建国路94号', '刘芳', '13900001004', '轻度骨质疏松', 1),
                                                                                                                                               ('陈大爷', 1, '110101194705305678', '1947-05-30', 78, '13800001005', '北京市朝阳区建国路96号', '陈伟', '13900001005', '心脏病，服药中', 1),
                                                                                                                                               ('赵奶奶', 2, '110101195106106789', '1951-06-10', 74, '13800001006', '北京市朝阳区建国路98号', '赵丽', '13900001006', '身体健康', 1),
                                                                                                                                               ('孙大爷', 1, '110101194907157890', '1949-07-15', 76, '13800001007', '北京市朝阳区建国路100号', '孙涛', '13900001007', '高血脂，需控制饮食', 1),
                                                                                                                                               ('周奶奶', 2, '110101195308208901', '1953-08-20', 72, '13800001008', '北京市朝阳区建国路102号', '周敏', '13900001008', '关节炎', 1),
                                                                                                                                               ('吴大爷', 1, '110101194609259012', '1946-09-25', 79, '13800001009', '北京市朝阳区建国路104号', '吴杰', '13900001009', '慢性支气管炎', 1),
                                                                                                                                               ('郑奶奶', 2, '110101195410300123', '1954-10-30', 71, '13800001010', '北京市朝阳区建国路106号', '郑婷', '13900001010', '身体健康，视力略差', 1),
                                                                                                                                               ('黄大爷', 1, '110101195011051234', '1950-11-05', 75, '13800001011', '北京市朝阳区建国路108号', '黄超', '13900001011', '轻度高血压', 1),
                                                                                                                                               ('林奶奶', 2, '110101195512102345', '1955-12-10', 70, '13800001012', '北京市朝阳区建国路110号', '林静', '13900001012', '身体健康', 1);

-- 4.2 插入服务项目（8个项目）
INSERT INTO tb_service_project (category_id, service_name, service_desc, duration, price, status) VALUES
                                                                                                      (1, '血压测量', '专业护士上门测量血压，记录健康数据', 30, 50.00, 1),
                                                                                                      (1, '血糖监测', '专业护士上门测量血糖，提供饮食建议', 30, 60.00, 1),
                                                                                                      (1, '健康体检', '全面身体检查，包含血常规、心电图等', 120, 280.00, 1),
                                                                                                      (2, '日常陪护', '专业护工提供日常生活照料服务', 240, 200.00, 1),
                                                                                                      (2, '助餐服务', '营养师配餐，工作人员送餐上门', 60, 35.00, 1),
                                                                                                      (2, '家政清洁', '专业保洁人员上门打扫卫生', 120, 150.00, 1),
                                                                                                      (3, '陪同散步', '工作人员陪同老人散步锻炼', 60, 80.00, 1),
                                                                                                      (4, '紧急呼叫', '24小时紧急呼叫响应服务', 1440, 0.00, 1);

-- 4.3 插入社区活动（5个活动）
INSERT INTO tb_activity (activity_name, activity_desc, activity_date, location, max_participants, current_participants, organizer_id, status) VALUES
                                                                                                                                                  ('健康讲座：秋季养生', '邀请中医专家讲解秋季养生知识，包括饮食调理、运动保健等内容', '2025-11-28 14:00:00', '社区活动中心', 50, 12, 2, 1),
                                                                                                                                                  ('书法兴趣小组', '书法爱好者交流学习，由退休书法老师指导', '2025-11-29 09:30:00', '文化活动室', 20, 8, 2, 1),
                                                                                                                                                  ('集体生日会', '为本月过生日的老人举办生日庆祝活动', '2025-11-30 15:00:00', '多功能厅', 60, 15, 2, 1),
                                                                                                                                                  ('太极拳晨练', '每周三次太极拳集体晨练活动', '2025-12-02 07:00:00', '社区广场', 30, 18, 2, 1),
                                                                                                                                                  ('手工制作课', '教授简单的手工艺品制作，活动手指防止老年痴呆', '2025-12-03 10:00:00', '手工活动室', 25, 10, 2, 1);

-- 4.4 插入活动报名记录
INSERT INTO tb_activity_registration (activity_id, elderly_id, registration_status) VALUES
                                                                                        (1, 1, 1), (1, 2, 1), (1, 3, 1), (1, 4, 1),
                                                                                        (2, 2, 1), (2, 4, 1), (2, 6, 1),
                                                                                        (3, 1, 1), (3, 2, 1), (3, 5, 1), (3, 7, 1),
                                                                                        (4, 3, 1), (4, 6, 1), (4, 8, 1), (4, 10, 1),
                                                                                        (5, 4, 1), (5, 5, 1), (5, 9, 1);

-- 更新活动参与人数以匹配报名记录
UPDATE tb_activity SET current_participants = 4 WHERE id = 1;
UPDATE tb_activity SET current_participants = 3 WHERE id = 2;
UPDATE tb_activity SET current_participants = 4 WHERE id = 3;
UPDATE tb_activity SET current_participants = 4 WHERE id = 4;
UPDATE tb_activity SET current_participants = 3 WHERE id = 5;

-- 4.5 插入健康记录（为前6位老人插入最近的健康数据）
-- 张大爷
INSERT INTO tb_health_record (elderly_id, record_date, blood_pressure, heart_rate, blood_sugar, temperature, weight, notes, data_source) VALUES
                                                                                                                                             (1, '2025-11-26', '145/90', 78, NULL, 36.5, 68.5, '血压偏高，建议增加降压药剂量', 1),
                                                                                                                                             (1, '2025-11-23', '142/88', 76, NULL, 36.6, 68.3, '血压控制良好', 1),
                                                                                                                                             (1, '2025-11-20', '148/92', 80, NULL, 36.4, 68.7, '血压略高', 1);

-- 李奶奶
INSERT INTO tb_health_record (elderly_id, record_date, blood_pressure, heart_rate, blood_sugar, temperature, weight, notes, data_source) VALUES
                                                                                                                                             (2, '2025-11-26', '130/85', 72, 7.2, 36.7, 55.5, '血糖控制尚可，继续监测', 1),
                                                                                                                                             (2, '2025-11-24', '128/82', 70, 6.8, 36.5, 55.3, '血糖正常', 1),
                                                                                                                                             (2, '2025-11-22', '132/86', 74, 7.5, 36.6, 55.4, '餐后血糖略高', 1);

-- 王大爷
INSERT INTO tb_health_record (elderly_id, record_date, blood_pressure, heart_rate, blood_sugar, temperature, weight, notes, data_source) VALUES
                                                                                                                                             (3, '2025-11-26', '125/80', 68, NULL, 36.6, 72.0, '各项指标正常', 1),
                                                                                                                                             (3, '2025-11-23', '128/82', 70, NULL, 36.5, 72.2, '身体状况良好', 1);

-- 刘奶奶
INSERT INTO tb_health_record (elderly_id, record_date, blood_pressure, heart_rate, blood_sugar, temperature, weight, notes, data_source) VALUES
                                                                                                                                             (4, '2025-11-26', '135/85', 75, NULL, 36.5, 58.0, '建议补钙，增加户外活动', 1),
                                                                                                                                             (4, '2025-11-24', '132/84', 73, NULL, 36.6, 57.8, '状态平稳', 1);

-- 陈大爷
INSERT INTO tb_health_record (elderly_id, record_date, blood_pressure, heart_rate, blood_sugar, temperature, weight, notes, data_source) VALUES
                                                                                                                                             (5, '2025-11-26', '138/88', 82, NULL, 36.4, 65.5, '心率略快，注意休息', 1),
                                                                                                                                             (5, '2025-11-25', '136/86', 80, NULL, 36.5, 65.3, '按时服药', 1),
                                                                                                                                             (5, '2025-11-23', '140/90', 84, NULL, 36.6, 65.6, '血压需要监控', 1);

-- 赵奶奶
INSERT INTO tb_health_record (elderly_id, record_date, blood_pressure, heart_rate, blood_sugar, temperature, weight, notes, data_source) VALUES
                                                                                                                                             (6, '2025-11-26', '120/78', 70, NULL, 36.5, 60.0, '健康状况良好', 1),
                                                                                                                                             (6, '2025-11-24', '122/80', 72, NULL, 36.6, 60.2, '一切正常', 1);

-- 4.6 插入服务订单（部分历史订单）
INSERT INTO tb_service_order (order_no, elderly_id, service_id, staff_id, appointment_time, order_status, completion_time, completion_notes, rating) VALUES
                                                                                                                                                         ('SO20251126091234', 1, 1, 2, '2025-11-26 09:30:00', 4, '2025-11-26 10:00:00', '血压145/90，已记录', 5),
                                                                                                                                                         ('SO20251126141567', 2, 2, 2, '2025-11-26 14:30:00', 4, '2025-11-26 15:00:00', '血糖7.2，需继续监测', 5),
                                                                                                                                                         ('SO20251125100890', 3, 7, 2, '2025-11-25 10:00:00', 4, '2025-11-25 11:00:00', '陪同散步1小时，老人状态良好', 5),
                                                                                                                                                         ('SO20251127091123', 1, 1, 2, '2025-11-27 09:30:00', 2, NULL, NULL, NULL),
                                                                                                                                                         ('SO20251127143456', 4, 5, 2, '2025-11-27 12:00:00', 1, NULL, NULL, NULL);

-- 4.7 插入家属绑定关系
INSERT INTO tb_family_relation (elderly_id, family_user_id, relation_type) VALUES
                                                                               (1, 3, '儿子'),
                                                                               (2, 3, '女儿');

COMMIT;

-- ==========================================
-- 5. 验证结果 (可选)
-- ==========================================
SELECT '数据初始化完成！' as status;
SELECT COUNT(*) as elderly_count, '老人信息' as table_name FROM tb_elderly
UNION ALL
SELECT COUNT(*), '服务项目' FROM tb_service_project
UNION ALL
SELECT COUNT(*), '社区活动' FROM tb_activity
UNION ALL
SELECT COUNT(*), '健康记录' FROM tb_health_record
UNION ALL
SELECT COUNT(*), '活动报名' FROM tb_activity_registration
UNION ALL
SELECT COUNT(*), '服务订单' FROM tb_service_order;