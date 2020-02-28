package com.rs.teach.mapper;

/**
 * @author wanghang
 * @Description
 * @create 2019-09-16 18:03
 */

import java.util.Arrays;
import java.util.List;

/**
 * Mybatis逆向工程配置
 *
 * @author
 * @create 2017-09-30 9:19
 */
public interface MybatisGenerateConfigs {

    //是否用逆向工程生成的Model,Dao,Mapper覆盖当前已存在的,若覆盖请做好备份工作
    Boolean OVERRIDE_EXIST = false;

    //指定要生成的Table
    /*List<String> TABLES = Arrays.asList("TF_TMEGA_ADMIN","TF_TMEGA_PERMISSION","TF_TMEGA_PICTURE"
            ,"TF_TMEGA_PICTURE_RESOURSE","TF_TMEGA_ROLE","TF_TMEGA_ROLE_PERMISSION","SYS_CODE");*/
    List<String> TABLES = Arrays.asList("GRADE_PRESCHOOL","GRADE_PRESCHOOL_OPTION");
    /*List<String> TABLES = Arrays.asList("TF_DIS_ADDRESS","TF_DIS_ADMIN","TF_DIS_BANNER","TF_DIS_CAMPUS",
            "TF_DIS_CHANGE","TF_DIS_COURSE","TF_DIS_DISAPPER_MANAGER","TF_DIS_GOODS","TF_DIS_GOODS_ATTR","TF_DIS_INTE",
            "TF_DIS_INTE_ORDER","TF_DIS_ORDER","TF_DIS_PARAMS","TF_DIS_PIC","TF_DIS_SCHOOL","TF_DIS_USER");*/

    //数据库表前缀（若无则不修改此项，有则改为自己的****小写，小写，小写（重要的说三遍））
    String TABLE_PREFIX = "tf_tmega_";

    //连接数据库驱动包 这里选择自己本地位置
    //String CLASSPATH_ENTRY = "D:/profile/postgresql-42.1.4.jar";
    String CLASSPATH_ENTRY = "D:/Maven2/repository/com/oracle/ojdbc14/10.2.0.4.0/ojdbc14-10.2.0.4.0.jar";
    //String CLASSPATH_ENTRY = "F:/maven/repository/mysql/mysql-connector-java/5.1.6/mysql-connector-java-5.1.6.jar";

    //指定生成java文件的编码格式
    String JAVA_FILEEN_CODING = "UTF-8";

    //指定JDBC信息oracle
    String JDBC_DRIVERCLASS = "oracle.jdbc.driver.OracleDriver";
    String JDBC_CONNECTIONURL = "jdbc:oracle:thin:@121.40.232.147:1521:orcl";
    String JDBC_USER_NAME = "dis";
    String JDBC_PASSWORD = "dis123456";

    //指定JDBC信息mysql
    /*String JDBC_DRIVERCLASS = "com.mysql.jdbc.Driver";
    String JDBC_CONNECTIONURL = "jdbc:mysql://localhost:3306/test";
    String JDBC_USER_NAME = "root";
    String JDBC_PASSWORD = "root";*/

    //如果maven工程只是单独的一个工程，targetProject="src/main/resources"
    //String DEFAULT_JAVA_TARGETPROJECT = "src/main/java";
    //String DEFAULT_RESOURCES_TARGETPROJECT = "src/main/resources";

    //若果maven工程是分模块的工程，即使时在当前模块下生产成Mybatis文件，也需要指定模块前缀，
    // targetProject="指定模块的名称/路径"，例如：targetProject="project-web/src/main/java"
    String DEFAULT_JAVA_TARGETPROJECT = "teach-dao/src/main/java";
    //java类和配置文件生成位置可以指向不同的项目
    String DEFAULT_RESOURCES_TARGETPROJECT = "teach-dao/src/main/resources";

    //指定Java Model生成位置
    String JAVA_MODEL_TARGETPROJECT = DEFAULT_JAVA_TARGETPROJECT;
    String JAVA_MODEL_TARGETPACKAGE = "com.rs.teach.mapper.grade.entity";
    //指定Java DAO接口生成位置
    String JAVACLIENT_TARGETPROJECT = DEFAULT_JAVA_TARGETPROJECT;
    String JAVACLIENT_TARGETPACKAGE = "com.rs.teach.mapper.grade.dao";
    //指定Mapper.xml生成位置
    String SQLMAP_TARGETPROJECT = DEFAULT_RESOURCES_TARGETPROJECT;
    String SQLMAP_TARGETPACKAGE = "mappings.modules.grade";

    /**
     * 可设置自定义的类型解析器
     * {@linkplain //JavaTypeResolver}
     */
    String JAVA_TYPE_RESOLVER = null;

}
