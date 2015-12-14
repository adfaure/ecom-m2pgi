package fr.ujf.m2pgi.elasticsearch;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

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
		IndexResponse response = connection.getClient().prepareIndex("ecom", "photo", String.valueOf(doc.getId()))
			        .setSource(jsonBuilder()
			                    .startObject()
			                        .field("name", doc.getName())
															.field("description", doc.getDescription())
															.field("location", doc.getLocation())
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
    	document.setId(Long.parseLong(response.getId()));
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
  	    updateRequest.id(String.valueOf(doc.getId()));

  	    updateRequest.doc(jsonBuilder()
  	    		.startObject()
  	    			.field("description", doc.getDescription())
			    .endObject());

		UpdateResponse resp = connection.getClient().update(updateRequest).get();
		return resp.isCreated();
	}

	public SearchResult getAll() {
		Client client = connection.getClient();
		SearchResponse response = client.prepareSearch("ecom")
				.setTypes("photo").setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();
		SearchResult result = new SearchResult();
		result.setTotalHits(response.getHits().totalHits());
		result.setTook(response.getTook().getMillis());

		List<PhotoDocument> hits = new ArrayList<PhotoDocument>();
        for (SearchHit hit: response.getHits().hits()) {
        	PhotoDocument document = new PhotoDocument();
        	document.setId(Long.parseLong(hit.getId()));
        	document.setName((String)hit.getSource().get("name"));
        	document.setDescription((String)hit.getSource().get("description"));
        	document.setLocation((String)hit.getSource().get("location"));
        	hits.add(document);
        }

        result.setHits(hits);

		return result;
	}

	/**
	 * Allows to execute a search query and get back search hits that match the query.
	 */
	public SearchResult search(String text, int first, int pageSize) {
		Client client = connection.getClient();
		SearchResponse response = client.prepareSearch("ecom")
				.setTypes("photo").setQuery(QueryBuilders.matchQuery("description", text)).execute().actionGet();

		SearchResult result = new SearchResult();
		result.setTotalHits(response.getHits().totalHits());
		result.setTook(response.getTook().getMillis());

		List<PhotoDocument> hits = new ArrayList<PhotoDocument>();
        for (SearchHit hit: response.getHits().hits()) {
        	PhotoDocument document = new PhotoDocument();
        	document.setId(Long.parseLong(hit.getId()));
        	document.setName((String)hit.getSource().get("name"));
        	document.setDescription((String)hit.getSource().get("description"));
        	document.setLocation((String)hit.getSource().get("location"));
        	hits.add(document);
        }

        result.setHits(hits);

		return result;
	}

	public List<Long> searchIds(String text) {
		Client client = connection.getClient();
		SearchResponse response = client.prepareSearch("ecom")
				.setTypes("photo").setQuery(QueryBuilders.matchQuery("description", text)).execute().actionGet();

		List<Long> hits = new ArrayList<Long>();
		for (SearchHit hit: response.getHits().hits()) {
			hits.add(Long.parseLong(hit.getId()));
		}
		return hits;
	}

	/**
	 * Allows to execute a search query and get back search hits that match the query.
	 */
	public SearchResult search(String text) {
		return search(text, 0, Integer.MAX_VALUE);
	}
}
