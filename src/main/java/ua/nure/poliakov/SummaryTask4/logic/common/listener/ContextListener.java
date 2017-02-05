package ua.nure.poliakov.SummaryTask4.logic.common.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log("Start Periodicals");
    }

    private void log(String msg) {
        System.out.println("Application: " + msg);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log("Periodicals finished");
    }
}
