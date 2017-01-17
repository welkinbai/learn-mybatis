package init;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.Reader;

/**
 * Created by welkinbai on 2017/1/17.
 * 第一次使用本项目时可以初始化数据库
 */
public class FirstRunMe {

    @Test
    public void initDB() throws Exception {
        String resource = "mybatisconfig_for_init.xml";//指定xml配置文件的路径
        InputStream inputStream = Resources.getResourceAsStream(resource);//使用资源工具类加载配置文件到流中
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);//通过一个配置文件的流来创建Factory对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            ScriptRunner scriptRunner = new ScriptRunner(sqlSession.getConnection());
            scriptRunner.setAutoCommit(true);
            Reader reader = new FileReader(new File("src/main/resources/world.sql"));
            scriptRunner.runScript(reader);
        } finally {
            sqlSession.close();
        }
    }
}
