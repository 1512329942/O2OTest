package testMybatis;
/**
 * @Author: Qi Weidong
 * @Description:
 * @Date: Create in 12:42 2020/5/26
 * @Modified by:
 */


import com.se.dao.AreaDao;
import com.se.domain.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Classname: TestArea
 * @Description: TODO
 * @Author: Qi weidong
 * @Date: 2020/5/26 12:42
 */
public class AreaTest extends BaseTest  {
    @Autowired
    private AreaDao areaDao;
    @Test
    public void testQueryArea(){
        List<Area> areaList=areaDao.queryArea();
        for(Area a:areaList){
            System.out.println(a);
        }
    }
}
