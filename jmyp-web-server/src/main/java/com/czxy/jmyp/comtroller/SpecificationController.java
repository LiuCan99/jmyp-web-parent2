package com.czxy.jmyp.comtroller;

import com.czxy.jmyp.pojo.Specification;
import com.czxy.jmyp.service.SpecificationService;
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
public class SpecificationController {
    @Resource
    private SpecificationService specificationService;

    @GetMapping("/specifications/{catid}")
    public ResponseEntity<BaseResult> findSpecificationsByCatid(@PathVariable("catid")Integer catid){

        // 根据类别查找规格
        List<Specification> specificationList = specificationService.findSpecificationByCategoryId(catid);
        // 组装结果
        BaseResult br = new BaseResult(1, "搜索成功")
                .append("data",specificationList);

        return  ResponseEntity.ok(br);
    }
}
