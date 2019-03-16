package com.czxy.jmyp.comtroller;

import com.czxy.jmyp.pojo.News;
import com.czxy.jmyp.service.NewsService;
import com.czxy.jmyp.vo.BaseResult;
import com.czxy.jmyp.vo.NewsResult;
import com.czxy.jmyp.vo.PageRequest;
import com.github.pagehelper.PageInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping
public class NewsController {
    @Resource
    private NewsService newsService;

    @GetMapping("/news")
    public ResponseEntity<BaseResult> findNewsByPage(PageRequest pageRequest){

        //1.查找当前页的新闻数据
        PageInfo<News> pageInfo =newsService.findNewsByPage(pageRequest);
        //2.封装
        BaseResult baseResult=new BaseResult(0,"成功")
                              .append("total",pageInfo.getTotal())
                              .append("data",pageInfo.getList());
        return ResponseEntity.ok(baseResult);

    }
}
