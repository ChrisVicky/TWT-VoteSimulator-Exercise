package TWT.Homework.VoteSimulator.service.impl;

import TWT.Homework.VoteSimulator.dao.UserSchemaMapper;
import TWT.Homework.VoteSimulator.dao.VoteSimulatorMapper;
import TWT.Homework.VoteSimulator.entity.VoteSchema;
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

    private ReentrantLock lock = new ReentrantLock();

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public APIResponse getAllQuestion() {
        lock.lock();
        try{
            return APIResponse.success(voteSimulatorMapper.getAllQuestion());
        }catch(Exception e){
            return APIResponse.error(500, "[ErrorMessage(GetAllVoteError)]:"+e.getMessage());
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
            return APIResponse.error(500, "[ErrorMessage]:"+e.getMessage());
        }finally {
            lock.unlock();
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public APIResponse getUserVote(String name, String code) {
        lock.lock();
        try {
            int userId = userSchemaMapper.getUserId(name);
            List<Integer> VoteIdList = voteSimulatorMapper.getAllVoteId(userId);
            List<VoteSchema> VoteList = new ArrayList<>();
            for(Integer voteId:VoteIdList){
                VoteSchema voteSchema = new VoteSchema();
                voteSchema.questionSchema = voteSimulatorMapper.getIndividualQuestion(voteId);
                voteSchema.choiceList = voteSimulatorMapper.getIndividualVoteChoice(voteId);
                VoteList.add(voteSchema);
                    System.out.println(voteId);
                    System.out.println(voteSchema);
                    System.out.println(VoteList);
            }
            System.out.println(VoteList);
            return APIResponse.success(VoteList);
        }catch (Exception e){
            return APIResponse.error(500,"[ErrorMessage(GetUserVotes)]:"+e.getMessage());
        }finally {
            lock.unlock();
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public APIResponse addVote(String name, String code, String question, List<String> choiceList){
        lock.lock();
        try {
            int userId = userSchemaMapper.getUserId(name);
            voteSimulatorMapper.addQuestion(question, userId);
            int voteId = voteSimulatorMapper.getVoteIdThroughQuestion(question, userId);
            for (String choice : choiceList) {
                int choiceId = choiceList.indexOf(choice) + 1;
                voteSimulatorMapper.addChoice(choice, choiceId, voteId);
            }
            return APIResponse.success(1);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return APIResponse.error(500, "[ErrorMessage(AddVote)]:"+e.getMessage());
        }finally {
            lock.unlock();
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public APIResponse deleteVote(String name, String code, int voteId) {
        lock.lock();
        try {
            if(userSchemaMapper.getUserId(name)== voteSimulatorMapper.getUserIdThroughVoteId(voteId)) {
                voteSimulatorMapper.deleteQuestion(voteId);
                voteSimulatorMapper.deleteChoice(voteId);
                return APIResponse.success(1);
            }else{
                return APIResponse.error(500, "[ErrorMessage]:Not Your Vote. You can not delete it.");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return APIResponse.error(500, "[ErrorMessage(DeleteVote)]:"+e.getMessage());
        }finally {
            lock.unlock();
        }
    }
}
