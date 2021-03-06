package com.luxoft.akkalabs.day2.trending.web;

import akka.actor.ActorSystem;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Init implements ServletContextListener {

    public static final String ACTOR_SYSTEM_KEY = "trendingActorSystem";
    public static final String ACTOR_SYSTEM_NAME = "Trending";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ActorSystem system = ActorSystem.create(ACTOR_SYSTEM_NAME);
        sce.getServletContext().setAttribute(ACTOR_SYSTEM_KEY, system);
        //
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ActorSystem system = (ActorSystem) sce.getServletContext().getAttribute(ACTOR_SYSTEM_KEY);
        system.shutdown();
    }

    public static ActorSystem getSystem(ServletContext context) {
        return (ActorSystem) context.getAttribute(ACTOR_SYSTEM_KEY);
    }
}
