package jp.co.hakuhodo.technologies.entity;

import java.util.List;

import javax.persistence.*;

import jp.co.hakuhodo.technologies.common.utils.StringListConverter;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class Customer extends BasicEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "customer_name", length = 20)
	private String customerName;

	@Column(name = "tags", columnDefinition = "text[]")
	@Convert(converter = StringListConverter.class)
	private List<String> tags;
}
