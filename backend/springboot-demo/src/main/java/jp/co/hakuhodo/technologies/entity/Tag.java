package jp.co.hakuhodo.technologies.entity;

import javax.persistence.*;

@Entity
@Table(name="tag")
public class Tag extends BasicEntity {

	private static final long serialVersionUID = 1L;

	@Column(name="NAME_TAG")
	private String nameTag;
}
