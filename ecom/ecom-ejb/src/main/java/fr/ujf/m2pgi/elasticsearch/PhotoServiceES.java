package fr.ujf.m2pgi.elasticsearch;

import java.util.List;

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

	public List<PhotoDocument> getAllPhotos() {
		return photoDao.getAll();
	}

	public List<PhotoDocument> searchPhotos(String text) {
		return photoDao.search(text);
	}

}
