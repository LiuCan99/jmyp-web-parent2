package com.czxy.jmyp.controller;

import com.czxy.jmyp.WebSearchApplication;
import com.czxy.jmyp.feignclient.SkuClient;
import com.czxy.jmyp.repository.SkuRepository;
import com.czxy.jmyp.vo.SearchSku;
import org.elasticsearch.client.ElasticsearchClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebSearchApplication.class )
public class SkuClientTest {

    //远程调用获得数据
    @Resource
    private SkuClient skuClient;
    //向es库存放数据
    @Resource
    private SkuRepository skuRepository;

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    //删除索引
    @org.junit.Test
    public void demo01(){
        elasticsearchTemplate.deleteIndex(SearchSku.class);
    }

    //创建索引
    @org.junit.Test
    public void demo02(){
        elasticsearchTemplate.createIndex(SearchSku.class);
        elasticsearchTemplate.putMapping(SearchSku.class);
    }

    @Test
    public void testInsert(){
        //1 获得数据
        ResponseEntity<List<SearchSku>> all = skuClient.findAll();
        //2 写入数据
        this.skuRepository.saveAll( all.getBody() );

    }

}
