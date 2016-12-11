package net.june.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class User {	//클래스명이 테이블명이 된다.
	@Id	//Id를 PK 지정
	@GeneratedValue	//자동증가형
	@JsonProperty	//JSON DATA로 받아진다. 이부분 명시가 없으면 get메소드가 있는것들만 받아지게 된다.
	private Long id;	//아이디(PK)
	
	@Column(unique=true, length=20, nullable=false)	//해당하는 userId는 유일해야 한다.
	//@Column(nullable=false, length=20)	//기본설정은 true 이므로 이와같이 해줘야 한다. @Column누르고 F3누르면 해당 세팅파일로 간다.
	@JsonProperty	//JSON DATA로 받아진다. 이부분 명시가 없으면 get메소드가 있는것들만 받아지게 된다.
	private String userId;	
	
	@Column(length=20, nullable=false)
	@JsonIgnore	//JSON DATA로 받아지지만 명시하지 않을 경우에 사용한다.
	private String password;
	
	@Column(length=30, nullable=false)
	@JsonProperty	//JSON DATA로 받아진다. 이부분 명시가 없으면 get메소드가 있는것들만 받아지게 된다.
	private String name;
	
	@Column(length=50)
	@JsonProperty	//JSON DATA로 받아진다. 이부분 명시가 없으면 get메소드가 있는것들만 받아지게 된다.
	private String email;

	public boolean matchPassword(String newPassword) {
		return this.password.equals(newPassword);
	}	

	public boolean matchId(Long id) {
		return this.id.equals(id);
	}
	
	public Long getId(){
		return id;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserId(){
		return userId;
	}
	
		
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void update(User newUser) {
		if(!password.equals(newUser.password)) {
			throw new IllegalArgumentException("password is wrong!");			
		}
		
		if(password.equals(newUser.password)) {
			//this.password = newUser.password;
			this.name = newUser.name;
			this.email = newUser.email;		
		}
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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
	}


	
}
