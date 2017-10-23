package com.lauro.banking.statistics;

import org.springframework.stereotype.Component;

@Component
public class RecentStatistics {

	private Double sum = 0D;
	private Double avg = 0D;
	private Double max = 0D;
	private Double min = 0D;
	private Long count = 0L;

	public Double getSum() {
		return sum;
	}

	public void setSum(Double sum) {
		this.sum = sum;
	}

	public Double getAvg() {
		return avg;
	}

	public void setAvg(Double avg) {
		this.avg = avg;
	}

	public Double getMin() {
		return min;
	}

	public void setMin(Double min) {
		this.min = min;
	}

	public Double getMax() {
		return max;
	}

	public void setMax(Double max) {
		this.max = max;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}
}
