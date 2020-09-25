package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entity.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialServiceImpl implements CredentialService {

    @Autowired
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialServiceImpl(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    @Override
    public List<Credential> getCredentials(Integer userId) {
        return credentialMapper.getCredentials(userId);
    }

    @Override
    public Integer insertCredential(CredentialForm credentialForm, Integer userId) throws Exception {

        String ekey = encryptionService.prepareSecreteKey();

        Credential credential = new Credential(null, credentialForm.getUrl(),
                                                credentialForm.getUsername(), ekey,
                                                encryptionService.encryptValue(credentialForm.getPassword(), ekey),
                                                userId);
        return credentialMapper.insertCredential(credential);
    }

    @Override
    public Integer updateCredential(CredentialForm credentialForm) {

        String ekey = encryptionService.prepareSecreteKey();

        return credentialMapper.updateCredential(credentialForm.getCredentialId(), credentialForm.getUrl(),
                                                credentialForm.getUsername(), ekey,
                                                encryptionService.encryptValue(credentialForm.getPassword(), ekey));
    }

    @Override
    public Integer deleteCredential(Integer credentialId) {
        return credentialMapper.deleteCredential(credentialId);
    }

}
