package com.miche.techensemblewebsocket.server;

import com.miche.techensemblewebsocket.common.Define;
import com.miche.techensemblewebsocket.common.User;
import com.miche.techensemblewebsocket.common.base.BaseClass;
import com.miche.techensemblewebsocket.common.code.CommonCode;
import com.miche.techensemblewebsocket.configuration.EndPointConfigurator;
import jakarta.persistence.PersistenceException;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@ServerEndpoint(value = "/tech-ensemble", configurator = EndPointConfigurator.class)
public class WebSocket extends BaseClass {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public WebSocket(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @OnOpen
    public void onOpen(Session session) {
        try {
            User user = new User(session);
            Define.USERS.put(session.getId(), user);
            logger.info("{}connect complete -> {}", super.getLogPrefix(new Object(){}), session.getId());

            user.sendOne("InitialService", "InitialMethod", redisTemplate.opsForList().range(CommonCode.TOPIC, 0, -1));
            logger.info("{}send complete", super.getLogPrefix(new Object(){}));
        } catch(PersistenceException | NullPointerException | IllegalArgumentException e) {
            logger.info("{}exception -> {}", super.getLogPrefix(new Object(){}), e.getMessage());
        }
    }

    /**
     *  { <br />
     *      &nbsp&nbsp"serviceName": "exampleService", <br />
     *      &nbsp&nbsp"methodName": "exampleMethod", <br />
     *      &nbsp&nbsp"parameter": <br />
     *      &nbsp&nbsp{ <br />
     *          &nbsp&nbsp&nbsp&nbsp"key1": "value1", <br />
     *          &nbsp&nbsp&nbsp&nbsp"key2": "value2" <br />
     *      &nbsp&nbsp} <br />
     *  } <br />
     */
    @OnMessage
    public void onMessage(String message, Session session) {
//        RequestDTO requestDTO = gson.fromJson(data, RequestDTO.class);
//        String serviceName = requestDTO.getServiceName();
//        String methodName = requestDTO.getMethodName();
//        Map<String, Object> parameter = requestDTO.getParameter();
//        parameter.put("session", session);
//
//        try {
//            Object service = Define.CONTEXT.getBean(serviceName);
//            Optional<Method> optionalMethod = Arrays.stream(service.getClass().getDeclaredMethods())
//                    .filter((method) -> method.getName().equals(methodName))
//                    .findAny();
//            Method method;
//            if(optionalMethod.isPresent()) {
//                method = optionalMethod.get();
//            } else {
//                throw new NoSuchMethodException();
//            }
//            method.invoke(service, parameter);
//
//        } catch(JsonSyntaxException | NullPointerException e) {
//            Define.USERS.get(session.getId()).sendOne(300, null, null, "Invalid data");
//            logger.error("Success sendOne, Invalid data -> {} / {}", session, data.isBlank() ? "null" : data);
//        } catch(NoSuchBeanDefinitionException e) {
//            Define.USERS.get(session.getId()).sendOne(300, serviceName, methodName, "Invalid serviceName");
//            logger.error("Success sendOne, Invalid serviceName -> {} / {}", session, serviceName.isBlank() ? "null" : serviceName);
//        } catch(NoSuchMethodException e) {
//            Define.USERS.get(session.getId()).sendOne(300, serviceName, methodName, "Invalid methodName");
//            logger.error("Success sendOne, Invalid methodName -> {} / {}", session, methodName.isBlank() ? "null" : methodName);
//        } catch(InvocationTargetException | IllegalAccessException | PersistenceException e) {
//            Define.USERS.get(session.getId()).sendOne(300, serviceName, methodName, "Invoke error");
//            logger.error("Success sendOne, Invoke error -> {} / {}", session, e.getMessage());
//        }
    }

    @OnClose
    public void onClose(Session session) {
        try {
            Define.USERS.remove(session.getId());
            session.close();
            logger.info("{}disconnect complete -> {}", super.getLogPrefix(new Object(){}), session.getId());
        } catch(ClassCastException | NullPointerException | UnsupportedOperationException | IOException e) {
            logger.info("{}exception -> {}", super.getLogPrefix(new Object(){}), e.getMessage());
        }
    }

    @OnError
    public void onError(Throwable e) {
        logger.info("{}exception -> {}", super.getLogPrefix(new Object(){}), e.getMessage());
        e.printStackTrace();
    }
}