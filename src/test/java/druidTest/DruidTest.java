package druidTest;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class DruidTest {
    @Test
    public void testDruid() throws Exception {
        //1.引入 Druid jar包
        //2.加入 配置文件 druid.properties，拷贝到项目的src目录下
        //3.创建Properties对象，读取配置文件
        InputStream resourceAsStream = DruidTest.class.getClassLoader().getResourceAsStream("druid.properties");

        Properties properties = new Properties();
        properties.load(resourceAsStream);
        //4.创建一个指定参数的数据库连接池
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 500000; i++) {
            Connection connection = dataSource.getConnection();
//            System.out.println("连接成功");
            connection.close();  //在数据库连接池技术种，close不是真的断掉连接，而是把使用的Connection对象放回连接池
        }
        long end = System.currentTimeMillis();
        System.out.println("druid连接池 操作500000 耗时="+(end-start));    //719
    }
}
