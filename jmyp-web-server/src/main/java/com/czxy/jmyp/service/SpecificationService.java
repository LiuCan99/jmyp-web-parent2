package com.czxy.jmyp.service;


import com.czxy.jmyp.dao.SpecificationMapper;
import com.czxy.jmyp.pojo.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
@Transactional
public class SpecificationService {
    @Resource
    private SpecificationMapper specificationMapper;

    public List<Specification> findSpecificationByCategoryId(Integer categoryId){
        System.out.println("catId:"+categoryId);
        return specificationMapper.findSpecificationByCategoryId(categoryId);
    }
}
