package net.june.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import net.june.domain.Question;

//public interface QuestionRepository extends CrudRepository<Question, Long>{	//create, read, update, delete
public interface QuestionRepository extends JpaRepository<Question, Long>{	//paging query, sorting query

}
