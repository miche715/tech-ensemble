package core.gate.verticle;

import common.code.CommonCode;
import common.record.VertxMessageRecord;
import common.vertx.BaseVerticle;
import common.vertx.VertxMessageRecordCodec;
import io.vertx.core.Promise;

import java.util.Objects;

public class GateVerticle extends BaseVerticle {
    @Override
    public void start(Promise<Void> startPromise) {
        vertx.eventBus().registerDefaultCodec(VertxMessageRecord.class, new VertxMessageRecordCodec());

        vertx.createNetServer().connectHandler(socket -> {
            System.out.println("\nClient connected: " + socket.remoteAddress());

            socket.handler(buffer -> {
                VertxMessageRecord vertxMessageRecord = gson.fromJson(buffer.toString(), VertxMessageRecord.class);
                System.out.println("\nReceive Packet: " + socket.remoteAddress() + " " + vertxMessageRecord);

                switch(vertxMessageRecord.verticleType()) {
                    case CommonCode.VERTICLE_TYPE_ONE: {
                        if(vertxMessageRecord.commandType().equals(CommonCode.COMMAND_TYPE_A)) {
                            vertx.eventBus().request(CommonCode.EVENT_BUS_ADDRESS_ONE, vertxMessageRecord, reply -> {
                                if(reply.succeeded()) {
                                    socket.write(gson.toJson(reply.result().body()));
                                } else {
                                    socket.write("Error: " + reply.cause().getMessage());
                                }
                            });
                        }
                        if(vertxMessageRecord.commandType().equals(CommonCode.COMMAND_TYPE_B)) {
                            getMessageProducer(CommonCode.EVENT_BUS_ADDRESS_ONE).write(vertxMessageRecord);
                        }
                        if(vertxMessageRecord.commandType().equals(CommonCode.COMMAND_TYPE_C)) {
                            getMessageProducer(CommonCode.EVENT_BUS_ADDRESS_ONE).write(vertxMessageRecord);
                        }
                        if(vertxMessageRecord.commandType().equals(CommonCode.COMMAND_TYPE_D)) {
                            getMessageProducer(CommonCode.EVENT_BUS_ADDRESS_ONE).write(vertxMessageRecord);
                        }

                        break;
                    }

                    case CommonCode.VERTICLE_TYPE_TWO: {
                        if(vertxMessageRecord.commandType().equals(CommonCode.COMMAND_TYPE_A)) {
                            vertx.eventBus().request(CommonCode.EVENT_BUS_ADDRESS_TWO, vertxMessageRecord, reply -> {
                                if(reply.succeeded()) {
                                    socket.write(gson.toJson(reply.result().body()));
                                } else {
                                    socket.write("Error: " + reply.cause().getMessage());
                                }
                            });
                        }
                        if(vertxMessageRecord.commandType().equals(CommonCode.COMMAND_TYPE_B)) {
                            getMessageProducer(CommonCode.EVENT_BUS_ADDRESS_TWO).write(vertxMessageRecord);
                        }
                        if(vertxMessageRecord.commandType().equals(CommonCode.COMMAND_TYPE_C)) {
                            getMessageProducer(CommonCode.EVENT_BUS_ADDRESS_TWO).write(vertxMessageRecord);
                        }
                        if(vertxMessageRecord.commandType().equals(CommonCode.COMMAND_TYPE_D)) {
                            getMessageProducer(CommonCode.EVENT_BUS_ADDRESS_TWO).write(vertxMessageRecord);
                        }

                        break;
                    }

                    case CommonCode.VERTICLE_TYPE_THREE: {
                        if(vertxMessageRecord.commandType().equals(CommonCode.COMMAND_TYPE_A)) {
                            vertx.eventBus().request(CommonCode.EVENT_BUS_ADDRESS_THREE, vertxMessageRecord, reply -> {
                                if(reply.succeeded()) {
                                    socket.write(gson.toJson(reply.result().body()));
                                } else {
                                    socket.write("Error: " + reply.cause().getMessage());
                                }
                            });
                        }
                        if(vertxMessageRecord.commandType().equals(CommonCode.COMMAND_TYPE_B)) {
                            getMessageProducer(CommonCode.EVENT_BUS_ADDRESS_THREE).write(vertxMessageRecord);
                        }
                        if(vertxMessageRecord.commandType().equals(CommonCode.COMMAND_TYPE_C)) {
                            getMessageProducer(CommonCode.EVENT_BUS_ADDRESS_THREE).write(vertxMessageRecord);
                        }
                        if(vertxMessageRecord.commandType().equals(CommonCode.COMMAND_TYPE_D)) {
                            getMessageProducer(CommonCode.EVENT_BUS_ADDRESS_THREE).write(vertxMessageRecord);
                        }

                        break;
                    }

                    default: {
                        break;
                    }
                }
            });

            socket.closeHandler(v -> System.out.println("Client disconnected: " + socket.remoteAddress()));
        }).listen(1234, result -> {
            if(result.succeeded()) {
                System.out.println("\nServer started on port 1234");
                startPromise.complete();
            } else {
                System.out.println("\nServer start fail: " + result.cause().getMessage());
                startPromise.fail(result.cause());
            }
        });
    }
}