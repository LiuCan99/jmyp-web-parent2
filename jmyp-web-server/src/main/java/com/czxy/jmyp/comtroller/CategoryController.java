package com.czxy.jmyp.comtroller;

import com.czxy.jmyp.pojo.Category;
import com.czxy.jmyp.service.CategoryService;
import com.czxy.jmyp.vo.BaseResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping("/categorys")
    public ResponseEntity<BaseResult> findAll(){
        List<Category> list=categoryService.findAll();
        return  ResponseEntity.ok(new BaseResult(0,"成功").append("data",list));
    }
}
