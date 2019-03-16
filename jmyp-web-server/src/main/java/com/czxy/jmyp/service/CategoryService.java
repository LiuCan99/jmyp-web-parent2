package com.czxy.jmyp.service;

import com.czxy.jmyp.dao.CategoryMapper;
import com.czxy.jmyp.pojo.Category;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CategoryService  {
    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 查询所有分类
     * @return
     */
    public List<Category> findAll(){
       //1.查询所有--按照parentId升序排序
        Example example=new Example(Category.class);
        example.setOrderByClause("parent_id asc");
        List<Category> temp=categoryMapper.selectByExample(example);

        //2. 过滤所有一级 parentId==0
        List<Category> list=new ArrayList<>();

        //3.记录所有元素，方法子元素寻找对应的丰富元素
        Map<Integer , Category> map = new HashMap<>();
        for(Category category:temp){
            //把所有parentId==0（一级分类）的对象添加到list中
            if(category.getParentId()==0){
                list.add(category);
            }
            //存储所有元素 (对象id，对象)
            map.put(category.getId(),category);

            //获得当前元素的父元素，并添加到父元素的children集合中
            Category parentCategory=map.get(category.getParentId());

            //如果父对象不为null，则把该对象添加到该父对象的子类中
            if(parentCategory!=null){
                parentCategory.getChildren().add(category);
            }
        }

        return list;
    }
}
