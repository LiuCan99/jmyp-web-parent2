package com.czxy;

import com.czxy.pojo.Item;
import com.czxy.pojo.ItemRepository;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)

public class Test {

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    @Resource
    private ItemRepository itemRepository;

    //创建索引
    @org.junit.Test
    public void demo02(){
        elasticsearchTemplate.createIndex(Item.class);
    }

    //删除索引
    @org.junit.Test
    public void demo01(){
        elasticsearchTemplate.deleteIndex("item");
    }

    //往item中新增对象
    @org.junit.Test
    public  void  demo03(){
        Item item = new Item(1L, "小米手机7", "手机","小米", 3499.00, "http://image.baidu.com/13123.jpg");
        itemRepository.save(item);
    }

    //批量新增
    @org.junit.Test
    public  void  demo04(){
        List<Item> list=new ArrayList<>();
        list.add(new Item(2L, "坚果手机R1", "手机", "锤子", 3699.00, "http://image.baidu.com/13123.jpg"));
        list.add(new Item(3L, "华为META10", " 手机", "华为", 4499.00, "http://image.baidu.com/13123.jpg"));

        //接收对象集合，实现批量新增
        itemRepository.saveAll(list);
    }

    @org.junit.Test
    public void indexList() {
        List<Item> list = new ArrayList<>();
        list.add(new Item(1L, "小米手机7", "手机", "小米", 3299.00, "http://image.baidu.com/13123.jpg"));
        list.add(new Item(2L, "坚果手机R1", "手机", "锤子", 3699.00, "http://image.baidu.com/13123.jpg"));
        list.add(new Item(3L, "华为META10", "手机", "华为", 4499.00, "http://image.baidu.com/13123.jpg"));
        list.add(new Item(4L, "小米Mix2S", "手机", "小米", 4299.00, "http://image.baidu.com/13123.jpg"));
        list.add(new Item(5L, "荣耀V10", "手机", "华为", 2799.00, "http://image.baidu.com/13123.jpg"));
        // 接收对象集合，实现批量新增
        itemRepository.saveAll(list);
    }


    //自定义方法，根据价格区间查询
    @org.junit.Test
    public void queryByPriceBetween(){
        //调用接口中的方法
        List<Item> list = this.itemRepository.findByPriceBetween(2000.00, 3500.00);
        for (Item item : list) {
            //输出符合条件的对象
            System.out.println("item = " + item);
        }
    }

    //自定义查询
    @org.junit.Test
    public void search(){
        //构建查询条件
        NativeSearchQueryBuilder queryBuilder=new NativeSearchQueryBuilder();
        //添加基本分词查询，matchQuery分词查询（模糊分词查询）
        queryBuilder.withQuery(QueryBuilders.matchQuery("title","小米手机"));
        //搜索获取结果
        Page<Item>items=itemRepository.search(queryBuilder.build());
        //总条数
        Long total=items.getTotalElements();
        System.out.println("total="+total);
        for(Item item:items){
            System.out.println(item);
        }
    }

    //分页
    @org.junit.Test
    public void searchByPage(){
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询，termQuery精确查询
        queryBuilder.withQuery(QueryBuilders.termQuery("category", "手机"));
        // 分页：
        int page = 0;
        int size = 2;
        queryBuilder.withPageable(PageRequest.of(page,size));

        // 搜索，获取结果
        Page<Item> items = this.itemRepository.search(queryBuilder.build());
        // 总条数
        long total = items.getTotalElements();
        System.out.println("总条数 = " + total);
        // 总页数
        System.out.println("总页数 = " + items.getTotalPages());
        // 当前页
        System.out.println("当前页：" + items.getNumber());
        // 每页大小
        System.out.println("每页大小：" + items.getSize());

        for (Item item : items) {
            System.out.println(item);
        }
    }









}
