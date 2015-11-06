package fr.ujf.m2pgi.elasticsearch;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SearchResult {
	
	private long took;
	private long totalHits;
	private List<PhotoDocument> hits;
	
	public long getTook() {
		return took;
	}
	
	public void setTook(long took) {
		this.took = took;
	}
	
	public long getTotalHits() {
		return totalHits;
	}
	
	public void setTotalHits(long totalHits) {
		this.totalHits = totalHits;
	}
	
	public List<PhotoDocument> getHits() {
		return hits;
	}
	
	public void setHits(List<PhotoDocument> hits) {
		this.hits = hits;
	}

}
