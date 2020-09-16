package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;

import java.util.List;

public interface CredentialService {

    List<Credential> getCredentials(Integer userId);

    Integer insertCredential(CredentialForm credentialForm);

    Integer editCredential();

    Integer deleteCredential(Integer credentialId);


}
