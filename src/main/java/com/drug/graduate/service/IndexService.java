package com.drug.graduate.service;

import com.drug.graduate.mapper.IndexMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class IndexService {

    @Autowired
    IndexMapper indexMapper;

    //轮播图url
    public List<Map<String, Object>> rotationImg(){
        return indexMapper.rotationImg();
    }

    //人气药品展示
    public List<Map<String, Object>> popularityGoods(){ return indexMapper.popularityGoods(); }

}
