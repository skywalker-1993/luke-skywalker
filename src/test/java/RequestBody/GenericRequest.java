package RequestBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.UUID;

@Slf4j
public class GenericRequest {

  private static final int LEFT_LIMIT = 97;
  private static final int RIGHT_LIMIT = 122;

  public static String getRandomUUID() {
    return UUID.randomUUID().toString();
  }

  public static String getRequestBody(Object instance) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(instance);
  }

  public static String getRandomBoundedString() {
    int targetStringLength = 8;
    Random random = new Random();
    StringBuilder buffer = new StringBuilder(targetStringLength);
    for (int i = 0; i < targetStringLength; i++) {
      int randomLimitedInt = LEFT_LIMIT + (int)
          (random.nextFloat() * (RIGHT_LIMIT - LEFT_LIMIT + 1));
      buffer.append((char) randomLimitedInt);
    }
    return buffer.toString().toLowerCase();
  }

}
