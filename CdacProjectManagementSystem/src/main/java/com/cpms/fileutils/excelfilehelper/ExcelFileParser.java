package com.cpms.fileutils.excelfilehelper;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.cpms.pojos.Course;
import com.cpms.pojos.Role;
import com.cpms.pojos.Student;
import com.cpms.pojos.UserAccount;
import com.cpms.wrapperclasses.UserAccountStudentWrapper;

public class ExcelFileParser {
	static {
		TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	}
	public static String TYPE ;
	public static String[] HEADERS = {"PRN", "First_name", "Last_name", "Email", "ContactNo", "Date_Of_Birth", "Course"};
	static String SHEET = "Sheet1";

	public static boolean hasExcelFormat(MultipartFile file) {
		if (!TYPE.equals(file.getContentType()))
			return false;
		return true;
	}

	public static UserAccountStudentWrapper excelToUsersandStudents(InputStream istream) throws IOException {
		try (
				Workbook workbook = new XSSFWorkbook(istream);
			){
			Sheet sheet = workbook.getSheet(SHEET);	
			Iterator<Row> rows = sheet.iterator();
			List<UserAccount> userAccountList = new ArrayList<UserAccount>();
			List<Student> studentList = new ArrayList<Student>();				
			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();
				// skip header row
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}				
				Iterator<Cell> cellsInRow = currentRow.iterator();
				UserAccount userAccount= new UserAccount();
				Student student = new Student();
				int cellIdx = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();
					switch (cellIdx) {
					case 0://PRN
						student.setPrn( Long.parseUnsignedLong(currentCell.getStringCellValue()) );
						break;
					case 1://FirstName
						userAccount.setFirstName(currentCell.getStringCellValue());
						break;
					case 2://LastName 
						userAccount.setLastName(currentCell.getStringCellValue());
						break;
					case 3://Email
						userAccount.setEmail(currentCell.getStringCellValue());
						break;
					case 4://ContactNo
						userAccount.setPhoneNumber(currentCell.getStringCellValue());
					case 5://Date_Of_Birth
						userAccount.setDateOfBirth(LocalDate.parse(currentCell.getStringCellValue(), 
								DateTimeFormatter.ofPattern("yyyy-MM-dd")));
						break;
					case 6://Course
						userAccount.setCourseName( Course.valueOf(currentCell.getStringCellValue()) );
						break;
					default:
						break;
					}

					cellIdx++;
				}
				userAccount.setRole(Role.STUDENT);
				userAccount.setPassword( ( (Long)(student.getPrn()%10000) ).toString() ); //last 4 digits as password
				userAccountList.add(userAccount);
				studentList.add(student);
			}
			UserAccountStudentWrapper wrapper = new UserAccountStudentWrapper();
			wrapper.setUserAccountList(userAccountList);
			wrapper.setStudentList(studentList);
			return wrapper;
		}//Autoclose work book
	}
}
