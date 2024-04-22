package jp.co.ht.model.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tag")
public class Tag extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "tag_name")
	private String tagName;
    
    public Tag(Long id, String tagName) {
    	super.setId(id);
    	this.setTagName(tagName);
    }
}
