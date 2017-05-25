package com.customcodec.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by wuxinjian on 2017/5/25.
 */

public class ServerMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        Server server = applicationContext.getBean(Server.class);
        server.start();
    }
}
