package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import db.DB;
import db.DbException;

public class Program {

	public static void main(String[] args) throws ParseException{
		
		Connection conn = null;
		PreparedStatement st = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			
			conn = DB.getConnection();
			st = conn.prepareStatement("INSERT INTO seller "
				+ "(Name, Email, Birthdate, BaseSalary, DepartmentId)"
				+ "VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, "Carl Purple");
			st.setString(2, "carl@gmail.com");
			st.setDate(3, new java.sql.Date(sdf.parse("22/04/1985").getTime()));
			st.setDouble(4, 3000.00);
			st.setInt(5, 4);
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				
				while(rs.next()) {
					int id = rs.getInt(1);
					
					System.out.println("Done! id = " + id);
				}				
				
			}else {
				System.out.println("No rows affected!");
			}
			
		}catch(DbException e) {
			System.out.println(e.getMessage());
			
		}catch(SQLException e) {
			e.printStackTrace();
			
		}finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
		
	}

}
