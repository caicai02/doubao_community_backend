package com.douyuehan.doubao.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.douyuehan.doubao.common.api.ApiErrorCode;
import com.douyuehan.doubao.common.api.ApiResult;
import com.douyuehan.doubao.project.model.entity.BmsBillboard;
import com.douyuehan.doubao.project.service.IBmsBillboardService;
import com.douyuehan.doubao.utils.PhoneMessageUtils.BaiduSmsComponent;
import com.douyuehan.doubao.utils.ImageUtils.ImageUtils;
import com.douyuehan.doubao.utils.PhoneMessageUtils.NumberUtils;
import com.douyuehan.doubao.utils.PhoneMessageUtils.PhoneFormatCheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/billboard")
public class BmsBillboardController extends BaseController {

    @Resource
    private IBmsBillboardService bmsBillboardService;

    @Autowired
    private RedisTemplate redisTemplate;

    private String uploadDirPic = "D:\\apache-tomcat-8.5.57\\webapps\\uploads\\";
    // 缩略图默认裁剪尺寸
    private final int DEAFAULT_WIDTH = 500;
    private final int DEAFAULT_HEIGHT = 500;
    // 缩略图后缀
    private final String SUFFIX = "_TN_";
    public byte[] compressionImage(byte[] source) {
        // 碎片图片，最大不超过500K，1280*720
        return ImageUtils.resize(source, DEAFAULT_WIDTH, DEAFAULT_HEIGHT);
    }

    //图片上传
    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public ApiResult upload(HttpServletRequest request, HttpServletResponse response, MultipartFile uploadFile, Integer flag) throws IOException {
        response.setContentType("text/plain; charset=utf-8");
        request.setCharacterEncoding("UTF-8");
        String dirImage=null;
        try {
            // 自定义的文件名称
            String trueFileName = System.currentTimeMillis() + uploadFile.getOriginalFilename();
            //格式化时间戳
            String nowTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date().getTime());

            File dir = new File(uploadDirPic+nowTime);
            //如果文件目录不存在，创建文件目录
            if (!dir.exists()) {
                dir.mkdir();
            }

            byte[] fileByte = uploadFile.getBytes();
            FileImageOutputStream imageOutput = new FileImageOutputStream(new File(uploadDirPic+nowTime, trueFileName));//打开输入流
            imageOutput.write(fileByte, 0, fileByte.length);//将byte写入tomcat某个文件夹
            imageOutput.close();
            //图片所在地址存储到数据库
            dirImage = nowTime+"/"+trueFileName;
            //myParkingService.saveImage(projectId,customerId,dirImage);
            //return RestResult.SUCCESS().put("absolutePath",dirImage);
            return ApiResult.success(dirImage);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ApiResult.failed();
    }

    /**
     * 获取公告栏
     * @return
     */
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
