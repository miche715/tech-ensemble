package common.vertx;

import com.google.gson.Gson;
import common.gson.GsonProvider;
import common.record.VertxMessageRecord;
import common.sql.SqlClientProvider;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.MessageProducer;
import io.vertx.sqlclient.SqlClient;

import java.util.HashMap;
import java.util.Map;

public class BaseVerticle extends AbstractVerticle {
    protected final Gson gson = GsonProvider.getInstance();
    protected final SqlClient sqlClient = SqlClientProvider.getInstance();
    protected final Map<String, MessageProducer<VertxMessageRecord>> messageProducerMap = new HashMap<>();

    protected MessageProducer<VertxMessageRecord> getMessageProducer(String address) {
        return messageProducerMap.computeIfAbsent(address, key -> vertx.eventBus().publisher(key));
    }

    protected String getClassName() {
        return this.getClass().getSimpleName();
    }

    protected String getMethodName(Object o) {
        String methodName = o.getClass().getEnclosingMethod().getName();

        if(methodName.length() > 6) {
            if(methodName.substring(0, 7).equalsIgnoreCase("lambda$")) {
                methodName = methodName.substring(7);
            }
        }
        int index = -1;
        for(int i = 0; i < methodName.length(); i++) {
            String c = methodName.charAt(i) + "";
            if(index > -1 && !c.chars().allMatch(Character::isDigit)) {
                index = -1;
            }
            if(c.equals("$")) {
                index = i;
            }
        }
        if(index > -1) {
            methodName = methodName.substring(0, index);
        }

        return methodName;
    }

    protected String getLogPrefix(Object o) {
        return getClassName() + " " + getMethodName(o) + "(): ";
    }
}