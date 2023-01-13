package PaginationCheck;

import com.intuit.karate.Http;
import lombok.Data;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

@Data
public class PagesCheck {

  private static final String usersEndpoint = "https://reqres.in/api/users";

  private int totalUsers = 0;

  private int totalPages = 0;
  private Map<Integer, JSONObject> responseBody = new HashMap<>();

  private static int getPageTotalUsers(JSONObject obj) {
    JSONArray data = (JSONArray) obj.get("data");
    return data.length();
  }

  private static int getTotalUsers(JSONObject obj) {
    return (int) obj.get("total");
  }

  private static int getTotalPages(JSONObject obj) {
    return (int) obj.get("total_pages");
  }

  private static String getUserPage(int page) {
    com.intuit.karate.http.Response response = Http.to(usersEndpoint + "?page=" + page).get();
    if (response.getStatus() != 200) throw new IllegalArgumentException("Users List was NOT retrieved!");
    return response.getBodyAsString();
  }

  private void addParsedPage(int page){
    JSONObject obj = new JSONObject(getUserPage(page));
    this.responseBody.put(page, obj);
    if (this.totalPages >= 0 && this.totalUsers >= 0) {
      this.totalPages = getTotalPages(obj);
      this.totalUsers = getTotalUsers(obj);
    }
  }

  public static void main(String[] args) {
    PagesCheck pageCheck = new PagesCheck();
    pageCheck.addParsedPage(1);
    System.out.println(pageCheck.responseBody);
    System.out.println(pageCheck.totalPages);
    System.out.println(pageCheck.totalUsers);
    int countUsers = getPageTotalUsers(pageCheck.responseBody.get(1));
    for (int pageIndex = 2; pageIndex < pageCheck.totalPages + 1; pageIndex++) {
      pageCheck.addParsedPage(pageIndex);
      countUsers += getPageTotalUsers(pageCheck.responseBody.get(pageIndex));
    }
    System.out.println(countUsers);
  }

}