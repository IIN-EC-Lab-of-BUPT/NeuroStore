package bupt.embemc.operators;

import bupt.embemc.operators.util.DLinkedNode;
import bupt.embemc.operators.util.FileWriterHandler;
import bupt.embemc.operators.util.LRUCache;
import bupt.embemc.utils.FileWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Component
public class FileWriterOperator implements Runnable{
    public static final Deque<String[]> dq = new ArrayDeque();
    private static  LRUCache lruCache;
    private static ThreadPoolExecutor threadPoolExecutor;
    private static FileWriterHandler fileWriterHandler = new FileWriterHandler();
    public static void initial(String prefixpath,LRUCache lruCache,ThreadPoolExecutor threadPoolExecutor) throws IOException{
        String pathPrefix;
        pathPrefix = prefixpath + "/data";
        String abstractPath = Paths.get(pathPrefix).toString();
        createFile(new File(abstractPath));
        pathPrefix = prefixpath + "/pictures";
        abstractPath = Paths.get(pathPrefix).toString();
        createFile(new File(abstractPath));
        pathPrefix = prefixpath +"/videos";
        abstractPath = Paths.get(pathPrefix).toString();
        createFile(new File(abstractPath));
        pathPrefix =prefixpath + "/others";
        abstractPath = Paths.get(pathPrefix).toString();
        createFile(new File(abstractPath));
        pathPrefix =prefixpath + "/logs";
        abstractPath = Paths.get(pathPrefix).toString();
        createFile(new File(abstractPath));
        FileWriterOperator.lruCache = lruCache;
        FileWriterOperator.threadPoolExecutor = threadPoolExecutor;

    }
    public static void createFile(File file) throws IOException{
        if (file.exists()) {
            log.info(file.getAbsolutePath()+" exists");
        } else {
            file.mkdirs();
        }

    }


    @Override
    public void run() {

        for(;;){

            Map<String,DLinkedNode> cache = lruCache.getCache();
            Iterator<Map.Entry<String,DLinkedNode>> iterator = cache.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String,DLinkedNode> entry  = iterator.next();
                Map value = entry.getValue().getValue();
//                AtomicBoolean reading = (AtomicBoolean)value.get("reading");
//                if(!(reading.get())){

                    String filePath = (String)value.get("filePath");
                    Queue q = (Queue) value.get("contentQ");
                    if(!q.isEmpty()){
//                        reading.set(true);
//                        FileWriterHandler fileWriterHandler = new FileWriterHandler(q,filePath,reading);
//                        threadPoolExecutor.execute(fileWriterHandler);
                        fileWriterHandler.write(q,filePath);
                    }
//                }
            }

        }
    }
}
