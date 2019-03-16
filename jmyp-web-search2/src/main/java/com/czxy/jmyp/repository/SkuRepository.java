package com.czxy.jmyp.repository;

import com.czxy.jmyp.vo.SearchSku;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SkuRepository extends ElasticsearchRepository<SearchSku , Long > {
}
