package PaginationCheck;

import com.intuit.karate.Http;
import org.json.JSONArray;
import org.json.JSONObject;

public class PagesCheck {

  public static void getUsersList(String url, int page) {
    String body = Http.to(url + page).get().getBodyAsString();
    JSONObject obj = new JSONObject(body);
    JSONArray userList = (JSONArray) obj.get("data");
    System.out.println(userList.get(0));
  }

  public static void main(String[] args) {
    getUsersList("https://reqres.in/api/users?page=", 1);
  }
}
