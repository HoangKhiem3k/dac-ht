package jp.co.ht.model.request.customer;

import java.util.List;
import jp.co.ht.common.annotation.DateFormat;
import jp.co.ht.model.common.BasePageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerSearchRequest extends BasePageRequest {

    private static final long serialVersionUID = 1L;

    private String customerName;
    
    @DateFormat(label="Insert date from")
    private String insertDateFrom;
    
    @DateFormat(label="Insert date to")
    private String insertDateTo;
    
    @DateFormat(label="Update date from")
    private String updateDateFrom;
    
    @DateFormat(label="Update date to")
    private String updateDateTo;

    private List<Long> tagIds;
}
