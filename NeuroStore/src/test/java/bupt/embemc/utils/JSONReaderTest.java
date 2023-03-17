package bupt.embemc.utils;

import bupt.embemc.App;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;


@SpringBootTest(classes = App.class)
public class JSONReaderTest {
    @Autowired
    JSONReader r;

    @Value("${Jsonfile-Path}")
    String jsonFile;

    @Value("${APIconf-Path}")
    String confPath;

    @Test
    public void testjsonreader() throws FileNotFoundException {
//        r.init(jsonFile,confPath);
//        BufferedReader bufferedReader = new BufferedReader(new FileReader("application.properties"));
//        System.out.println(bufferedReader);
    }

}