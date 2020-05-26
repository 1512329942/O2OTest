package testMybatis; /**
 * @Author: Qi Weidong
 * @Description:
 * @Date: Create in 12:40 2020/5/26
 * @Modified by:
 */

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Classname: BaseTest
 * @Description: 配置spring和junit整合，junit启动时加载ioc容器
 * @Author: Qi weidong
 * @Date: 2020/5/26 12:40
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit D spring配置文件的位置
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class BaseTest {

}
