package bupt.embemc.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Slf4j
@Component
public class DbOperator {
    static Map dataType = new HashMap<String,String>();
    static{
        dataType.put("CHAR(60)","\"");
        dataType.put("VARCHAR(300)","\"");
        dataType.put("VARCHAR(60)","\"");
        dataType.put("CHAR(15)","\"");
        dataType.put("CHAR(30)","\"");
        dataType.put("CHAR(36)","\"");
        dataType.put("VARCHAR(260)","\"");
        dataType.put("CHAR(20)","\"");
        dataType.put("INT", " ");
        dataType.put("BIGINT", " ");
        dataType.put("TINYINT", " ");
        dataType.put("FLOAT", " ");
        dataType.put("float", " ");
        dataType.put("DATE", "\"");
    }

    public void connect(int transacationLevel,PreparaedStatementUpdate ps) throws SQLException, IOException, ClassNotFoundException {
        ps.connect(false,transacationLevel);
    }
    public void commit(PreparaedStatementUpdate ps) throws SQLException {
        ps.commit();
    }
    public void rollback(PreparaedStatementUpdate ps) throws SQLException {
        ps.rollback();
    }
    public void disconnect(PreparaedStatementUpdate ps){
        ps.disconnect();
    }
    /*
    mainBody:
    {
        "CLASS":"CALSSNAME"
        "filed#TYPE":"value"
    }
     */
    public int insert(HashMap<String,String> mainBody,PreparaedStatementUpdate ps) throws SQLException {
       String tableName =  mainBody.get("CLASS");
       mainBody.remove("CLASS");
       StringBuilder stringBuilderF = new StringBuilder("insert into ");
       StringBuilder stringBuilderB = new StringBuilder(" ( ");
       stringBuilderF.append(tableName);
       stringBuilderF.append(" ( ");
       Iterator<String> iterator = mainBody.keySet().iterator();
       while(iterator.hasNext()){
           String filed = iterator.next();
           String[] filedArray= filed.split("#");
           String filedName = filedArray[0];
           String filedType = filedArray[1];
           stringBuilderF.append("`");
           stringBuilderF.append(filedName);
           stringBuilderF.append("`");

           stringBuilderB.append(dataType.get(filedType));
           stringBuilderB.append(mainBody.get(filed));
           stringBuilderB.append(dataType.get(filedType));
           stringBuilderF.append(", ");
           stringBuilderB.append(", ");
       }
        stringBuilderF.append("`");
        stringBuilderF.append("TimeStamp");
        stringBuilderF.append("`");
        stringBuilderB.append("CURRENT_TIMESTAMP()");

       stringBuilderF.append(" ) values ");
       stringBuilderF.append(stringBuilderB.append(");"));
       return ps.update(stringBuilderF.toString());
    }

    public int insert(HashMap<String,String> mainBody,boolean ignoreDuplicate,PreparaedStatementUpdate ps) throws SQLException {
        String tableName =  mainBody.get("CLASS");
        mainBody.remove("CLASS");
        StringBuilder stringBuilderF;
        if(ignoreDuplicate) {
            stringBuilderF = new StringBuilder("insert ignore into ");
        }else{
            stringBuilderF = new StringBuilder("insert into ");
        }
        StringBuilder stringBuilderB = new StringBuilder(" ( ");
        stringBuilderF.append(tableName);
        stringBuilderF.append(" ( ");
        Iterator<String> iterator = mainBody.keySet().iterator();
        while(iterator.hasNext()){
            String filed = iterator.next();
            String[] filedArray= filed.split("#");
            String filedName = filedArray[0];
            String filedType = filedArray[1];
            stringBuilderF.append("`");
            stringBuilderF.append(filedName);
            stringBuilderF.append("`");

            stringBuilderB.append(dataType.get(filedType));
            stringBuilderB.append(mainBody.get(filed));
            stringBuilderB.append(dataType.get(filedType));
            stringBuilderF.append(", ");
            stringBuilderB.append(", ");
        }
        stringBuilderF.append("`");
        stringBuilderF.append("TimeStamp");
        stringBuilderF.append("`");
        stringBuilderB.append("CURRENT_TIMESTAMP()");

        stringBuilderF.append(" ) values ");
        stringBuilderF.append(stringBuilderB.append(");"));
        return ps.update(stringBuilderF.toString());
    }

