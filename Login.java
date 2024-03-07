package com.sfr.bankingSystem;
import java.io.Console;
import java.sql.ResultSet;
class Login {
	Console console = System.console();
	Validation valid = new Validation();
	
	public void login(String login,String pass)
	{
		try 
		{
			Admin admin = new Admin();
        		Clerk clerk = new Clerk();
        		Customer customer = new Accountant();
		        Accountant ac = new Accountant();
			int role_id = getRoleId(login);
			switch(role_id)
			{
				case 1 : admin.adminOptions(login);
					 break;
				case 2 : admin.managerOptions(login);
					 break;
				case 3 : clerk.clerkOptions(login);
					 break;
				case 4 : ac.accountantOptions(login);
					 break;
				case 5 : customer.customerOptions(login);
					 break;
				default : System.out.println("------------- Thank You -----------------");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public int getRoleId(String login) throws Exception
	{
		String query = "select login.role_id from role inner join login on role.role_id = login.role_id where login ='"+login+"'";
		ResultSet rs = Database.st.executeQuery(query);
		while(rs.next())
		{
			return rs.getInt(1);
		}
		return -1;
	}
}
