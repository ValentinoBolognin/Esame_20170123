package it.polito.tdp.borders.db;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.IntegerPair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BordersDAO {
	
	public List<Country> loadAllCountries() {
		
		String sql = 
				"SELECT ccode,StateAbb,StateNme " +
				"FROM country " +
				"ORDER BY StateAbb " ;

		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet rs = st.executeQuery() ;
			
			List<Country> list = new LinkedList<Country>() ;
			
			while( rs.next() ) {
				
				Country c = new Country(
						rs.getInt("ccode"),
						rs.getString("StateAbb"), 
						rs.getString("StateNme")) ;
				
				list.add(c) ;
			}
			
			conn.close() ;
			
			return list ;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null ;
	}
	
	public List<IntegerPair> getCountryPairs(int anno) {
		String sql = "SELECT state1no, state2no " +
				"FROM contiguity " +
				"WHERE year<=? " +
				"AND conttype=1 " ;
		
		List<IntegerPair> result = new ArrayList<>();
		
		Connection conn = DBConnect.getConnection() ;
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			
			ResultSet res = st.executeQuery() ;
			
			while (res.next()) {
				result.add( new IntegerPair(
						res.getInt("state1no"), 
						res.getInt("state2no"))) ;
			}

			conn.close();
			return result ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}	
	
	}
	
	public static void main(String[] args) {
		List<Country> list ;
		BordersDAO dao = new BordersDAO() ;
		list = dao.loadAllCountries() ;
		for(Country c: list) {
			System.out.println(c);
		}
	}
	
	
}
