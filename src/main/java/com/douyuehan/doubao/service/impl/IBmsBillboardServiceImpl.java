package com.douyuehan.doubao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.douyuehan.doubao.mapper.BmsBillboardMapper;
import com.douyuehan.doubao.model.entity.BmsBillboard;
import com.douyuehan.doubao.service.IBmsBillboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class IBmsBillboardServiceImpl extends ServiceImpl<BmsBillboardMapper, BmsBillboard>
implements IBmsBillboardService {

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Override
    public String put(String phone, String phoneCode) {
        redisTemplate.opsForValue().set(phone,phoneCode);
        Object o = redisTemplate.opsForValue().get(phone);
        return String.valueOf(o);
    }

}
