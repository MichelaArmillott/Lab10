package it.polito.tdp.rivers.db;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class RiversDAO {

	public List<River> getAllRivers() {
		
		final String sql = "SELECT id, name FROM river";

		List<River> rivers = new LinkedList<River>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				rivers.add(new River(res.getInt("id"), res.getString("name")));
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return rivers;
	}
	
	public void getFlow(River river){
		String sql="SELECT f.day,f.flow,f.river "
				+ "FROM flow f, river r "
				+ "WHERE r.id=f.river AND r.id=? ";
		List<Flow>mis=new ArrayList<>();
		int cont=0;
		double somma=0;
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, river.getId());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				mis.add(new Flow(res.getDate("day").toLocalDate(),res.getDouble("flow"),river));
				cont++;
				somma=somma+res.getDouble("flow");
			}
			if(res.first())
				river.prima=res.getDate("day").toLocalDate();
			if(res.last())
				river.ultima=res.getDate("day").toLocalDate();
            river.nMisurazioni=mis.size();
            river.media=(somma/(double)cont);
			conn.close();
			river.setFlows(mis);
			
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

	}
}
