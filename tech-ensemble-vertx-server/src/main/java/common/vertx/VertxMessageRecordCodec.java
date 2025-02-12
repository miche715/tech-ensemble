package common.vertx;

import com.google.gson.Gson;
import common.gson.GsonProvider;
import common.record.VertxMessageRecord;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

public class VertxMessageRecordCodec implements MessageCodec<VertxMessageRecord, VertxMessageRecord> {
    private final Gson gson = GsonProvider.getInstance();

    @Override
    public void encodeToWire(Buffer buffer, VertxMessageRecord record) {
        buffer.appendString(gson.toJson(record));
    }

    @Override
    public VertxMessageRecord decodeFromWire(int pos, Buffer buffer) {
        return gson.fromJson(buffer.getString(pos, buffer.length()), VertxMessageRecord.class);
    }

    @Override
    public VertxMessageRecord transform(VertxMessageRecord record) {
        return record;  // Local event bus에서는 직접 전달되므로 그냥 반환.
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }

    @Override
    public byte systemCodecID() {
        return -1;  // 사용자 정의 Codec은 -1 반환
    }
}