package TWT.Homework.VoteSimulator.controller;

import TWT.Homework.VoteSimulator.dao.UserSchemaMapper;
import TWT.Homework.VoteSimulator.service.UserService;
import TWT.Homework.VoteSimulator.service.VoteService;
import TWT.Homework.VoteSimulator.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class VoteController {
    @Autowired
    VoteService voteService;
    @Autowired
    UserService userService;
    @Autowired
    UserSchemaMapper userSchemaMapper;
    @Autowired
    HttpSession session;

    @GetMapping("/question")
    public APIResponse getAllQuestion(){
        return voteService.getAllQuestion();
    }

    @GetMapping("/choice")
    public APIResponse getAllChoice(){ return voteService.getAllChoice(); }

    @GetMapping("/vote")
    public APIResponse getAllVote(){ return voteService.getAllVote(); }


    @PostMapping("/addVote")
    public APIResponse addVote(@RequestParam("question") String question,
                               @RequestParam("choice") List<String> choiceList){
        String name = session.getAttribute("user").toString();
        int userId = userSchemaMapper.getUserId(name).get(0);
        System.out.println(userId);
        return voteService.addVote(userId, question, choiceList);
    }

    @PostMapping("/participate")
    public APIResponse participateVote(@RequestParam("questionId") int questionId,
                                       @RequestParam("choiceId") int choiceId){
        System.out.println("Log in to Participate vote");
        String name = session.getAttribute("user").toString();
        int userId = userSchemaMapper.getUserId(name).get(0);
        return voteService.participateVote(userId, questionId, choiceId);
    }

    @PostMapping("/deleteVote")
    public APIResponse deleteVote(@RequestParam("questionId") int questionId){
        String name = session.getAttribute("user").toString();
        int userId = userSchemaMapper.getUserId(name).get(0);
        return voteService.deleteVote(userId, questionId);
    }

    @PostMapping("/deleteParticipation")
    public APIResponse deleteParticipation(@RequestParam("questionId") int questionId){
        String name = session.getAttribute("user").toString();
        int userId = userSchemaMapper.getUserId(name).get(0);
        return voteService.deleteVote(userId, questionId);
    }

    @PostMapping("/reVote")
    public APIResponse reVote(@RequestParam("questionId") int questionId,
                              @RequestParam("choiceId") int choiceId){
        String name = session.getAttribute("user").toString();
        System.out.println("Log in to ReParticipate vote");
        int userId = userSchemaMapper.getUserId(name).get(0);
        System.out.println(voteService.deleteParticipation(userId, questionId));
        return voteService.participateVote(userId, questionId, choiceId);
    }

    @PostMapping("/result")
    public APIResponse getResult(@RequestParam("questionId") int questionId){
        return voteService.getResult(questionId);
    }

    @GetMapping("/myParticipation")
    public APIResponse getParticipation(){
        String name = session.getAttribute("user").toString();
        int userId = userSchemaMapper.getUserId(name).get(0);
        return voteService.getParticipation(userId);
    }

    @GetMapping("/myVote")
    public APIResponse getUserVote(){
        String name = session.getAttribute("user").toString();
        System.out.println(name);
        int userId = userSchemaMapper.getUserId(name).get(0);
        return voteService.getUserVote(userId);
    }

    /*
    @PutMapping("/vote")
    public APIResponse updateVote(@RequestParam("name") String name,
                                  @RequestParam("code") String code,
                                  @RequestParam("question") String question,
                                  @RequestParam("questionId") int questionId,
                                  @RequestParam("choice") List<String> choiceList,
                                  @RequestParam("deChoice") List<String> deChoiceList){
        if(userService.logIn(name, code)){
            int userId = userSchemaMapper.getUserId(name).get(0);

        }else{
            if(userSchemaMapper.getUserId(name).size()!=0)
                return APIResponse.error(500,"[Log In Error]Wrong code.");
            else
                return APIResponse.error(500,"[Log In Error]Absent name.");
        }
    }
    */
}

