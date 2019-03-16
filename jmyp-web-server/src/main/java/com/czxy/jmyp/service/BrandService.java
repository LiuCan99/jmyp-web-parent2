package com.czxy.jmyp.service;

import com.czxy.jmyp.dao.BrandMapper;
import com.czxy.jmyp.pojo.Brand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class BrandService {

    @Resource
    private BrandMapper brandMapper;

    public List<Brand>  findBrandByCatid(Integer catid){
        return  brandMapper.findBrandByCatid(catid);
    }
}