    public void excuteSql(String sql,PreparaedStatementUpdate ps) throws SQLException {
        ps.update(sql);
    }
    /*
    mainBody:
    {
        "CLASS":"CALSSNAME"
        "CONDITION":"{{"filed#TYPE","=","value"}}"
        "filed#TYPE":"value"
    }
     */
    public int update(HashMap mainBody,PreparaedStatementUpdate ps) throws SQLException {
        String tableName =  (String) mainBody.get("CLASS");
        mainBody.remove("CLASS");
        String[][] condition = (String[][]) mainBody.get("CONDITION");
        mainBody.remove("CONDITION");
        StringBuilder stringBuilderF = new StringBuilder("update ");
        stringBuilderF.append(tableName);
        StringBuilder stringBuilderB = new StringBuilder(" set ");
        Iterator iterator = mainBody.keySet().iterator();
        while(iterator.hasNext()){
            String filed = (String) iterator.next();
            String[] filedArray= filed.split("#");
            String filedName = filedArray[0];
            String filedType = filedArray[1];

            stringBuilderB.append("`");
            stringBuilderB.append(filedName);
            stringBuilderB.append("`=");
            stringBuilderB.append(dataType.get(filedType));
            stringBuilderB.append((String)mainBody.get(filed));
            stringBuilderB.append(dataType.get(filedType));
            stringBuilderB.append(", ");
        }
        stringBuilderB.append(" TimeStamp = ");
        stringBuilderB.append(" CURRENT_TIMESTAMP() ");
        stringBuilderB.append(" where ");
        for(int i = 0;i < condition.length;i++){
            String filed = condition[i][0];
            String[] filedArray= filed.split("#");
            String filedName = filedArray[0];
            String filedType = filedArray[1];
            stringBuilderB.append("`");
            stringBuilderB.append(filedName);
            stringBuilderB.append("`");
            stringBuilderB.append(condition[i][1]);
            stringBuilderB.append(dataType.get(filedType));
            stringBuilderB.append(condition[i][2]);
            stringBuilderB.append(dataType.get(filedType));
            if(i+1 < condition.length){
                stringBuilderB.append(" and ");
            }
        }
        stringBuilderF.append(stringBuilderB.append(";"));
        return ps.update(stringBuilderF.toString());

    }
     /*
    mainBody:
    {
        "CLASS":"CALSSNAME"
        "filed#TYPE":"value"

    }
     */

    public ResultSet select(HashMap<String,String> mainBody,PreparaedStatementUpdate ps){
        String tableName =  mainBody.get("CLASS");
        mainBody.remove("CLASS");
        StringBuilder stringBuilderF = new StringBuilder("select * ");
        StringBuilder stringBuilderB = new StringBuilder(" from ");
        stringBuilderB.append(tableName);

        for (String filed : mainBody.keySet()) {
            String[] filedArray = filed.split("#");
            String filedName = filedArray[0];
            String filedType = filedArray[1];
            stringBuilderB.append(" where ");
            stringBuilderB.append("`");
            stringBuilderB.append(filedName);
            stringBuilderB.append("`=");
            stringBuilderB.append(dataType.get(filedType));
            stringBuilderB.append(mainBody.get(filed));
            stringBuilderB.append(dataType.get(filedType));

        }
        stringBuilderF.append(stringBuilderB.append(";"));
        return ps.query(stringBuilderF.toString());
    }

    public ResultSet select(HashMap<String,String> mainBody,boolean addLock,PreparaedStatementUpdate ps) throws SQLException{
        String tableName =  mainBody.get("CLASS");
        if(addLock){
            ps.update("lock table `"+tableName + "` write;");
        }
        mainBody.remove("CLASS");
        StringBuilder stringBuilderF = new StringBuilder("select * ");
        StringBuilder stringBuilderB = new StringBuilder(" from ");
        stringBuilderB.append(tableName);

        for (String filed : mainBody.keySet()) {
            String[] filedArray = filed.split("#");
            String filedName = filedArray[0];
            String filedType = filedArray[1];
            stringBuilderB.append(" where ");
            stringBuilderB.append("`");
            stringBuilderB.append(filedName);
            stringBuilderB.append("`=");
            stringBuilderB.append(dataType.get(filedType));
            stringBuilderB.append(mainBody.get(filed));
            stringBuilderB.append(dataType.get(filedType));

        }

        stringBuilderF.append(stringBuilderB.append(";"));
        return ps.query(stringBuilderF.toString());
    }
    public static ResultSet select(String table,String property,String condition,PreparaedStatementUpdate ps) throws SQLException{
        StringBuilder stringBuilder = new StringBuilder("select * from ");
        stringBuilder.append(table);
        stringBuilder.append(" where ");
        stringBuilder.append("`");
        stringBuilder.append(property);
        stringBuilder.append("`");
        stringBuilder.append(" = ");
        stringBuilder.append("\"");
        stringBuilder.append(condition);
        stringBuilder.append("\";");
        return ps.query(stringBuilder.toString());
    }
    public static ResultSet select(String table,String target, String property,String condition,PreparaedStatementUpdate ps) throws SQLException{
        StringBuilder stringBuilder = new StringBuilder("select ");
        stringBuilder.append(target);
        stringBuilder.append(" from ");
        stringBuilder.append(table);
        stringBuilder.append(" where ");
        stringBuilder.append(property);
        stringBuilder.append(" = ");
        stringBuilder.append("`");
        stringBuilder.append(condition);
        stringBuilder.append("`;");
        return ps.query(stringBuilder.toString());
    }
    /*
    mainBody:
    {
        "CLASS":"CALSSNAME"
        "CONDITION":"{{"filed#TYPE",">","value"}}"

    }
     */

