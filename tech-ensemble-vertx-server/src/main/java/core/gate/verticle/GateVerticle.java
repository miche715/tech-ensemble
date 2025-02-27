package core.gate.verticle;

import common.code.CommonCode;
import common.record.VertxMessageRecord;
import common.vertx.BaseVerticle;
import common.vertx.VertxMessageRecordCodec;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class GateVerticle extends BaseVerticle {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void start(Promise<Void> startPromise) {
        vertx.eventBus().registerDefaultCodec(VertxMessageRecord.class, new VertxMessageRecordCodec());

        vertx.createNetServer().connectHandler(socket -> {
            logger.info("{}connect complete -> {}", super.getLogPrefix(new Object(){}), socket.remoteAddress());

            socket.handler(buffer -> {
                VertxMessageRecord vertxMessageRecord = gson.fromJson(buffer.toString(), VertxMessageRecord.class);
                logger.info("{}receive complete -> {} {}", super.getLogPrefix(new Object(){}), socket.remoteAddress(), vertxMessageRecord);

                switch(vertxMessageRecord.verticleType()) {
                    case CommonCode.VERTICLE_TYPE_ONE: {
                        if(vertxMessageRecord.commandType().equals(CommonCode.COMMAND_TYPE_A)) {
                            vertx.eventBus().request(CommonCode.EVENT_BUS_ADDRESS_ONE, vertxMessageRecord, reply -> {
                                if(reply.succeeded()) {
                                    socket.write(gson.toJson(reply.result().body()));
                                } else {
                                    socket.write("exception: " + reply.cause().getMessage());
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
                                    socket.write("exception: " + reply.cause().getMessage());
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
                                    socket.write("exception: " + reply.cause().getMessage());
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

            socket.closeHandler(handler -> {
                logger.info("{}disconnect complete -> {}", super.getLogPrefix(new Object(){}), socket.remoteAddress());
            });
        }).listen(7000, result -> {
            if(result.succeeded()) {
                logger.info("{}server start complete on port -> {}", super.getLogPrefix(new Object(){}), result.result().actualPort());
                startPromise.complete();
            } else {
                logger.error("{}exception -> {}", super.getLogPrefix(new Object(){}), result.cause().getMessage());
                startPromise.fail(result.cause());
            }
        });
    }
}