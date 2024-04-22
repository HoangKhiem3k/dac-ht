package jp.co.ht.model.request.customer;

import java.io.Serializable;
import java.util.List;
import jp.co.ht.common.annotation.MaxLength;
import jp.co.ht.common.annotation.Required;
import jp.co.ht.model.entity.Tag;
import lombok.Data;

@Data
public class CustomerRegistRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@Required(label="Customer name")
	@MaxLength(label="Customer name", val = 20)
	private String customerName;

	private List<Tag> tags;
}
