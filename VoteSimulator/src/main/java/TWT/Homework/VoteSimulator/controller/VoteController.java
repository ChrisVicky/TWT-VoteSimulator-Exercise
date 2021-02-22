package TWT.Homework.VoteSimulator.controller;

import TWT.Homework.VoteSimulator.dao.UserSchemaMapper;
import TWT.Homework.VoteSimulator.service.UserService;
import TWT.Homework.VoteSimulator.service.VoteService;
import TWT.Homework.VoteSimulator.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VoteController {
    @Autowired
    VoteService voteService;
    @Autowired
    UserService userService;
    @Autowired
    UserSchemaMapper userSchemaMapper;

    @GetMapping("/vote")
    public APIResponse getAllVote(){ return voteService.getAllVote(); }
    @GetMapping("/question")
    public APIResponse getAllQuestion(){
        return voteService.getAllQuestion();
    }
    @GetMapping("/choice")
    public APIResponse getAllChoice(){ return voteService.getAllChoice(); }

    @PostMapping("/myVote")
    public APIResponse getUserVote(@RequestParam("name") String name,
                                   @RequestParam("code") String code){
        if(userService.logIn(name, code)){
            int userId = userSchemaMapper.getUserId(name).get(0);
            return voteService.getUserVote(userId);
        }else{
            if(userSchemaMapper.getUserId(name).size()!=0)
                return APIResponse.error(500,"[Log In Error]Wrong code.");
            else
                return APIResponse.error(500,"[Log In Error]Invalid name.");
        }
    }

    @PostMapping("/vote")
    public APIResponse addVote(@RequestParam("name") String name,
                               @RequestParam("code") String code,
                               @RequestParam("question") String question,
                               @RequestParam("choice") List<String> choiceList){
        if(userService.logIn(name, code)){
            int userId = userSchemaMapper.getUserId(name).get(0);
            return voteService.addVote(userId, question, choiceList);
        }else{
            if(userSchemaMapper.getUserId(name).size()!=0)
                return APIResponse.error(500,"[Log In Error]Wrong code.");
            else
                return APIResponse.error(500,"[Log In Error]Invalid name.");
        }
    }

    @DeleteMapping("/vote")
    public APIResponse deleteVote(@RequestParam("name") String name,
                                  @RequestParam("code") String code,
                                  @RequestParam("voteId") int voteId){
        if(userService.logIn(name, code)){
            int userId = userSchemaMapper.getUserId(name).get(0);
            return voteService.deleteVote(userId, voteId);
        }else{
            if(userSchemaMapper.getUserId(name).size()!=0)
                return APIResponse.error(500,"[Log In Error]Wrong code.");
            else
                return APIResponse.error(500,"[Log In Error]Invalid name.");
        }
    }

    /*
    @PutMapping("/vote")
    public APIResponse updateVote(@RequestParam("name") String name,
                                  @RequestParam("code") String code,
                                  @RequestParam("question") String question,
                                  @RequestParam("voteId") int voteId,
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

    @PutMapping("/vote")
    public APIResponse participateVote(@RequestParam("name") String name,
                                       @RequestParam("code") String code,
                                       @RequestParam("voteId") int voteId,
                                       @RequestParam("choiceId") int choiceId){
        if(userService.logIn(name, code)){
            int userId = userSchemaMapper.getUserId(name).get(0);
            return voteService.participateVote(userId, voteId, choiceId);
        }else{
            if(userSchemaMapper.getUserId(name).size()!=0)
                return APIResponse.error(500,"[Log In Error]Wrong code.");
            else
                return APIResponse.error(500,"[Log In Error]Absent name.");
        }
    }
}
