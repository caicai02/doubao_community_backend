package com.douyuehan.doubao.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.douyuehan.doubao.project.mapper.BmsBillboardMapper;
import com.douyuehan.doubao.project.model.entity.BmsBillboard;
import com.douyuehan.doubao.project.service.IBmsBillboardService;
import org.springframework.stereotype.Service;

@Service
public class IBmsBillboardServiceImpl extends ServiceImpl<BmsBillboardMapper, BmsBillboard>
implements IBmsBillboardService {
}
