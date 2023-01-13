package RequestBody;

import static RequestBody.GenericRequest.getRequestBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@AllArgsConstructor
public class ReqresUser {

  private String email;
  private String password;

  public static ReqresUser getUserObject(String username, String password) {
    return new ReqresUser(username, password);
  }

  public static String getUserBody(ReqresUser userObject) throws JsonProcessingException {
    return getRequestBody(userObject);
  }

}
