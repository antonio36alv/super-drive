package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entity.CredentialForm;

import java.util.List;

public interface CredentialService {

    List<Credential> getCredentials(Integer userId);

    Integer insertCredential(CredentialForm credentialForm, Integer userId) throws Exception;

    Integer updateCredential(CredentialForm credentialForm);

    Integer deleteCredential(Integer credentialId);

}
