package com.cpms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpms.pojos.Guide;
import com.cpms.repository.GuideRepository;

@Service
public class GuideService implements IGuideService {

	@Autowired
	private GuideRepository guiderepository;

	@Override
	public Guide registerGuide(Guide guide) {
		return guiderepository.save(guide);
	}

}
