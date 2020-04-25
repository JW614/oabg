package com.dzxy.util;

import com.dzxy.pojo.Dept;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * 连接数据库的工具类
 *
 * @author Administrator
 */
public class DBUtil {
    private static String driver = "oracle.jdbc.driver.OracleDriver";
    private static String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private static String username = "system";
    private static String password = "jiangwen";

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
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
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
    //父类可以调用 子类继承过的父类方法
    public static void closeAll(ResultSet rs, Statement ps, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            if (ps != null) {
                ps.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 封装DML
    public static int executeDML(String sql, Object... objs) {
        // 创建连接对象
        Connection conn = getConnection();
        // 创建sql命令对象
        PreparedStatement ps = DBUtil.getPreparedStatement(sql, conn);
        // 给占位符赋值
        try {
            conn.setAutoCommit(false);
            for (int i = 0; i < objs.length; i++) {
                ps.setObject(i + 1, objs[i]);
            }
            int i = ps.executeUpdate();
            conn.commit();
            return i;
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        } finally {
            // 关闭资源
            DBUtil.closeAll(null, ps, conn);
        }
        // 返回结果
        return -1;
    }

    //返回对象

    /**
     * sql
     * <p>
     * 参数可变  Object ... obj
     * <p>
     * 向对象存放数据的时候，对象的属性未知，属性类型未知
     * Class 反射 可以在不知道类的属性的个数和属性类型的时候，去操作这个属性
     *
     * @param
     * @param
     * @return
     */
    @SuppressWarnings("unused")
    public static List getFieldsRows(String sql, Class clazz, Object... args) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List list = new ArrayList();
        //1 创建连接
        Connection conn = DBUtil.getConnection();
        //2 获取 预处理块对象  preparestatement
        try {
            ps = conn.prepareStatement(sql);
            //3 绑定参数   ??????

            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    Object arg = args[i];
                    ps.setObject(i + 1, arg);
                }
            }


            //4 执行sql语句  //5 获取结果集
            rs = ps.executeQuery();

            //6 遍历结果集放到  user对象中
            while (rs.next()) {
					/* int id = rs.getInt("id");
					 String uname2 = rs.getString("uname");
					 String pwd2 = rs.getString("pwd");
					 //将数据放到对象中
					 user= new T_user(id, uname2, pwd2);
					 user.setId(id);
					 user.setUname("uname值");
					 user.setId(id);
					 *
					 *
					 *
					 */

                //创建一个对象
                Object obj = clazz.newInstance();

                // 类的属性数量未知   ，属性类型未知   ，属性值未知
                //7 获取类的属性的数量  --通过 数据库表获取
                ResultSetMetaData metaData = rs.getMetaData();
                //返回列的数量 -----》 表的列的数量  和类的数量是一致的
                int columnCount = metaData.getColumnCount();
                for (int i = 0; i < columnCount; i++) {
                    //获取 属性 的名称  ---规定 属性名称和 表的字段名称一致  id
                    String columnName = metaData.getColumnName(i + 1);
                    //怎么获取列的值 ---属性的值
                    Object objValue = rs.getObject(columnName);
                    // System.out.println(objValue);


                    //获取  set方法的参数的类型
                    Field field = clazz.getDeclaredField(columnName.toLowerCase());
                    Class<?> type = field.getType();
                    String typeName = type.getName();

                    //想办法调用  user.setId(objValue);方法
                    //现在已经有了  属性的名称columnName   ----setColumnName
                    //数据库  字段   名称  必须全部小写 ，否则这个不适应
                    String setMethod = "set" + columnName.substring(0, 1).toUpperCase()
                            + columnName.substring(1).toLowerCase();
                    //操作 set方法  将值  objValue放到一个对象中
                    Method method = clazz.getDeclaredMethod(setMethod, type);


//						 * @see     java.lang.Byte
//						 * @see     java.lang.Double
//						 * @see     java.lang.Float
//						 * @see     java.lang.Integer
//						 * @see     java.lang.Long
//						 * @see     java.lang.Short
                    //oracle number 需要判断属性的类型
                    if (objValue != null && !"".equals(objValue)) {
                        if ("int".equals(typeName) || "java.lang.Integer".equals(typeName)) {
                            int intValue = Integer.valueOf(objValue.toString());
                            method.invoke(obj, intValue);

                        } else if ("byte".equals(typeName) || "java.lang.Byte".equals(typeName)) {
                            byte byteValue = Byte.valueOf(objValue.toString());
                            method.invoke(obj, byteValue);

                        } else if ("double".equals(typeName) || "java.lang.Double".equals(typeName)) {
                            double doubleValue = Double.valueOf(objValue.toString());
                            method.invoke(obj, doubleValue);

                        } else if ("float".equals(typeName) || "java.lang.Float".equals(typeName)) {
                            float floatValue = Float.valueOf(objValue.toString());
                            method.invoke(obj, floatValue);

                        } else if ("long".equals(typeName) || "java.lang.Long".equals(typeName)) {
                            long longValue = Long.valueOf(objValue.toString());
                            method.invoke(obj, longValue);


                        } else if ("short".equals(typeName) || "java.lang.Short".equals(typeName)) {
                            short shortValue = Short.valueOf(objValue.toString());
                            method.invoke(obj, shortValue);

                        } else if ("date".equals(typeName) || "java.sql.Date".equals(typeName)) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date utilDate = sdf.parse(objValue.toString());

                            //util date　－－－－＞sql date
                            java.sql.Date date = new java.sql.Date(utilDate.getTime());
                            method.invoke(obj, date);
                        } else if ("java.util.Date".equals(typeName)) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date parse = sdf.parse(objValue.toString());
                            method.invoke(obj, parse);
                        } else {
                            //通过invoke方法调用 对象底层的set方法
                            method.invoke(obj, objValue);
                        }
                    }


                }
                //将数据放到list中
                list.add(obj);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //7 关闭  释放资源  （注意：web 项目必须释放资源 ）
            DBUtil.closeAll(rs, ps, conn);
        }

        return list;
    }

    /**
     * 封装一个通用 添加方法
     * insert into  t_user (uname, pwd ) values ( 'hahaa' , '123' );
     * String sql
     * 参数
     * Class clazz
     */
    public static int excuteObj(String sql, Class clazz, Object... obj) {
        Connection conn = DBUtil.getConnection();
        PreparedStatement ps = null;
        int num = 0;
        try {
            ps = conn.prepareStatement(sql);

            if (obj != null) {
                for (int i = 0; i < obj.length; i++) {
                    //绑定参数
                    ps.setObject(i + 1, obj[i]);
                }

            }
            //执行sql语句
            num = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(null, ps, conn);
        }


        return num;
    }

    /**
     * 用反射进一步封装
     */
    /**
     * 封装一个通用 添加方法
     * insert into  t_user (uname, pwd ) values ( 'hahaa' , '123' );
     * <p>
     * insert into  t_user (uname, pwd ) values ( ? , ? );
     * String sql
     * 参数
     * Class clazz
     */
    public static int excuteObj2(Object obj) {
        PreparedStatement ps = null;
        int num = 0;
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("insert into  ");

        //获取表名称
        Class<? extends Object> clazz = obj.getClass();
        sqlBuilder.append(clazz.getSimpleName() + " ( ");

        Connection conn = DBUtil.getConnection();

        try {

            //拼装 sql   属性拼装  属性的数量和名称  不确定
            //反射获取
            Field[] fields = clazz.getDeclaredFields();

            List<String> fNameList = new ArrayList();
            List<String> wenList = new ArrayList();
            List objList = new ArrayList();

            for (int i = 0; i < fields.length; i++) {

                //属性的对象    属性的名  属性的值  属性的类型
                Field field = fields[i];

                field.setAccessible(true);
                //属性的名称
                String name = field.getName();

                if (i == (fields.length - 1)) {
                    if ("id" != name) {
                        //属性的值
                        Object objValue = field.get(obj);
                        //sqlBuilder.append(name +")" );
                        fNameList.add(name + ")");
                        wenList.add(" ? )");
                        objList.add(objValue);
                    }
                } else {
                    if ("id" != name) {
                        //属性的值
                        Object objValue = field.get(obj);
                        //sqlBuilder.append(name+"," );
                        fNameList.add(name + ",");
                        wenList.add(" ? , ");
                        objList.add(objValue);
                    }
                }


            }

            //	System.out.println(sqlBuilder.toString());
            for (int j = 0; j < fNameList.size(); j++) {
                String fName = fNameList.get(j);

                sqlBuilder.append(fName);
            }
            //拼装  values
            sqlBuilder.append(" values ( ");

            //拼装  ?
            for (int j = 0; j < wenList.size(); j++) {
                String wen = wenList.get(j);
                sqlBuilder.append(wen);
            }


            ps = conn.prepareStatement(sqlBuilder.toString());

            if (objList != null) {
                for (int i = 0; i < objList.size(); i++) {
                    //绑定参数
                    ps.setObject(i + 1, objList.get(i));
                }

            }
            //执行sql语句
            num = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(null, ps, conn);
        }


        return num;
    }


    public static void main(String[] args) {
//		Connection connection = DBUtil.getConnection();
//		System.out.println(connection);
        String sql = "insert into  dept (deptno , deptname , location  values ( 11 , '宿管部' ,'101') ";
        int num = excuteObj(sql, Dept.class);
        System.out.println(num);
    }
}
