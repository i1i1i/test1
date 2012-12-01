package entity;

public class ForSingleBP {
	
	private BussinesProcess bp;
	public ForSingleBP(BussinesProcess bp, Float minTime, Float maxTime,
			Float averageTerm, int overheadAmountPercent, int amount, int overheadPercent) {
		super();
		this.bp = bp;
		this.minTime = minTime;
		this.maxTime = maxTime;
		this.averageTerm = averageTerm;
		this.overheadAmountPercent = overheadAmountPercent;
		this.overheadPercent = overheadPercent;
		this.amount = amount;
	}
	private Float minTime;
	private Float maxTime;
	private Float averageTerm;
	private  int overheadPercent;
	private  int overheadAmountPercent;
	private  int amount;
	
	public BussinesProcess getBp() {
		return bp;
	}
	public void setBp(BussinesProcess bp) {
		this.bp = bp;
	}
	public Float getMinTime() {
		return minTime;
	}
	public void setMinTime(Float minTime) {
		this.minTime = minTime;
	}
	public Float getMaxTime() {
		return maxTime;
	}
	public void setMaxTime(Float maxTime) {
		this.maxTime = maxTime;
	}
	public Float getAverageTerm() {
		return averageTerm;
	}
	public void setAverageTerm(Float averageTerm) {
		this.averageTerm = averageTerm;
	}
	public int getOverheadPercent() {
		return overheadPercent;
	}
	public void setOverheadPercent(int overheadPercent) {
		this.overheadPercent = overheadPercent;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getOverheadAmountPercent() {
		return overheadAmountPercent;
	}
	public void setOverheadAmountPercent(int overheadAmountPercent) {
		this.overheadAmountPercent = overheadAmountPercent;
	}
}