    public  void delete(HashMap mainBody,PreparaedStatementUpdate ps) throws SQLException {
        String tableName =  (String)mainBody.get("CLASS");
        String[][] condition = (String[][])mainBody.get("CONDITION");
        StringBuilder stringBuilderF = new StringBuilder("delete from ");
        stringBuilderF.append(tableName);
        stringBuilderF.append(" where ");
        for (String[] s:condition
             ) {
            String[] filedArray = s[0].split("#");
            String filedName = filedArray[0];
            String filedType = filedArray[1];
            stringBuilderF.append("`");
            stringBuilderF.append(filedName);
            stringBuilderF.append("`");
            stringBuilderF.append(s[1]);
//            Todo:delete中的类型问题
//            stringBuilderF.append(dataType.get(filedType));
            stringBuilderF.append(s[2]);
//            stringBuilderF.append(dataType.get(filedType));
        }
        stringBuilderF.append(";");

        ps.update(stringBuilderF.toString());


    }

    /*
    mainBody:
    {
        "CLASS":"CALSSNAME"
        "PRIMARYKEY":"KEYNAME"
        "CLASSCOMMENT":"COMMENT"
        "KEYNAME":"TYPE ACTION COMMENT='something'"
        "filed":"TYPE ACTION COMMENT='something'"

    }
     */
    public  void create(HashMap<String,String> mainBody,PreparaedStatementUpdate ps) throws SQLException {
        String tableName =  mainBody.get("CLASS");
        mainBody.remove("CLASS");
        String primaryKey = mainBody.get("PRIMARYKEY");
        mainBody.remove("PRIMARYKEY");
        String classComment = mainBody.get("CLASSCOMMENT");
        mainBody.remove("CLASSCOMMENT");
        String primaryKeyType = mainBody.get(primaryKey);
        mainBody.remove(primaryKey);
        StringBuilder stringBuilderF = new StringBuilder("create table if not exists ");
        stringBuilderF.append("`");
        stringBuilderF.append(tableName);
        stringBuilderF.append("` (");
        stringBuilderF.append("`");
        stringBuilderF.append(primaryKey);
        stringBuilderF.append("` ");
        stringBuilderF.append(primaryKeyType);
        stringBuilderF.append(", ");



        Iterator<String> iterator = mainBody.keySet().iterator();
        while(iterator.hasNext()){
            String filedName = iterator.next();
            String filedType = mainBody.get(filedName);
            stringBuilderF.append("`");
            stringBuilderF.append(filedName);
            stringBuilderF.append("` ");
            stringBuilderF.append(filedType);
            stringBuilderF.append(", ");


        }
        stringBuilderF.append("`");
        stringBuilderF.append("TimeStamp");
        stringBuilderF.append("` ");
        stringBuilderF.append("DATETIME COMMENT 'Time when data was last updated'");
        stringBuilderF.append(", ");
        stringBuilderF.append("primary key (");
        stringBuilderF.append("`");
        stringBuilderF.append(primaryKey);
        stringBuilderF.append("` ),");
        stringBuilderF.append("index timeIndex(TimeStamp))");
        stringBuilderF.append("ENGINE=InnoDB DEFAULT CHARSET=utf8 ");
        stringBuilderF.append("COMMENT=");
        stringBuilderF.append("'");
        stringBuilderF.append(classComment);
        stringBuilderF.append("';");

        ps.update(stringBuilderF.toString());
    }
    public void createProcesses(PreparaedStatementUpdate ps,String ...sqls) throws SQLException {
        ps.createProcess(sqls);
    }
}
