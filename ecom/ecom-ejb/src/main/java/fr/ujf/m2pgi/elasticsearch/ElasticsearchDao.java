package fr.ujf.m2pgi.elasticsearch;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;


public class ElasticsearchDao {

	@Inject ElasticsearchBridge connection;

	/**
	 * Allows to index a document into a specific index and make it searchable.
	 * @throws IOException
	 */
	public boolean index(PhotoDocument doc) throws IOException {
		IndexResponse response = connection.getClient().prepareIndex("ecom", "photo", String.valueOf(doc.getPhotoId()))
			        .setSource(jsonBuilder()
			                    .startObject()
			                        .field("name", doc.getName())
															.field("description", doc.getDescription())
															.field("tags", doc.getTags())
															.field("thumbnail", doc.getThumbnail())
															.field("price", doc.getPrice())
															.field("views", doc.getViews())
															.field("likes", doc.getLikes())
															.field("created", doc.getDateCreated())
			                    .endObject()
			                  )
			        .get();
		return response.isCreated();
	}

	/**
	 * Allows to delete document from a specific index based on its id.
	 */
	public boolean delete(String id) {
		DeleteResponse response = connection.getClient().prepareDelete("ecom", "photo", String.valueOf(id)).get();
		return(response.isFound());
	}

	/**
	 * Allows to get a document from the index based on its id.
	 */
	public PhotoDocument find(String id) {
		GetResponse response = connection.getClient().prepareGet("ecom", "photo", id).get();
		if (!response.isExists()) return null;
		PhotoDocument document = new PhotoDocument();
    document.setPhotoId(Long.parseLong(response.getId()));
    document.setDescription((String)response.getSource().get("description"));
    return document;
	}

	/**
	 * Allows to update a document based on a script provided.
	 * @throws IOException
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public boolean update(PhotoDocument doc) throws IOException, InterruptedException, ExecutionException {
		UpdateRequest updateRequest = new UpdateRequest();
		updateRequest.index("ecom");
		updateRequest.type("photo");
		updateRequest.id(String.valueOf(doc.getPhotoId()));

		updateRequest.doc(jsonBuilder()
				.startObject()
					.field("name", doc.getName())
					.field("description", doc.getDescription())
					.field("tags", doc.getTags())
					.field("price", doc.getPrice())
				.endObject());

		UpdateResponse resp = connection.getClient().update(updateRequest).get();
		return resp.isCreated();
	}

	public boolean updateViews(Long id, Integer views) throws IOException, InterruptedException, ExecutionException {
		UpdateRequest updateRequest = new UpdateRequest();
		updateRequest.index("ecom");
		updateRequest.type("photo");
		updateRequest.id(String.valueOf(id));
		updateRequest.doc(jsonBuilder().startObject().field("views", views).endObject());
		UpdateResponse resp = connection.getClient().update(updateRequest).get();
		return resp.isCreated();
	}

	public boolean updateLikes(Long id, Integer likes) throws IOException, InterruptedException, ExecutionException {
		UpdateRequest updateRequest = new UpdateRequest();
		updateRequest.index("ecom");
		updateRequest.type("photo");
		updateRequest.id(String.valueOf(id));
		updateRequest.doc(jsonBuilder().startObject().field("likes", likes).endObject());
		UpdateResponse resp = connection.getClient().update(updateRequest).get();
		return resp.isCreated();
	}

	public List<PhotoDocument> getAll() {
		Client client = connection.getClient();
		SearchResponse response = client.prepareSearch("ecom")
		.setTypes("photo").setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();

		List<PhotoDocument> hits = new ArrayList<PhotoDocument>();
		for (SearchHit hit: response.getHits().hits()) {
			PhotoDocument document = new PhotoDocument();
			document.setPhotoId(Long.parseLong(hit.getId()));
			document.setName((String)hit.getSource().get("name"));
			document.setDescription((String)hit.getSource().get("description"));
			document.setTags((String)hit.getSource().get("tags"));
			document.setThumbnail((String)hit.getSource().get("thumbnail"));
			document.setPrice(new Float((double)hit.getSource().get("price")));
			document.setViews((Integer)hit.getSource().get("views"));
			document.setLikes((Integer)hit.getSource().get("likes"));
			document.setDateCreated((Long)hit.getSource().get("created"));
			hits.add(document);
		}

		return hits;
	}

	/**
	 * Allows to execute a search query and get back search hits that match the query.
	 */
	public List<PhotoDocument> search(String text, int first, int pageSize) {
		Client client = connection.getClient();
		SearchResponse response = client.prepareSearch("ecom")
				.setTypes("photo").setQuery(QueryBuilders.matchQuery("_all", text)).execute().actionGet();

		//SearchResult result = new SearchResult();
		//result.setTotalHits(response.getHits().totalHits());
		//result.setTook(response.getTook().getMillis());

		List<PhotoDocument> hits = new ArrayList<PhotoDocument>();
		for (SearchHit hit: response.getHits().hits()) {
			PhotoDocument document = new PhotoDocument();
			document.setPhotoId(Long.parseLong(hit.getId()));
			document.setName((String)hit.getSource().get("name"));
			document.setDescription((String)hit.getSource().get("description"));
			document.setTags((String)hit.getSource().get("tags"));
			document.setThumbnail((String)hit.getSource().get("location"));
			document.setPrice(new Float((double)hit.getSource().get("price")));
			document.setViews((int)hit.getSource().get("views"));
			document.setLikes((int)hit.getSource().get("likes"));
			document.setDateCreated((Long)hit.getSource().get("created"));
			hits.add(document);
		}

		//result.setHits(hits);
		return hits;
	}

	public List<Long> searchIds(String text) {
		Client client = connection.getClient();
		SearchResponse response = client.prepareSearch("ecom")
				.setTypes("photo").setQuery(QueryBuilders.matchQuery("_all", text)).execute().actionGet();

		List<Long> hits = new ArrayList<Long>();
		for (SearchHit hit: response.getHits().hits()) {
			hits.add(Long.parseLong(hit.getId()));
		}
		return hits;
	}

	/**
	 * Allows to execute a search query and get back search hits that match the query.
	 */
	public List<PhotoDocument> search(String text) {
		return search(text, 0, Integer.MAX_VALUE);
	}
}
