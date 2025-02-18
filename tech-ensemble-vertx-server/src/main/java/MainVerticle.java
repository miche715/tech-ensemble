import core.acthree.verticle.ThreeVerticle;
import core.abtwo.verticle.TwoVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import core.aaone.verticle.OneVerticle;
import core.server.verticle.ServerVerticle;

public class MainVerticle extends AbstractVerticle {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(new ServerVerticle());
        vertx.deployVerticle(new OneVerticle());
        vertx.deployVerticle(new TwoVerticle());
        vertx.deployVerticle(new ThreeVerticle());
    }
}