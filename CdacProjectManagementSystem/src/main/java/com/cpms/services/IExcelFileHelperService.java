package com.cpms.services;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cpms.pojos.UserAccount;

public interface IExcelFileHelperService {
	
	List<UserAccount> saveToDatabase(MultipartFile file) throws IOException;
}
