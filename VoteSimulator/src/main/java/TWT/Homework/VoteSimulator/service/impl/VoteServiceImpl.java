package TWT.Homework.VoteSimulator.service.impl;

import TWT.Homework.VoteSimulator.dao.UserSchemaMapper;
import TWT.Homework.VoteSimulator.dao.VoteSimulatorMapper;
import TWT.Homework.VoteSimulator.entity.VoteSchema;
import TWT.Homework.VoteSimulator.service.UserService;
import TWT.Homework.VoteSimulator.service.VoteService;
import TWT.Homework.VoteSimulator.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class VoteServiceImpl implements VoteService {
    @Autowired
    VoteSimulatorMapper voteSimulatorMapper;
    @Autowired
    UserSchemaMapper userSchemaMapper;
    @Autowired
    UserService userService;

    private ReentrantLock lock = new ReentrantLock();

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public APIResponse getAllQuestion() {
        lock.lock();
        try{
            return APIResponse.success(voteSimulatorMapper.getAllQuestion());
        }catch(Exception e){
            return APIResponse.error(500, "[Get All Question Error]"+e.getMessage());
        }finally {
            lock.unlock();
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public APIResponse getAllChoice(){
        lock.lock();
        try {
            return APIResponse.success(voteSimulatorMapper.getAllChoice());
        }catch (Exception e){
            System.out.println(e.getMessage());
            return APIResponse.error(500, "[Get All Choice Error]"+e.getMessage());
        }finally {
            lock.unlock();
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public APIResponse getAllVote() {
        lock.lock();
        try {
            List<Integer> VoteIdList = voteSimulatorMapper.getAllVoteId();
            List<VoteSchema> VoteList = new ArrayList<>();
            List<String> TempList;
            for (Integer voteId : VoteIdList) {
                TempList = voteSimulatorMapper.getQuestion(voteId);
                if (TempList.size()==0)
                    return APIResponse.error(500, "[Get Individual Question Error]Invalid voteId " + voteId.toString());
                VoteList.add(new VoteSchema(voteId, TempList.get(0), voteSimulatorMapper.getVoteChoice(voteId)));
            }
            return APIResponse.success(VoteList);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return APIResponse.error(500,"[Get All Vote Error]"+e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public APIResponse getUserVote(int userId) {
        lock.lock();
        try {
            List<Integer> VoteIdList = voteSimulatorMapper.getMyVoteId(userId);
            List<VoteSchema> VoteList = new ArrayList<>();
            List<String> TempList;
            for (Integer voteId : VoteIdList) {
                TempList = voteSimulatorMapper.getQuestion(voteId);
                if (TempList.size()==0)
                    return APIResponse.error(500, "[Get Individual Question Error]Invalid voteId " + voteId.toString());
                VoteList.add(new VoteSchema(voteId, TempList.get(0), voteSimulatorMapper.getVoteChoice(voteId)));
            }
            return APIResponse.success(VoteList);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return APIResponse.error(500,"[Get User Vote Error]"+e.getMessage());
        }finally {
            lock.unlock();
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public APIResponse addVote(int userId, String question, List<String> choiceList){
        lock.lock();
        try {
            voteSimulatorMapper.addQuestion(question, userId);
            int voteId = voteSimulatorMapper.getVoteId(question);
            for (String choice : choiceList) {
                int choiceId = choiceList.indexOf(choice) + 1;
                voteSimulatorMapper.addChoice(choice, choiceId, voteId);
            }
            return APIResponse.success(1);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return APIResponse.error(500, "[Add Vote Error]"+e.getMessage());
        }finally {
            lock.unlock();
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public APIResponse deleteVote(int userId, int voteId) {
        lock.lock();
        try {
            List<Integer> TempList = voteSimulatorMapper.getUserId(voteId, "");
            if (TempList.size()!=0) {
                Integer userIdTemp = TempList.get(0);
                if(userIdTemp == userId) {
                    voteSimulatorMapper.deleteQuestion(voteId);
                    voteSimulatorMapper.deleteChoice(voteId);
                    voteSimulatorMapper.deleteAnswer(voteId);
                    return APIResponse.success(1);
                }
                return APIResponse.error(500, "[Delete Vote Error]Not Your Vote. You can not delete it.\nTry /myVote (Post Method) to Find Correct Vote.");
            } else {
                return APIResponse.error(500,"[Delete Vote Error]Invalid voteId.\nTry /myVote (Post Method) to Find Correct voteId.");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return APIResponse.error(500, "[Delete Vote Error]"+e.getMessage());
        }finally {
            lock.unlock();
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public APIResponse participateVote(int userId, int voteId, int choiceId) {
        lock.unlock();
        try {
            if(voteSimulatorMapper.getQuestion(voteId).size()==0)
                return APIResponse.error(500,"[Participate Vote Error]Invalid voteId.\nTry /question for Correct voteId.");
            List<String> choiceList = voteSimulatorMapper.getChoice(choiceId, voteId);
            if(choiceList.size()==0)
                return APIResponse.error(500, "[Participate Vote Error]Invalid choiceId.\nTry /choice for Correct choiceId.");
            String choice = choiceList.get(0);
            if(voteSimulatorMapper.existAnswer(voteId, userId).size()==0) {
                voteSimulatorMapper.updateChoiceTimes(choice, choiceId);
                voteSimulatorMapper.addAnswer(voteId, choiceId, userId);
                return APIResponse.success(1);
            }else{
                return APIResponse.error(500,"[Participate Vote Error]You've Voted this Before.");
            }
        }catch (Exception e){
            return APIResponse.error(500, "[Participate Vote Error]"+e.getMessage());
        }finally {
            lock.unlock();
        }
    }

    /*
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public APIResponse updateVote(int userId, int voteId, String question, List<String> addChoiceList, List<Integer> deChoiceList) {
        lock.lock();
        try {
            if(voteSimulatorMapper.getQuestion(voteId).size()==0)
                return APIResponse.error(500,"[Participate Vote Error]Invalid voteId.\nTry /allQuestion for Correct voteId.");
            if(!"".equals(question))
                voteSimulatorMapper.updateQuestion(question, voteId);
            if(addChoiceList.size()!=0){
                int choiceId=1, maxId = voteSimulatorMapper.getMaxChoiceId(voteId);
                for(String choice:addChoiceList){
                    voteSimulatorMapper.addChoice(choice, choiceId+maxId, voteId);
                }
            }
            if(deChoiceList.size()!=0){
                for(String choice:deChoiceList){
                    voteSimulatorMapper.deleteChoice(choice);
                }
            }
        }
    }
    */
}
