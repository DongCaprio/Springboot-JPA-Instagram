package com.cos.photogramstart.web.dto.subscribe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SubscribeDto {
	private int id;
	private String username;
	private String profileImageUrl;
	private Integer subscribeState; //얘네둘은 int가 아닌 Integer로 한 이유
	private Integer equalUserState; //int라고 하면 마리아디비 true값을 리턴을 못받음
}
