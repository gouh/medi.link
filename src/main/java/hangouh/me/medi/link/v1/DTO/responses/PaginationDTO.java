package hangouh.me.medi.link.v1.DTO.responses;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationDTO {
  private int totalItems;
  private int itemsPerPage;
  private int totalInPage;
  private int currentPage;
  private int totalPages;
  private List<Integer> prevPages;
  private List<Integer> nextPages;
  private Integer prevPage;
  private Integer nextPage;

  public static PaginationDTO calcPagination(
      int page, int totalInPage, int totalItems, int itemsPerPage, int limitAround) {
    itemsPerPage = Math.max(1, itemsPerPage);
    totalItems = Math.max(0, totalItems);
    int totalPages = Math.max(1, (int) Math.ceil((double) totalItems / itemsPerPage));
    int currentPage = Math.min(Math.max(0, page), totalPages - 1);

    int previousStart = Math.max(0, currentPage - limitAround);
    int previousEnd = currentPage - 1;
    List<Integer> prevPages =
        (previousStart <= previousEnd) ? range(previousStart, previousEnd) : List.of();

    int nextStart = currentPage + 1;
    int nextEnd = Math.min(nextStart + limitAround - 1, totalPages - 1);
    List<Integer> nextPages = (nextStart <= totalPages - 1) ? range(nextStart, nextEnd) : List.of();

    Integer prevPage = !prevPages.isEmpty() ? prevPages.get(prevPages.size() - 1) : null;
    Integer nextPage = !nextPages.isEmpty() ? nextPages.get(0) : null;

    PaginationDTO paginationDTO = new PaginationDTO();
    paginationDTO.setTotalItems(totalItems);
    paginationDTO.setItemsPerPage(itemsPerPage);
    paginationDTO.setTotalInPage(totalInPage);
    paginationDTO.setCurrentPage(currentPage);
    paginationDTO.setTotalPages(totalPages);
    paginationDTO.setPrevPages(prevPages);
    paginationDTO.setNextPages(nextPages);
    paginationDTO.setPrevPage(prevPage);
    paginationDTO.setNextPage(nextPage);

    return paginationDTO;
  }

  private static List<Integer> range(int start, int end) {
    return java.util.stream.IntStream.rangeClosed(start, end).boxed().toList();
  }
}
