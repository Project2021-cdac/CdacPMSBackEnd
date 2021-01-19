package com.cpms.fileutils.excelfilehelper;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
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
	//do we need this?
	public static String[] HEADERS = {"PRN", "First_Name", "Last_Name", "Email", "Contact_No", "Date_Of_Birth", "Course"};
	static String SHEET = "Sheet1";

	public static boolean hasExcelFormat(MultipartFile file) {
		if (!TYPE.equals(file.getContentType()))
			return false;
		return true;
	}
	//TODO figure out how to skip blank cells
	public static UserAccountStudentWrapper excelToUsersandStudents(InputStream istream) throws IOException {
		try (
				Workbook workbook = new XSSFWorkbook(istream);
			){
			Sheet sheet = workbook.getSheet(SHEET);	
			List<UserAccount> userAccountList = new ArrayList<UserAccount>();
			List<Student> studentList = new ArrayList<Student>();				
			Iterator<Row> rows = sheet.iterator();
			boolean dataReadFromCell;
			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();
				// skip header row
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}				
				dataReadFromCell = false;
				Iterator<Cell> cellsInRow = currentRow.iterator();
				System.out.println("Row started");
				UserAccount userAccount= new UserAccount();
				Student student = new Student();
				int cellIdx = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();
					if(CellType.BLANK != currentCell.getCellType()) {
//						System.out.println("Entered Cell");
						dataReadFromCell=true;
						switch (cellIdx) {
							case 0://PRN
								{
									long longValue = ((Double)currentCell.getNumericCellValue()).longValue();
	//								System.out.println("PRN: "+rowNumber+ " : " +longValue);
									student.setPrn( longValue );
									break;
								}
							case 1://FirstName
								{
									String firstName = currentCell.getStringCellValue();
			//						if()
	//								System.out.println("First Name: "+rowNumber+ " : "+firstName);
									userAccount.setFirstName(firstName);
									break;
								}
							case 2://LastName 
								{
	//								System.out.println(currentCell.getCellType());
									String lastName = currentCell.getStringCellValue();
	//								System.out.println("Last Name: "+rowNumber+ " : " +lastName);
									if(!lastName.equals("")) {
	//									System.out.println("Blank Last Name");
										userAccount.setLastName(lastName);
									}
									break;
								}
							case 3://Email
								{
									String email = currentCell.getStringCellValue();
	//								System.out.println("Email: "+rowNumber+" : "+email);
									userAccount.setEmail(email);
									break;
								}
							case 4://ContactNo
								{
			//						System.out.println(currentCell.getCellType());
									long ContactNo = ((Double)currentCell.getNumericCellValue()).longValue();
	//								System.out.println("Contact No: "+rowNumber+" : "+ContactNo);
									userAccount.setPhoneNumber(ContactNo+"");
									break;
								}
							case 5://Date_Of_Birth
								{
	//								System.out.println(currentCell.getCellType());
									String dob = currentCell.getStringCellValue();
	//								System.out.println("Date of birth: "+rowNumber+" : "+dob);
			//						userAccount.setDateOfBirth(dob.);
									userAccount.setDateOfBirth(LocalDate.parse(dob.toString(), 
											DateTimeFormatter.ofPattern("yyyy-MM-dd")));
									break;
								}
							case 6://Course
								{
									String course = currentCell.getStringCellValue().toUpperCase();
		//								System.out.println("Course "+rowNumber + " : "+course);
									userAccount.setCourseName( Course.valueOf(course) );
									break;
								}
							default:
								break;
						}		
					}
					cellIdx++;
				}
				if(dataReadFromCell) {
					userAccount.setRole(Role.ROLE_STUDENT);
					//TODO PRN Length
					String password = student.getPrn().toString().substring(8);
					System.out.println("Password : "+password);
					userAccount.setPassword(password); //last 4 digits as password
					userAccountList.add(userAccount);
					studentList.add(student);
				}
				rowNumber++;
			}
			UserAccountStudentWrapper wrapper = new UserAccountStudentWrapper();
			wrapper.setUserAccountList(userAccountList);
			wrapper.setStudentList(studentList);
			return wrapper;
		}//Autoclose work book
	}
}
