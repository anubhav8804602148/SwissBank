package com.cs.swiss.externalTransactions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class TransactionHandler {
	private String url;
	public TransactionHandler(){
		System.out.println("Checking any approved transactions for processing . . .");
		url = "jdbc:mysql://localhost:3306/swissbank?allowPublicKeyRetrieval=true&useSSL=false";
        String user = "anubhav";
        String password = "anubhav";
        Connection con;
        Statement fetchQuery;
        PreparedStatement updateQuery;
        ResultSet result = null;
        try {
	        con = DriverManager.getConnection(url, user, password);
	        fetchQuery = con.createStatement();
		    result = fetchQuery.executeQuery("select id, account_number, credited_amount, debited_amount from transaction where status='PENDING' and approved='1' and approval_required='1';");
		    while(result.next()) {
		    	int id = result.getInt(1);
		    	System.out.println("Fetched id : "+id);
		    	long acc_num = result.getLong(2);
		    	double cred = result.getDouble(3);
		    	double debt = result.getDouble(4);
		    	
		    	//update account balance
		    	updateQuery = con.prepareStatement("update account set balance=balance+ ? where account_number=?");
		    	updateQuery.setDouble(1, cred-debt);
		    	updateQuery.setLong(2, acc_num);
		    	updateQuery.execute();
		    	
		    	//update transaction_details
		    	updateQuery = con.prepareStatement("update transaction set status='COMPLETED' where id=?");
		    	updateQuery.setInt(1, id);
		    	updateQuery.execute();
		    	
		    }
        }
        catch(Exception ex) {
        	ex.printStackTrace();
        }
		System.out.println("Done for this round. Sleeping for 100 sec now.");
	}
	public static void runHandler() {

		while(true) {
			new TransactionHandler();
			try {
				Thread.sleep(100000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}
