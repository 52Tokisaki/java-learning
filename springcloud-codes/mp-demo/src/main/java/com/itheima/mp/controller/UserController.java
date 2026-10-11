package com.itheima.mp.controller;

import com.itheima.mp.domain.dto.UserFormDTO;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.query.UserQuery;
import com.itheima.mp.domain.vo.UserVO;
import com.itheima.mp.service.IUserService;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@Api(tags = "用户管理接口")
@RequiredArgsConstructor
public class UserController { 
    
    private final IUserService userService;
    
    @PostMapping
    @ApiOperation("新增用户")
    public void saveUser(@RequestBody UserFormDTO userFormDTO) {
        User user = new User();
        BeanUtils.copyProperties(userFormDTO, user);
        userService.save(user);
    }
    
    @ApiOperation("根据id删除用户")
    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userService.removeById(id);
    } 
    
    @GetMapping("/{id}")
    @ApiOperation("根据id查询用户")
    public UserVO queryById(@PathVariable Long id) {
//        User user = userService.getById(id);
//        UserVO userVO = new UserVO();
//        BeanUtils.copyProperties(user, userVO);
//        return userVO;
        return userService.queryUserAndAddressById(id);
    }
    
    @GetMapping
    @ApiOperation("根据id批量查询")
    public List<UserVO> queryUsers(@RequestParam("ids") List<Long> ids) {
        List<User> users = userService.listByIds(ids);
        return BeanUtil.copyToList(users, UserVO.class); 
    }
    
    @PutMapping("/{id}/deduction/{money}")
    @ApiOperation("根据id扣减余额")
    public void deductMoneyById(@PathVariable Long id, @PathVariable Integer money) {
        userService.deductMoneyById(id, money);
    }

    @GetMapping("/list")
    @ApiOperation("根据复杂条件查询用户")
    public List<User> queryUsers(UserQuery userQuery) {
        return userService.queryUsers(userQuery.getUsername(), userQuery.getStatus(), userQuery.getMinBalance(), userQuery.getMaxBalance());
    }
}