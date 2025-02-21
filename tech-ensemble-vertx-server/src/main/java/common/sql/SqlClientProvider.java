package common.sql;

import io.vertx.pgclient.PgBuilder;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.SqlClient;

public class SqlClientProvider {
    public static SqlClient getInstance() {
        return LazyHolder.SQL_CLIENT;
    }

    private static class LazyHolder {
        private static final SqlClient SQL_CLIENT = PgBuilder
                .client()
                .with(new PoolOptions()
                        .setMaxSize(5))
                .connectingTo(new PgConnectOptions()
                        .setPort(5432)
                        .setHost("127.0.0.1")
                        .setDatabase("postgres")
                        .setUser("postgres")
                        .setPassword("1q2w3e4r"))
                .build();
    }
}