package com.czxy.jmyp.comtroller;


import com.czxy.jmyp.service.SkuCommentService;
import com.czxy.jmyp.vo.CommentResult;
import com.czxy.jmyp.vo.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class SkuCommentController {
    @Autowired
    private SkuCommentService skuCommentService;


    @GetMapping("/comments/{spuid}")
    public ResponseEntity<Object> findCommentsByPage(@PathVariable("spuid") Integer spuid, PageRequest pageRequest){
        CommentResult comments = skuCommentService.findComments(spuid, pageRequest);
        return ResponseEntity.ok(comments);
    }
}

