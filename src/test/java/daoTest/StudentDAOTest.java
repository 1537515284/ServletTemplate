package daoTest;

import entity.Student;
import org.junit.jupiter.api.Test;

import java.util.List;

public class StudentDAOTest {

    // 查询多行记录
    @Test
    public void testQueryMulti(){
        StudentDAO studentDAO = new StudentDAO();
        String sql = "select * from student where gender = ?";
        List<Student> students = studentDAO.queryMulti(sql, Student.class, "男");
        System.out.println("===查询结果===");
        for (Student student : students) {
            System.out.println(student);
        }
    }

    // 查询单行记录
    @Test
    public void testQuerySingle(){
        StudentDAO studentDAO = new StudentDAO();
        String sql = "select * from student where id = ?";
        Student student = studentDAO.querySingle(sql, Student.class, 2);
        System.out.println("===查询单行记录===");
        System.out.println(student);
    }

    // 查询单行单列
    @Test
    public void testQueryScalar(){
        StudentDAO studentDAO = new StudentDAO();
        String sql = "select name from student where id = ?";
        Object name = studentDAO.queryScalar(sql, 2);
        System.out.println("===查询单行单列值===");
        System.out.println(name);
    }

    // dml操作  insert,update,delete
    @Test
    public void testUpdate(){
        StudentDAO studentDAO = new StudentDAO();
        String sql = "insert into student(name,gender,phone) values(?,?,?)";
        int update = studentDAO.update(sql, "小当家", "男", "1537515284");
        System.out.println("受影响行数:"+update);
    }
}
