package com.czxy.jmyp.comtroller;

import com.czxy.jmyp.pojo.Brand;
import com.czxy.jmyp.service.BrandService;
import com.czxy.jmyp.vo.BaseResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping
public class BrandController {
    @Resource
    private BrandService brandService;

    @GetMapping("/brands/{catid}")
    public ResponseEntity<BaseResult> findBrandByCatid(@PathVariable("catid") Integer catid ){
        List<Brand> list=brandService.findBrandByCatid(catid);

        if(list.size()>0){
            BaseResult baseResult=new BaseResult(1,"成功")
                                  .append("data",list);
            return  ResponseEntity.ok(baseResult);
        }
        BaseResult baseResult=new BaseResult(1,"成功")
                .append("data",null);

        return ResponseEntity.ok(baseResult);
    }
}
