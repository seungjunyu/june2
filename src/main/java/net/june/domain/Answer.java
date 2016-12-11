package net.june.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Answer {
	@Id	//Id를 PK 지정
	@GeneratedValue	//자동증가형
	@JsonProperty	//JSON DATA로 받아진다. 이부분 명시가 없으면 get메소드가 있는것들만 받아지게 된다.
	private Long id;	//아이디(PK)
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
	@JsonProperty	//JSON DATA로 받아진다. 이부분 명시가 없으면 get메소드가 있는것들만 받아지게 된다.
	private User writer;
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
	@JsonProperty	//JSON DATA로 받아진다. 이부분 명시가 없으면 get메소드가 있는것들만 받아지게 된다.
	private Question question;
	
	@Lob		//데이타타입의 크기를 크게 할경우 지정한다.
	@JsonProperty	//JSON DATA로 받아진다. 이부분 명시가 없으면 get메소드가 있는것들만 받아지게 된다.
	private String contents;
		
	private LocalDateTime createDate;	//자바8부터 날짜관련 API가 추가됨.

	private boolean deleted;
	
	public Answer(){
		
	}
	
	public Answer(User writer, Question question, String contents){
		this.writer = writer;
		this.question = question;
		this.contents = contents;
		this.createDate = LocalDateTime.now();
	}
	
	public String getFormattedCreateData(){
		if(createDate == null){
			return "";
		}
		return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
	}	
	
	public boolean isSameWriter(User loginUser) {
		return loginUser.equals(this.writer);
	}

	public void delete(User loginUser) {
		if (!writer.equals(loginUser)) {
			throw new IllegalStateException("다른 사용자가 작성한 답변을 삭제할 수 없습니다.");
		}
		
		this.deleted = true;		
	}	

	//마우스오른쪽 Source > Generate hashCode() and equals()...
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
		Answer other = (Answer) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	//마우스오른쪽 Source > Generate toString()...	
	@Override
	public String toString() {
		return "Answer [id=" + id + ", writer=" + writer + ", contents=" + contents + ", createDate=" + createDate
				+ "]";
	}

}
