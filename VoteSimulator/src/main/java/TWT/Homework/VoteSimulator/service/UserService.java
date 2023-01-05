package TWT.Homework.VoteSimulator.service;

import TWT.Homework.VoteSimulator.utils.APIResponse;

public interface UserService {
    public APIResponse getAllUsers();
    public APIResponse addUser(String name, String code);
    public APIResponse updateUser(String name, String newCode, String newName);
    public APIResponse deleteUser(String name, String code, String managerName, String managerCode);
    public APIResponse getAllUserName();
    public APIResponse existUser(String name);
    public boolean logIn(String name, String code);
    public APIResponse findCode(String name, String managerName, String managerCode);
}
