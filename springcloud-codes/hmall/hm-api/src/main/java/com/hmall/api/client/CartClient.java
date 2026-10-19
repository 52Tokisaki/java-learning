package com.hmall.api.client;

import com.hmall.api.client.fallback.CartClientFallback;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

@FeignClient(value = "cart-service", fallbackFactory = CartClientFallback.class)
public interface CartClient {
    @ApiOperation("批量删除购物车中商品")
    @DeleteMapping("/carts")
    void deleteCartItemByIds(@RequestParam("ids") Collection<Long> ids);
}
