package TWT.Homework.VoteSimulator.controller;

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

    @GetMapping("/allQuestion")
    public APIResponse getAllQuestion(){
        return voteService.getAllQuestion();
    }
    @GetMapping("/allChoice")
    public APIResponse getAllChoice(){
        return voteService.getAllChoice();
    }

    @PostMapping("/userVote")
    public APIResponse getUserVote(@RequestParam("name") String name,
                                   @RequestParam("code") String code){
        if(userService.checkUser(name, code)){
            return voteService.getUserVote(name, code);
        }else{
            return APIResponse.error(500, "[WrongName/Passcode]");
        }
    }

    @PostMapping("/vote")
    public APIResponse addVote(@RequestParam("name") String name,
                               @RequestParam("code") String code,
                               @RequestParam("question") String question,
                               @RequestParam("choice") List<String> choiceList){
        if(userService.checkUser(name, code)){
            return voteService.addVote(name, code, question, choiceList);
        }else{
            return APIResponse.error(500, "[WrongName/Passcode]");
        }
    }

    @DeleteMapping("/vote")
    public APIResponse deleteVote(@RequestParam("name") String name,
                                  @RequestParam("code") String code,
                                  @RequestParam("voteId") int voteId){
        if(userService.checkUser(name, code)){
            return voteService.deleteVote(name, code, voteId);
        }else{
            return APIResponse.error(500, "[WrongName/Passcode]");
        }
    }
}
