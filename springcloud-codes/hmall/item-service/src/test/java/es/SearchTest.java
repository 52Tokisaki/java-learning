package es;

import cn.hutool.json.JSONUtil;
import com.hmall.item.domain.dto.ItemDoc;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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

    @Test
    void testHighlight() throws IOException {
        SearchRequest request = new SearchRequest("items");
        request.source().query(QueryBuilders.matchQuery("name", "牛奶"));
        request.source().highlighter(SearchSourceBuilder.highlight().field("name")); // 添加高亮条件
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        parseResponse(response);
    }

    private static void parseResponse(SearchResponse response) {
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            String json = hit.getSourceAsString();
            ItemDoc itemDoc = JSONUtil.toBean(json, ItemDoc.class);
            Map<String, HighlightField> hlf = hit.getHighlightFields();
            if (hlf != null && !hlf.isEmpty()) {
                Text[] names = hlf.get("name").getFragments();
                StringBuilder highlightName = new StringBuilder();
                for (Text name : names) {
                    highlightName.append(name.string());
                }
                itemDoc.setName(highlightName.toString());

            }
            System.out.println(itemDoc);
        }
    }

    @Test
    void testAgg() throws IOException {
        // 1.创建Request
        SearchRequest request = new SearchRequest("items");
        // 2.准备请求参数
        BoolQueryBuilder bool = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery("category", "手机"))
                .filter(QueryBuilders.rangeQuery("price").gte(300000));
        request.source().query(bool).size(0);
        // 3.聚合参数
        request.source().aggregation(
                AggregationBuilders.terms("brand_agg").field("brand").size(5)
        );
        // 4.发送请求
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 5.解析聚合结果
        Aggregations aggregations = response.getAggregations();
        // 5.1.获取品牌聚合
        Terms brandTerms = aggregations.get("brand_agg");
        // 5.2.获取聚合中的桶
        List<? extends Terms.Bucket> buckets = brandTerms.getBuckets();
        // 5.3.遍历桶内数据
        for (Terms.Bucket bucket : buckets) {
            // 5.4.获取桶内key
            String brand = bucket.getKeyAsString();
            System.out.print("brand = " + brand);
            long count = bucket.getDocCount();
            System.out.println("; count = " + count);
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
