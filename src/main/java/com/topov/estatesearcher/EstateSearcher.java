package com.topov.estatesearcher;

import com.topov.estatesearcher.config.RootConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.TaskScheduler;

public class EstateSearcher {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(RootConfig.class);

        context.refresh();

        TaskScheduler bean = context.getBean(TaskScheduler.class);
        System.out.println(bean.toString());
    }
}
