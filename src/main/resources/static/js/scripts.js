//$(".answer-write input[type=submit]").click(addAnswer);	//클래스는 .으로 시작된다.
//$(".answer-write input[type=submit]").on("click",addAnswer);	//클래스는 .으로 시작된다.
$(".answer-write input[type='submit']").on("click", addAnswer); //클래스는 .으로 시작된다.

function addAnswer(e){
	console.log("click answer button!");
	e.preventDefault();	//서버로 바로 전송이 되지 않도록 해준다.
	
	var url = $(".answer-write").attr("action");	//action이라는 속성의 값을 가져올수 있다.
	console.log("url : " + url);
	
	var queryString = $(".answer-write").serialize();
	console.log("queryString : " + queryString);
	
	$.ajax({ 
		type : 'post', 
		url : url, 
		data : queryString, 
		dataType : 'json', 
		error: function(){
			console.log("fail!"); 
		},
		success : function(data, status){
			console.log('data', data);
			
			var answerTemplate = $("#answerTemplate").html();	//id는 #으로 읽어온다. 
			var template = answerTemplate.format(data.writer.userId, data.formattedCreateDate, data.contents, data.question.id, data.id); 
			$(".qna-comment-slipp-articles").prepend(template); 
			$("textarea[name=contents]").val("");	
			
			//document.location.reload();
		}
	});			
}

$("a.link-delete-article").click(deleteAnswer);
//$(".qna-comment-slipp-articles").on("click",".delete-answer-form button[type=submit]", deleteAnswer);
//$(".qna-comment-slipp-articles").on("click", ".delete-answer-form button[type='submit']", deleteAnswer);

function deleteAnswer(e){
	console.log("click delete button!");
	e.preventDefault();	//서버로 바로 전송이 되지 않도록 해준다.
	
	var deleteBtn = $(this);
	var url = deleteBtn.attr("href");
	//var url = $(".delete-answer-form").attr("action");
	console.log("url : " + url);
		
	$.ajax({ 
		type : 'delete', 
		url : url, 
		dataType : 'json', 
		error: function(xhr, status){
			console.log("error"); 
		},
		success : function(data, status){
			console.log('data', data);
			if (data.valid){
				deleteBtn.closest("article").remove();
			} else {
				alert(data.errorMessage);
			}
		}
	});			
}

//show.html 하단에 자바스크립트 템플릿을 사용하기 위한 코드부분
String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};