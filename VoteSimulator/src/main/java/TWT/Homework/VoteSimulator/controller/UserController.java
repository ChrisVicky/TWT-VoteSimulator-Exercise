package TWT.Homework.VoteSimulator.controller;

import TWT.Homework.VoteSimulator.service.UserService;
import TWT.Homework.VoteSimulator.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/allUser")
    public APIResponse getAllUser(){
        return userService.getAllUsers();
    }

    @GetMapping("/allUserName")
    public APIResponse getAllUserName(){
        return userService.getAllUserName();
    }

    @PostMapping("/user")
    public APIResponse addUser(@RequestParam("name") String name,
                               @RequestParam("code") String code){
        System.out.println(getAllUserName());
        return userService.addUser(name, code);
    }

    @PutMapping("/user")
    public APIResponse updateUser(@RequestParam("name") String name,
                                  @RequestParam("code") String code,
                                  @RequestParam("newCode") String newCode){
        return userService.updateUserCode(name, code, newCode);
    }

    @DeleteMapping("/user")
    public APIResponse deleteUser(@RequestParam("name") String name,
                                  @RequestParam("code") String code){
        return userService.deleteUser(name, code);
    }

    @PostMapping("/existUser")
    public APIResponse existUser(@RequestParam("name") String name){
        return userService.existUser(name);
    }

}
