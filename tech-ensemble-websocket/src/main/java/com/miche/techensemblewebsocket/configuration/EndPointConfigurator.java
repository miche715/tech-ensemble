package com.miche.techensemblewebsocket.configuration;

import com.miche.techensemblewebsocket.common.Define;
import jakarta.websocket.server.ServerEndpointConfig;

public class EndPointConfigurator extends ServerEndpointConfig.Configurator {
    @Override
    public <T> T getEndpointInstance(Class<T> clazz) {
        return Define.CONTEXT.getBean(clazz);
    }
}