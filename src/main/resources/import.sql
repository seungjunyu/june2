INSERT INTO USER (ID, USER_ID, PASSWORD, NAME, EMAIL) VALUES (1, 'test', '1234', '테스트', 'test@test.co.kr');
INSERT INTO USER (ID, USER_ID, PASSWORD, NAME, EMAIL) VALUES (2, 'seungjun', '1111', '승준', 'seungjun@test.co.kr');

INSERT INTO QUESTION (id, writer_id, title, contents, create_date, count_of_answer) VALUES (1,1,'제목1','내용1',CURRENT_TIMESTAMP(),0);
INSERT INTO QUESTION (id, writer_id, title, contents, create_date, count_of_answer) VALUES (2,2,'제목2','내용2',CURRENT_TIMESTAMP(),0);