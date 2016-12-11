package net.june.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Question {
	@Id	//Id를 PK 지정
	@GeneratedValue	//자동증가형
	@JsonProperty	//JSON DATA로 받아진다. 이부분 명시가 없으면 get메소드가 있는것들만 받아지게 된다.
	private Long id;	//아이디(PK)
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
	@JsonProperty	//JSON DATA로 받아진다. 이부분 명시가 없으면 get메소드가 있는것들만 받아지게 된다.
	//@Column(length=30, nullable=false)
	//private String writer;
	private User writer;
	
	//@Column(length=50, nullable=false)
	@JsonProperty	//JSON DATA로 받아진다. 이부분 명시가 없으면 get메소드가 있는것들만 받아지게 된다.
	private String title;

	//@Column(length=100, nullable=false)
	@Lob		//데이타타입의 크기를 크게 할경우 지정한다.
	@JsonProperty	//JSON DATA로 받아진다. 이부분 명시가 없으면 get메소드가 있는것들만 받아지게 된다.
	private String contents;
	
	@JsonProperty
	private Integer countOfAnswer = 0;
	
	private LocalDateTime createDate;	//자바8부터 날짜관련 API가 추가됨.
	
	@OneToMany(mappedBy="question")
	//@OrderBy("id ASC")	
	@OrderBy("id DESC")
	private List<Answer> answers;
	
//	public void setWriter(String writer) {
//		this.writer = writer;
//	}	
//	public void setTitle(String title) {
//		this.title = title;
//	}	
//	public void setContents(String contents) {
//		this.contents = contents;
//	}
	
	public Question() {}	//jpa에서는 매핑을 할때 기본(디폴트) 생성자가 있어야 한다.
	
	//public Question(String writer, String title, String contents){
	public Question(User writer, String title, String contents){
		super();
		this.writer = writer;
		this.title = title;
		this.contents = contents;		
		this.createDate = LocalDateTime.now();
	}
	
	public String getFormattedCreateData(){
		if(createDate == null){
			return "";
		}
		return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
	}

	public void update(String title, String contents) {
		this.title = title;
		this.contents = contents;		
	}

	public boolean isSameWriter(User loginUser) {
		return this.writer.equals(loginUser);
	}

	public void addAnswer() {
		this.countOfAnswer += 1;
	}	
	
	public void deleteAnswer(){
		this.countOfAnswer -= 1;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
	//public void setId(Long id) {
	//	this.id = id;
	//}

	//public void setUser(User user) {
	//	this.user = user;
	//}
	
	
	//@Override
	//public String toString() {
	//	return "Question [writer=" + writer + ", title=" + title + ", contents=" + contents + "]";
		//return "Question [title=" + title + ", contents=" + contents + "]";
	//}
	
	
}
