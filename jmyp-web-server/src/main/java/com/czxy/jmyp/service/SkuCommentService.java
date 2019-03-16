package com.czxy.jmyp.service;


import com.czxy.jmyp.dao.ImpressionMapper;
import com.czxy.jmyp.dao.SkuCommentMapper;
import com.czxy.jmyp.pojo.Impression;
import com.czxy.jmyp.pojo.SkuComment;
import com.czxy.jmyp.vo.CommentResult;
import com.czxy.jmyp.vo.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SkuCommentService {

    @Autowired
    private SkuCommentMapper skuCommentMapper;
    @Autowired
    private ImpressionMapper impressionMapper;

    public CommentResult findComments(Integer spuid, PageRequest pageRequest){
        CommentResult commentResult = new CommentResult();

        //1、 List<Impression> impressions;
        List<Impression> impressionList = impressionMapper.findImpressionsBySpuid(spuid);
        commentResult.setImpressions(impressionList);
        //2、 Map<String,Object> ratio;
        Integer goodCount = skuCommentMapper.findCommentCountByRatio(spuid,0);// 好评
        Integer commonCount = skuCommentMapper.findCommentCountByRatio(spuid,1);// 中评
        Integer badCount = skuCommentMapper.findCommentCountByRatio(spuid,2);// 差评
        Integer totalCount = skuCommentMapper.findNumBySpuId(spuid);//

        Map<String,Object> ratio = new HashMap<>();
        if(totalCount!=0){
            //Integer goods = (goodCount%totalCount)==0? goodCount*100/totalCount :  goodCount*100/totalCount+1;
            ratio.put("goods",String.format("%.1f",goodCount*100.0/totalCount));
           // Integer common = (commonCount%totalCount)==0? commonCount*100/totalCount :  commonCount*100/totalCount+1;
            ratio.put("common",String.format("%.1f",commonCount*100.0/totalCount));
            //Integer bad = (badCount%totalCount)==0? badCount*100/totalCount :  badCount*100/totalCount+1;
            ratio.put("bad",String.format("%.1f",badCount*100.0/totalCount));
        }


        commentResult.setRatio(ratio);

        // Integer comment_count;
        Integer comment_count = skuCommentMapper.findNumBySpuId(spuid);
        commentResult.setComment_count(comment_count);

        // List<SkuComment> comments;
        List<SkuComment> comments = skuCommentMapper.findCommentsBySpuid(spuid);
        commentResult.setComments(comments);

        return commentResult;
    }


}

