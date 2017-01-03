package me.welkinbai.mybatis.withoutspring;

import me.welkinbai.mybatis.withoutspring.mapper.CityMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by welkinbai on 2017/1/3.
 */
public class SettingsTest {

    private SqlSession sqlSession;
    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void setUp() throws Exception {
        init();
    }

    private void init() throws IOException {
        String resource = "mybatisconfig_test_settings.xml";//指定xml配置文件的路径
        InputStream inputStream = Resources.getResourceAsStream(resource);//使用资源工具类加载配置文件到流中
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);//通过一个配置文件的流来创建Factory对象
        this.sqlSessionFactory = sqlSessionFactory;
        sqlSession = sqlSessionFactory.openSession();
    }

    @Test
    public void testCloseCache() throws Exception {
        try {
            CityMapper city = sqlSession.getMapper(CityMapper.class);
            System.out.println(city.selectById(1).getName());
            sqlSession.close();
            SqlSession sqlSession2 = this.sqlSessionFactory.openSession();
            CityMapper city2 = sqlSession2.getMapper(CityMapper.class);
            System.out.println(city2.selectById(1).getName());
        } finally {
            sqlSession.close();
        }
    }


}
