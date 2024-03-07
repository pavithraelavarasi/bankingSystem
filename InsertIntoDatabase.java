package com.sfr.bankingSystem;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Connection;
import java.util.HashMap;

class InsertIntoDatabase {
	Validation valid = new Validation();
	Display display = new Display();
	private static Connection con = Database.getInstance().makeConnection();
	public void insertRole()
	{
		try(ResultSet rs = Database.st.executeQuery("select * from role"))
		{
			int count = 0;
			while(rs.next())
			{
				count++;
			}
			if(count == 0)
			{
				String query = "INSERT INTO role (role) values('admin'),('manager'),('clerk'),('accountant'),('customer')";
				Database.st.executeUpdate(query);
				System.out.println("Role entered in role table");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void insertLogin(String login,String pass,int role_id)
        {
                try
                {
			if(!valid.isLogin(login))
			{
                        String query = "insert into login(login,password,role_id) values (?,?,?)";
                        PreparedStatement pst = con.prepareStatement(query);
                        pst.setString(1,login);
                        pst.setString(2,pass);
			pst.setInt(3,role_id);
                        pst.executeUpdate();
                        System.out.println("Login inserted");
			}
			else
			{
				System.out.println("The login is already exists");
			}
                }
                catch(Exception e)
                {
                        e.printStackTrace();
                }
        }
	public void interest()
	{
		try(ResultSet rs = Database.st.executeQuery("select * from interest"))
		{
			 int count = 0;
                        while(rs.next())
                        {
                                count++;
                        }
                        if(count == 0)
                        {
				String query = "insert into interest values ('minor scheme',2.5),('adult scheme',2.0),('senior citizen',3.5)";
				Database.st.executeUpdate(query);
				System.out.println("interest inserted");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void insertAdmin(HashMap<Integer,String> role,UserModel users) throws Exception
        {
			int id = valid.getRoleId(role,"admin");
			insertLogin(users.getLogin(),users.getPass(),id);
                        String query = "insert into all_users(name,login,password,phone,role_id) values(?,?,?,?,?)";
                        PreparedStatement pst = con.prepareStatement(query);
                        pst.setString(1,users.getName());
                        pst.setString(2,users.getLogin());
			pst.setString(3,users.getPass());
                        pst.setLong(4,users.getPhone());
                        pst.setInt(5,id);
                        pst.executeUpdate();
                        System.out.println("Admin inserted to all users");
        }
	public void insertManager(HashMap<Integer,String> role,UserModel users)
        {
                try(ResultSet rs = Database.st.executeQuery("select role_id from login where role_id = 2"))
                {
			int count = 0;
			while(rs.next())
			{
				count++;
			}
			if(count == 0)
			{
			int id = valid.getRoleId(role,"manager");
                        insertLogin(users.getLogin(),users.getPass(),id);
                        String query = "insert into all_users(name,login,password,phone,role_id) values(?,?,?,?,?)";
                        PreparedStatement pst = con.prepareStatement(query);
                        pst.setString(1,users.getName());
                        pst.setString(2,users.getLogin());
                        pst.setString(3,users.getPass());
                        pst.setLong(4,users.getPhone());
                        pst.setInt(5,id);
                        pst.executeUpdate();
                        System.out.println("Manager inserted to all users");
			}
			else
			{
				System.out.println("\t\t already manager is here");
			}
                }
                catch(Exception e)
                {
                        e.printStackTrace();
                }
        }
	void insertClerk(HashMap<Integer,String> role,UserModel users)
        {
                try
                {
			int id = valid.getRoleId(role,"clerk");
                        insertLogin(users.getLogin(),users.getPass(),id);
                        String query = "insert into all_users(name,login,password,phone,role_id) values(?,?,?,?,?)";
                        PreparedStatement pst = con.prepareStatement(query);
                        pst.setString(1,users.getName());
                        pst.setString(2,users.getLogin());
                        pst.setString(3,users.getPass());
                        pst.setLong(4,users.getPhone());
                        pst.setLong(5,id);
                        pst.executeUpdate();
                        System.out.println("\t\tCLERK ADDED SUCCESSFULLY");
                }
                catch(Exception e)
                {
                        e.printStackTrace();
                }
        }
	public void insertAccountant(HashMap<Integer,String> role,UserModel users)
        {
                try
                {
			int id = valid.getRoleId(role,"accountant");
			insertLogin(users.getLogin(),users.getPass(),id);
                        String query = "insert into all_users(name,login,password,phone,role_id) values(?,?,?,?,?)";
                        PreparedStatement pst = con.prepareStatement(query);
                        pst.setString(1,users.getName());
                        pst.setString(2,users.getLogin());
			pst.setString(3,users.getPass());
                        pst.setLong(4,users.getPhone());
                        pst.setLong(5,id);
                        pst.executeUpdate();
			System.out.println("\t\t ACCOUNTANT ADDED SUCCESSFULLY");
                }
                catch(Exception e)
                {
                        e.printStackTrace();
                }
        }
	public void insertCustomer(HashMap<Integer,String>role,CustomerModel customer,java.sql.Date dob_sql)
	{
		try
                {
			int id = valid.getRoleId(role,"customer");
			insertLogin(customer.getLogin(),customer.getPass(),id);
			String query = "create sequence if not exists customer_id_seq start with 101 increment by 1";
			Database.st.executeUpdate(query);
			System.out.println("Customer id sequence created");
			PreparedStatement pst = con.prepareStatement("insert into customers(customer_id,name,login,password,date_of_birth,adhar_number,pan_number,phone,role_id) values(nextval('customer_id_seq'),?,?,?,?,?,?,?,?)");
                        pst.setString(1,customer.getName());
                        pst.setString(2,customer.getLogin());
			pst.setString(3,customer.getPass());
			pst.setDate(4,dob_sql);
			pst.setLong(5,customer.getAdhar());
			pst.setString(6,customer.getPan());
			pst.setLong(7,customer.getPhone());
                        pst.setInt(8,id);
                        pst.executeUpdate();
			System.out.println("\t\t CUSTOMER DETAILS ADDED SUCCESSFULLY");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void insertCustomerAnotherAcc(String login,String branch,String scheme,String account_type)
        {

                try(ResultSet rs = Database.st.executeQuery("select customer_id from customers where login = '"+login+"'"))
                {
                        rs.next();
                        int cus_id = rs.getInt(1);
			PreparedStatement pst = con.prepareStatement("insert into account_details(customer_id,account_number,branch,scheme,account_type) values(?,nextval('account_number_seq'),?,?,?)");
			pst.setInt(1,cus_id);
                 	pst.setString(2,branch);
		       	pst.setString(3,scheme);
			pst.setString(4,account_type);
			pst.executeUpdate();	
                        System.out.println("\t\t ACCOUNT CREATED SUCCESSFULLY");
			
                }
                catch(Exception e)
                {
                        e.printStackTrace();
                }
        }
	public void insertIntoAccountDetails(String login,String branch,String scheme,String account_type)
        {
                try
                {
                        String query = "create sequence if not exists account_number_seq increment 1 start 123456789001";
                        Database.st.executeUpdate(query);
                        System.out.println("\t\t sequence created");
                        int id = valid.getIdFromLogin(login);
                        query = "insert into account_details (customer_id,account_number,branch,scheme,account_type) values (?,nextval('account_number_seq'),?,?,?)";
			
                        PreparedStatement pst = con.prepareStatement(query);
                        pst.setInt(1,id);
                        pst.setString(2,branch);
                        pst.setString(3,scheme);
                        pst.setString(4,account_type);
                        pst.executeUpdate();
                        System.out.println("\t\t ACCOUNT DETAILS ADDED SUCCESSFULLY");
                }
                catch(Exception e)
                {
                        e.printStackTrace();
                }
        }
	public void insertDepositHistory(int cus_id,long acc_num,long deposit,long balance)
        {
                try
                {
                        Timestamp date = new Timestamp(valid.getToday().getTime());
                        String query = "insert into transaction_history(customer_id,account_number,date,description,deposits,balance) values(?,?,?,?,?,?)";
                        String des = "Deposit";
                        PreparedStatement pst = con.prepareStatement(query);
                        pst.setInt(1,cus_id);
			pst.setLong(2,acc_num);
                        pst.setTimestamp(3,date);
                        pst.setString(4,des);
                        pst.setLong(5,deposit);
                        pst.setLong(6,balance);
                        pst.executeUpdate();
                        System.out.println("\t\t DEPOSITED SUCCESSFULLY");
                }
                catch(Exception e)
                {
                        e.printStackTrace();
                }
        }
	public void insertWithdrawHistory(int cus_id,long acc_num,long withdraw,long balance)
        {
                try
                {
                        Timestamp date = new Timestamp(valid.getToday().getTime());
                        String query = "insert into transaction_history(customer_id,account_number,date,description,withdrawals,balance) values(?,?,?,?,?,?)";
			String des = "Debit";
//                        String des = "WITHDRAWAL";
                        PreparedStatement pst = con.prepareStatement(query);
                        pst.setInt(1,cus_id);
			pst.setLong(2,acc_num);
                        pst.setTimestamp(3,date);
                        pst.setString(4,des);
                        pst.setLong(5,withdraw);
                        pst.setLong(6,balance);
                        pst.executeUpdate();
                        System.out.println("\t\t WITHDRAWAL HAS BEEN SUCCESSFULLY COMPLETED");
                }
                catch(Exception e)
                {
                        e.printStackTrace();
                }
        }
	public void insertPayBills(int cust_id,long acc_num,String bill,long bill_amt,long balance)
        {
                try
                {
                        Timestamp today = new Timestamp(valid.getToday().getTime());
                        String query = "insert into pay_bills(customer_id,account_number,date,bill,amount) values (?,?,?,?,?)";
                        PreparedStatement pst = con.prepareStatement(query);
                        pst.setInt(1,cust_id);
			pst.setLong(2,acc_num);
                        pst.setTimestamp(3,today);
                        pst.setString(4,bill);
                        pst.setLong(5,bill_amt);
                        pst.executeUpdate();
                        System.out.println("\t\t BILL ADDED TO THE BILL TABLE ");
                        String des = bill;
                        query = "insert into transaction_history(customer_id,account_number,date,description,bill,balance) values(?,?,?,?,?,?)";
                        System.out.println("\t\t Bill amt :"+ bill_amt+"\tbalance :"+balance);
                        pst = con.prepareStatement(query);
                        pst.setInt(1,cust_id);
			pst.setLong(2,acc_num);
                        pst.setTimestamp(3,today);
                        pst.setString(4,des);
                        pst.setLong(5,bill_amt);
                        pst.setLong(6,balance);
                        pst.executeUpdate();
                        System.out.println("\t\t BILL AMOUNT ADDED TO YOUR TRANSACTION HISTORY");
                }
                catch(Exception e)
                {
                        e.printStackTrace();
                }
        }
	public void insertTransfer(int cus_id,long acc_num,long re_acc_num,long amount,long balance)
        {
                try
                {
                        Timestamp date = new Timestamp(valid.getToday().getTime());
                        String query = "insert into transfer_funds values(?,?,?,?,?)";
                        PreparedStatement pst = con.prepareStatement(query);
                        pst.setInt(1,cus_id);
                        pst.setLong(2,acc_num);
                        pst.setLong(3,re_acc_num);
                        pst.setLong(4,amount);
                        pst.setTimestamp(5,date);
                        pst.executeUpdate();
                        System.out.println("Tranfer table inserted");
                        query = "insert into transaction_history (customer_id,account_number,date,description,transfer,balance) values(?,?,?,?,?,?)";
                        String des = "Sent";
                        pst = con.prepareStatement(query);
                        pst.setInt(1,cus_id);
			pst.setLong(2,acc_num);
                        pst.setTimestamp(3,date);
                        pst.setString(4,des);
                        pst.setLong(5,amount);
                        pst.setLong(6,balance);
                        pst.executeUpdate();
                        System.out.println("\t\t TRANFER FUND ADDED TO YOUR TRANSACTION HISTORY ");
                }
                catch(Exception e)
                {
                        e.printStackTrace();
                }
        }
	public void insertInterest(int cus_id,String acc_type,double interest,long acc_num)
	{
		try
		{
			Timestamp today = new Timestamp(valid.getToday().getTime());
			String query = "insert into account_interest(customer_id,account_number,date,scheme,interest) values (?,?,?,?,?)";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1,cus_id);
			pst.setLong(2,acc_num);
			pst.setTimestamp(3,today);
			pst.setString(4,acc_type);
			pst.setDouble(5,interest);
			pst.executeUpdate();
			System.out.println("\t\t INTEREST ADDED SUCCESSFULLY");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void addInterestToBalance(String login)
        {
                 int cus_id = valid.getIdFromLogin(login);
		 
//		 double interest = valid.getInterest(login);
                 display.accountDetails(login);
                 System.out.println("Enter your account_number");
                 long acc_num = BankingSystem.sc.nextLong();
		 if(!valid.is3Months(acc_num))
		 {
			 System.out.println("\t\t Oooppss...only once in 3 months you have to add the interest to the account balance");
			 return;
		 }
		 double interest = Bank.bank.totalInterestOfAccount(acc_num);
		 System.out.println("Interst :"+interest);
                 try(ResultSet rs = Database.st.executeQuery("select balance from transaction_history where customer_id = "+cus_id+" and account_number = "+acc_num+" order by date desc"))
                 {
                         while(rs.next())
                         {
                                 double balance = rs.getLong(1);
                                 balance = balance + interest;
				 Timestamp date = new Timestamp(valid.getToday().getTime());
				 String des = "Interest";
                                 String query = "insert into transaction_history (customer_id,account_number,date,description,balance) values(?,?,?,?,?)";
                                 PreparedStatement pst = con.prepareStatement(query);
				 pst.setInt(1,cus_id);
				 pst.setLong(2,acc_num);
				 pst.setTimestamp(3,date);
				 pst.setString(4,des);
				 pst.setDouble(5,balance);
				 pst.executeUpdate();
                         }
                                 System.out.println("\t\t INTEREST AMOUNT ADDED");
                 }
                 catch(Exception e)
                 {
                         e.printStackTrace();
                 }
        }
	public void updatePassword(String login,long phone,String pass)
        {
                try
                {
                        String query = "update customers set password = '"+pass+"' where login = '"+login+"' and phone = "+phone;
                        Database.st.executeUpdate(query);
                        System.out.println("Password updated");
			Database.st.executeUpdate("update login set password = '"+pass+"' where login = '"+login+"'");
			System.out.println("\t\t PASSWORD UPDATED");
                }
                catch(Exception e)
                {
                        e.printStackTrace();
                }
        }
	public void addTransAmtToReceiver(long re_acc_num,long amount)
	{
		long balance = valid.returnBalance(re_acc_num);
		try
		{
			balance = balance + amount;
			String des = "Received";
			int cus_id = valid.getIdFromAcc(re_acc_num);
			Timestamp today = new Timestamp(valid.getToday().getTime());
/*			String query = "UPDATE transaction_history AS p1 SET received = "+amount+",description = '"+des+"',balance = "+balance+" FROM (SELECT account_number, MAX(date) AS max_date FROM transaction_history WHERE account_number ="+re_acc_num+ "GROUP BY account_number) AS p2 WHERE p1.account_number = p2.account_number AND p1.date = p2.max_date";
//			String query = "update transaction_history set balance = (select balance,date from transaction_histroy) where account_number = "+re_acc_num +" ORDER BY date DESC LIMIT 1";
			Database.st.executeUpdate(query);*/
			String query = "insert into transaction_history(customer_id,account_number,date,description,received,balance) values (?,?,?,?,?,?)";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1,cus_id);
			pst.setLong(2,re_acc_num);
			pst.setTimestamp(3,today);
			pst.setString(4,des);
			pst.setLong(5,amount);
			pst.setLong(6,balance);
			pst.executeUpdate();
			System.out.println("\t\t AMOUNT ADDED TO RECEIVER ACCOUNT");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	void insertDepositAnotherAcc(int cus_id,long acc_num,long dep) // if any customer close their account the amount will be deposit to the another account number based on their option
        {
                try(ResultSet rs = Database.st.executeQuery("select account_number,balance from transaction_history where customer_id= "+cus_id +" and account_number = "+acc_num+" order by date desc"))
                {
                        while(rs.next())
                        {
                                if(rs.getLong(1) == acc_num)
                                {
                                        long balance = rs.getLong(2);
                                        balance = balance + dep;
                                        insertDepositHistory(cus_id,acc_num,dep,balance);
                                }
                                /*else
                                {
                                        System.out.println("\t\tNO SUCH ACCOUNT");
                                        return;
                                }*/
                        }
                }
                catch(Exception e)
                {
                        e.printStackTrace();
                }
        }
}
