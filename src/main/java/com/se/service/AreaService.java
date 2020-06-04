package com.se.service;

import com.se.domain.Area;

import java.util.List;

/**
 * @Author: Qi Weidong
 * @Description:
 * @Date: Create in 12:46 2020/5/26
 * @Modified by:
 */
public interface AreaService {
    /**
     * 获取区域列表
     * @return
     */
    List<Area> getAreaList();
}
