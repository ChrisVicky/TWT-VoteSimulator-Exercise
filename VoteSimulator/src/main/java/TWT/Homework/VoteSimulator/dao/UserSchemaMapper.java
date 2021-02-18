package TWT.Homework.VoteSimulator.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import TWT.Homework.VoteSimulator.entity.UserSchema;

import java.util.List;

@Repository
@Mapper
public interface UserSchemaMapper {
    public List<UserSchema> getAllUser();
    public int addUser(String name, String code);
    public int updateUserCode(String name, String code, String newCode);
    public int deleteUser(String name, String code);
    public List<String> getAllUserName();
    public int getUserId(String name);
    public List<String> showTable();
    public String getUserCode(String name);
}
