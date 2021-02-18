package TWT.Homework.VoteSimulator.controller;

import TWT.Homework.VoteSimulator.dao.UserSchemaMapper;
import TWT.Homework.VoteSimulator.entity.UserSchema;
import TWT.Homework.VoteSimulator.service.UserService;
import TWT.Homework.VoteSimulator.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestFunction {
    @Autowired
    UserService userService;


    @PostMapping("/testFunction")
    public APIResponse testFunction(@RequestParam("choice") List<String> choice,
                                    @RequestParam("id") int id){
        return APIResponse.success(choice);
    }
}
