package bupt.embemc.operators;

import bupt.embemc.utils.DbOperator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Iterator;

@Slf4j
public class MessageProcess1 implements Runnable {
    private ArrayDeque<String> dq;
    private DbOperator dbOperator;
    public MessageProcess1(ArrayDeque<String> dq,DbOperator dbOperator){
        this.dq=dq;
        this.dbOperator =dbOperator;
    }
    @Override
    public void run() {

        while(true) {
            if (dq.isEmpty()) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException interruptedException) {
                    log.debug(interruptedException.toString());
                }

            } else {
                try {
                    String re = dq.poll();
                    JSONObject jsonObject = JSON.parseObject(re);


                    HashMap<String, String> map = new HashMap();
                    Iterator<String> iterator = jsonObject.keySet().iterator();
                    while (iterator.hasNext()) {
                        String key = iterator.next();
                        String value = jsonObject.getString(key);
                        map.put(key, value);
                    }
                    HashMap<String, String> logMap = new HashMap<>(map);
                    logMap.remove("content");
                    MessageHandleOperator messageHandleOperator = new MessageHandleOperator(map, dbOperator);
                    messageHandleOperator.proprecess();
                    messageHandleOperator.run();
                    System.out.println(1);
                } catch (Exception e) {
                    log.info(e.getMessage());
                }
            }
        }
    }
}
