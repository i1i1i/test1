package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import entity.BussinesProcess;

public class BussinessProcessDao {
	private SessionFactory sf;

	public BussinessProcessDao(SessionFactory sf) {
		this.sf = sf;
	}

	@SuppressWarnings("unchecked")
	public List<BussinesProcess> getAll() {
		Session session = sf.openSession();
		List<BussinesProcess> processes = session.createQuery(
				"from BussinesProcess").list();
		session.close();
		return processes;
	}

	public BussinesProcess getOne(int id) {
		Session session = sf.openSession();
		BussinesProcess process = (BussinesProcess) session
				.createQuery("from BussinesProcess where id = :id")
				.setInteger("id", id).uniqueResult();
		session.close();
		return process;
	}
	
	public BussinesProcess getOneByName(String name) {
		Session session = sf.openSession();
		BussinesProcess process = (BussinesProcess) session
				.createQuery("from BussinesProcess where name = :name")
				.setString("name", name).uniqueResult();
		session.close();
		return process;
	}
}
