package TWT.Homework.VoteSimulator.service.impl;

import TWT.Homework.VoteSimulator.dao.UserSchemaMapper;
import TWT.Homework.VoteSimulator.service.UserService;
import TWT.Homework.VoteSimulator.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.ReentrantLock;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserSchemaMapper userSchemaMapper;

    private static ReentrantLock lock = new ReentrantLock();

    @Override
    public APIResponse getAllUsers() {
        lock.lock();
        try {
            Object Result = userSchemaMapper.getAllUser();
            System.out.println(Result);
            return APIResponse.success(Result);
        }catch (Exception e){
            return APIResponse.error(500, "[ErrorMessage(GetError)]:"+e.getMessage());
        }finally {
            lock.unlock();
        }
    }

    @Override
    public APIResponse addUser(String name, String code) {
        lock.lock();
        try {
            return APIResponse.success(userSchemaMapper.addUser(name, code));
        }catch (Exception e){
            return APIResponse.error(500, "[ErrorMessage(AddError)]:"+e.getMessage());
        }finally {
            lock.unlock();
        }
    }

    @Override
    public APIResponse updateUserCode(String name, String code, String newCode) {
        lock.lock();
        try {
            return APIResponse.success(userSchemaMapper.updateUserCode(name, code, newCode));
        }catch (Exception e){
            return APIResponse.error(500, "[ErrorMessage(UpdateError)]:"+e.getMessage());
        }finally {
            lock.unlock();
        }
    }

    @Override
    public APIResponse deleteUser(String name, String code) {
        lock.lock();
        try {
            return APIResponse.success(userSchemaMapper.deleteUser(name, code));
        }catch (Exception e){
            return APIResponse.error(500, "[ErrorMessage(DeletionError)]:"+e.getMessage());
        }finally {
            lock.unlock();
        }
    }

    @Override
    public APIResponse getAllUserName() {
        lock.lock();
        try{
            return APIResponse.success(userSchemaMapper.getAllUserName());
        }catch (Exception e){
            return APIResponse.error(500, "[ErrorMessage(GetUserNameError]:"+e.getMessage());
        }finally {
            lock.unlock();
        }
    }

    @Override
    public APIResponse existUser(String name) {
        lock.lock();
        try {
            int result = userSchemaMapper.getUserId(name);
            return APIResponse.success(result);
        }catch (Exception e){
            return APIResponse.error(500, "[ErrorMessage(ExistNameError)]:"+e.getMessage());
        }finally {
            lock.unlock();
        }
    }

    @Override
    public APIResponse showTable() {
        lock.lock();
        try {
            return APIResponse.success(userSchemaMapper.showTable());
        }catch (Exception e){
            return APIResponse.error(500, "[ErrorMessage(ExistNameError)]:"+e.getMessage());
        }finally {
            lock.unlock();
        }
    }

    @Override
    public boolean checkUser(String name, String code) {
        lock.lock();
        try {
            return code.equals(userSchemaMapper.getUserCode(name));
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }finally {
            lock.unlock();
        }
    }
}
