package bupt.embemc.operators;

import bupt.embemc.utils.DbOperator;
import bupt.embemc.utils.PreparaedStatementUpdate;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@Slf4j
public class DataCleaner implements Runnable{
    private DbOperator dbo;
    private PreparaedStatementUpdate ps;
    private HashMap tempTable;
    boolean Flag = true;
    @Value("${TempNoUUIDDataAliveDays}")
    Integer TempNoUUIDDataAliveDays;
    @Value("${TempUUIDDataAliveDays}")
    Integer TempUUIDDataAliveDays;

    public void init(DbOperator dbo,PreparaedStatementUpdate ps, HashMap tempTable) throws Exception {
        this.dbo = dbo;
        this.tempTable = tempTable;
        this.ps = ps;
        Flag = true;
        if((TempNoUUIDDataAliveDays<=0 &TempNoUUIDDataAliveDays != -1) || (TempUUIDDataAliveDays<=0 &TempUUIDDataAliveDays != -1)){
            Exception exception = new Exception("config error!,TempNoUUIDDataAliveDays and TempUUIDDataAliveDays are int and Greater than zero!!");
            throw exception;
        }

    }
    @SneakyThrows
    @Override
    public void run() {
        int size = tempTable.keySet().size();
        int interval = 1800000/size;
        while(Flag){
            for(String s: (Set<String>) tempTable.keySet()){
                Map<String, String> map = (HashMap<String, String>) tempTable.get(s);
                String primarykey = map.get("PRIMARYKEY");
                HashMap sql = new HashMap();
                sql.put("CLASS",s);
                String[][] condition;
                if(primarykey.equals("UUID")){
                    if(TempUUIDDataAliveDays != -1) {
                        condition = new String[][]{{"TimeStamp#DATE", "<", String.format("SUBDATE(CURRENT_TIMESTAMP(),%d)", TempUUIDDataAliveDays)}};
                        sql.put("CONDITION", condition);
                        dbo.delete(sql,ps);
                    }
                }else{
                    if(TempNoUUIDDataAliveDays != -1){
                        condition = new String[][]{{"TimeStamp#DATE", "<", String.format("SUBDATE(CURRENT_TIMESTAMP(),%d)", TempNoUUIDDataAliveDays)}};
                        sql.put("CONDITION",condition);
                        dbo.delete(sql,ps);
                    }
                }
                dbo.commit(ps);
                log.info("transaction commit!");
                Thread.sleep(interval);
            }
        }
    }

    public void stop(){
        Flag = false;
    }
}
