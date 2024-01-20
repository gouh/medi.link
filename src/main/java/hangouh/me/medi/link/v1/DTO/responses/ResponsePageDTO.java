package hangouh.me.medi.link.v1.DTO.responses;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class ResponsePageDTO<T> {
  private MetadataDTO metadata;
  private List<T> data;
  private PaginationDTO pagination;

  public ResponsePageDTO(HttpStatus httpStatus, String errorMsg, Page<T> page) {
    this.metadata = new MetadataDTO(httpStatus, httpStatus.getReasonPhrase(), errorMsg);
    this.data = page.getContent();
    this.pagination =
        PaginationDTO.calcPagination(
            page.getNumber(),
            page.getNumberOfElements(),
            (int) page.getTotalElements(),
            page.getSize(),
            5);
  }
}
