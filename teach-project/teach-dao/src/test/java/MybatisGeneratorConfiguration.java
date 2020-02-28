/**
 * @author wanghang
 * @Description
 * @create 2019-09-16 18:05
 */

import org.mybatis.generator.config.CommentGeneratorConfiguration;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JDBCConnectionConfiguration;
import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.config.JavaTypeResolverConfiguration;
import org.mybatis.generator.config.PluginConfiguration;
import org.mybatis.generator.config.SqlMapGeneratorConfiguration;
import org.mybatis.generator.config.TableConfiguration;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import static com.rs.teach.mapper.MybatisGenerateConfigs.CLASSPATH_ENTRY;
import static com.rs.teach.mapper.MybatisGenerateConfigs.JAVACLIENT_TARGETPACKAGE;
import static com.rs.teach.mapper.MybatisGenerateConfigs.JAVACLIENT_TARGETPROJECT;
import static com.rs.teach.mapper.MybatisGenerateConfigs.JAVA_FILEEN_CODING;
import static com.rs.teach.mapper.MybatisGenerateConfigs.JAVA_MODEL_TARGETPACKAGE;
import static com.rs.teach.mapper.MybatisGenerateConfigs.JAVA_MODEL_TARGETPROJECT;
import static com.rs.teach.mapper.MybatisGenerateConfigs.JAVA_TYPE_RESOLVER;
import static com.rs.teach.mapper.MybatisGenerateConfigs.JDBC_CONNECTIONURL;
import static com.rs.teach.mapper.MybatisGenerateConfigs.JDBC_DRIVERCLASS;
import static com.rs.teach.mapper.MybatisGenerateConfigs.JDBC_PASSWORD;
import static com.rs.teach.mapper.MybatisGenerateConfigs.JDBC_USER_NAME;
import static com.rs.teach.mapper.MybatisGenerateConfigs.SQLMAP_TARGETPACKAGE;
import static com.rs.teach.mapper.MybatisGenerateConfigs.SQLMAP_TARGETPROJECT;
import static com.rs.teach.mapper.MybatisGenerateConfigs.TABLES;
import static com.rs.teach.mapper.MybatisGenerateConfigs.TABLE_PREFIX;

/**
 * Mybatis逆向工程基于Java形式的配置解析器
 *
 * @author
 * @create 2017-09-30 9:17
 */
public class MybatisGeneratorConfiguration {

    public Configuration configMybatisGenerator() {
        Configuration configuration = new Configuration();

        configuration.addClasspathEntry(CLASSPATH_ENTRY);

        Context context = new Context(null);
        context.setTargetRuntime("MyBatis3");
        context.setId("bob");

        context.addProperty("javaFileEncoding", JAVA_FILEEN_CODING);
        //添加序列化接口
        context.addPluginConfiguration(generatePluginConfiguration());
        //设置注解生成器
        context.setCommentGeneratorConfiguration(generateCommentConfiguration());
        //设置JDBC连接配置
        context.setJdbcConnectionConfiguration(generateJDBCConnectionConfiguration());
        //设置JDBC Type 与Java Type之间的映射解析器
        context.setJavaTypeResolverConfiguration(generateJavaTypeResolverConfiguration());
        //设置Java Model生成配置
        context.setJavaModelGeneratorConfiguration(generateJavaModelGeneratorConfiguration());
        //设置DAO层的生成配置
        context.setSqlMapGeneratorConfiguration(generateSqlMapGeneratorConfiguration());
        //设置Mapper.xml生成
        context.setJavaClientGeneratorConfiguration(generateJavaClientGeneratorConfiguration());
        //设置需要生成的Table及生成形式
        for (TableConfiguration tableConfiguration : generateTableConfigurations(context)) {
            context.addTableConfiguration(tableConfiguration);
        }
        configuration.addContext(context);
        return configuration;
    }

    /**
     * 配置序列化接口
     *
     * @return
     */
    private PluginConfiguration generatePluginConfiguration() {
        PluginConfiguration pluginConfiguration = new PluginConfiguration();
        pluginConfiguration.setConfigurationType("org.mybatis.generator.plugins.SerializablePlugin");
        return pluginConfiguration;
    }

    /**
     * 配置注解生成器
     *
     * @return
     */
    private CommentGeneratorConfiguration generateCommentConfiguration() {
        CommentGeneratorConfiguration configuration = new CommentGeneratorConfiguration();
        configuration.setConfigurationType(GeneralCommentGenerator.class.getName());
        //是否去除自动生成的注释 true：是 ： false:否
        configuration.addProperty("suppressAllComments", "false");
        configuration.addProperty("addRemarkComments", "true");
        return configuration;
    }

    /**
     * 设置数据库连接的信息：驱动类、连接地址、用户名、密码
     *
     * @return
     */
    private JDBCConnectionConfiguration generateJDBCConnectionConfiguration() {
        JDBCConnectionConfiguration configuration = new JDBCConnectionConfiguration();
        configuration.setDriverClass(JDBC_DRIVERCLASS);
        //为mysql时打开
        String jdbcSuffix = "?useUnicode=true&characterEncoding=UTF8&useSSL=false";
        configuration.setConnectionURL(JDBC_CONNECTIONURL + jdbcSuffix);
        configuration.setConnectionURL(JDBC_CONNECTIONURL);
        configuration.setUserId(JDBC_USER_NAME);
        configuration.setPassword(JDBC_PASSWORD);
        //ORACLE注释不生成解决办法
        configuration.addProperty("remarksReporting", "true");
        return configuration;
    }

