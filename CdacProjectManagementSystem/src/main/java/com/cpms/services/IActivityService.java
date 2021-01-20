package com.cpms.services;

import com.cpms.pojos.Activity;

public interface IActivityService {
	Activity createActivity(String activityDescription, Integer projectId);
}
