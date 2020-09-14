package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import org.apache.ibatis.annotations.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE userId = #{userId}")
    List<File> getFiles(Integer userId);

    @Insert("INSERT INTO FILES(filename, contenttype, filesize, userid, filedata) VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    void insertFile(File file);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileId}")
    Integer deleteFile(Integer id);

}
