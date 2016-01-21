package com.luxoft.akkalabs.day1.wikipedia2.web.wikitopics;

import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import com.luxoft.akkalabs.day1.wikipedia2.web.listeners.impl.AsyncListenerImpl;
import com.luxoft.akkalabs.day1.wikipedia2.web.listeners.impl.WikipediaListenerImpl;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet(asyncSupported = true, urlPatterns = {"/day1/wikitopics"})
public class WikipediaStream extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/event-stream");
        resp.setCharacterEncoding("UTF-8");

        String streamId = UUID.randomUUID().toString();
        AsyncContext asyncContext = req.startAsync();
        asyncContext.setTimeout(240000);

        WikipediaListenerImpl wikipediaListener = new WikipediaListenerImpl(streamId, asyncContext);
        ActorSystem actorSystem = (ActorSystem) req.getServletContext().getAttribute("actorSystem");
        ActorSelection actorSelection = actorSystem.actorSelection("/user/connections");

        Register register = new Register(wikipediaListener);
        actorSelection.tell(register, null);
        asyncContext.addListener(new AsyncListenerImpl(streamId, actorSelection));
    }

}
