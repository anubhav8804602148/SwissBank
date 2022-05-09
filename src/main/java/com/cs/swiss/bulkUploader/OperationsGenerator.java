package com.cs.swiss.bulkUploader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class OperationsGenerator {
	
	String[] allAvailableOperations;
	String[] allAvailableUsers;
	String[] allAvailableAccounts;
	String[] allAvailableTransactions;
	
	File operationsFile;
	File usersFile;
	File accountsFile;
	File transactionsFile;
	
	Connection con;
	Random random;
	
	public boolean loadAllValues(){
		return true;
	}
	
	public String[] readAllLinesAsArray(File file) {
		ArrayList<String> lineArray = new ArrayList<String>();
		Scanner scanner;
		try {
			scanner = new Scanner(file);
			while(scanner.hasNext()) lineArray.add(scanner.nextLine());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		scanner = null;
		return (String[]) lineArray.toArray();
	}
	
	public boolean loadFiles(){
		allAvailableOperations = readAllLinesAsArray(operationsFile);
		allAvailableUsers = readAllLinesAsArray(usersFile);
		allAvailableAccounts = readAllLinesAsArray(accountsFile);
		allAvailableTransactions = readAllLinesAsArray(transactionsFile);
		return true;
	}
	
	OperationsGenerator(Connection con){
		
		random = new Random();
		
		operationsFile = new File("D:\\my_projects\\SwissBank\\src\\main\\java\\com\\cs\\swiss\\bulkUploader\\operationsFile.dat");
		usersFile = new File("D:\\my_projects\\SwissBank\\src\\main\\java\\com\\cs\\swiss\\bulkUploader\\usersFile.dat");
		accountsFile = new File("D:\\my_projects\\SwissBank\\src\\main\\java\\com\\cs\\swiss\\bulkUploader\\accountsFile.dat");
		transactionsFile = new File("D:\\my_projects\\SwissBank\\src\\main\\java\\com\\cs\\swiss\\bulkUploader\\transactionsFile.dat");
		this.con = con;

		loadFiles();
		generateOperations();
	}
	
	public boolean generateOperations() {
		String operation = allAvailableOperations[random.nextInt(allAvailableOperations.length)];
		String user = allAvailableUsers[random.nextInt(allAvailableUsers.length)];
		String account = allAvailableAccounts[random.nextInt(allAvailableAccounts.length)];
		String transaction = allAvailableTransactions[random.nextInt(allAvailableTransactions.length)];
		
		if(operation.contains("create_account")) {
			
		}
		
		return true;
	}
	
	public static void main(String args[]) throws SQLException{
		
		new OperationsGenerator(
				DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/SwissBank?allowPublicKeyRetrieval=true&useSSL=false",
						"anubhav",
						"anubhav"
				)
		);
	}
	
}