    /**
     * 设置JDBC Type 与Java Type之间的映射解析器
     *
     * @return
     */
    private JavaTypeResolverConfiguration generateJavaTypeResolverConfiguration() {
        JavaTypeResolverConfiguration configuration = new JavaTypeResolverConfiguration();
        //可自定义类型映射解析器
        configuration.setConfigurationType(JAVA_TYPE_RESOLVER);
        //默认false，把JDBC DECIMAL 和 NUMERIC 类型解析为 Integer，为 true时把JDBC DECIMAL 和 NUMERIC 类型解析为java.math.BigDecimal
        configuration.addProperty("forceBigDecimals", "true");

        return configuration;
    }

    /**
     * 配置Java Model生成
     *
     * @return
     */
    private JavaModelGeneratorConfiguration generateJavaModelGeneratorConfiguration() {
        JavaModelGeneratorConfiguration configuration = new JavaModelGeneratorConfiguration();
        configuration.setTargetProject(JAVA_MODEL_TARGETPROJECT);
        configuration.setTargetPackage(JAVA_MODEL_TARGETPACKAGE);
        //是否让schema作为包的后缀
        configuration.addProperty("enableSubPackages", "false");
        //从数据库返回的值被清理前后的空格
        configuration.addProperty("trimStrings", "true");
        //去除getset方法
        //configuration.addProperty("immutable", "false");
        //获取注释
        configuration.addProperty("remarksReporting", "true");

        return configuration;
    }

    /**
     * 配置Mapper.xml生成
     *
     * @return
     */
    private SqlMapGeneratorConfiguration generateSqlMapGeneratorConfiguration() {
        SqlMapGeneratorConfiguration configuration = new SqlMapGeneratorConfiguration();
        configuration.setTargetProject(SQLMAP_TARGETPROJECT);
        configuration.setTargetPackage(SQLMAP_TARGETPACKAGE);
        //是否让schema作为包的后缀
        configuration.addProperty("enableSubPackages", "false");
        return configuration;
    }

    /**
     * 设置DAO生成
     *
     * @return
     */
    private JavaClientGeneratorConfiguration generateJavaClientGeneratorConfiguration() {
        JavaClientGeneratorConfiguration configuration = new JavaClientGeneratorConfiguration();
        configuration.setConfigurationType("XMLMAPPER");
        configuration.setTargetProject(JAVACLIENT_TARGETPROJECT);
        configuration.setTargetPackage(JAVACLIENT_TARGETPACKAGE);
        //是否让schema作为包的后缀
        configuration.addProperty("enableSubPackages", "false");
        return configuration;
    }

    /*private List<TableConfiguration> generateTableConfigurations(Context context) {
        List<TableConfiguration> configurations = new ArrayList<TableConfiguration>();
        for (String table : TABLES) {
            TableConfiguration configuration = new TableConfiguration(context);
            configuration.setTableName(table);
            configuration.setSelectByExampleStatementEnabled(false);
            configuration.setDeleteByExampleStatementEnabled(false);
            configuration.setCountByExampleStatementEnabled(false);
            configuration.setUpdateByExampleStatementEnabled(false);
            configurations.add(configuration);
        }
        return configurations;
    }*/

    /**
     * -----------------------------------------改造后的-----------------------------------------------
     * @author  wanghang
     * @create 2017-09-30 9:17
     */

    private List<TableConfiguration> generateTableConfigurations(Context context) {
        List<TableConfiguration> configurations = new ArrayList<TableConfiguration>();
        for (String table : TABLES) {
            TableConfiguration configuration = new TableConfiguration(context);
            configuration.setTableName(table);
            configuration.setDomainObjectName(convertTableToClassName(table));
            configuration.setSelectByExampleStatementEnabled(false);
            configuration.setDeleteByExampleStatementEnabled(false);
            configuration.setCountByExampleStatementEnabled(false);
            configuration.setUpdateByExampleStatementEnabled(false);
            configurations.add(configuration);
        }
        return configurations;
    }

    /**
     * 依据驼峰原则格式化将表名转换为类名,当遇到下划线时去除下划线并对之后的一位字符大写
     *
     * @param table
     * @return
     */
    private String convertTableToClassName(String table) {
        Assert.hasText(table, "表名不能为空");
        StringBuilder sb = new StringBuilder();
        if (table.toLowerCase().contains(TABLE_PREFIX)) {
            table = table.toLowerCase().split(TABLE_PREFIX)[1];
        }
        sb.append(toUpperCase(table.charAt(0)));
        for (int i = 1; i < table.length(); i++) {
            sb.append('_' == table.charAt(i) ? toUpperCase(table.charAt(++i)) : table.charAt(i));
        }
        return sb.toString();
    }

    /**
     * 将字符转换为大写
     *
     * @param ch
     * @return
     */
    private char toUpperCase(char ch) {
        return Character.toUpperCase(ch);
    }

}