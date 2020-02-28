-- auto-generated definition
create table SYS_CODE
(
  CID         VARCHAR2(50) not null
    constraint PK_SYS_CODE
      primary key,
  CODE        VARCHAR2(50),
  CODE_VALUE  VARCHAR2(50),
  CODE_TYPE   VARCHAR2(50),
  CODE_SORT   VARCHAR2(10),
  CREATE_BY   VARCHAR2(32),
  CREATE_DATE VARCHAR2(32),
  DEL_FLAG    VARCHAR2(2),
  REMARKS     VARCHAR2(50)
)
/comment on table SYS_CODE is '字典表'
/comment on column SYS_CODE.CID is '主键id)'
/comment on column SYS_CODE.CODE is 'code值'
/comment on column SYS_CODE.CODE_VALUE is 'code对应的值'
/comment on column SYS_CODE.CODE_TYPE is 'code的类型'
/comment on column SYS_CODE.CODE_SORT is '排序'
/comment on column SYS_CODE.CREATE_BY is '创建人'
/comment on column SYS_CODE.CREATE_DATE is '创建时间'
/comment on column SYS_CODE.DEL_FLAG is '是否已删除（1：已删除；0：未删除）'
/comment on column SYS_CODE.REMARKS is '备注'
/create index SYS_CODE_INDEX1 on SYS_CODE (CODE)
/create index SYS_CODE_INDEX2 on SYS_CODE (CODE_TYPE)
/

-- auto-generated definition
create table TF_DIS_ADDRESS
(
  ADDR_ID         VARCHAR2(32) not null
    constraint PK_TF_DIS_ADDRESS
      primary key,
  USER_ID         VARCHAR2(20),
  ADDRESS         VARCHAR2(200),
  RECEIPT_TYPE    VARCHAR2(20),
  RECEIPT_NAME    VARCHAR2(20),
  RECEIPT_ACCOUNT VARCHAR2(32),
  OTHER           VARCHAR2(100),
  PHONE_NUM       VARCHAR2(11)
)
/comment on table TF_DIS_ADDRESS is '地址表'
/comment on column TF_DIS_ADDRESS.ADDR_ID is '地址ID'
/comment on column TF_DIS_ADDRESS.USER_ID is '用户id'
/comment on column TF_DIS_ADDRESS.ADDRESS is '地址详情'
/comment on column TF_DIS_ADDRESS.RECEIPT_TYPE is '收款方式'
/comment on column TF_DIS_ADDRESS.RECEIPT_NAME is '收款人'
/comment on column TF_DIS_ADDRESS.RECEIPT_ACCOUNT is '收款账号'
/comment on column TF_DIS_ADDRESS.OTHER is '其他备注信息'
/comment on column TF_DIS_ADDRESS.PHONE_NUM is '收货人手机号'
/create index TF_DIS_ADDRESS_INDEX1 on TF_DIS_ADDRESS (USER_ID)
/

-- auto-generated definition
create table TF_DIS_ADMIN
(
  ADMIN_ID  VARCHAR2(20) not null
    constraint PK_TF_DIS_ADMIN
      primary key,
  PASS_WORD VARCHAR2(20),
  USER_NAME VARCHAR2(50) not null,
  GROUP_ID  VARCHAR2(32),
  STATUS    VARCHAR2(2)
)
/comment on table TF_DIS_ADMIN is '管理员表'
/comment on column TF_DIS_ADMIN.ADMIN_ID is '管理员ID'
/comment on column TF_DIS_ADMIN.PASS_WORD is '密码'
/comment on column TF_DIS_ADMIN.USER_NAME is '账号名'
/comment on column TF_DIS_ADMIN.GROUP_ID is '管理员分组'
/comment on column TF_DIS_ADMIN.STATUS is '状态（0：停用；1：启用）'
/create index TF_DIS_ADMIN_INDEX1 on TF_DIS_ADMIN (USER_NAME)
/

