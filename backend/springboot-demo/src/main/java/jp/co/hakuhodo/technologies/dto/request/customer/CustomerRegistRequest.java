package jp.co.hakuhodo.technologies.dto.request.customer;

import java.util.List;

import jp.co.hakuhodo.technologies.common.annotation.MaxLength;
import jp.co.hakuhodo.technologies.dto.common.BasePageInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerRegistRequest extends BasePageInfo {

	private static final long serialVersionUID = 1L;

	@MaxLength(val = 20)
	private String customerName;

	private List<String> tags;
}
