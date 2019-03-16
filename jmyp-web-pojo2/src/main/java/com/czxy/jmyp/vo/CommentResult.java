package com.czxy.jmyp.vo;


import com.czxy.jmyp.pojo.Impression;
import com.czxy.jmyp.pojo.SkuComment;
import lombok.Data;
import java.util.List;
import java.util.Map;
@Data
public class CommentResult {
    private List<Impression> impressions;
    private Map<String,Object> ratio;
    private Integer comment_count;
    private List<SkuComment> comments;
}