-- auto-generated definition
create table TF_DIS_BANNER
(
  BANNER_ID   VARCHAR2(32) not null
    constraint PK_TF_DIS_BANNER
      primary key,
  BANNER_URL  VARCHAR2(100),
  BANNER_PATH VARCHAR2(100),
  TO_URL      VARCHAR2(100),
  STATUS      VARCHAR2(2),
  IMAGE_TITLE VARCHAR2(50)
)
/comment on table TF_DIS_BANNER is '首页轮播表'
/comment on column TF_DIS_BANNER.BANNER_ID is '轮播id'
/comment on column TF_DIS_BANNER.BANNER_URL is '轮播映射url'
/comment on column TF_DIS_BANNER.BANNER_PATH is '轮播图片物理路径'
/comment on column TF_DIS_BANNER.TO_URL is '轮播图片跳转url'
/comment on column TF_DIS_BANNER.STATUS is '轮播图片状态（0：下架；1：上架）'
/comment on column TF_DIS_BANNER.IMAGE_TITLE is '图片标题'
/

-- auto-generated definition
create table TF_DIS_CAMPUS
(
  CAMPUS_ID   VARCHAR2(32) not null
    constraint PK_TF_DIS_CAMPUS
      primary key,
  CAMPUS_NAME VARCHAR2(50),
  PROVINCE    VARCHAR2(20),
  CITY        VARCHAR2(20),
  AREA        VARCHAR2(20)
)
/comment on table TF_DIS_CAMPUS is '校区表'
/comment on column TF_DIS_CAMPUS.CAMPUS_ID is '主键id'
/comment on column TF_DIS_CAMPUS.CAMPUS_NAME is '校区名称'
/comment on column TF_DIS_CAMPUS.PROVINCE is '省'
/comment on column TF_DIS_CAMPUS.CITY is '市'
/comment on column TF_DIS_CAMPUS.AREA is '区'
/

-- auto-generated definition
create table TF_DIS_CHANGE
(
  CID          VARCHAR2(32) not null
    constraint PK_TF_DIS_CHANGE
      primary key,
  CREATE_TIME  VARCHAR2(30),
  USER_NAME    VARCHAR2(50),
  CHANGE_MONEY VARCHAR2(4),
  ADDR_ID      VARCHAR2(20),
  STATUS       VARCHAR2(2),
  REASON       VARCHAR2(100),
  USER_ID      VARCHAR2(16),
  DEL_SCORE    VARCHAR2(16)
)
/comment on table TF_DIS_CHANGE is '打款申请表'
/comment on column TF_DIS_CHANGE.CID is '主键id'
/comment on column TF_DIS_CHANGE.CREATE_TIME is '申请提现时间'
/comment on column TF_DIS_CHANGE.USER_NAME is '用户名'
/comment on column TF_DIS_CHANGE.CHANGE_MONEY is '提现金额'
/comment on column TF_DIS_CHANGE.ADDR_ID is '地址id'
/comment on column TF_DIS_CHANGE.STATUS is '状态（0：初始化状态；1：未打款；2：已打款；3：拒绝）'
/comment on column TF_DIS_CHANGE.REASON is '拒绝原因'
/comment on column TF_DIS_CHANGE.USER_ID is '用户id'
/comment on column TF_DIS_CHANGE.DEL_SCORE is '消耗积分'
/create index TF_DIS_CHANGE_INDEX1 on TF_DIS_CHANGE (USER_NAME)
/

-- auto-generated definition
create table TF_DIS_COURSE
(
  COURSE_ID     VARCHAR2(20) not null
    constraint PK_TF_DIS_COURSE
      primary key,
  COURSE_NAME   VARCHAR2(50),
  PRICE         NUMBER(10, 2),
  LIMIT_NUM     NUMBER(5),
  COURSE_DETAIL VARCHAR2(2000),
  STATUS        VARCHAR2(2) default 1,
  USE_NUM       NUMBER(5),
  COURSE_TERM   NUMBER(5)   default null,
  SCORE_VALUE   NUMBER      default 10
)
/comment on table TF_DIS_COURSE is '试听课表'
/comment on column TF_DIS_COURSE.COURSE_ID is '试听课程id'
/comment on column TF_DIS_COURSE.COURSE_NAME is '试听课程名称'
/comment on column TF_DIS_COURSE.PRICE is '价格'
/comment on column TF_DIS_COURSE.LIMIT_NUM is '限制单个用户购买数量'
/comment on column TF_DIS_COURSE.COURSE_DETAIL is '课程详情'
/comment on column TF_DIS_COURSE.STATUS is '课程状态（0：下架；1：上架）'
/comment on column TF_DIS_COURSE.USE_NUM is '限制单个用户的销核数量'
/comment on column TF_DIS_COURSE.COURSE_TERM is '课程有效期(单位：月)'
/comment on column TF_DIS_COURSE.SCORE_VALUE is '课程对应积分'
/

