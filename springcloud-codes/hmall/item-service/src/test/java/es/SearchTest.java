package es;

import cn.hutool.json.JSONUtil;
import com.hmall.item.domain.dto.ItemDoc;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class SearchTest {
    private RestHighLevelClient client;

    @Test
    void testMatchAll() throws IOException {
        // 1. 创建SearchRequest对象，指定索引
        SearchRequest request = new SearchRequest("items");
        // 2. 创建SearchSourceBuilder对象，设置查询条件
        request.source().query(QueryBuilders.matchAllQuery());
        // 3. 执行查询
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 4. 处理查询结果
        parseResponse(response);
    }

    @Test
    void testSearch() throws IOException {
        SearchRequest request = new SearchRequest("items");
        // 添加match条件
        request.source().query(QueryBuilders.matchQuery("name", "手机"));
        // 添加term条件
        request.source().query(QueryBuilders.termQuery("brand", "小米"));
        // 添加range条件
        request.source().query(QueryBuilders.rangeQuery("price").from(100000).to(200000));
        // 3. 执行查询
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 4. 处理查询结果
        parseResponse(response);
    }

    @Test
    void testBool() throws IOException {
        SearchRequest request = new SearchRequest("items");
        request.source().query(
                QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery("name", "牛奶"))
                        .filter(QueryBuilders.termQuery("brand", "宾格瑞"))
                        .filter(QueryBuilders.rangeQuery("price").gte(0).lte(200000)));
        // 3. 执行查询
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 4. 处理查询结果
        parseResponse(response);
    }

    @Test
    void testSortAndPage() throws IOException {
        int pageNo = 1; // 当前页码
        int pageSize = 5;
        SearchRequest request = new SearchRequest("items");
        request.source().from((pageNo - 1) * pageSize).size(pageSize); // 设置分页参数
        request.source().sort("sold", SortOrder.DESC)
                        .sort("price", SortOrder.ASC); // 添加排序条件
        request.source().query(QueryBuilders.matchAllQuery());
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        parseResponse(response);
    }

    private static void parseResponse(SearchResponse response) {
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            String json = hit.getSourceAsString();
            ItemDoc itemDoc = JSONUtil.toBean(json, ItemDoc.class);
            System.out.println(itemDoc);
        }
    }

    @BeforeEach
    void setUp() {
        client = new RestHighLevelClient(RestClient.builder(HttpHost.create("http://192.168.150.101:9200")));
    }

    @AfterEach
    void tearDown() throws IOException {
        if (client != null) {
            client.close();
        }
    }
}
