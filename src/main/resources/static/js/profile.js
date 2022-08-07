/**
  1. 유저 프로파일 페이지
  (1) 유저 프로파일 페이지 구독하기, 구독취소
  (2) 구독자 정보 모달 보기
  (3) 구독자 정보 모달에서 구독하기, 구독취소
  (4) 유저 프로필 사진 변경
  (5) 사용자 정보 메뉴 열기 닫기
  (6) 사용자 정보(회원정보, 로그아웃, 닫기) 모달
  (7) 사용자 프로파일 이미지 메뉴(사진업로드, 취소) 모달 
  (8) 구독자 정보 모달 닫기
 */

// (1) 유저 프로파일 페이지 구독하기, 구독취소
function toggleSubscribe(toUserId, obj) {
	if ($(obj).text() === "구독취소") {
		$.ajax({
			type: "delete",
			url: "/api/subscribe/" + toUserId,
			dataType: "json"
		}).done(res => {
			$(obj).text("구독하기");
			$(obj).toggleClass("blue");
		}).fail(error => {
			debugger;
			console.log("구독취소실패", error)
		});

	} else {
		$.ajax({
			type: "post",
			url: "/api/subscribe/" + toUserId,
			dataType: "json"
		}).done(res => {
			$(obj).text("구독취소");
			$(obj).toggleClass("blue");
		}).fail(error => {
			console.log("구독하기실패", error)
		});
	}
}

// (2) 구독자 정보  모달 보기
function subscribeInfoModalOpen(pageUserId) {
	$(".modal-subscribe").css("display", "flex");
	$.ajax({
		url: `/api/user/${pageUserId}/subscribe`,
		dataType: "json"

	}).done(res => {
		console.log(res.data); //위의 dataType:"json"은 응답받는 타입이 json이므로 명시

		//이 forEach뭐지 검색하기..
		res.data.forEach((u) => {
			console.log(u);
			let item = getSubscribeModalItem(u);
			$("#subscribeModalList").append(item);
		})
	}).fail(error => {
		console.log("구독정보 불러오기 에러", error);
	});
}

function getSubscribeModalItem(u) {
	let item = `		
			<div class="subscribe__item" id="subscribeModalItem-${u.id}">
				<div class="subscribe__img">
					<img src="/upload/${u.profileImageUrl}" onerror="this.src='/images/person.jpeg'" />
				</div>
				<div class="subscribe__text">
					<h2>${u.username}</h2>
				</div>
				<div class="subscribe__btn">`;
	if (!u.equalUserState) { //동일 유저가 아닐때 버튼이 만들어져야함
		if (u.subscribeState) { // 구독한 상태일때
			item += `<button class="cta blue" onclick="toggleSubscribe(${u.id},this)">구독취소</button>`;
		} else { //구독안한상태
			item += `<button class="cta blue" onclick="toggleSubscribe(${u.id},this)">구독하기</button>`;
		}
	} else {

	}
	item += `
				</div>
			</div>`;
	return item;
}

// (3) 유저 프로파일 사진 변경 (완)
function profileImageUpload(pageUserId, principalId) {
	debugger;
	if (pageUserId != principalId) {
		alert('다른사람의 사진은 변경할 수 없습니다');
		return;
	}

	$("#userProfileImageInput").click();

	$("#userProfileImageInput").on("change", (e) => {
		let f = e.target.files[0];

		if (!f.type.match("image.*")) {
			alert("이미지를 등록해야 합니다.");
			return;
		}

		//서버에 이미지를 전송
		let profileImageForm = $("#userProfileImageForm")[0];
		console.log(profileImageForm);

		// FormData객체를 이용하면 form 태그의 필드와 그 값을 나타내는 일련의 key/value 쌍을 담을 수 있다.
		let formData = new FormData(profileImageForm); //이렇게 해주면 폼태그가 들고있는 값들이 담긴다.
		$.ajax({
			type:"put",
			url: `/api/user/${principalId}/profileImageUrl`,
			data: formData,
			contentType: false, //필수!: ajax로 form의 사진전송하려면 false 필수로 넣어야한다(기본값은 x-www-form~)
			processData: false, //필수!: 위에꺼 false로 주면 기본값이 QueryString이 된다. 그것도 해제하려면 이것도 값을 false로 설정
			enctype: "multipart/form-data", //jsp form태그안에 enctype을 설정하면 여기에 안적어도 된다
			dataType: "json"
		}).done(res => {
			// 사진 전송 성공시 이미지 변경
			let reader = new FileReader();
			reader.onload = (e) => {
				$("#userProfileImage").attr("src", e.target.result);
			}
			reader.readAsDataURL(f); // 이 코드 실행시 reader.onload 실행됨.
		}).fail(error => {
			console.log(error);
		});


	});
}


// (4) 사용자 정보 메뉴 열기 닫기
function popup(obj) {
	$(obj).css("display", "flex");
}

function closePopup(obj) {
	$(obj).css("display", "none");
}


// (5) 사용자 정보(회원정보, 로그아웃, 닫기) 모달
function modalInfo() {
	$(".modal-info").css("display", "none");
}

// (6) 사용자 프로파일 이미지 메뉴(사진업로드, 취소) 모달
function modalImage() {
	$(".modal-image").css("display", "none");
}

// (7) 구독자 정보 모달 닫기
function modalClose() {
	$(".modal-subscribe").css("display", "none");
	location.reload();
}