-- auto-generated definition
create table TF_DIS_DISAPPER
(
  DISAPPER_ID   VARCHAR2(16) not null
    constraint PK_TF_DIS_DISAPPER
      primary key,
  DISAPPER_NAME VARCHAR2(16),
  DISAPPER_PWD  VARCHAR2(16),
  DISAPPER_SORT NUMBER,
  SCHOOL_ID     VARCHAR2(32)
)
/comment on table TF_DIS_DISAPPER is '校区销核用户'
/comment on column TF_DIS_DISAPPER.DISAPPER_ID is '销核用户ID'
/comment on column TF_DIS_DISAPPER.DISAPPER_NAME is '销核用户账号'
/comment on column TF_DIS_DISAPPER.DISAPPER_PWD is '销核用户密码'
/comment on column TF_DIS_DISAPPER.DISAPPER_SORT is '账号序号（1为第一个账户，2为第二个，3为第三个）'
/comment on column TF_DIS_DISAPPER.SCHOOL_ID is '关联学校ID'
/create index TF_DIS_DISAPPER_INDEX on TF_DIS_DISAPPER (SCHOOL_ID)
/

-- auto-generated definition
create table TF_DIS_DISAPPER_MANAGER
(
  MANAGE_ID   VARCHAR2(20) not null
    constraint PK_TF_DIS_DISAPPER_MANAGER
      primary key,
  DIS_TIME    VARCHAR2(50),
  COURSE_ID   VARCHAR2(32),
  COURSE_NAME VARCHAR2(100),
  USER_ID     VARCHAR2(20),
  SCHOOL_ID   VARCHAR2(32),
  DISAPPER_ID VARCHAR2(16)
)
/comment on table TF_DIS_DISAPPER_MANAGER is '校区消核表'
/comment on column TF_DIS_DISAPPER_MANAGER.MANAGE_ID is '主键id'
/comment on column TF_DIS_DISAPPER_MANAGER.DIS_TIME is '消核时间'
/comment on column TF_DIS_DISAPPER_MANAGER.COURSE_ID is '课程id'
/comment on column TF_DIS_DISAPPER_MANAGER.COURSE_NAME is '课程名称'
/comment on column TF_DIS_DISAPPER_MANAGER.USER_ID is '用户ID'
/comment on column TF_DIS_DISAPPER_MANAGER.SCHOOL_ID is '教学点id'
/comment on column TF_DIS_DISAPPER_MANAGER.DISAPPER_ID is '销核账户id'
/create index TF_DIS_DISAPPER_MANAGER_INDEX1 on TF_DIS_DISAPPER_MANAGER (SCHOOL_ID)
/

