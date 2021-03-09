package com.douyuehan.doubao.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.douyuehan.doubao.common.api.ApiErrorCode;
import com.douyuehan.doubao.common.api.ApiResult;
import com.douyuehan.doubao.common.api.IErrorCode;
import com.douyuehan.doubao.model.entity.BmsBillboard;
import com.douyuehan.doubao.service.IBmsBillboardService;
import com.douyuehan.doubao.utils.BaiduSmsComponent;
import com.douyuehan.doubao.utils.NumberUtils;
import com.douyuehan.doubao.utils.PhoneFormatCheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/billboard")
public class BmsBillboardController extends BaseController {

    @Resource
    private IBmsBillboardService bmsBillboardService;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/show")
    public ApiResult<BmsBillboard> getNotices(){
        //select * from bms_billboard where show = true;
        List<BmsBillboard> list = bmsBillboardService.list(new
                LambdaQueryWrapper<BmsBillboard>().eq(BmsBillboard::isShow,true));
        return ApiResult.success(list.get(list.size()- 1));
    }

    /**
     * 发送验证码
     * @param cusPhone
     * @return
     */
    @RequestMapping(value = "/sendCode", method = RequestMethod.GET)
    public ApiResult sendCode(String cusPhone) {
        try {
            if(!PhoneFormatCheckUtils.isChinaPhoneLegal(cusPhone)){
                //参数是接口情况，传接口实现类
                return ApiResult.failed(ApiErrorCode.VALIDATE_FAILED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String code = NumberUtils.generateRandomCode(4);
        //2分钟后清除验证码
        redisTemplate.opsForValue().set(cusPhone, code, Long.valueOf(120), TimeUnit.SECONDS);
        String result = BaiduSmsComponent.send(cusPhone, code);
        if (result.equals("fail")) {
            // 如果发送失败就重试
            BaiduSmsComponent.send(cusPhone,code);
        }
        return ApiResult.success(code);
    }

    /**
     * 验证码校验成功后登录
     */
    @ResponseBody
    @PostMapping(value = "/login")
    public ApiResult<BmsBillboard> login(String cusPhone, String code){
        BmsBillboard userInfo = new BmsBillboard();
        String checkCode = String.valueOf(redisTemplate.opsForValue().get(cusPhone));
        if(StringUtils.isEmpty(checkCode) || !checkCode.equals(code)) return ApiResult.failed("验证码错误,请重新输入");
        //验证成功就删除验证码
        //redisTemplate.delete(cusPhone);
        return ApiResult.success(userInfo);
    }

}
