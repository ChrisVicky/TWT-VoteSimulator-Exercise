package TWT.Homework.VoteSimulator.service.impl;

import TWT.Homework.VoteSimulator.dao.UserSchemaMapper;
import TWT.Homework.VoteSimulator.dao.VoteSimulatorMapper;
import TWT.Homework.VoteSimulator.service.UserService;
import TWT.Homework.VoteSimulator.service.VoteService;
import TWT.Homework.VoteSimulator.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserSchemaMapper userSchemaMapper;
    @Autowired
    VoteSimulatorMapper voteSimulatorMapper;
    @Autowired
    VoteService voteService;


    private static ReentrantLock lock = new ReentrantLock();

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public APIResponse getAllUsers() {
        lock.lock();
        try {
            return APIResponse.success(userSchemaMapper.getAllUser());
        }catch (Exception e){
            return APIResponse.error(500, "[Get All User Error]:"+e.getMessage());
        }finally {
            lock.unlock();
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public APIResponse addUser(String name, String code) {
        lock.lock();
        try {
            return APIResponse.success(userSchemaMapper.addUser(name, code));
        }catch (Exception e){
            return APIResponse.error(500, "[AddError]"+e.getMessage());
        }finally {
            lock.unlock();
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public APIResponse updateUser(String name, String code, String newCode, String newName) {
        lock.lock();
        try {
            if ("".equals(newCode) && "".equals(newName))
                return APIResponse.error(500, "[Variable Error]Invalid newName and newCode.");
            else if ("".equals(newCode))
                return APIResponse.success(userSchemaMapper.updateUserName(name, code, newName));
            else if ("".equals(newName))
                return APIResponse.success(userSchemaMapper.updateUserCode(name, code, newCode));
            return APIResponse.success(userSchemaMapper.updateUserNameCode(name, code, newName, newCode));
        }catch (Exception e){
            return APIResponse.error(500, "[Update Error]"+e.getMessage());
        }finally {
            lock.unlock();
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public APIResponse deleteUser(String name, String code, String managerName, String managerCode) {
        lock.lock();
        try {
            if("".equals(managerName) || "".equals(managerCode) || "".equals(name))
                return APIResponse.error(500,"[Parameter Error]Invalid managerName/managerCode/name.");
            List<String> managerCodeList = userSchemaMapper.getManagerCode(managerName);
            if(managerCode.equals(managerCodeList.get(0))) {
                int userId = userSchemaMapper.getUserId(name).get(0);
                List<Integer> voteIdList = voteSimulatorMapper.getMyParticipatedVoteId(userId);
                for(int voteId:voteSimulatorMapper.getMyVoteId(userId)){
                    if(voteIdList.contains(voteId)) continue;
                    voteIdList.add(voteId);
                }
                for(int voteId:voteIdList)
                    voteService.deleteParticipation(userId, voteId);
                for(int voteId:voteIdList)
                    voteService.deleteVote(userId, voteId);
                return APIResponse.success(userSchemaMapper.deleteUser(name, code));
            }
            return APIResponse.error(500, "[Manager Log In Error]Wrong Manager Code/Name.");
        }catch (Exception e){
            return APIResponse.error(500, "[Deletion Error]"+e.getMessage());
        }finally {
            lock.unlock();
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public APIResponse getAllUserName() {
        lock.lock();
        try{
            return APIResponse.success(userSchemaMapper.getAllUserName());
        }catch (Exception e){
            return APIResponse.error(500, "[Get User Name Error]"+e.getMessage());
        }finally {
            lock.unlock();
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public APIResponse existUser(String name) {
        lock.lock();
        try {
            List<Integer> result = userSchemaMapper.getUserId(name);
            if(result.size()!=0)
                return APIResponse.success(result.get(0));
            else return APIResponse.error(500,"Name Not Exist.");
        }catch (Exception e){
            return APIResponse.error(500, "[Check Existence Error]"+e.getMessage());
        }finally {
            lock.unlock();
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public boolean logIn(String name, String code) {
        lock.lock();
        try {
            List<String> userCodeList = userSchemaMapper.getUserCode(name);
            if(userCodeList == null) return false;
            return code.equals(userCodeList.get(0));
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }finally {
            lock.unlock();
        }
    }


    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public APIResponse findCode(String name, String managerName, String managerCode) {
        lock.lock();
        try {
            if("".equals(managerName) || "".equals(managerCode) || "".equals(name))
                return APIResponse.error(500,"[Parameter Error]Invalid managerName/managerCode/name.");
            List<String> managerCodeList = userSchemaMapper.getManagerCode(managerName);
            if(managerCode.equals(managerCodeList.get(0)))
                return APIResponse.success(userSchemaMapper.getUserCode(name));
            return APIResponse.error(500,"[Manager Log In Error]Wrong Manager Code/Name.");
        }catch (Exception e){
            System.out.println(e.getMessage());
            return APIResponse.error(500,"[Find Code Error]"+e.getMessage());
        }finally {
            lock.unlock();
        }
    }

}