-- auto-generated definition
create table TF_DIS_GOODS
(
  GOODS_ID     VARCHAR2(20) not null
    constraint PK_TF_DIS_GOODS
      primary key,
  GOODS_NAME   VARCHAR2(50),
  GOODS_TYPE   VARCHAR2(32),
  GOODS_NUM    NUMBER(8),
  GOODS_FEE    NUMBER(8, 2),
  INTE_FEE     NUMBER(8, 2),
  GOODS_DETAIL VARCHAR2(1000),
  GOODS_ATTR   VARCHAR2(100),
  GOODS_STATUS VARCHAR2(2),
  ADD_TIME     VARCHAR2(32),
  COMPANY      VARCHAR2(32)
)
/comment on table TF_DIS_GOODS is '商品表'
/comment on column TF_DIS_GOODS.GOODS_ID is '商品id'
/comment on column TF_DIS_GOODS.GOODS_NAME is '商品名称'
/comment on column TF_DIS_GOODS.GOODS_TYPE is '商品类型'
/comment on column TF_DIS_GOODS.GOODS_NUM is '库存'
/comment on column TF_DIS_GOODS.GOODS_FEE is '商品价格'
/comment on column TF_DIS_GOODS.INTE_FEE is '积分价格'
/comment on column TF_DIS_GOODS.GOODS_DETAIL is '商品详情'
/comment on column TF_DIS_GOODS.GOODS_ATTR is '商品属性(0：热门兑换；1：好物推荐)'
/comment on column TF_DIS_GOODS.GOODS_STATUS is '商品状态（0：下架；1：上架）'
/comment on column TF_DIS_GOODS.ADD_TIME is '创建时间'
/comment on column TF_DIS_GOODS.COMPANY is '库存单位'
/

-- auto-generated definition
create table TF_DIS_GOODS_ATTR
(
  ATTR_ID  VARCHAR2(20) not null
    constraint PK_TF_DIS_GOODS_ATTR
      primary key,
  GOODS_ID VARCHAR2(20),
  PIC_URL  VARCHAR2(100),
  PIC_PATH VARCHAR2(100),
  PIC_TYPE VARCHAR2(2)
)
/comment on table TF_DIS_GOODS_ATTR is '商品属性表'
/comment on column TF_DIS_GOODS_ATTR.ATTR_ID is '属性id'
/comment on column TF_DIS_GOODS_ATTR.GOODS_ID is '商品id'
/comment on column TF_DIS_GOODS_ATTR.PIC_URL is '商品图片映射路径'
/comment on column TF_DIS_GOODS_ATTR.PIC_PATH is '商品图片物理路径'
/comment on column TF_DIS_GOODS_ATTR.PIC_TYPE is '商品图片位置（1：商品封面图片；2：商品图片）'
/create index TF_DIS_GOODS_ATTR_INDEX1 on TF_DIS_GOODS_ATTR (GOODS_ID)
/

-- auto-generated definition
create table TF_DIS_GROUP
(
  GROUP_ID           VARCHAR2(20) not null
    constraint PK_TF_DIS_GROUP
      primary key,
  ADMIN_MODULE       VARCHAR2(32),
  USER_MODULE        VARCHAR2(32),
  EXCHANGE_MODULE    VARCHAR2(32),
  DISPARAM_MODULE    VARCHAR2(32),
  SCORECHANGE_MODULE VARCHAR2(32),
  SCHOOL_MODULE      VARCHAR2(32),
  COURSE_MODULE      VARCHAR2(32),
  ORDER_MODULE       VARCHAR2(32),
  MONEY_MODULE       VARCHAR2(32),
  SHOP_MODULE        VARCHAR2(32),
  OTHER_MODULE       VARCHAR2(32),
  DISMANAGER_MODULE  VARCHAR2(32)
)
/comment on table TF_DIS_GROUP is '管理员一级菜单权限表'
/comment on column TF_DIS_GROUP.GROUP_ID is '管理员分组id'
/comment on column TF_DIS_GROUP.ADMIN_MODULE is '管理员模块（null或空：无权限）'
/comment on column TF_DIS_GROUP.USER_MODULE is '合伙人模块（null或空：无权限）'
/comment on column TF_DIS_GROUP.EXCHANGE_MODULE is '兑换列表模块（null或空：无权限）'
/comment on column TF_DIS_GROUP.DISPARAM_MODULE is '分销参数模块（null或空：无权限）'
/comment on column TF_DIS_GROUP.SCORECHANGE_MODULE is '积分兑换模块（null或空：无权限）'
/comment on column TF_DIS_GROUP.SCHOOL_MODULE is '教学点模块（null或空：无权限）'
/comment on column TF_DIS_GROUP.COURSE_MODULE is '试听课管理模块（null或空：无权限）'
/comment on column TF_DIS_GROUP.ORDER_MODULE is '订单模块（null或空：无权限）'
/comment on column TF_DIS_GROUP.MONEY_MODULE is '财务管理模块（null或空：无权限）'
/comment on column TF_DIS_GROUP.SHOP_MODULE is '商城模块（null或空：无权限）'
/comment on column TF_DIS_GROUP.OTHER_MODULE is '其他设置模块（null或空：无权限）'
/comment on column TF_DIS_GROUP.DISMANAGER_MODULE is '试听课消核管理（null或空：无权限）'
/

