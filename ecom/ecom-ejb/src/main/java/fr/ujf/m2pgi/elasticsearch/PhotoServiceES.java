package fr.ujf.m2pgi.elasticsearch;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * 
 * @author AZOUZI Marwen
 *
 */
@Stateless
public class PhotoServiceES {

	@Inject
	private ElasticsearchDao photoDao;

	public SearchResult getAllPhotos() {
		return photoDao.getAll();
	}	
	
	public SearchResult searchPhotos(String text) {
		return photoDao.search(text);
	}
	
}
