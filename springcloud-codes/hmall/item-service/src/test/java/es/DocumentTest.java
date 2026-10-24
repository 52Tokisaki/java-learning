package es;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hmall.item.domain.dto.ItemDoc;
import com.hmall.item.domain.po.Item;
import com.hmall.item.service.IItemService;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest(classes = com.hmall.item.ItemApplication.class)
public class DocumentTest {
    @Autowired
    private IItemService itemService;
    private RestHighLevelClient client;


    @Test
    void testAddDoc() throws IOException {
        Item item = itemService.getById(317578L);
        ItemDoc itemDoc = BeanUtil.copyProperties(item, ItemDoc.class);
        // 1. 创建请求对象
        IndexRequest request = new IndexRequest("items");
        // 2. 添加请求参数
        request.source(JSONUtil.toJsonStr(itemDoc), XContentType.JSON).id(itemDoc.getId());
        // 3. 发送请求
        client.index(request, RequestOptions.DEFAULT);
    }

    @Test
    void testDeleteDoc() throws IOException {
        DeleteRequest request = new DeleteRequest("items").id("317578");
        client.delete(request, RequestOptions.DEFAULT);
    }

    @Test
    void testGetDoc() throws IOException {
        GetRequest request = new GetRequest("items").id("317578");
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        String json = response.getSourceAsString();
        ItemDoc itemDoc = JSONUtil.toBean(json, ItemDoc.class);
        System.out.println(itemDoc);
    }

    @Test
    void testUpdateDoc () throws IOException {
        UpdateRequest request = new UpdateRequest("items", "317578");
        request.doc(
                "price", 24500
        );
        client.update(request, RequestOptions.DEFAULT);
    }

    @Test
    void testBulkDoc() throws IOException {
        int pageNo = 1;
        int pageSize = 500;

        while (true) {
            Page<Item> page = itemService.lambdaQuery()
                    .eq(Item::getStatus, 1)
                    .page(Page.of(pageNo, pageSize));
            List<Item> itemList = page.getRecords();
            if (itemList == null || itemList.isEmpty()) {
                return;
            }
            BulkRequest bulkRequest = new BulkRequest();
            for (Item item : itemList) {
                bulkRequest.add(new IndexRequest("items")
                        .source(JSONUtil.toJsonStr(BeanUtil.copyProperties(item, ItemDoc.class)), XContentType.JSON)
                        .id(item.getId().toString()));
            }
            client.bulk(bulkRequest, RequestOptions.DEFAULT);
            pageNo++;
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
