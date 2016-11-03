package info.xiaomo.core.service.website.impl;

import info.xiaomo.core.dao.website.UserDao;
import info.xiaomo.core.exception.UserNotFoundException;
import info.xiaomo.core.model.website.UserModel;
import info.xiaomo.core.service.website.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 把今天最好的表现当作明天最新的起点．．～
 * いま 最高の表現 として 明日最新の始発．．～
 * Today the best performance  as tomorrow newest starter!
 * Created by IntelliJ IDEA.
 *
 * @author: xiaomo
 * @github: https://github.com/qq83387856
 * @email: hupengbest@163.com
 * @QQ_NO: 83387856
 * @Date: 2016/4/1 17:46
 * @Description: 用户service实现
 * @Copyright(©) 2015 by xiaomo.
 **/
@Service
public class UserServiceImpl implements UserService {

    private final UserDao dao;

    @Autowired
    public UserServiceImpl(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public UserModel findUserById(Long id) {
        return dao.findOne(id);
    }

    @Override
    public UserModel findUserByEmail(String email) {
        return dao.findUserByEmail(email);
    }

    @Override
    public UserModel addUser(UserModel model) {
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        return dao.save(model);
    }

    @Override
    public UserModel updateUser(UserModel model) throws UserNotFoundException {
        UserModel userUpdate = dao.findUserByEmail(model.getEmail());
        if (userUpdate == null) {
            throw new UserNotFoundException();
        }
        if (model.getAddress() != null) {
            userUpdate.setAddress(model.getAddress());
        }
        if (model.getEmail() != null) {
            userUpdate.setEmail(model.getEmail());
        }
        if (model.getGender() != 0) {
            userUpdate.setGender(model.getGender());
        }
        if (model.getImgUrl() != null) {
            userUpdate.setImgUrl(model.getImgUrl());
        }
        if (model.getNickName() != null) {
            userUpdate.setNickName(model.getNickName());
        }
        if (model.getPhone() != 0) {
            userUpdate.setPhone(model.getPhone());
        }
        userUpdate.setUpdateTime(new Date());
        dao.save(userUpdate);
        return userUpdate;
    }

    @Override
    public Page<UserModel> findAll(int start, int pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        return dao.findAll(new PageRequest(start - 1, pageSize, sort));
    }

    @Override
    public List<UserModel> findAll() {
        return dao.findAll();
    }

    @Override
    public UserModel deleteUserById(Long id) throws UserNotFoundException {
        UserModel userModel = dao.findOne(id);
        if (userModel == null) {
            throw new UserNotFoundException();
        }
        dao.delete(userModel.getId());
        return userModel;
    }

}