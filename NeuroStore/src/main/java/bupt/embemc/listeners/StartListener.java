package bupt.embemc.listeners;

import bupt.embemc.App;
import bupt.embemc.controllers.PersistenceController;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StartListener implements  ApplicationListener<WebServerInitializedEvent> {

    @Autowired
    PersistenceController persistentController;

    @SneakyThrows
    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        log.info("event listened：" + event);
        persistentController.run();
        persistentController.startThreads();
        log.info("after event listened：" + event);
    }
}