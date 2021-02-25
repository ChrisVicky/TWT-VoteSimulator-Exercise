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

    @GetMapping("/question")
    public APIResponse getAllQuestion(){
        return voteService.getAllQuestion();
    }

    @GetMapping("/choice")
    public APIResponse getAllChoice(){ return voteService.getAllChoice(); }

    @GetMapping("/vote")
    public APIResponse getAllVote(){ return voteService.getAllVote(); }


    @PostMapping("/addVote")
    public APIResponse addVote(@RequestParam("name") String name,
                               @RequestParam("code") String code,
                               @RequestParam("question") String question,
                               @RequestParam("choice") List<String> choiceList){
        if(userService.logIn(name, code)){
            int userId = userSchemaMapper.getUserId(name).get(0);
            System.out.println(userId);
            return voteService.addVote(userId, question, choiceList);
        }else{
            if(userSchemaMapper.getUserId(name).size()!=0)
                return APIResponse.error(500,"[Log In Error]Wrong code.");
            else
                return APIResponse.error(500,"[Log In Error]Invalid name.");
        }
    }

    @PostMapping("/participate")
    public APIResponse participateVote(@RequestParam("name") String name,
                                       @RequestParam("code") String code,
                                       @RequestParam("voteId") int voteId,
                                       @RequestParam("choiceId") int choiceId){
        if(userService.logIn(name, code)){
            System.out.println("Log in to Participate vote");
            List<Integer> userIdList = userSchemaMapper.getUserId(name);
            System.out.println(userIdList);
            int userId = userIdList.get(0);
            return voteService.participateVote(userId, voteId, choiceId);
        }else{
            if(userSchemaMapper.getUserId(name).size()!=0)
                return APIResponse.error(500,"[Log In Error]Wrong code.");
            else
                return APIResponse.error(500,"[Log In Error]Absent name.");
        }
    }

    @PostMapping("/deleteVote") //
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

    @PostMapping("/deleteParticipation")
    public APIResponse deleteParticipation(@RequestParam("name") String name,
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

    @PostMapping("/reVote")
    public APIResponse reVote(@RequestParam("name") String name,
                              @RequestParam("code") String code,
                              @RequestParam("voteId") int voteId,
                              @RequestParam("choiceId") int choiceId){
        if(userService.logIn(name, code)){
            System.out.println("Log in to ReParticipate vote");
            int userId = userSchemaMapper.getUserId(name).get(0);
            System.out.println(voteService.deleteParticipation(userId, voteId));
            return voteService.participateVote(userId, voteId, choiceId);
        }else{
            if(userSchemaMapper.getUserId(name).size()!=0)
                return APIResponse.error(500,"[Log In Error]Wrong code.");
            else
                return APIResponse.error(500,"[Log In Error]Absent name.");
        }
    }

    @GetMapping("/result")
    public APIResponse getResult(@RequestParam("voteId") int voteId){
        return voteService.getResult(voteId);
    }

    @PostMapping("/myParticipation")
    public APIResponse getParticipation(@RequestParam("name") String name,
                                        @RequestParam("code") String code){
        if(userService.logIn(name, code)){
            int userId = userSchemaMapper.getUserId(name).get(0);
            return voteService.getParticipation(userId);
        }else{
            if(userSchemaMapper.getUserId(name).size()!=0)
                return APIResponse.error(500,"[Log In Error]Wrong code.");
            else
                return APIResponse.error(500,"[Log In Error]Invalid name.");

        }
    }

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
}