-- auto-generated definition
create table TF_DIS_GROUP_SECOND
(
  MODULE_ID   VARCHAR2(20),
  SECOND_MENU VARCHAR2(20),
  LIMIT_CONTR VARCHAR2(2),
  GROUP_ID    VARCHAR2(20)
)
/comment on table TF_DIS_GROUP_SECOND is '管理员二级菜单权限表'
/comment on column TF_DIS_GROUP_SECOND.MODULE_ID is '管理员组所对应的一级菜单id'
/comment on column TF_DIS_GROUP_SECOND.SECOND_MENU is '二级菜单名称'
/comment on column TF_DIS_GROUP_SECOND.LIMIT_CONTR is '二级菜单权限控制（0：无权限；1：有权限）'
/comment on column TF_DIS_GROUP_SECOND.GROUP_ID is '管理员组id'
/create index TF_DIS_GROUP_SECOND_INDEX1 on TF_DIS_GROUP_SECOND (MODULE_ID)
/

-- auto-generated definition
create table TF_DIS_INTE
(
  UUID          VARCHAR2(32) not null
    constraint PK_TF_DIS_INTE
      primary key,
  USER_ID       VARCHAR2(20),
  SCORE_VALUE   VARCHAR2(10),
  CREATE_TIME   VARCHAR2(20),
  ORDER_ID      VARCHAR2(20),
  COURSE_ID     VARCHAR2(32),
  STATUS        VARCHAR2(2),
  SCORE_CONTEXT VARCHAR2(100)
)
/comment on table TF_DIS_INTE is '积分表'
/comment on column TF_DIS_INTE.UUID is 'ID'
/comment on column TF_DIS_INTE.USER_ID is '用户id'
/comment on column TF_DIS_INTE.SCORE_VALUE is '积分值'
/comment on column TF_DIS_INTE.CREATE_TIME is '创建时间'
/comment on column TF_DIS_INTE.ORDER_ID is '订单id'
/comment on column TF_DIS_INTE.COURSE_ID is '课程id'
/comment on column TF_DIS_INTE.STATUS is '积分状态'
/comment on column TF_DIS_INTE.SCORE_CONTEXT is '积分详情'
/create index TF_DIS_INTE_INDEX1 on TF_DIS_INTE (USER_ID)
/

-- auto-generated definition
create table TF_DIS_INTE_ORDER
(
  UUID           VARCHAR2(32) not null
    constraint PK_TF_DIS_INTE_ORDER
      primary key,
  CREATE_TIME    VARCHAR2(32),
  DEL_SCOREVALUE VARCHAR2(10),
  USER_ID        VARCHAR2(20),
  DEL_CONTEXT    VARCHAR2(100),
  STATUS         NUMBER(1) default 1,
  ADDR_ID        VARCHAR2(32)
)
/comment on table TF_DIS_INTE_ORDER is '积分订单表'
/comment on column TF_DIS_INTE_ORDER.UUID is '积分订单ID'
/comment on column TF_DIS_INTE_ORDER.CREATE_TIME is '积分订单创建时间'
/comment on column TF_DIS_INTE_ORDER.DEL_SCOREVALUE is '消耗的积分值'
/comment on column TF_DIS_INTE_ORDER.USER_ID is '购买的用户id'
/comment on column TF_DIS_INTE_ORDER.DEL_CONTEXT is '积分消耗详情(积分购买内容)'
/comment on column TF_DIS_INTE_ORDER.STATUS is '1:待发货，0：已发货'
/comment on column TF_DIS_INTE_ORDER.ADDR_ID is '收货地址ID'
/create index TF_DIS_INTE_ORDER_INDEX1 on TF_DIS_INTE_ORDER (USER_ID)
/

