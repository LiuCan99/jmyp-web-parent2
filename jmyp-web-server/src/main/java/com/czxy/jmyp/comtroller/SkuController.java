package com.czxy.jmyp.comtroller;

import com.czxy.jmyp.service.SkuService;
import com.czxy.jmyp.vo.BaseResult;
import com.czxy.jmyp.vo.ESData;
import com.czxy.jmyp.vo.OneSkuResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping
public class SkuController {

    @Autowired
    private SkuService skuService;


    @GetMapping("/esData")
    public ResponseEntity<List<ESData>> findESData(){

        List<ESData> esData = skuService.findESData();
        return ResponseEntity.ok(esData);
    }

    /**
     * 查询商品详情
     * @param skuid
     * @return
     */
    @GetMapping("/goods/{skuid}")
    public ResponseEntity<Object> findSkuById(@PathVariable("skuid") Integer skuid){
        OneSkuResult sku = skuService.findSkuById(skuid);
        return ResponseEntity.ok(sku);

    }

    /**
     * 更新商品库存
     * @param skuid
     * @param count
     * @return
     */
    @PutMapping("/good/{skuid}")
    public ResponseEntity<BaseResult> updateSkuNum(@PathVariable("skuid") Integer skuid , @RequestParam("count") Integer count){
        skuService.updateSkuNum(skuid , count);
        return ResponseEntity.ok( new BaseResult(0,"成功"));
    }



}

