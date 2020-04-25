package com.dzxy.util;

import com.dzxy.pojo.ExpenseItem;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;


public class DBUtil2 {
    private static String driver = "oracle.jdbc.driver.OracleDriver";
    private static String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private static String username = "system";
    private static String password = "jiangwen";

    //维护 每次线程  ，唯一的connection  ，保证事务是同一个事务
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

    static {
        try {
            // 加载驱动
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 获取Connection对象
    public static Connection getConnection() {
        Connection conn = threadLocal.get();
        try {
            if (conn == null) {
                conn = DriverManager.getConnection(url, username, password);
                threadLocal.set(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    // 封装获取PreparedStatement对象
    public static PreparedStatement getPreparedStatement(String sql,
                                                         Connection conn) {

        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ps;

    }

    // 封装获取Statement对象
    public static Statement getStatement(Connection conn) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stmt;

    }

    // 关闭资源
    public static void closeAll(ResultSet rs, Statement ps, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {

        }
        try {
            if (ps != null) {
                ps.close();
            }

        } catch (SQLException e) {

        }
        try {
            if (conn != null) {
                //清空  connection对象
                threadLocal.set(null);
                conn.close();
            }
        } catch (SQLException e) {

        }
    }

    // 封装DML
    public static int executeDML(String sql, Object... objs) {
        // 创建连接对象
        Connection conn = getConnection();
        // 创建sql命令对象
        PreparedStatement ps = DBUtil2.getPreparedStatement(sql, conn);
        // 给占位符赋值
        try {
            //conn.setAutoCommit(false);
            for (int i = 0; i < objs.length; i++) {
                ps.setObject(i + 1, objs[i]);
            }
            int i = ps.executeUpdate();
            //两张表操作
            //conn.commit();
            return i;
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
                //向 方法调用者去抛出异常  ；  成功 没有异常 ；如果失败  抛出异常
                // 成功  返回1 ；失败返回0 或 -1
                throw new MyException(e1.getMessage());
            }
            e.printStackTrace();
            throw new MyException(e.getMessage());
        } finally {
            // 关闭资源  不能关掉 conn
            DBUtil2.closeAll(null, ps, null);
        }
    }

    public static <T> T executeOne(String sql, T t, Object... objs) throws InstantiationException, IllegalAccessException {
        Object obj = t.getClass().newInstance();
        ArrayList<T> executeQuery = executeQuery(sql, t, objs);
        if (executeQuery != null) {
            if (executeQuery.size() > 0) {
                obj = executeQuery.get(0);
            }
        }
        return (T) obj;
    }


    //封装公共查询方法-=-返回List集合，适用返回结果数据量大
    public static <T> ArrayList<T> executeQuery(String sql, T t, Object... objs) {
        //声明jdbc变量
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<T> list = null;
        try {
            //获取连接对象
            conn = DBUtil2.getConnection();
            //获取sql命令对象
            ps = DBUtil2.getPreparedStatement(sql, conn);
            //给占位符赋值
            if (objs != null) {
                for (int i = 0; i < objs.length; i++) {
                    ps.setObject(i + 1, objs[i]);
                }
            }

            //执行sql命令
            rs = ps.executeQuery();//获取查询结果
            ResultSetMetaData rm = rs.getMetaData();//获取结果集的表结构(结果集字段名和类型和字段数量)
            int count = rm.getColumnCount();//获取结果集列数
            list = new ArrayList<T>();
            //遍历执行结果
            while (rs.next()) {
                //创建实体类对象
                //创建类对象
                Class cla = t.getClass();
                //创建实例化对象
                T obj = (T) cla.newInstance();
                //给实体类属性赋值
                //使用循环进行字段赋值
                for (int i = 0; i < count; i++) {
                    //相当于p.setEmpno(rs.getInt("empno"))
                    //获取方法名
                    //获取字段名
                    String columnName = rm.getColumnName(i + 1);
                    //获取方法名
                    String methodName = "set" + columnName.substring(0, 1).toUpperCase() + columnName.substring(1).toLowerCase();
                    //获取字段类型
                    Field field = t.getClass().getDeclaredField(columnName.toLowerCase());
                    Class<?> type = field.getType();
                    String typeName = type.getName();

                    //获取实体类属性赋值方法对象--set方法    ,方法的参数类型（class）
                    Method m = cla.getDeclaredMethod(methodName, type);

                    Object objValue = rs.getObject(columnName);
                    if (objValue != null) {
                        if ("int".equals(typeName) || "java.lang.Integer".equals(typeName)) {
                            int valueInt = Integer.valueOf(objValue.toString());
                            m.invoke(obj, valueInt);
                        } else if ("double".equals(typeName) || "java.lang.Double".equals(typeName)) {
                            double valueDouble = Double.valueOf(objValue
                                    .toString());
                            m.invoke(obj, valueDouble);
                        } else if ("long".equals(typeName) || "java.lang.Long".equals(typeName)) {
                            long value = Long.valueOf(objValue
                                    .toString());
                            m.invoke(obj, value);
                        } else if ("byte".equals(typeName) || "java.lang.Byte".equals(typeName)) {
                            byte value = Byte.valueOf(objValue
                                    .toString());
                            m.invoke(obj, value);
                        } else if ("float".equals(typeName) || "java.lang.Float".equals(typeName)) {
                            float value = Float.valueOf(objValue
                                    .toString());
                            m.invoke(obj, value);
                        } else if ("short".equals(typeName) || "java.lang.Short".equals(typeName)) {
                            short value = Short.valueOf(objValue
                                    .toString());
                            m.invoke(obj, value);
                        } else if ("java.util.Date".equals(typeName)) {
                            // double valueDouble =
                            // Double.valueOf(objValue.toString());
                            // obj ---> util
                            String dateStr = objValue.toString();
                            SimpleDateFormat sdf;
                            if (dateStr.length() == 10) {
                                sdf = new SimpleDateFormat("yyyy-MM-dd");
                            } else {
                                sdf = new SimpleDateFormat(
                                        "yyyy-MM-dd hh:mm:ss");
                            }

                            Date parse = sdf.parse(dateStr);

                            m.invoke(obj, parse);
                        } else {
                            m.invoke(obj, objValue);
                        }
                    }
                }
                //将对象存储到集合中
                list.add(obj);
            }
            //返回结果
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil2.closeAll(rs, ps, conn);
        }
        return list;
    }

    /**
     * 查询数据总行数
     * @param args
     * @throws InstantiationException
     * @throws IllegalAccessException
     */

    /**
     * 查询数据总行数
     *
     * @param
     */
    public static int getCount(String sql) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        int num = 0;
        // 1 获取连接对象
        Connection conn = DBUtil2.getConnection();

        try {
            // 2 创建预处理块对象
            ps = conn.prepareStatement(sql);
            // 3 执行sql语句 ，返回结果
            rs = ps.executeQuery();
            // 4 遍历结果集  ， 将数据放到对象中， 放到list中
            rs.next();
            num = rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 5 释放资源
            DBUtil2.closeAll(rs, ps, conn);
        }
        return num;
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException, SQLException {
//		Connection connection = getConnection();
//		System.out.println(connection);

//		  Dept executeOne = executeOne("select * from dept where deptno = ?  " , 
//									new Dept(), new Object[]{3 });
//		System.out.println(executeOne);
        Connection connection = null;
        try {
            /**expid 自增不能重复
             * 维护自增的id  序列
             */
            connection = DBUtil2.getConnection();

            connection.setAutoCommit(false);
            DBUtil2.executeDML("insert  into  expense  (expid  , empid , totalamount )"
                    + "  values  ( seq_expense_expid.nextval ,   'fangyl' ,  22.2) ");
            DBUtil2.executeDML("insert into  expenseitem  "
                    + " (itemid , expid ,type  ) values (   seq_expenseitem_itemid.nextval , 3 ,'通信' ) ", new ExpenseItem());

            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            DBUtil2.closeAll(null, null, connection);
        }
    }
}
