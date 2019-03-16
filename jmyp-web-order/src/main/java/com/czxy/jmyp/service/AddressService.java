package com.czxy.jmyp.service;

import com.czxy.jmyp.mapper.AddressMapper;
import com.czxy.jmyp.pojo.Address;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class AddressService {

    @Resource
    private AddressMapper addressMapper;

    /**
     * 根据用户id查询对应的地址
     * @param userId  用户id
     * @return
     */
    public List<Address> findAllByUserId(Long userId) {
        Example example = new Example(Address.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);

        return this.addressMapper.selectByExample(example);
    }
}

