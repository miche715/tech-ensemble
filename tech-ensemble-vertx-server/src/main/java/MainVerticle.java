import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import one.verticle.OneVerticle;
import server.verticle.ServerVerticle;

public class MainVerticle extends AbstractVerticle {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(new ServerVerticle());
        vertx.deployVerticle(new OneVerticle());
    }
}