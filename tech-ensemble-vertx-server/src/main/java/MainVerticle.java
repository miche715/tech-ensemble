import core.acthree.verticle.ThreeVerticle;
import core.abtwo.verticle.TwoVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import core.aaone.verticle.OneVerticle;
import core.gate.verticle.GateVerticle;

public class MainVerticle extends AbstractVerticle {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(new GateVerticle());
        vertx.deployVerticle(new OneVerticle());
        vertx.deployVerticle(new TwoVerticle());
        vertx.deployVerticle(new ThreeVerticle());
    }
}