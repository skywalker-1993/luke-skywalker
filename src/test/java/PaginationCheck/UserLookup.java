package PaginationCheck;

import static PaginationCheck.PagesCheck.getPageCheckInstance;
import static PaginationCheck.PagesCheck.getPageUsers;

import org.json.JSONArray;
import org.json.JSONObject;

public class UserLookup {

  private static JSONObject getUserWithEmail(JSONArray jsonArray, String email) {
    for (Object obj: jsonArray) {
      JSONObject user = (JSONObject) obj;
      if (user.get("email").equals(email)) {
        return user;
      }
    }
    return new JSONObject("{}");
  }

  public static JSONObject lookForUser(PagesCheck pageCheck, String email) {
    pageCheck.addParsedPage(1);
    JSONObject currUser = getUserWithEmail(getPageUsers(pageCheck.getResponseBody().get(1)), email);
    if ("{}".equals(currUser.toString())) {
      for (int pageIndex = 2; pageIndex < pageCheck.totalPages + 1; pageIndex++) {
        pageCheck.addParsedPage(pageIndex);
        currUser = getUserWithEmail(getPageUsers(pageCheck.getResponseBody().get(pageIndex)), email);
        if (!"{}".equals(currUser.toString())) break;
      }
    }
    return currUser;
  }

  public static void main(String[] args) {
    PagesCheck pageCheck1 = getPageCheckInstance();
    System.out.println(lookForUser(pageCheck1, "eve.holt@reqres.in"));
    //PagesCheck pageCheck2 = getPageCheckInstance();
    System.out.println(lookForUser(pageCheck1, "tobias.funke@reqres.in"));
  }

}
