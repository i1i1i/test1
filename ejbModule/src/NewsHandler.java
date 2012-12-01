package src;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.SessionFactory;

import dao.BussinessProcessDao;
import dao.NewsDao;
import entity.BussinesProcess;
import entity.News;

public class NewsHandler {
	private BussinessProcessDao bpDao;
	private NewsDao newsDao;

	public static enum Keys {
		HANDLED, TERM, NEWS, VARIANCE, AVERAGE_TERM, MIN_TERM, MAX_TERM, OVERHEAD_AMOUNT_PERCENT, OVERHEAD_PERCENT;
	}

	public NewsHandler(SessionFactory sf) {
		this.bpDao = new BussinessProcessDao(sf);
		this.newsDao = new NewsDao(sf);
	}

	public Map<Keys, Object> countWholeStatistics(String bpName,
			List<Integer> years) {
		Map<Keys, Object> result = new HashMap<>();
		BussinesProcess bp = bpDao.getOneByName(bpName);
		List<News> news = getNewsWithParamByBPId(bp.getId(), years);
		if (ArrayUtils.isEmpty(news.toArray()))
			return null;
		// handled scores
		result.put(Keys.HANDLED, news.size());
		// the biggest news score
		News max = getBiggestNews(news);
		result.put(Keys.NEWS, max);
		// average duration of process handling
		result.put(Keys.AVERAGE_TERM, getAverageTerm(news));
		result.put(Keys.MIN_TERM, getMinTerm(news));
		result.put(Keys.MAX_TERM, max.getDuration());
		result.put(Keys.OVERHEAD_AMOUNT_PERCENT, getOverheadAmountPercent(news, bp.getBpTerm() * 24));
		result.put(Keys.OVERHEAD_PERCENT, getOverheadPercent(news, bp.getBpTerm() * 24));
		return result;
	}
	
	public List<News> getNewsWithParamByBPId(int bpId, List<Integer> years) {
		return newsDao.getAllByBPPlusParam(bpId,
				prepareDatesInSql(years));
	}

	public int getOverheadPercent(List<News> news, int expectedTerm) {
		if (news == null || news.isEmpty()) 
			return 0;
		List<Float> durations = getDurations(news);
		int sumPerc = 0;
		for (float duration : durations) {
			if (duration > expectedTerm)
				sumPerc += duration * 100 / expectedTerm - 100;
		}
		return Math.round(sumPerc / news.size());
	}
	
	public float getPercentOfHandling(List<News> news, int expectedTerm) {
		if (news == null || news.isEmpty()) 
			return 0;
		List<Float> durations = getDurations(news);
		float sumPerc = 0;
		for (float duration : durations) {
				sumPerc += duration / expectedTerm;
		}
		return sumPerc / durations.size();
//		return getAverageTerm(news) *100/ expectedTerm;
	}

	private News getBiggestNews(List<News> news) {
		News newsToReturn = news.get(0);
		float duration = newsToReturn.getDuration();
		for (News n : news) {
			if (duration < n.getDuration()) {
				newsToReturn = n;
				duration = newsToReturn.getDuration();
			}
		}
		return newsToReturn;
	}
	
	private float getAverageTerm(List<News> news) {
		float sum = 0;
		for (News n : news) {
			sum += n.getDuration();
		}
		sum /= news.size();
		return sum;
	}
	
	private float getMinTerm(List<News> news) {
		List<Float> d = getDurations(news);
		float min = Collections.min(d);
		while(min <= 0) {
			d.remove(min);
			min = Collections.min(d);
		}
		return min;
	}
	
	private List <Float> getDurations(List<News> news) {
		List <Float> terms= new ArrayList<>();
		for (News n : news) {
			terms.add(n.getDuration());
		}
		return terms;
	}

	private float getMaxTerm(List<News> news) {
		return Collections.max(getDurations(news));
	}
	
	private int getOverheadAmountPercent(List<News> news, float expectedTerm) {
		List<Float> durations = getDurations(news);
		int counter = 0;
		for (float duration : durations) {
			if (duration > expectedTerm)
				counter++;
		}
		return Math.round(counter * 100 / news.size());
	}

	private String prepareDatesInSql(List<Integer> years) {
		StringBuilder result = new StringBuilder(" AND (");
		Calendar date = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		boolean needOR = false;
		for (Integer year : years) {
			if (needOR)
				result.append(" OR ");
			result.append("wo.planed");
			result.append(" BETWEEN ");
			date.set(year, Calendar.JANUARY, 1, 0, 0, 0);
			result.append("\'");
			result.append(sdf.format(date.getTime()));
			result.append("\' AND \'");
			date.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
			result.append(sdf.format(date.getTime()));
			result.append("\' ");
			needOR = true;
		}
		result.append(")");
		return result.toString();
	}
}
