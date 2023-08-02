package com.codebeginner.sovary.fingerprinttest.reotrfit;
import org.springframework.http.ResponseEntity;
import com.codebeginner.sovary.fingerprinttest.reotrfit.User;


import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserApi {



  @POST("/demo/addusers")
  Call<ResponseEntity<String>> addUser(@Body User user);

}
