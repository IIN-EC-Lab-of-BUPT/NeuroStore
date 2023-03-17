package bupt.embemc.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Queue;

@Slf4j
public class FileWriter implements Runnable{


    private String content;
    private String abstractPath;


    public FileWriter(String content,String abstractPath){
        this.content = content;
        this.abstractPath = abstractPath;
    }
    @Override
    public void run() {
        byte[] data = Base64.getDecoder().decode(content);

        try {
            FileOutputStream fo = new FileOutputStream(abstractPath,true);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fo,
                    10);

            bufferedOutputStream.write(data);
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            log.debug(String.format("file %s saved", abstractPath));

        } catch (IOException e) {
            log.error("file storege failed!");
        }
    }


}
