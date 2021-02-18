package TWT.Homework.VoteSimulator.service;

import TWT.Homework.VoteSimulator.utils.APIResponse;

public interface UserService {
    public APIResponse getAllUsers();
    public APIResponse addUser(String name, String code);
    public APIResponse updateUserCode(String name, String code, String newCode);
    public APIResponse deleteUser(String name, String code);
    public APIResponse getAllUserName();
    public APIResponse existUser(String name);
    public APIResponse showTable();
    public boolean checkUser(String name, String code);
}
