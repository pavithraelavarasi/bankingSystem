package com.sfr.bankingSystem;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.io.Console;
import java.util.Map.Entry;
import java.util.HashMap;
import java.time.LocalDate;

class Validation {
	Console console = System.console();

	public boolean isValidPhoneNumber(long phone)
	{
		String num = String.valueOf(phone);
		if(num.length() == 10)
		{
			return true;
		}
		return false;
	}
	public boolean isAdmin()
	{
		try(ResultSet rs = Database.st.executeQuery("select role_id from login where role_id = 1"))
		{
			if(rs.next())
				return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public HashMap<Integer,String> getRoles()
	{
		HashMap<Integer,String> role = new HashMap<>();
		try(ResultSet rs = Database.st.executeQuery("select * from role"))
		{
			while(rs.next())
			{
				role.put(rs.getInt(1),rs.getString(2));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return role;
	}
	public int getRoleId(HashMap<Integer,String> role,String position)
	{
		//System.out.println(role);
		for(Entry<Integer,String> entry : role.entrySet())
		{
			if(entry.getValue().equals(position))
			{
				return entry.getKey();
			}
		}
		return -1;
	}	
	public boolean isValidAdhar(long adhar)
	{
		String num = String.valueOf(adhar);
		if(num.length() == 12)
		{
			return true;
		}
		return false;
	}
	public boolean isValidLoginPass(String login,String pass)
	{
		try(ResultSet rs = Database.st.executeQuery("select login,password from login where login = '"+login+"' and password = '"+pass+"'"))
		{
			if(rs.next())
			{
				return true;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public int getIdFromLogin(String login)
	{
		try(ResultSet rs = Database.st.executeQuery("select customer_id from customers where login = '"+login+"'"))
		{
			if(rs.next())
			{
				return rs.getInt(1);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return -1;
	}
	public boolean isLogin(String login)
	{
		try(ResultSet rs = Database.st.executeQuery("select login from login"))
		{
			while(rs.next())
			{
				if(rs.getString(1).equals(login))
				{
					return true;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public boolean isAlreadyCustomer(String login) // restrict duplicate login
	{
		try(ResultSet rs = Database.st.executeQuery("select role_id from login where login = '"+login+"'"))
                {
                        while(rs.next())
                        {
                                if(rs.getInt(1) == 5)
                                {
                                        return true;
                                }
                        }
                }
                catch(Exception e)
                {
                        e.printStackTrace();
                }
		return false;
	}
	public boolean isSufficientAmount(String login,long acc_num,long amount)
	{
		int cus_id = getIdFromLogin(login);
		try(ResultSet rs = Database.st.executeQuery("select balance from transaction_history where customer_id = "+cus_id+" and account_number = "+acc_num))
		{
			if(rs.next())
			{
				long balance = rs.getLong(1);
				if(balance > amount)
				{
					return true;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public Date getToday()
	{
		return new Date();
	}
	public long returnBalance(String login,long acc_num)
	{
		int cus_id = getIdFromLogin(login);
		try(ResultSet rs = Database.st.executeQuery("select balance from transaction_history where customer_id = "+cus_id+" and account_number = "+acc_num+" order by date desc"))
		{
			if(rs.next())
			{
				return rs.getLong(1);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}
	public long returnBalance(long acc_num)  // same method name different args (method overloading);
        {
                try(ResultSet rs = Database.st.executeQuery("select balance from transaction_history where account_number = "+acc_num+" order by date desc"))
                {
                       if(rs.next())
		       {
                           return rs.getLong(1);
                       }
                }
                catch(Exception e)
                {
                        e.printStackTrace();
                }
                return 0;
        }
	public boolean isCrtAccNum(String login,long acc_num) // check the customer have this account number
	{
		int cus_id = getIdFromLogin(login);
		try( ResultSet rs = Database.st.executeQuery("select account_number from account_details where customer_id ="+cus_id))
		{
			while(rs.next())
			{
				if(rs.getLong(1) == acc_num)
				{
					return true;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
/*	public boolean isCrtPass(String login,String pass) // validate the entering password is crt to the login
	{
		try(ResultSet rs = Database.st.executeQuery("select password from login where login = '"+login+"'"))
		{
			while(rs.next())
			{
				if(rs.getString(1).equals(pass))
				{
					return true;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}*/
	public boolean isPresentAccNum(long acc_num) // if the accNumber present in account details
	{
		try(ResultSet rs = Database.st.executeQuery("select account_number from account_details where active_status = true"))
		{
			while(rs.next())
			{
				if(rs.getLong(1) == acc_num)
				{
					return true;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public String getNameFromLogin(String login)
	{
		try(ResultSet rs = Database.st.executeQuery("select name from all_users where login='"+login+"' union select name from customers where login = '"+login+"'"))
		{
			if(rs.next())
			{
				return rs.getString(1);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
/*	public String getNameFromLogin(String login)
	{
		try(ResultSet rs = Database.st.executeQuery("select name from all_users where login = '"+login+"'"))
		{
			while(rs.next())
			{
				return rs.getString(1);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	public String getNameFromLoginCus(String login)
	{
		try(ResultSet rs = Database.st.executeQuery("select name from customers where login ='"+login+"'"))
		{
			while(rs.next())
			{
				return rs.getString(1);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}*/
	public boolean isAlreadyAccType(String login,String acc_type) // to restrict one (unique)account type to the customer
	{
		int cus_id = getIdFromLogin(login);
		try(ResultSet rs = Database.st.executeQuery("select customer_id,account_type from account_details where customer_id = "+cus_id+ " and active_status = true"))
		{
			while(rs.next())
			{
				if(rs.getString(2).equalsIgnoreCase(acc_type))
				{
					return true;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public String getAccountType(String login,long acc_num) // getting account type of the customer given account number
	{
		int cus_id = getIdFromLogin(login);
		try(ResultSet rs = Database.st.executeQuery("select account_type from account_details where customer_id = "+cus_id + " and account_number = "+acc_num +" and active_status = true"))
		{
			while(rs.next())
			{
				String acc_type = rs.getString(1);
				return acc_type;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public boolean isValidPan(String pan)
	{
        	if(pan.length() != 10)
            		return false;
        	for(int i = 0; i < 5; i++) 
		{
            	   if(!Character.isUpperCase(pan.charAt(i))) 
		   {
	                return false;
            	    }
        	}
	        for(int i = 5; i < 9; i++) 
		{
        	    if(!Character.isDigit(pan.charAt(i))) 
                	return false;
            	}	
	        if(!Character.isUpperCase(pan.charAt(9)))
        	    return false;
       	 return true;
	}
	public boolean isValidDate(String date)
	{
		int day = Integer.parseInt(date.substring(0,2));
		int month = Integer.parseInt(date.substring(3,5));
		int year = Integer.parseInt(date.substring(6,10));
		if(day > 31 || day < 1 || month > 12 || month < 1)
		{
			return false;
		}
		else if((month == 2 && year % 4 == 0 )&& day > 29)
		{
			return false;
		}
		else if((year % 4 != 0 && month == 2 ) && day > 28)
		{
			return false;
		}
		else if((month == 1 || month ==3 || month ==5 || month == 7 || month == 8 ||month == 10||month == 12) && day > 31)
		{
			return false;
		}
		else if((month == 4 || month == 6 || month == 9 || month == 11) && day > 30)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	public boolean isDateOrder(Date date) // for dob validation
	{
		if(getToday().compareTo(date) < 0)
			return false;
		return true;
	}
	public String getValidScheme(Date dob) // based on our age scheme will be provided
	{
		try
		{
			long dif_in_year = ((getToday().getTime() - dob.getTime()) / (1000l * 60 * 60 * 24 * 365));
			if(dif_in_year < 18)
			{
				return "minor scheme";
			}
			else if(dif_in_year > 60)
			{
				return "senior citizen";
			}
			else
			{
				return "adult scheme";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
/*	public long getDays(String login) 
	{
		try
		{
			int cus_id = getIdFromLogin(login);
			Date today = getToday();
			ResultSet rs = Database.st.executeQuery("select date from transaction_history where description = 'DEPOSIT' and customer_id = "+cus_id);
			while(rs.next())
			{
				Date dopDate = rs.getDate(1);
				long days = ((today.getTime() - dopDate.getTime()) / (1000 * 60 * 60 * 24 )) % 365;
				return days;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return -1;
	}*/
	public boolean isCorrectPhoneNumber(String login,long phone) // phone validation for reset password
	{
		try(ResultSet rs = Database.st.executeQuery("select phone from customers where login = '"+login+"' union select phone from all_users where login = '"+login+"'" ))
		{
			while(rs.next())
			{
				if(rs.getLong(1) == phone)
				{
					return true;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
/*	public boolean isStaffPhoneNumber(String login,long phone)
	{
		try(ResultSet rs = Database.st.executeQuery("select phone from all_users where login = '"+login+"'"))
                {
                        while(rs.next())
                        {
                                if(rs.getLong(1) == phone)
                                {
                                        return true;
                                }
                        }
                }
                catch(Exception e)
                {
                        e.printStackTrace();
                }
                return false;
        }*/
	public double getInterest(String login)
	{
		Display display = new Display();
		Console console = System.console();
		display.accountDetails(login);
		int cus_id = getIdFromLogin(login);
		System.out.println("Enter the scheme");
		String scheme = console.readLine();
		try(ResultSet rs = Database.st.executeQuery("select interest from account_interest where customer_id = "+cus_id +" and scheme ='"+scheme+"'"))
		{
			while(rs.next())
			{
				return rs.getDouble(1);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return 0.0;
	}
	public void resetPassword(String login)
	{
		try
		{
			InsertIntoDatabase insert = new InsertIntoDatabase();
			System.out.println("Enter your phone number ");
			long phone = BankingSystem.sc.nextLong();
			if(isCorrectPhoneNumber(login,phone))
			{
				System.out.println("Enter your new Password");
				String password = console.readLine();
				System.out.println("Re - enter the new password for confirmation");
				String pass = console.readLine();
				if(!password.equals(pass))
				{
					System.out.println("\t\t INVALID PHONE NUMBER");
					return;
				}
				System.out.println("\t\t NEW PASSWORD CREATED");
				insert.updatePassword(login,phone,pass);
			}
			else
			{
				System.out.println("\t\t OOPPPS ..WRONG PHONE NUMBER");
				return;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public boolean isLimitTransfer(long acc_num,String acc_type,long amount)
	{
		try
		{
			ResultSet rs = Database.st.executeQuery("select account_type from account_details where account_number = "+acc_num + " and active_status = true");
			while(rs.next())
			{
				if(rs.getString(1).equals("savings account"))
				{
					if(amount > 10000)
						return false;
				}
				else if(rs.getString(1).equals("current account"))
				{
					if(amount > 50000)
						return false;
				}
				else
					if(amount > 80000)
						return false;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public int getIdFromAcc(long acc_num)
	{
		try(ResultSet rs = Database.st.executeQuery("select customer_id from account_details where account_number = "+acc_num))
		{
			if(rs.next())
			{
				return rs.getInt(1);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}
	public boolean isOnedayInterest(long acc_num)
	{
		try(ResultSet rs = Database.st.executeQuery("select date from account_interest where account_number ="+acc_num+" order by date desc"))
		{
			while(rs.next())
			{
				Timestamp date = rs.getTimestamp(1);
				Timestamp today = new Timestamp(getToday().getTime());
				long dif  = today.getTime() - date.getTime();
				long dif_hrs = (dif / (1000 * 60 * 60)%24 );
				if(dif_hrs <= 24)
				{
					return false;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return true;
	}
	public boolean is3Months(long acc_num)
	{
		try(ResultSet rs = Database.st.executeQuery("select date from transaction_history where account_number = "+acc_num +" and description = 'INTEREST' order by date desc"))
		{
			while(rs.next())
			{
				Timestamp date = rs.getTimestamp(1);
				Timestamp today = new Timestamp(getToday().getTime());
				long dif = today.getTime() - date.getTime();
				long dif_days = (dif)/((1000 * 60 * 60 * 24) % 365);
				if(dif_days >= 91)
				{
					return true;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public boolean enoughStaff(int role_id)
	{
		try(ResultSet rs = Database.st.executeQuery("select count(*) from all_users where role_id = "+role_id))
		{
			while(rs.next())
			{
				int count = rs.getInt(1);
				if(count < 3)
				{
					return true;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public void changeStatus(long acc_num)
	{
		try
		{
			String query = "update account_details set active_status = 'false' where account_number ="+acc_num;
			Database.st.executeUpdate(query);
			System.out.println("status changed");
			Database.st.executeUpdate("UPDATE transaction_history SET balance = 0 WHERE id = (SELECT id FROM transaction_history where account_number = "+ acc_num +" ORDER BY date DESC LIMIT 1");
			System.out.println("Balance 0 changed");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public java.sql.Timestamp getBeforeDate(int day)
	{
		try
		{
			LocalDate today = LocalDate.now();
			LocalDate date = today.minusDays(day);
			java.sql.Date sqlDate = java.sql.Date.valueOf(date);
			Timestamp sqlTime = new Timestamp(sqlDate.getTime());
			System.out.println("Today :"+today +"\t date :"+ date +"\t sql :"+sqlDate +"\t sql Tim:"+sqlTime);
			return sqlTime;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}


}
