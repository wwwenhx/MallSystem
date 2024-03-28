package com.drug.graduate.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IndexMapper {

    //轮播图url
    public List<Map<String, Object>> rotationImg ();

    //人气药品展示
    public List<Map<String, Object>> popularityGoods();
}
