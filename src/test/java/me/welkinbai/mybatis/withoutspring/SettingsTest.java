package me.welkinbai.mybatis.withoutspring;

import me.welkinbai.mybatis.withoutspring.domain.City;
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

    /*
    * 测试方法(steps)：
    * 1.mybatisconfig_test_settings.xml cacheEnabled->false
    * 2.run this unit test and see sql executed twice
    * 3.mybatisconfig_test_settings.xml cacheEnabled->true
    * 4.run this unit test again and see sql executed once*/
    @Test
    public void testCloseCache() throws Exception {
        SqlSession sqlSession2;
        sqlSession2 = this.sqlSessionFactory.openSession();
        try {
            CityMapper city = sqlSession.getMapper(CityMapper.class);
            System.out.println(city.selectById(1).getName());
            sqlSession.close();
            CityMapper city2 = sqlSession2.getMapper(CityMapper.class);
            System.out.println(city2.selectById(1).getName());
        } finally {
            sqlSession.close();
            sqlSession2.close();
        }
    }

    /*
    * 测试方法（steps）：
    * 1. CityMapper.xml insert useGeneratedKeys="true"->just remove it
    * 2. change mybatisconfig_test_settings.xml useGeneratedKeys->true
    * 3. run this unit test and see city.getId() return right number. useGeneratedKeys config take effect.
    * 4. add useGeneratedKeys="false" to CityMapper.xml insert
    * 5. run this unit test again and see useGeneratedKeys in CityMapper.xml cover useGeneratedKeys in mybatisconfig_test_settings.xml*/
    @Test
    public void testUseGeneratedKeys() throws Exception {
        try {
            CityMapper cityMapper = sqlSession.getMapper(CityMapper.class);
            City city = new City();
            city.setName("test_use_generated_keys");
            city.setCountryCode("CHN");
            city.setPopulation(122);
            city.setDistrict("wuhan");
            cityMapper.insert(city);
            System.out.println(city.getId());
        } finally {
            sqlSession.close();
        }
    }

}