-- auto-generated definition
create table TF_DIS_LOGISTICS
(
  LOGISTICS_ID   VARCHAR2(32) not null
    constraint TF_DIS_LOGISTICS_PK
      primary key,
  LOGISTICS_NAME VARCHAR2(32),
  LOGISTICS_CODE VARCHAR2(32),
  UUID           VARCHAR2(32)
)
/comment on table TF_DIS_LOGISTICS is '物流表'
/comment on column TF_DIS_LOGISTICS.LOGISTICS_ID is '物流id'
/comment on column TF_DIS_LOGISTICS.LOGISTICS_NAME is '物流厂商名'
/comment on column TF_DIS_LOGISTICS.LOGISTICS_CODE is '物流单号'
/comment on column TF_DIS_LOGISTICS.UUID is '积分订单id'
/

-- auto-generated definition
create table TF_DIS_ORDER
(
  ORDER_ID      VARCHAR2(20) not null
    constraint PK_TF_DIS_ORDER
      primary key,
  CREATE_TIME   VARCHAR2(20),
  USER_ID       VARCHAR2(20),
  COURSE_ID     VARCHAR2(32),
  TOTLE_PRICE   NUMBER(8, 2),
  STATUS        VARCHAR2(20),
  STATUS_DETAIL VARCHAR2(50),
  ORDER_PID     VARCHAR2(20)
)
/comment on table TF_DIS_ORDER is '子订单表'
/comment on column TF_DIS_ORDER.ORDER_ID is '订单ID'
/comment on column TF_DIS_ORDER.CREATE_TIME is '订单创建时间'
/comment on column TF_DIS_ORDER.USER_ID is '购买用户id'
/comment on column TF_DIS_ORDER.COURSE_ID is '购买课程id'
/comment on column TF_DIS_ORDER.TOTLE_PRICE is '价格'
/comment on column TF_DIS_ORDER.STATUS is '订单状态（1：转赠出去；2：已消核；0：待使用；4：获取转增）'
/comment on column TF_DIS_ORDER.STATUS_DETAIL is '订单详情（转赠：记录用户id；消核：记录教学点id）'
/comment on column TF_DIS_ORDER.ORDER_PID is '订单父ID'
/create index TF_DIS_ORDER_INDEX1 on TF_DIS_ORDER (USER_ID)
/create index TF_DIS_ORDER_INDEX2 on TF_DIS_ORDER (COURSE_ID)
/

-- auto-generated definition
create table TF_DIS_PARAMS
(
  PID            VARCHAR2(32) not null
    constraint PK_TF_DIS_PARAMS
      primary key,
  MAX_MONEY      VARCHAR2(6),
  MIN_MONEY      VARCHAR2(5),
  CHANGE_NUM     VARCHAR2(2),
  CHANGE_TYPE    VARCHAR2(10),
  CHARGE_SERVICE VARCHAR2(3),
  SCORE          VARCHAR2(2),
  MONEY          VARCHAR2(100)
)
/comment on table TF_DIS_PARAMS is '分销参数表'
/comment on column TF_DIS_PARAMS.PID is '主键id'
/comment on column TF_DIS_PARAMS.MAX_MONEY is '最大提现金额'
/comment on column TF_DIS_PARAMS.MIN_MONEY is '最小提现金额'
/comment on column TF_DIS_PARAMS.CHANGE_NUM is '提现次数'
/comment on column TF_DIS_PARAMS.CHANGE_TYPE is '提现类型（每周，每日）'
/comment on column TF_DIS_PARAMS.CHARGE_SERVICE is '提现手续费'
/comment on column TF_DIS_PARAMS.SCORE is '积分'
/comment on column TF_DIS_PARAMS.MONEY is '金额'
/

