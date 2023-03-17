package bupt.embemc.receiver;

import bupt.embemc.operators.MessageHandleOperator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bupt_embemc_lab.EEGPlatformCommunicationModule4j.implement.KafkaConsumerImpl;
import com.bupt_embemc_lab.EEGPlatformCommunicationModule4j.implement.KafkaTopicOperatorImpl;
import com.bupt_embemc_lab.EEGPlatformCommunicationModule4j.interfaces.CommunicationConsumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.apache.commons.lang3.ArrayUtils;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Component
public class MessageReceiver implements Runnable{

    @Value("${MessageReceiver-configPath}")
    String configPath;

    @Value(("${MessageReceiver-topic}"))
    String topic;

    private int i = 0;

    public static ArrayDeque<String> dq;


    int headLength = 8;
    volatile boolean receiveFlag = true;

    public void init() {

        KafkaTopicOperatorImpl kafkaTopicOperator = new KafkaTopicOperatorImpl();
        kafkaTopicOperator.initial(configPath);
        kafkaTopicOperator.topicCreate(topic);
        dq = new ArrayDeque<String>();


    }



    public void receive() throws IOException, SQLException, InterruptedException {
        CommunicationConsumer consumer = new KafkaConsumerImpl();
        consumer.initial(configPath, 50);
        // TODO: 2022/4/13  无配置文件异常 或未连接网络异常，直接退出程序
        consumer.subscribe(topic);
        while (receiveFlag){
            Integer n = dq.size();
//            log.info(n.toString());
            try {
                byte[] receiveMessage = ArrayUtils.toPrimitive(consumer.receive());
//            System.out.println(receiveFlag);
//            System.out.println(receiveMessage);
                if (receiveMessage.length < headLength * 8) {
//                log.info("ignore command message: " + new String(receiveMessage,
//                        "UTF8"));
//                    Thread.sleep(1);
                } else {
                    try {
//                    log.info("biaoji"+String.valueOf(++i));
                        String re = new String((byte[]) ArrayUtils.toPrimitive(receiveMessage), "UTF8");
                        if (dq.size() <= 300) {
                            dq.addFirst(re);
                        }
                        else{
                            while(true){
//                                Thread.sleep(1);
                                if (dq.size() <= 300) {
                                    dq.addFirst(re);
                                    break;
                                }
                            }
                        }


//                    JSONObject jsonObject = JSON.parseObject(re);
//
//
//                    HashMap<String,String> map = new HashMap();
//                    Iterator<String> iterator = jsonObject.keySet().iterator();
//                    while(iterator.hasNext()){
//                        String key = iterator.next();
//                        String value = jsonObject.getString(key);
//                        map.put(key,value);
//                    }
//                    HashMap<String,String> logMap = new HashMap<>(map);
//                    logMap.remove("content");
//                    log.info(logMap.toString());
//                    MessageHandleOperator messageHandleOperator = new MessageHandleOperator(map);
//                    messageHandleOperator.proprecess();
//                    threadPoolExecutor.execute(messageHandleOperator);

//                        messageOperator.parse_map(map);
                    } catch (Exception e) {
                        log.error("Message storage failed!\n" + e.toString());
//                        e.printStackTrace();
                    }
                }
            }catch (Throwable e){

                log.error( e.toString());
            }
        }
    }

    @Override
    public void run() {
        log.info("start message receiver");
        try {
            receive();
        }
        catch (UnsupportedEncodingException uee){
            log.error("message receiver run error", uee);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        receiveFlag = false;
    }
}