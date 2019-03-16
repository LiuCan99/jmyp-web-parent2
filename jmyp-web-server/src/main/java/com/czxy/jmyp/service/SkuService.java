package com.czxy.jmyp.service;

import com.alibaba.fastjson.JSON;
import com.czxy.jmyp.dao.*;
import com.czxy.jmyp.pojo.Sku;
import com.czxy.jmyp.pojo.SkuPhoto;
import com.czxy.jmyp.pojo.Specification;
import com.czxy.jmyp.pojo.Spu;
import com.czxy.jmyp.vo.ESData;
import com.czxy.jmyp.vo.OneSkuResult;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SkuService {
    @Resource
    private SkuMapper skuMapper;
    @Resource
    private SpuMapper spuMapper;
    @Resource
    private SkuPhotoMapper skuPhotoMapper;
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private  SpecificationMapper specificationMapper;

    @Resource
    private SkuCommentMapper skuCommentMapper;


    /**
     * @return
     */
    public List<ESData> findESData(){

        /**1 查询所有详情sku*/
        List<Sku> skulist = skuMapper.findAllSkus();

        /**2 将SKU 转换成 ESData*/
        List<ESData> esDataList = new ArrayList<>();

        for (Sku sku:skulist){
            ESData esData = new ESData();

            System.out.println(sku.getSkuName()+"   " + sku.getSpecInfoIdTxt() + "   " +sku.getSpu().getBrand().getBrandName());
            // id
            esData.setId(sku.getId());
            // logo
            esData.setLogo(sku.getImages());
            // sku_name
            esData.setSkuName(sku.getSkuName());
            // all  “华为xx {"机身颜色":"白色","内存":"3GB","机身存储":"16GB"} 荣耀 ”
            esData.setAll(sku.getSkuName()+"   " + sku.getSpecInfoIdTxt() + "   " +sku.getSpu().getBrand().getBrandName());
            // on_sale_time
            esData.setOnSaleTime(sku.getSpu().getOnSaleTime());
            // brand_id
            esData.setBrandId(sku.getSpu().getBrandId());
            // cat_id
            esData.setCatId(sku.getSpu().getCat3Id());
            //  Map<String, Object> specs;// 可搜索的规格参数，key是参数名，值是参数值
            Map specs = JSON.parseObject(sku.getSpecInfoIdTxt(), Map.class);
            esData.setSpecs(specs);
            // price 价格
            esData.setPrice(sku.getPrice());
            // spu_name
            esData.setSpuName(sku.getSpu().getSpuName());
            // stock 库存
            esData.setStock(sku.getStock());
            // description
            esData.setDescription(sku.getSpu().getDescription());
            // packages;//规格与包装
            esData.setPackages(sku.getSpu().getPackages());
            // aftersale;//售后保障
            esData.setAftersale(sku.getSpu().getAftersale());
            // midlogo;
            esData.setMidlogo(sku.getSpu().getLogo());
            // comment_count; 评价数
            Integer comment_count = skuCommentMapper.findNumBySkuId(sku.getId());
            esData.setCommentCount(comment_count);

            //销售量
            esData.setSellerCount(10);

            esDataList.add(esData);
        }
        return esDataList;
    }

    /**
     * 查询商品详情
     * @param skuid 商品id
     * @return
     */
    public OneSkuResult findSkuById(Integer skuid){
        OneSkuResult skuResult = new OneSkuResult();
        // 1 查找sku基本信息
        Sku sku = skuMapper.selectByPrimaryKey(skuid);
        // 2 根据sku查找spu信息
        Spu spu = spuMapper.findSpuById(sku.getSpuId());
        // 3 赋值
        // skuid;
        skuResult.setSkuid(sku.getId());
        // spuid;
        skuResult.setSpuid(sku.getSpuId());
        // 商品名称
        skuResult.setGoodsName(sku.getSkuName());
        // 价格
        skuResult.setPrice(sku.getPrice());
        // 上架时间
        skuResult.setOnSaleDate(spu.getOnSaleTime());
        // 评价数
        Integer comment_count = skuCommentMapper.findNumBySkuId(sku.getId());
        skuResult.setCommentCount(comment_count);
        // 评论级别
        skuResult.setCommentLevel(skuCommentMapper.findAvgStarBySkuId(sku.getId()));
        // 一级分类
        skuResult.setCat1Info(categoryMapper.selectByPrimaryKey(spu.getCat1Id()));
        // 二级分类
        skuResult.setCat2Info(categoryMapper.selectByPrimaryKey(spu.getCat2Id()));
        // 三级分类
        skuResult.setCat3Info(categoryMapper.selectByPrimaryKey(spu.getCat3Id()));
        // 第一张图片
        Map<String,String> logo = new HashedMap();
        logo.put("smlogo",spu.getLogo());
        logo.put("biglogo",spu.getLogo());
        logo.put("xbiglogo",spu.getLogo());
        skuResult.setLogo(logo);
        // 通过skuId查询对应的所有的图片
        List<SkuPhoto> skuPhotoList = skuPhotoMapper.findSkuPhotoBySkuId(sku.getId());
        List<Map> photos = new ArrayList<>();
        for(SkuPhoto sp:skuPhotoList){
            Map<String,String> map = new HashedMap();
            map.put("smimg",sp.getUrl());
            map.put("bigimg",sp.getUrl());
            map.put("xbigimg",sp.getUrl());
            photos.add(map);
        }
        skuResult.setPhtotos(photos);

        // description;
        skuResult.setDescription(spu.getDescription());
        // aftersale;
        skuResult.setAftersale(spu.getAftersale());
        // stock;
        skuResult.setStock(sku.getStock());
        // List<SpecResult> spec_list; 根据分类查找规格和规格选项
        List<Specification> spec_list = specificationMapper.findSpecificationByCategoryId(spu.getCat3Id());
        skuResult.setSpecList(spec_list);
        // //id_list:'规格ID:选项ID|规格ID:选项ID|...',
        //  //id_txt:'规格名称:选项名称|规格名称:选项名称|...'
        // Map<String, String> spec_info;
        Map<String,String> spec_info = new HashMap<>();
        spec_info.put("id_list",sku.getSpecInfoIdList());
        spec_info.put("id_txt",sku.getSpecInfoIdTxt());
        skuResult.setSpecInfo(spec_info);
        // List<Map<String, String>> sku_list;
        List<Sku> skuBySpuIdList = skuMapper.findSkuBySpuId(spu.getId());
        List<Map<String, String>> sku_list = new ArrayList<>();
        for(Sku s:skuBySpuIdList){
            Map<String,String> map = new HashMap<>();
            map.put("skuid",s.getId().toString());
            map.put("id_list",sku.getSpecInfoIdList());
            sku_list.add(map);
        }
        skuResult.setSkuList(sku_list);
        // 返回结果
        return skuResult;
    }


    /**
     * 更新商品库存数量
     * @param skuid 商品id
     * @param count 商品数量
     */
    public void updateSkuNum(Integer skuid, Integer count) {
        Sku sku = skuMapper.selectByPrimaryKey(skuid);
        sku.setStock( sku.getStock() - count);
        skuMapper.updateByPrimaryKey( sku );
    }



}
