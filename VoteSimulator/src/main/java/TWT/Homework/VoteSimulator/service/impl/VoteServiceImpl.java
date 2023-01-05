package TWT.Homework.VoteSimulator.service.impl;

import TWT.Homework.VoteSimulator.dao.UserSchemaMapper;
import TWT.Homework.VoteSimulator.dao.VoteSimulatorMapper;
import TWT.Homework.VoteSimulator.entity.Choice;
import TWT.Homework.VoteSimulator.entity.Question;
import TWT.Homework.VoteSimulator.entity.Vote;
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

    public APIResponse getVote(List<Integer> questionIdList){
        List<Vote> VoteList = new ArrayList<>();
        List<String> TempList;
        int userId;
        String userName, question;
        List<Choice> choiceList;
        for (Integer questionId : questionIdList) {
            System.out.println("[Internal Message]: Trying to Get vote with QuestionId " + questionId);
            TempList = voteSimulatorMapper.getQuestion(questionId);
            if (TempList.size()==0)
                return APIResponse.error(500, "[Get Individual Question Error]Invalid questionId " + questionId);
            if(voteSimulatorMapper.getExistence(questionId)==1){
                System.out.println("[Internal Message]: Vote (QuestionId) " + questionId + " has been deleted.");
                continue;
            }
            question = TempList.get(0);
            choiceList = voteSimulatorMapper.getVoteChoice(questionId);
            for(Choice choice : choiceList){
                choice.times = voteSimulatorMapper.getChoiceTimes(questionId, choice.choiceId);
            }
            userId = voteSimulatorMapper.getUserId(questionId,"").get(0);
            userName = userSchemaMapper.getUserName(userId).get(0);
            VoteList.add(new Vote(new Question(questionId, question, userId, userName), choiceList));
        }
        return APIResponse.success(VoteList);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public APIResponse getAllVote() {
        lock.lock();
        try {
            List<Integer> questionIdList = voteSimulatorMapper.getAllQuestionId();
            return getVote(questionIdList);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return APIResponse.error(500,"[Get All Vote Error]"+e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public APIResponse getResult(int questionId) {
        lock.lock();
        try {
            if(voteSimulatorMapper.getVoteChoice(questionId).size()==0)
                return APIResponse.error(500, "[Get Result Error]:Invalid questionId. Try /vote (Get Method) for Correct questionId.");
            List<Integer> questionIdList = new ArrayList<>();
            questionIdList.add(questionId);
            return getVote(questionIdList);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return APIResponse.error(500,"[Get Result Error]"+e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public APIResponse getParticipation(int userId) {
        lock.lock();
        try {
            List<Integer> questionIdList = voteSimulatorMapper.getMyParticipatedQuestionId(userId);
            return getVote(questionIdList);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return APIResponse.error(500,"[Get User Participation Error]"+e.getMessage());
        }finally {
            lock.unlock();
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public APIResponse getUserVote(int userId) {
        lock.lock();
        try {
            List<Integer> questionIdList = voteSimulatorMapper.getMyQuestionId(userId);
            return getVote(questionIdList);
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
            System.out.println("[question]"+question);
            int questionId = voteSimulatorMapper.getQuestionId(question);
            for (String choice : choiceList) {
                int choiceId = choiceList.indexOf(choice) + 1;
                voteSimulatorMapper.addChoice(choice, choiceId, questionId);
                System.out.println("[choice"+choiceId+"]"+choice);
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
    public APIResponse deleteVote(int userId, int questionId) {
        lock.lock();
        try {
            List<Integer> TempList = voteSimulatorMapper.getUserId(questionId, "");
            System.out.println(TempList);
            if (TempList.size()!=0) {
                Integer userIdTemp = TempList.get(0);
                if(userIdTemp == userId) {
                    System.out.println(voteSimulatorMapper.getVoteChoice(questionId));
                    voteSimulatorMapper.deleteQuestion(questionId);
                    System.out.println(userId);
                    return APIResponse.success(1);
                }
                return APIResponse.error(500, "[Delete Vote Error]Not Your Vote. You can not delete it.\nTry /myVote (Post Method) to Find Correct Vote.");
            } else {
                return APIResponse.error(500,"[Delete Vote Error]Invalid questionId.\nTry /myVote (Post Method) to Find Correct questionId.");
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
    public APIResponse deleteParticipation(int userId, int questionId) {
        lock.lock();
        try {
            List<Integer> TempList = voteSimulatorMapper.getUserId(questionId, "");
            System.out.println(TempList);
            if (TempList.size()!=0) {
                int choiceId = voteSimulatorMapper.getChoiceId(userId, questionId);
                voteSimulatorMapper.deleteOneAnswer(questionId, choiceId);
                return APIResponse.success(1);
            } else {
                return APIResponse.error(500,"[Delete Vote Error]Invalid questionId.\nTry /vote to Find Correct questionId.");
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
    public APIResponse participateVote(int userId, int questionId, int choiceId) {
        lock.lock();
        try {
            System.out.println("[Participate Vote]");
            if(voteSimulatorMapper.getQuestion(questionId).size()==0)
                return APIResponse.error(500,"[Participate Vote Error]Invalid questionId.\nTry /question for Correct questionId.");
            System.out.println(userSchemaMapper.getUserName(userId)+" Participate Vote " + questionId + " " + voteSimulatorMapper.getVoteQuestion(questionId));
            List<String> choiceList = voteSimulatorMapper.getChoice(choiceId, questionId);
            if(choiceList.size()==0)
                return APIResponse.error(500, "[Participate Vote Error]Invalid choiceId.\nTry /choice for Correct choiceId.");
            String choice = choiceList.get(0);
            if(voteSimulatorMapper.existAnswer(questionId, userId).size()==0) {
                System.out.println("[choice]"+choice);
                voteSimulatorMapper.updateChoiceTimes(questionId, choiceId);
                voteSimulatorMapper.addAnswer(questionId, choiceId, userId);
                System.out.println("[answer]"+questionId+" "+choiceId+" "+userId);
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

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public APIResponse multipleParticipateVote(int userId, int questionId, List<Integer> choiceIdList) {
        lock.lock();
        try {
            System.out.println("[Participate Vote Multiply]");
            if(voteSimulatorMapper.getQuestion(questionId).size()==0)
                return APIResponse.error(500,"[Participate Vote Error]Invalid questionId.\nTry /question for Correct questionId.");
            System.out.println(userSchemaMapper.getUserName(userId)+" Participate Vote " + questionId + " " + voteSimulatorMapper.getVoteQuestion(questionId));
            for(Integer choiceId : choiceIdList){
                List<String> choiceList = voteSimulatorMapper.getChoice(choiceId, questionId);
                if(choiceList.size()==0)
                    return APIResponse.error(500, "[Participate Vote Error]Invalid choiceId.\nTry /choice for Correct choiceId.");
                String choice = choiceList.get(0);
                if(voteSimulatorMapper.existAnswer(questionId, userId).size()==0) {
                    System.out.println("[choice]"+choice);
                    voteSimulatorMapper.updateChoiceTimes(questionId, choiceId);
                    voteSimulatorMapper.addAnswer(questionId, choiceId, userId);
                    System.out.println("[answer]"+questionId+" "+choiceId+" "+userId);
                }
            }
            return APIResponse.success(1);
        }catch (Exception e){
            e.printStackTrace();
            return APIResponse.error(500, "[Vote Multiply Error]:" + e.getMessage());
        }finally {
            lock.unlock();
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public APIResponse reParticipate(int userId, int questionId, int choiceId) {
        lock.lock();
        try {
            System.out.println(deleteParticipation(userId, questionId));
            return participateVote(userId, questionId, choiceId);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return APIResponse.error(500, "[Re Participate Error]"+e.getMessage());
        }finally {
            lock.unlock();
        }
    }

    /*
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public APIResponse updateVote(int userId, int questionId, String question, List<String> addChoiceList, List<Integer> deChoiceList) {
        lock.lock();
        try {
            if(voteSimulatorMapper.getQuestion(questionId).size()==0)
                return APIResponse.error(500,"[Participate Vote Error]Invalid questionId.\nTry /allQuestion for Correct questionId.");
            if(!"".equals(question))
                voteSimulatorMapper.updateQuestion(question, questionId);
            if(addChoiceList.size()!=0){
                int choiceId=1, maxId = voteSimulatorMapper.getMaxChoiceId(questionId);
                for(String choice:addChoiceList){
                    voteSimulatorMapper.addChoice(choice, choiceId+maxId, questionId);
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

