package druidTest;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.template.utils.DruidUtil;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DruidUtilTest {

    @Test
    public void testSelect() {
        System.out.println("使用druid方式完成");
        //1.得到连接
        Connection connection = null;
        //2.组织SQL
        String sql = "select * from student";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        //3.创建PreparedStatement对象
        try {
            connection = DruidUtil.getConnection();
            System.out.println(connection.getClass());  // 运行类型class com.alibaba.druid.pool.DruidPooledConnection
            preparedStatement = connection.prepareStatement(sql);
            // 执行,得到结果集
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String gender = resultSet.getString("gender");
                String phone = resultSet.getString("phone");
                System.out.println(id + "\t" + name + "\t" + gender  + "\t" + phone);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            DruidUtil.close(resultSet, preparedStatement, connection);
        }
    }
}
