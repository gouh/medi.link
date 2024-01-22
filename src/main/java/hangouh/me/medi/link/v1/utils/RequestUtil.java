package hangouh.me.medi.link.v1.utils;

import java.util.ArrayList;
import java.util.Map;
import org.springframework.data.domain.Sort;

public class RequestUtil {

  /**
   * Get a sort for a get
   *
   * @param sortBy Name of the column
   * @return Sort
   */
  public static Sort buildSort(Map<String, String> sortBy) {
    if (sortBy == null || sortBy.isEmpty()) {
      return Sort.unsorted();
    }
    Map.Entry<String, String> entry = new ArrayList<>(sortBy.entrySet()).get(sortBy.size() - 1);
    String direction = entry.getValue().equalsIgnoreCase("asc") ? "asc" : "desc";

    return Sort.by(Sort.Direction.fromString(direction), entry.getKey());
  }
}
