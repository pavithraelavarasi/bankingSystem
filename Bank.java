package com.sfr.bankingSystem;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Timestamp;
class Bank {
	private long mini_bal;
	String acc_type;
	Validation valid = new Validation();
	InsertIntoDatabase insert = new InsertIntoDatabase();
	static Bank bank = new Bank();
	Bank(long mini_bal)
	{
		this.mini_bal = mini_bal;
	}
	Bank()
	{
	}
	public void setMiniBal(long mini_bal)
	{
		this.mini_bal = mini_bal;
	}
	public long getMiniBal()
	{
		return mini_bal;
	}
	public boolean isCrtMinimumBal(long deposit,String acc_type)
	{
		if(acc_type.equals("savings account") && (deposit < 500))
		{
			return false;
		}
		else if(acc_type.equals("current account") && (deposit < 10000))
        	{
                        return false;
		}
		return true;
	}
	public long viewMinimumBalance(String acc_type)
	{
		if(acc_type.equalsIgnoreCase("savings account"))
               	{
                       	bank.setMiniBal(500);
			System.out.println("Saving accouunt minimum Balance :"+bank.getMiniBal());
			return bank.getMiniBal();
        	}
               	else if(acc_type.equalsIgnoreCase("current account"))
	        {
        	     bank.setMiniBal(10000);
				System.out.println("Current account minimum balance :"+bank.getMiniBal());
		     return bank.getMiniBal();
                }
	        else if(acc_type.equalsIgnoreCase("salary account"))
        	{
               		bank.setMiniBal(0);
				System.out.println("Salary account Minimum balance :"+bank.getMiniBal());
			return bank.getMiniBal();
	        }
		return -1;
	}
	public void calculation(String login)
        {
                int cus_id = valid.getIdFromLogin(login);
		System.out.println("\t\t Customer id :"+cus_id);
                ArrayList<String> scheme = new ArrayList<>();
                ArrayList<Long> acc_num = new ArrayList<>();
                double rate = 0.0;
                try(ResultSet rs = Database.st.executeQuery("select account_number from account_details where customer_id = "+cus_id))
                {
                        while(rs.next())
                        {
                                acc_num.add(rs.getLong(1));
                        }
                        for(int i =0;i<acc_num.size();i++)
                        {
				System.out.println("T/F :"+valid.isOnedayInterest(acc_num.get(i)));
				if(!valid.isOnedayInterest(acc_num.get(i)))
				{
					System.out.println("\t\t oops..interest calculated recently ..wait 24 hrs to calculate again");
					return;
				}
                                ResultSet res = Database.st.executeQuery("select scheme from account_details where account_number = "+acc_num.get(i));
                                while(res.next())
                                {

                                        scheme.add(res.getString(1));
				}
				System.out.println("Account scheme :"+ scheme);
				System.out.println("Account number :"+acc_num);
                                if(scheme.get(i).equals("minor scheme"))
                                {
					double balance = valid.returnBalance(login,acc_num.get(i));
                                        double interest = (balance*3.0*1) / (100*365);
                                        insert.insertInterest(cus_id,scheme.get(i),interest,acc_num.get(i));
                                }
                                else if(scheme.get(i).equals("adult scheme"))
                                {
					double balance = valid.returnBalance(login,acc_num.get(i));
                                        double interest = (balance*2.5*1)/ (100*365);
                                        insert.insertInterest(cus_id,scheme.get(i),interest,acc_num.get(i));
                               }
                               else if(scheme.get(i).equals("senior citizen"))
                               {
				       double balance = valid.returnBalance(login,acc_num.get(i));
                                       double interest = (balance*3.0*1)/ (100*365);
                                       insert.insertInterest(cus_id,scheme.get(i),interest,acc_num.get(i));
                               }
                        }
               }
                catch(Exception e)
                {
                        e.printStackTrace();
                }
        }
	public double totalInterestOfAccount(long acc_num)
	{
		ArrayList<Double> interest = new ArrayList<>();
		try(ResultSet rs = Database.st.executeQuery("select interest from account_interest where account_number = "+acc_num))
		{
			while(rs.next())
			{
				interest.add(rs.getDouble(1));
			}
			double acc_int = 0;
			for(int i = 0;i<interest.size();i++)
			{
				acc_int = acc_int + interest.get(i);
			}
			return acc_int;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}
}	
