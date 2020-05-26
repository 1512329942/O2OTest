package com.se.service.impl;
/**
 * @Author: Qi Weidong
 * @Description:
 * @Date: Create in 12:47 2020/5/26
 * @Modified by:
 */

import com.se.dao.AreaDao;
import com.se.domain.Area;
import com.se.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname: AreaServiceImpl
 * @Description: TODO
 * @Author: Qi weidong
 * @Date: 2020/5/26 12:47
 */
@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaDao areaDao;

    @Override
    public List<Area> getAreaList() {
        return areaDao.queryArea();
    }
}
