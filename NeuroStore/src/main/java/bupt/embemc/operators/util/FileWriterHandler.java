package bupt.embemc.operators.util;

import bupt.embemc.operators.FileWriterOperator;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class FileWriterHandler implements Runnable{
    private Queue q;
    private String filePath;
    private AtomicBoolean reading;
//    public FileWriterHandler(Queue q,String filePath,AtomicBoolean reading){
//        this.q = q;
//        this.filePath=filePath;
//        this.reading=reading;
//    }

    @Override
    public void run() {
        for(;;){
            String fileContent  = (String) q.poll();
            if(fileContent==null){
                break;
            }
            byte[] data = Base64.getDecoder().decode(fileContent);
//                System.out.println(fileContent[1]);
            try {
                FileOutputStream fo = new FileOutputStream(filePath,true);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fo,
                        10);

                bufferedOutputStream.write(data);
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
                log.debug(String.format("file %s saved", filePath));

            } catch (IOException e) {
                log.error("file storege failed!");
            }
        }
//        reading.set(false);
    }
    public void write(Queue q,String filePath){
        for(;;){
            String fileContent  = (String) q.poll();
            if(fileContent==null){
                break;
            }
            byte[] data = Base64.getDecoder().decode(fileContent);
//                System.out.println(fileContent[1]);
            try {
                FileOutputStream fo = new FileOutputStream(filePath,true);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fo,
                        10);

                bufferedOutputStream.write(data);
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
                log.debug(String.format("file %s saved", filePath));

            } catch (IOException e) {
                e.printStackTrace();
                log.error("file storege failed!");
            }
        }
    }
}
