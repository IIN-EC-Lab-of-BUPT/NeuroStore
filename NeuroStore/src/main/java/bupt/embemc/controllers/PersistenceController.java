package bupt.embemc.controllers;

import bupt.embemc.operators.*;
import bupt.embemc.operators.util.ABCCircle;
import bupt.embemc.operators.util.LRUCache;
import bupt.embemc.receiver.MessageReceiver;
import bupt.embemc.singleton.MajorToMajorTableName;
import bupt.embemc.singleton.TempToBaseTables;
import bupt.embemc.utils.DbOperator;
import bupt.embemc.utils.JDBCUtils;
import bupt.embemc.utils.JSONReader;
import bupt.embemc.utils.PreparaedStatementUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class PersistenceController{

    @Autowired
    private DbInitOperator dbInitOperator;
    @Autowired
    private DbOperator dbOperator;

    @Autowired
    private MessageReceiver messageReceiver;


    @Autowired
    TempToBaseTables tempToBaseTables;

    @Autowired
    MajorToMajorTableName majorToMajorTableName;

    @Autowired
    DataCleaner dataCleaner;

    @Autowired
    JDBCUtils jdbcUtils;

    @Autowired
    FileWriterOperator fileWriterOperator;

    @Autowired
    MessageProcess messageProcess;



    @Value("${Jsonfile-Path}")
    String jsonFile;

    @Value("${APIconf-Path}")
    String confPath;

    @Value("${corePoolsize}")
    int corePoolsize;

    @Value("${maximumPoolSize}")
    int maximumPoolSize;

    @Value("${keepAliveTime}")
    long keepAliveTime;

    @Value("${DataFolder}")
    String pathPrefix;



    public void run() throws Exception {

        LRUCache lruCache = new LRUCache(500);
        ABCCircle abcCircle = new ABCCircle();
        ThreadPoolExecutor executor1 = new ThreadPoolExecutor(
                corePoolsize,
                maximumPoolSize,
                keepAliveTime,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(2),
                new ThreadFactory(){
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread t = new Thread(r);
                        return t;
                    }
                },new ThreadPoolExecutor.CallerRunsPolicy()
        );
        FileWriterOperator.initial(pathPrefix,lruCache,executor1);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                corePoolsize,
                maximumPoolSize,
                keepAliveTime,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(2),
                new ThreadFactory(){
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread t = new Thread(r);
                        return t;
                    }
                },new ThreadPoolExecutor.DiscardPolicy()
        );
        jdbcUtils.init();
        JSONReader.init(jsonFile,confPath);
        tempToBaseTables.init(JSONReader.getAPIMap(),JSONReader.getTempTables());
        majorToMajorTableName.init();

        PreparaedStatementUpdate ps = new PreparaedStatementUpdate();
        dbOperator.connect(1,ps);
        dbInitOperator.createTables(dbOperator,ps,tempToBaseTables,majorToMajorTableName);




        MessageHandleOperator.init(tempToBaseTables,majorToMajorTableName,pathPrefix,lruCache,abcCircle);

        PreparaedStatementUpdate ps1 = new PreparaedStatementUpdate();
        dbOperator.connect(1,ps1);
        HashMap tempTable = new HashMap(JSONReader.getTempTables());

        dataCleaner.init(dbOperator,ps1,tempTable);
        messageReceiver.init();
        messageProcess.init(executor,dbOperator);



    }

    public void startThreads() {
        log.info("messageReceiver starting");
        new Thread(messageReceiver).start();
        new Thread(messageReceiver).start();

        log.info("messagePreProcess starting");
        new Thread(messageProcess).start();

        log.info("dataCleaner starting");
        new Thread(dataCleaner).start();

        log.info("filesaver starting");
        new Thread(fileWriterOperator).start();
    }

    public void newTimer(){
        TimerTask task = new TimerTask() {
            public void run() {

            }
        };

        Timer timer = new Timer("Timer");
        long delay = 600000L;
        timer.schedule(task, delay);
    }

    public void stopThreads() {
        log.info("PersistentController stoping");
        messageReceiver.stop();
        log.info("PersistentController stoped");

    }
}

