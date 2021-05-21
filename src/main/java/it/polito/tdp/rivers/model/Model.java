package it.polito.tdp.rivers.model;

import java.util.List;

import it.polito.tdp.rivers.db.RiversDAO;

public class Model {
	RiversDAO dao=new RiversDAO();

	public List<River> getRiver() {
		// TODO Auto-generated method stub
		return dao.getAllRivers();
	}
	public void inf(River river) {
		dao.getFlow(river);
	}

}
