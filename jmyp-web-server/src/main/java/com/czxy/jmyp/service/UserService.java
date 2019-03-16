package com.czxy.jmyp.service;

import com.czxy.jmyp.dao.UserMapper;
import com.czxy.jmyp.pojo.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

@Service
@Transactional
public class UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 用户注册
     */
    public  void saveUser(User user){
        userMapper.insert(user);
    }

    /**
     * 登录
     */

    public User findByMobile(String mobile){
        //1 拼凑条件
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("mobile" , mobile );
        //2 查询
        return this.userMapper.selectOneByExample( example );
    }

}
