package com.cpms.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface IExcelFileHelperService {
	
	String save(MultipartFile file) throws IOException;
}
