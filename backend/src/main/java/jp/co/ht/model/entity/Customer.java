package jp.co.ht.model.entity;

import java.util.List;
import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class Customer extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "customer_name", length = 20)
	private String customerName;
	
	@Transient
	private List<Tag> tags;

}