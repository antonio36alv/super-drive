package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entity.CredentialForm;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CredentialMapper {

    @Insert("INSERT INTO CREDENTIALS(url, username, ekey, password, userid) " +
    "VALUES(#{url}, #{username}, #{ekey}, #{password}, #{userId})")
    Integer insertCredential(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    List<Credential> getCredentials(Integer userId);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    Integer deleteCredential(Integer credentialId);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, ekey = #{ekey}," +
            "password = #{password} WHERE credentialid = #{credentialId}")
    Integer updateCredential(Integer credentialId, String url, String username, String ekey, String password);

}
