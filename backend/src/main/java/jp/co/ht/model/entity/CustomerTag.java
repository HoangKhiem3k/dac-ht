package jp.co.ht.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer_tag")
public class CustomerTag extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@Column(name = "customer_id")
	private Long customerId;

	@Column(name = "tag_id")
	private Long tagId;
}