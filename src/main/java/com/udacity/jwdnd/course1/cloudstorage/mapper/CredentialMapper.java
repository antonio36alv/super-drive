package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entity.CredentialForm;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Insert("INSERT INTO CREDENTIALS(url, username, key, password, userid) " +
    "VALUES(#{url}, #{username}, #{key}, #{password}, #{userId})")
    Integer insertCredential(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    List<Credential> getCredentials(Integer userId);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    Integer deleteCredential(Integer credentialId);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, key = #{key}," +
            "password = #{password} WHERE credentialid = #{credentialId}")
    Integer updateCredential(Integer credentialId, String url, String username, String key, String password);

}