-- auto-generated definition
create table TF_DIS_PIC
(
  PIC_ID  VARCHAR2(32) not null
    constraint PK_TF_DIS_PIC
      primary key,
  RELA_ID VARCHAR2(20),
  URL     VARCHAR2(100),
  PATH    VARCHAR2(100)
)
/comment on table TF_DIS_PIC is '图片表'
/comment on column TF_DIS_PIC.PIC_ID is '图片id'
/comment on column TF_DIS_PIC.RELA_ID is '关联id'
/comment on column TF_DIS_PIC.URL is '映射路径'
/comment on column TF_DIS_PIC.PATH is '物理路径'
/create index TF_DIS_PIC_INDEX1 on TF_DIS_PIC (RELA_ID)
/

-- auto-generated definition
create table TF_DIS_PORDER
(
  ORDER_PID   VARCHAR2(20) not null
    constraint PK_TF_DIS_PORDER
      primary key,
  CREATE_TIME VARCHAR2(20),
  USER_ID     VARCHAR2(20),
  COURSE_ID   VARCHAR2(32),
  NUM         NUMBER(8),
  TOTLE_PRICE NUMBER(8, 2),
  PAY_STATUS  VARCHAR2(20)
)
/comment on table TF_DIS_PORDER is '父订单表'
/comment on column TF_DIS_PORDER.ORDER_PID is '订单父ID'
/comment on column TF_DIS_PORDER.CREATE_TIME is '订单创建时间'
/comment on column TF_DIS_PORDER.USER_ID is '购买用户id'
/comment on column TF_DIS_PORDER.COURSE_ID is '购买课程id'
/comment on column TF_DIS_PORDER.NUM is '购买数量'
/comment on column TF_DIS_PORDER.TOTLE_PRICE is '总价格'
/comment on column TF_DIS_PORDER.PAY_STATUS is '订单支付状态'
/create index TF_DIS_PORDER_INDEX1 on TF_DIS_PORDER (USER_ID)
/create index TF_DIS_PORDER_INDEX2 on TF_DIS_PORDER (COURSE_ID)
/

-- auto-generated definition
create table TF_DIS_QRCODE
(
  QR_ID    VARCHAR2(32) not null
    constraint PK_TF_DIS_QRCODE
      primary key,
  ORDER_ID VARCHAR2(32),
  QR_PATH  VARCHAR2(200),
  QR_URL   VARCHAR2(200)
)
/comment on table TF_DIS_QRCODE is '销核二维码'
/comment on column TF_DIS_QRCODE.QR_ID is '主键'
/comment on column TF_DIS_QRCODE.ORDER_ID is '子订单id'
/comment on column TF_DIS_QRCODE.QR_PATH is '绝对路径'
/comment on column TF_DIS_QRCODE.QR_URL is '映射路径'
/create index TF_DIS_QRCODE_INDEX on TF_DIS_QRCODE (ORDER_ID)
/

-- auto-generated definition
create table SYS_CODE
(
  CID         VARCHAR2(50) not null
    constraint PK_SYS_CODE
      primary key,
  CODE        VARCHAR2(50),
  CODE_VALUE  VARCHAR2(50),
  CODE_TYPE   VARCHAR2(50),
  CODE_SORT   VARCHAR2(10),
  CREATE_BY   VARCHAR2(32),
  CREATE_DATE VARCHAR2(32),
  DEL_FLAG    VARCHAR2(2),
  REMARKS     VARCHAR2(50)
)
/comment on table SYS_CODE is '字典表'
/comment on column SYS_CODE.CID is '主键id)'
/comment on column SYS_CODE.CODE is 'code值'
/comment on column SYS_CODE.CODE_VALUE is 'code对应的值'
/comment on column SYS_CODE.CODE_TYPE is 'code的类型'
/comment on column SYS_CODE.CODE_SORT is '排序'
/comment on column SYS_CODE.CREATE_BY is '创建人'
/comment on column SYS_CODE.CREATE_DATE is '创建时间'
/comment on column SYS_CODE.DEL_FLAG is '是否已删除（1：已删除；0：未删除）'
/comment on column SYS_CODE.REMARKS is '备注'
/create index SYS_CODE_INDEX1 on SYS_CODE (CODE)
/create index SYS_CODE_INDEX2 on SYS_CODE (CODE_TYPE)
/

