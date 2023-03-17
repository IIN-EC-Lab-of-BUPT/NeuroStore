package bupt.embemc.operators;

import bupt.embemc.receiver.MessageReceiver;
import bupt.embemc.utils.DbOperator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Component
public class MessageProcess implements Runnable{
    private static ArrayDeque<String> dq;
    private ThreadPoolExecutor threadPoolExecutor;
    private DbOperator dbOperator;
    private int i;

    public void init(ThreadPoolExecutor threadPoolExecutor,DbOperator dbOperator){
        this.dbOperator = dbOperator;
        this.threadPoolExecutor = threadPoolExecutor;
        dq = MessageReceiver.dq;
    }


    @Override
    public void run() {
        int n = 120;
        for(i = 0;i < n;i++){
            MessageProcess1 messageProcess1 = new MessageProcess1(dq,dbOperator);
            threadPoolExecutor.execute(messageProcess1);

        }

//        while(true) {
//            if (dq.isEmpty()) {
//                try {
//                    Thread.sleep(1);
//                } catch (InterruptedException interruptedException) {
//                    log.debug(interruptedException.toString());
//                }
//
//            } else {
//                try {
//                    String re = dq.poll();
//                    JSONObject jsonObject = JSON.parseObject(re);
//
//
//                    HashMap<String, String> map = new HashMap();
//                    Iterator<String> iterator = jsonObject.keySet().iterator();
//                    while (iterator.hasNext()) {
//                        String key = iterator.next();
//                        String value = jsonObject.getString(key);
//                        map.put(key, value);
//                    }
//                    HashMap<String, String> logMap = new HashMap<>(map);
//                    logMap.remove("content");
//
//                    MessageHandleOperator messageHandleOperator = new MessageHandleOperator(map, dbOperator);
//                    messageHandleOperator.proprecess();
//                    messageHandleOperator.run();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }


}

