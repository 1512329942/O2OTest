package com.se.dao;

import com.se.domain.Area;

import java.util.List;

/**
 * @Author: Qi Weidong
 * @Description:
 * @Date: Create in 12:37 2020/5/26
 * @Modified by:
 */
public interface AreaDao {
    /**
     * 列出区域列表
     * @return
     */
    List<Area> queryArea();
}