-- auto-generated definition
create table TF_DIS_SCHOOL
(
  SCHOOL_ID         VARCHAR2(32) not null
    constraint PK_TF_DIS_SCHOOL
      primary key,
  SCHOOL_NAME       VARCHAR2(50),
  CAMPUS_ID         VARCHAR2(32),
  ADDR              VARCHAR2(100),
  CONCAT_USER       VARCHAR2(20),
  CONCAT_USER_PHONE VARCHAR2(11)
)
/comment on table TF_DIS_SCHOOL is '教学点表'
/comment on column TF_DIS_SCHOOL.SCHOOL_ID is '学校id'
/comment on column TF_DIS_SCHOOL.SCHOOL_NAME is '学校名称'
/comment on column TF_DIS_SCHOOL.CAMPUS_ID is '校区id'
/comment on column TF_DIS_SCHOOL.ADDR is '地址'
/comment on column TF_DIS_SCHOOL.CONCAT_USER is '联系人'
/comment on column TF_DIS_SCHOOL.CONCAT_USER_PHONE is '联系人电话'
/create index TF_DIS_SCHOOL_INDEX1 on TF_DIS_SCHOOL (CAMPUS_ID)
/

-- auto-generated definition
create table TF_DIS_USER
(
  USER_ID       VARCHAR2(20)  not null
    constraint PK_TF_DIS_USER
      primary key,
  USER_NAME     VARCHAR2(50),
  SERIAL_NUMBER VARCHAR2(11),
  CREATE_TIME   VARCHAR2(16),
  PARENT_ID     VARCHAR2(20),
  OPEN_ID       VARCHAR2(100) not null,
  LEV_SCORE     VARCHAR2(10),
  SCORE_VALUE   VARCHAR2(10),
  STATUS        VARCHAR2(2),
  LEV_CODE      VARCHAR2(10)
)
/comment on table TF_DIS_USER is '合伙人表'
/comment on column TF_DIS_USER.USER_ID is 'ID'
/comment on column TF_DIS_USER.USER_NAME is '名称'
/comment on column TF_DIS_USER.SERIAL_NUMBER is '手机号码'
/comment on column TF_DIS_USER.CREATE_TIME is '创建时间'
/comment on column TF_DIS_USER.PARENT_ID is '推荐人id'
/comment on column TF_DIS_USER.OPEN_ID is '微信授权登录的openId'
/comment on column TF_DIS_USER.LEV_SCORE is '等级积分'
/comment on column TF_DIS_USER.SCORE_VALUE is '提现积分'
/comment on column TF_DIS_USER.STATUS is '状态（0：停用；1：启用）'
/comment on column TF_DIS_USER.LEV_CODE is '等级名称code值'
/create index TF_DIS_USER_SER on TF_DIS_USER (SERIAL_NUMBER)
/create index TF_DIS_USER_INDEX3 on TF_DIS_USER (LEV_CODE)
/

-- auto-generated definition
create table TF_DIS_SCHOOL
(
  SCHOOL_ID         VARCHAR2(32) not null
    constraint PK_TF_DIS_SCHOOL
      primary key,
  SCHOOL_NAME       VARCHAR2(50),
  CAMPUS_ID         VARCHAR2(32),
  ADDR              VARCHAR2(100),
  CONCAT_USER       VARCHAR2(20),
  CONCAT_USER_PHONE VARCHAR2(11)
)
/comment on table TF_DIS_SCHOOL is '教学点表'
/comment on column TF_DIS_SCHOOL.SCHOOL_ID is '学校id'
/comment on column TF_DIS_SCHOOL.SCHOOL_NAME is '学校名称'
/comment on column TF_DIS_SCHOOL.CAMPUS_ID is '校区id'
/comment on column TF_DIS_SCHOOL.ADDR is '地址'
/comment on column TF_DIS_SCHOOL.CONCAT_USER is '联系人'
/comment on column TF_DIS_SCHOOL.CONCAT_USER_PHONE is '联系人电话'
/create index TF_DIS_SCHOOL_INDEX1 on TF_DIS_SCHOOL (CAMPUS_ID)
/