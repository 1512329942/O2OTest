package TestSpring;
/**
 * @Author: Qi Weidong
 * @Description:
 * @Date: Create in 12:51 2020/5/26
 * @Modified by:
 */

import com.se.domain.Area;
import com.se.service.AreaService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Classname: TestArea
 * @Description: TODO
 * @Author: Qi weidong
 * @Date: 2020/5/26 12:51
 */
public class TestArea extends BaseTest {
    @Autowired
    private AreaService areaService;
    @Test
    public void test(){
        List<Area> areaList=areaService.getAreaList();
        for(Area a:areaList){
            System.out.println(a.toString());
        }
    }
}
