package web.forum.type.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FORUM_TYPE")
public class ForumType implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FOR_TYPE_NO")
	private Integer for_type_no;

	@Column(name = "FOR_TYPE")
	private String for_type;

	public Integer getFor_type_no() {
		return for_type_no;
	}

	public void setFor_type_no(Integer for_type_no) {
		this.for_type_no = for_type_no;
	}

	public String getFor_type() {
		return for_type;
	}

	public void setFor_type(String for_type) {
		this.for_type = for_type;
	}

}
