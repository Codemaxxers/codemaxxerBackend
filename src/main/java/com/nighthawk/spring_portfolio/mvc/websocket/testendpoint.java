package com.nighthawk.spring_portfolio.mvc.websocket;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import org.springframework.web.socket.server.standard.SpringConfigurator;

import java.io.IOException;

@ServerEndpoint(value = "/websocket", configurator = SpringConfigurator.class)
public class testendpoint {

        @OnOpen
        public void onOpen(Session session) throws IOException {
                session.getBasicRemote().sendText("Test");
        }

        @OnMessage
        public void onMessage(Session session, String message) throws IOException {
        }

        @OnClose
        public void onClose(Session session) throws IOException {
        }

        @OnError
        public void onError(Session session, Throwable throwable) {
        }
}
