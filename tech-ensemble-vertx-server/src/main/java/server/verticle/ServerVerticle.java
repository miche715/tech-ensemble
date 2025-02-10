package server.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class ServerVerticle extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) {
        vertx.createNetServer().connectHandler(socket -> {
            System.out.println("클라이언트 연결됨!");

            socket.handler(buffer -> {
                String received = buffer.toString();
                System.out.println("수신: " + received);

                String response = "서버 응답: " + received.toUpperCase();
                socket.write(response);
            });

            socket.closeHandler(v -> System.out.println("클라이언트 연결 종료"));
        }).listen(1234, res -> {
            if(res.succeeded()) {
                System.out.println("서버가 1234 포트에서 시작됨");
                startPromise.complete();
            } else {
                System.out.println("서버 시작 실패: " + res.cause().getMessage());
                startPromise.fail(res.cause());
            }
        });
    }
}