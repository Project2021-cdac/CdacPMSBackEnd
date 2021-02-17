package com.cpms.dto;

import java.util.List;

import com.cpms.pojos.UserAccount;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
//TODO Rename the file may be?
public class RegisterGuideDTO {
	private UserAccount guidedata;
	private List<Integer> technologylist;
}
