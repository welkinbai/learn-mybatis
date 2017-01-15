package me.welkinbai.mybatis.withoutspring.mapper;

import me.welkinbai.mybatis.withoutspring.domain.City;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Created by welkinbai on 2016/12/29.
 */
public interface CityMapper {
    City selectById(Integer id);

    City selectByIdWithoutResultMap(Integer id);

    City selectByIdWithAssociationResult(Integer id);

    City selectByIdWithUnknownColumnResult(Integer id);

    List<City> selectListById(Integer id);

    List<City> selectListByIdWithRowBounds(Integer id, RowBounds rowBounds);

    int update(City newCity);

    int delete(City oldCity);

    int insert(City newCity);
}
