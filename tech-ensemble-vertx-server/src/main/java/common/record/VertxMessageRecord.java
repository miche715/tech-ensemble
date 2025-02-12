package common.record;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public record VertxMessageRecord(
        String verticleType,  // one, two, three

        String commandType,  // a, b, c

        String message
)  {

}