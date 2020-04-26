package cn.strong.leke;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author shaowenxing@cnstrong.cn
 * @since 18:12
 */
@SpringBootApplication()
public class SynchronizationApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(SynchronizationApplication.class, args);
//        new ShardingTest().s(args);
    }

    public void s(String[] args) throws Exception {
        System.out.println(System.getProperty("user.dir"));
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("homework-bean.xml");
//        dataSource.save("this is test");
//          dataSource.testUpdateDtl(1582968639420L,10002L);
    }
}
