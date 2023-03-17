package bupt.embemc.utils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.Map;


@Slf4j
@Component
public class PreparaedStatementUpdate {

    private Connection connection = null;
    private PreparedStatement preparedStatement = null;

    public void connect(boolean transactionOff, int isolationLevel) throws SQLException, IOException, ClassNotFoundException {

        try {
            connection = JDBCUtils.getConnection();
            connection.setAutoCommit(transactionOff);
            if(!transactionOff){
                switch (isolationLevel){
                    case 0:
                        connection.setTransactionIsolation(connection.TRANSACTION_NONE);
                    case 1:
                        connection.setTransactionIsolation(connection.TRANSACTION_READ_UNCOMMITTED);
                        break;
                    case 2:
                        connection.setTransactionIsolation(connection.TRANSACTION_READ_COMMITTED);
                        break;
                    case 3:
                        connection.setTransactionIsolation(connection.TRANSACTION_REPEATABLE_READ);
                        break;
                    case 4:
                        connection.setTransactionIsolation(connection.TRANSACTION_SERIALIZABLE);
                        break;
                    default:
                        break;
                }
            }

//            log.info("Mysql connection succeeded!");
        } catch (Throwable t) {
            log.error("Mysql connection failed!");
            throw (t);
        }


    }
    public void rollback() throws SQLException {
        connection.rollback();
    }
    public void commit() throws SQLException {

        connection.commit();




    }
    public void update(String sql, Object ...args){//sql中占位符的个数与args长度相同
        //String sql = "insert into customers(name,email,birth)values(?,?,?)";sql的语法
        try {
            preparedStatement = connection.prepareStatement(sql);
            for(int i = 0;i < args.length;i++){
                preparedStatement.setObject(i + 1,args[i]);
                //preparedStatement自动将？填充
            }
            preparedStatement.execute();
//            preparedStatement.close();
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
    }
    public int update(String sql) throws SQLException {//sql中占位符的个数与args长度相同
        //String sql = "insert into customers(name,email,birth)values(?,?,?)";sql的语法
//        log.info(sql);
        preparedStatement = connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.execute();

        ResultSet rs = preparedStatement.getGeneratedKeys();
        if(rs.next()){
            return rs.getInt(1);
        }
        else{
            return -1;
        }

    }
    public void createProcess(String ...sqls) throws SQLException {
        Statement statement = connection.createStatement();
        for(String sql : sqls){
//            log.info(sql);
            statement.execute(sql);
        }

        statement.close();
    }
    public ResultSet query(String sql){
        try {
            preparedStatement = connection.prepareStatement(sql);
//            log.info(sql);
            ResultSet s = preparedStatement.executeQuery();
//            System.out.println(s);
            return s;

        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return null;
    }

    public void disconnect(){
        JDBCUtils.closeResource(connection, preparedStatement);
    }
}

