package com.rohan.myProject.service.impl;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rohan.myProject.entity.AllowedUser;
import com.rohan.myProject.entity.User;
import com.rohan.myProject.repository.AllowedUserRepo;
import com.rohan.myProject.repository.UserRepository;
import com.rohan.myProject.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private AllowedUserRepo allowedUserRepo;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public boolean addAllowedUserstoDb(MultipartFile file) throws IOException {

		Workbook workbook = new XSSFWorkbook(file.getInputStream());
		Sheet sheet = workbook.getSheetAt(0);
		

		for (Row row : sheet) {
			
			AllowedUser allowedUser = new AllowedUser();

			for (Cell cell : row) {
				
				switch(cell.getCellType()) {
					
	            	case NUMERIC:
	            		
	    				Integer username = Double.valueOf(cell.getNumericCellValue()).intValue();
	    				allowedUser.setUsername(username.toString());
	            		
	            		break;
	            		
	            	case STRING:
	    				String name = cell.getStringCellValue();
	    				allowedUser.setName(name);
	            		break;
	            		
	            	default:
	            		break;
				
				}

				allowedUserRepo.save(allowedUser);

			}

		}

		workbook.close();
		return true;

	}

	@Override
	public List<User> getUsersList() {
		
		List<User> userList = userRepository.findAll();
		
		return userList;
		
	}

}
