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
import java.util.List;

/**
 * Created by welkinbai on 2017/1/2.
 */
public class StatementTest {

    private SqlSession sqlSession;

    @Before
    public void setUp() throws Exception {
        init();
    }

    private void init() throws IOException {
        String resource = "mybatisconfig.xml";//指定xml配置文件的路径
        InputStream inputStream = Resources.getResourceAsStream(resource);//使用资源工具类加载配置文件到流中
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);//通过一个配置文件的流来创建Factory对象
        sqlSession = sqlSessionFactory.openSession();
    }

    @Test
    public void testCallSqlByMapper() throws Exception {
        try {
            CityMapper city = sqlSession.getMapper(CityMapper.class);
            System.out.println(city.selectById(1).getName());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testCallSqlByStringPath() throws Exception {
        try {
            City city = sqlSession.selectOne("me.welkinbai.mybatis.withoutspring.mapper.CityMapper.selectById", 1);
            System.out.println(city.getName());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelect() throws Exception {
        testCallSqlByMapper();
        //or
        init();
        testCallSqlByStringPath();
    }

    @Test
    public void testSelectList() throws Exception {
        try {
            CityMapper cityMapper = sqlSession.getMapper(CityMapper.class);
            List<City> cities = cityMapper.selectListById(2000);
            for (City city : cities) {
                System.out.println(city.getName());
            }
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testUpdate() throws Exception {
        try {
            CityMapper cityMapper = sqlSession.getMapper(CityMapper.class);
            City newCity = new City();
            newCity.setName("test2");
            newCity.setId(1);
            int influenceRow = cityMapper.update(newCity);
            System.out.println(influenceRow);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testDelete() throws Exception {
        try {
            CityMapper cityMapper = sqlSession.getMapper(CityMapper.class);
            City oldCity = new City();
            oldCity.setId(1);
            int influenceRow = cityMapper.delete(oldCity);
            System.out.println(influenceRow);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testInsert() throws Exception {
        try {
            CityMapper cityMapper = sqlSession.getMapper(CityMapper.class);
            City newCity = new City();
            newCity.setName("testInsert_auto_id");
            newCity.setCountryCode("CHN");
            newCity.setDistrict("test");
            newCity.setPopulation(1122);
            int influenceRow = cityMapper.insert(newCity);
            System.out.println(influenceRow);
            System.out.println(newCity.getId());
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

}
