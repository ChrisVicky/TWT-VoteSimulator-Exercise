package TWT.Homework.VoteSimulator.controller;

import TWT.Homework.VoteSimulator.dao.UserSchemaMapper;
import TWT.Homework.VoteSimulator.service.UserService;
import TWT.Homework.VoteSimulator.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserSchemaMapper userSchemaMapper;

    @GetMapping("/user")//PostMapping("/user")
    public APIResponse getAllUser(@RequestParam("managerName") String name,
                                  @RequestParam("managerCode") String code){

        List<String> managerCodeList = userSchemaMapper.getManagerCode(name);
        if(managerCodeList.size()==0) return APIResponse.error(500,"[Manager Log In Error]Invalid Manager Name.");
        if(code.equals(managerCodeList.get(0)))
            return userService.getAllUsers();
        return APIResponse.error(500,"[Manager Log In Error]Wrong Manager Code.");
    }


    @PostMapping("/user")
    public APIResponse addUser(@RequestParam("name") String name,
                               @RequestParam("code") String code){
        return userService.addUser(name, code);
    }

    @PutMapping("/user")
    public APIResponse updateUser(@RequestParam("name") String name,
                                  @RequestParam("code") String code,
                                  @RequestParam("newCode") String newCode,
                                  @RequestParam("newName") String newName){
        if(userService.logIn(name, code)){
            return userService.updateUser(name, code, newCode, newName);
        }else{
            if(userSchemaMapper.getUserId(name).size()!=0) {
                return APIResponse.error(500, "[Log In Error]Wrong code.");
            }
            else
                return APIResponse.error(500,"[Log In Error]Absent name.");
        }
    }

    @DeleteMapping("/user")
    public APIResponse deleteUser(@RequestParam("name") String name,
                                  @RequestParam("code") String code,
                                  @RequestParam("managerName") String managerName,
                                  @RequestParam("managerCode") String managerCode){
        if(userService.logIn(name, code)){
            return userService.deleteUser(name, code, managerName, managerCode);
        }else{
            if(userSchemaMapper.getUserId(name).size()!=0)
                return APIResponse.error(500,"[Log In Error]Wrong code.");
            else
                return APIResponse.error(500,"[Log In Error]Absent name.");
        }

    }

    @GetMapping("/findCode")
    public APIResponse findCode(@RequestParam("name") String name,
                                @RequestParam("managerName") String managerName,
                                @RequestParam("managerCode") String managerCode){
        if(userSchemaMapper.getUserId(name).size()==0)
            return APIResponse.error(500, "[Find Code Error]Absent name.");
        return userService.findCode(name, managerName, managerCode);
    }

    @GetMapping("/userName")
    public APIResponse getAllUserName(){
        return userService.getAllUserName();
    }

}
