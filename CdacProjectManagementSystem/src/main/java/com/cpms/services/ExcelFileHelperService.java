package com.cpms.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cpms.fileutils.excelfilehelper.ExcelFileParser;
import com.cpms.pojos.Student;
import com.cpms.pojos.UserAccount;
import com.cpms.repository.StudentRepository;
import com.cpms.repository.UserAccountRepository;
import com.cpms.wrapperclasses.UserAccountStudentWrapper;

@Service
public class ExcelFileHelperService implements IExcelFileHelperService {

	@Autowired
	private StudentRepository studentrepository;
	
	@Autowired
	private UserAccountRepository userAcctRepositiory;
	
	@Override
	public List<UserAccount> saveToDatabase(MultipartFile file) throws IOException{
		UserAccountStudentWrapper wrapper=ExcelFileParser.excelToUsersandStudents(file.getInputStream());
		List<UserAccount> accountList = wrapper.getUserAccountList();
		List<Student> studentlist = wrapper.getStudentList();
		int length = studentlist.size();
		for(int i=0;i<length;++i) {
			Student s = studentlist.get(i);
			s.setUserAccount(accountList.get(i));
		}
		accountList= userAcctRepositiory.saveAll(accountList);
		studentrepository.saveAll(studentlist);
		return accountList;
	}

}
