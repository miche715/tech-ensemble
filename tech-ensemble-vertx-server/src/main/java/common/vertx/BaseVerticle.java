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
}