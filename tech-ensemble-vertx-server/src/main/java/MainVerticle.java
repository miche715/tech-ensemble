import io.vertx.core.Vertx;
import server.verticle.ServerVerticle;

public class MainVerticle {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(new ServerVerticle());
    }
}