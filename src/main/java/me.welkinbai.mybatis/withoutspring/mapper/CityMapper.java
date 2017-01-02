package me.welkinbai.mybatis.withoutspring.mapper;

import me.welkinbai.mybatis.withoutspring.domain.City;

import java.util.List;

/**
 * Created by welkinbai on 2016/12/29.
 */
public interface CityMapper {
    City selectById(Integer id);

    List<City> selectListById(Integer id);

    int update(City newCity);

    int delete(City oldCity);

    int insert(City newCity);
}
