package com.sfr.bankingSystem;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.PreparedStatement;

class Display {
	Validation valid = new Validation();
	private static Connection con = Database.getInstance().makeConnection();

	public void accountDetails(String login)
	{
		try
		{
			int id = valid.getIdFromLogin(login);
			String query = "select customer_id,account_number,account_type,scheme from account_details where customer_id = "+id;
			ResultSet rs = Database.st.executeQuery(query);
			while(rs.next())
			{
				System.out.println("customer id :"+rs.getInt(1)+"\t Account number :"+rs.getLong(2)+"\t Account type :"+rs.getString(3)+"\tAccount scheme :"+rs.getString(4));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public ArrayList getAccNo(int id)
	{
		ArrayList acc_num = new ArrayList();
		int k = 0;
		try(ResultSet rs = Database.st.executeQuery("select account_number from account_details where customer_id ="+id))
		{
			while(rs.next())
			{
				acc_num.add(rs.getLong(1));
			}
			return acc_num;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return acc_num;
	}
	public void viewAccountDetails(String login)
	{
		try
                {
			Statement st = con.createStatement();
                     	int id = valid.getIdFromLogin(login);
			ArrayList acc_num = getAccNo(id);
		//	System.out.println("The account numberslist :"+acc_num);
			for(int i = 0;i<acc_num.size();i++)
			{
                        String query = "select customer_id,account_number,account_type,branch,scheme from account_details where customer_id = "+id + " and account_number = "+acc_num.get(i);
                        ResultSet rs = Database.st.executeQuery(query);
			System.out.println("\t\t YOUR ACCOUNT INFORMATION\n");
                        while(rs.next())
                        {
                                System.out.println("customer id :"+rs.getInt(1)+"\nAccount number :"+rs.getLong(2)+"\nAccount type :"+rs.getString(3)+"\nBranch :" +rs.getString(4)+"\nScheme :"+rs.getString(5));
				query = "select balance from transaction_history where customer_id = "+id+" and account_number ="+acc_num.get(i)+" order by date desc limit 1";
				rs = st.executeQuery(query);
				while(rs.next()) {
				long bal = rs.getLong(1);
				System.out.println("Balance :"+bal);
				}
                        }
			}
                }
                catch(Exception e)
                {
                        e.printStackTrace();
                }
	}
/*	public void viewAccountBalance(String login)
	{
		try
		{
			int id = valid.getIdFromLogin(login);
                        ArrayList acc_num = getAccNo(id);
			for(int i = 0;i<acc_num.size();i++)
			{
				ResultSet rs = Database.st.executeQuery("select balance from transaction_history where customer_id = "+id+" and account_number ="+acc_num.get(i)+" order by date desc");
				while(rs.next())
				{
					long bal = rs.getLong(1);
					System.out.println("\t Your account number :"+ acc_num.get(i) +" Balance : "+ bal);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}*/
	public void viewMyTransaction(String login)
	{
		try
		{
			int cus_id = valid.getIdFromLogin(login);
			String query = "select customer_id,account_number,date,description,bill,transfer,withdrawals,deposits,balance from transaction_history where customer_id = "+cus_id;
                        ResultSet rs = Database.st.executeQuery(query);
                        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\nCustomer id |\taccount number \t|\tDate \t\t\t|\tDescription\t|     Bill\t|\tTransfer amount |   Withdrawals |    Deposits   |\tBalance");
                        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                        while(rs.next())
                        {
                                System.out.println("  "+rs.getInt(1)+"\t    |\t"+rs.getLong(2)+"\t| "+rs.getTimestamp(3)+"\t|\t"+rs.getString(4)+"\t\t|\t"+rs.getLong(5)+"\t|\t\t"+rs.getLong(6)+"\t|\t"+rs.getLong(7)+"\t|\t"+rs.getLong(8)+"\t|\t"+rs.getLong(9));
                        }
                        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                }
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void viewTransactionHistory()
	{
		try
		{
			String query = "select customer_id,account_number,date,description,bill,transfer,withdrawals,deposits,balance from transaction_history";
                        ResultSet rs = Database.st.executeQuery(query);
                        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\nCustomer id |\taccount number \t|\tDate \t\t\t|\tDescription\t|      Bill\t|\tTransfer amount |   Withdrawals |    Deposits   |\tBalance");
			System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                        while(rs.next())
                        {
                                System.out.println("  "+rs.getInt(1)+"\t    |\t"+rs.getLong(2)+"\t| "+rs.getTimestamp(3)+"\t|\t"+rs.getString(4)+"\t\t|\t"+rs.getLong(5)+"\t|\t"+rs.getLong(6)+"\t\t|\t"+rs.getLong(7)+"\t|\t"+rs.getLong(8)+"\t|\t"+rs.getLong(9));
                        }
			System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                }
                catch(Exception e)
                {
                        e.printStackTrace();
                }
	}
	public void viewTransactionHistory(Timestamp sqlTime)
        {
                try
                {
                        String query = "select customer_id,account_number,date,description,bill,transfer,withdrawals,deposits,balance from transaction_history where date >= ?";
			PreparedStatement pstmt = con.prepareStatement(query);
		        pstmt.setTimestamp(1, sqlTime);
		        ResultSet rs = pstmt.executeQuery();
                //        ResultSet rs = Database.st.executeQuery(query);
                        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\nCustomer id |\taccount number \t|\tDate \t\t\t|\tDescription\t|      Bill\t|\tTransfer amount |   Withdrawals |    Deposits   |\tBalance");
                        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                        while(rs.next())
                        {
                                System.out.println("  "+rs.getInt(1)+"\t    |\t"+rs.getLong(2)+"\t| "+rs.getTimestamp(3)+"\t|\t"+rs.getString(4)+"\t\t|\t"+rs.getLong(5)+"\t|\t"+rs.getLong(6)+"\t\t|\t"+rs.getLong(7)+"\t|\t"+rs.getLong(8)+"\t|\t"+rs.getLong(9));
                        }
                        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                }
                catch(Exception e)
                {
                        e.printStackTrace();
                }
        }

	public void viewCustomersNameId()
	{
		try
		{
			HashMap<Integer,String> customers = new HashMap<>();
			String query = "select role_id from role where role = 'customer'";
			ResultSet rs = Database.st.executeQuery(query);
			rs.next();
			int id = rs.getInt(1);
			query = "select customer_id,name from customers where role_id = "+id;
			rs = Database.st.executeQuery(query);
			while(rs.next())
			{
				customers.put(rs.getInt(1),rs.getString(2));
			}
			System.out.println("----------------+-------------------------\nCustomer id  \t|\tCustomer Name\n----------------+-------------------------");
			for(Map.Entry<Integer,String> entry : customers.entrySet())
			{
				System.out.println("\t"+entry.getKey() + " \t|\t " + entry.getValue());
			}
			System.out.println("----------------+-------------------------");
		}	
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void viewCustomers()
	{
		try
                {
			ArrayList<CustomerModel> customers = new ArrayList<>();
                        String query = "select role_id from role where role = 'customer'";
                        ResultSet rs = Database.st.executeQuery(query);
                        rs.next();
                        int id = rs.getInt(1);
                        query = "select name,login,password,adhar_number,pan_number,phone from customers where role_id = "+id;
			rs = Database.st.executeQuery(query);
			while(rs.next())
			{
				String name = rs.getString(1);
				String login = rs.getString(2);
				String pass = rs.getString(3);
				long adhar = rs.getLong(4);
				String pan = rs.getString(5);
				long phone = rs.getLong(6);
				CustomerModel customer = new CustomerModel(name,phone,login,pass,adhar,pan);
				customers.add(customer);
			}
			System.out.println("\t\t CUSTOMERS PERSONAL DETAILS");
			for(CustomerModel cus : customers)
			{
				System.out.println(cus);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void viewTranferFunds(String login)
	{
		try
		{
			int cus_id = valid.getIdFromLogin(login);
			ArrayList<Long> acc_num = getAccNo(cus_id);
			System.out.println("---------------------------------------------------------------------------------------------------------------------\nCustomerId |\tmy account number |\t receiver account number|\t amount |\t\t date\n---------------------------------------------------------------------------------------------------------------------");
			for(int i = 0;i<acc_num.size();i++)
			{
//				String query = "select sender_account_number,receiver_account_number,amount from transfer_funds where sender_account_number ="+acc_num.get(i);
				String query = "select * from transfer_funds where sender_account_number = "+acc_num.get(i);
				ResultSet rs = Database.st.executeQuery(query);
				while(rs.next())
				{
//					System.out.println(rs.getLong(1)+"\t"+rs.getLong(2)+"\t"+rs.getLong(3));
					System.out.println(rs.getInt(1)+"\t|\t"+rs.getLong(2)+"\t|\t"+rs.getLong(3)+"\t\t|\t"+rs.getLong(4)+"\t|\t"+rs.getTimestamp(5));
				}
			}
			System.out.println("---------------------------------------------------------------------------------------------------------------------");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void viewStaffs()
	{
		try(ResultSet rs = Database.st.executeQuery("SELECT a.id,a.name, l.login, l.role_id, r.role FROM all_users a INNER JOIN login l ON a.login = l.login INNER JOIN role r ON l.role_id = r.role_id where l.role_id in (2,3,4)"))
		{
			int i =1;
			while(rs.next())
			{

				System.out.println("----------------------Staff "+ i+" -----------------------");
				System.out.println("Id :"+rs.getInt(1) +"\nName : "+rs.getString(2)+"\nLogin : "+rs.getString(3)+"\nRole id : "+rs.getInt(4)+"\nRole : "+rs.getString(5));
				i++;
			}
			System.out.println("-----------------------------------------------------------------------------");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void viewTransactionPeriod()
	{
		try
		{
			System.out.println("Enter any option");
			boolean flag = true;
			while(flag)
			{
				System.out.println("1 - last 7 days (one week) \n2 - last 30 days(one month) \n3 - last 90 days (3 months) \n4 - exit");
				int option = BankingSystem.sc.nextInt();
				switch(option)
				{
					case 1 :
						 viewTransactionHistory(valid.getBeforeDate(7));
						 break;
					case 2 : 
						 viewTransactionHistory(valid.getBeforeDate(30));
						 break;
					case 3 : 
						 viewTransactionHistory(valid.getBeforeDate(90));
						 break;
					case 4 : 
						 flag = false;
						 break;
					default : System.out.println("Invalid");
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}


