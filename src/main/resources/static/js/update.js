// (1) 회원정보 수정
function update(userId, event) {
		event.preventDefault(); // 폼태그 액션막기!!
		let data = $("#profileUpdate").serialize();
		console.log(data);
		$.ajax({
			type : "put",
			url : `/api/user/${userId}`,
			data: data,
			contentType:"application/x-www-form-urlencoded; charset=utf-8",
			dataType : "json"
		}).done(res=>{
			console.log("update성공",res);
			location.href = `/user/${userId}`
		}).fail(error=>{
			console.log("update실패",res);
		});
}