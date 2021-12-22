package com.system.ws.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {

    private static final int MAX_NUM_OF_ATTEMPTS = 5;
    private static final int ATTEMPT_INCREMENT = 1;
    private LoadingCache<String,Integer> loadingCache;

    public LoginAttemptService(){
      super();
      loadingCache = CacheBuilder
                      .newBuilder()
                      .expireAfterWrite(15, TimeUnit.MINUTES)
                      .maximumSize(100)
                      .build(new CacheLoader<String, Integer>() {
                          @Override
                          public Integer load(String key) throws Exception {
                              return 0;
                          }
                      });
    }

    public void addSystemUserToLoginAttemptCache(String username){
      int attempts = 0;
      try{
          attempts = ATTEMPT_INCREMENT + loadingCache.get(username);
          loadingCache.put(username,attempts);
      } catch (ExecutionException e) {
          e.printStackTrace();
      }
    }

    public void deleteSystemUserFromLoginAttemptCache(String username){
        loadingCache.invalidate(username);
    }

    public boolean hasExceededMaxAttempts(String username){
        try{
            return loadingCache.get(username) >= MAX_NUM_OF_ATTEMPTS;
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }
}
