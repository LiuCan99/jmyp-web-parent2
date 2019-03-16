package com.czxy.jmyp.service;

import com.czxy.jmyp.dao.NewsMapper;
import com.czxy.jmyp.pojo.News;
import com.czxy.jmyp.vo.PageRequest;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class NewsService {
    @Resource
    private NewsMapper newsMapper;

    public PageInfo<News> findNewsByPage(PageRequest pageRequest){
        //分页
        PageHelper.startPage(pageRequest.getPage(),pageRequest.getPerPage());
        //条件查询--按照创建时间排序
        Example example=new Example(News.class);
       if("asc".equals(pageRequest.getSortWay())){
            example.setOrderByClause("created_at asc");
       }else {
           example.setOrderByClause("created_at desc");
       }
       //查询所有（支持分页）
        List<News> list =newsMapper.selectByExample(example);
       //封装成PageInfo
        return  new PageInfo<>(list);
    }
}
