package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import entity.News;

public class NewsDao {
	private SessionFactory sf;

	public NewsDao(SessionFactory sf) {
		this.sf = sf;
	}

	public List<News> getAllByBP(int id) {
		return getAllByBPPlusParam(id, "");
	}
	
	@SuppressWarnings("unchecked")
	public List<News> getAllByBPPlusParam(int id, String sqlWithParam) {
		Session session = sf.openSession();
		String sql = "SELECT wo.start as start, wo.term as planed_term, wo.stop as stop, "
				+ "wo.planed as planed, wo.done as done, personal.name as name, work.name as bp, work.term as term, wo.rem as comment "
				+ "FROM news left join wo on news.id_wo=wo.id "
				+ "left join personal on wo.id_pers=personal.id "
				+ "left join work on wo.id_w=work.id where work.id = :id and start is not null" + sqlWithParam;
		List<News> news = (List<News>) session
				.createSQLQuery(sql).addEntity(News.class)
								.setInteger("id", id).list();
		session.close();
		return news;
	}
}
