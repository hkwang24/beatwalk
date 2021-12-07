package com.example.beatwalk.Retrofit;

import io.reactivex.Observable;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IMyService {

    @POST("register")
    @FormUrlEncoded
    Observable<String> registerUser(@Field("email") String email,
                                    @Field("name") String name,
                                    @Field("password") String password,
                                    @Field("year") String year,
                                    @Field("phone") String phone);

    @POST("login")
    @FormUrlEncoded
    Observable<String> loginUser(@Field("email") String email,
                                 @Field("password") String name);

    @POST("contains")
    @FormUrlEncoded
    Observable<String> contains(@Field("email") String email);

    @POST("getUser")
    @FormUrlEncoded
    Observable<String> getUser(@Field("email") String email);

    @POST("containsId")
    @FormUrlEncoded
    Observable<String> containsId(@Field("id") String id);

    @POST("modify")
    @FormUrlEncoded
    Observable<String> modifyUser(@Field("email") String email,
                                  @Field("name") String name,
                                  @Field("year") String year,
                                  @Field("phone") String phone);

    @POST("users")
    @FormUrlEncoded
    Observable<String> allUsers(@Field("email") String email);

    @POST("allProjects")
    @FormUrlEncoded
    Observable<String> allProjects(@Field("id") String id);

    @POST("addRating")
    @FormUrlEncoded
    Observable<String> addRating(@Field("email") String email,
                                 @Field("rating") int rating);

    @POST("createProject")
    @FormUrlEncoded
    Observable<String> createProject(@Field("id") String id,
                                  @Field("user") String user,
                                  @Field("creationDate") String creationDate);

    @POST("modifyProject")
    @FormUrlEncoded
    Observable<String> modifyProject(@Field("id") String id,
                                  @Field("user") String user,
                                  @Field("creationDate") String creationDate);

    @POST("deleteProject")
    @FormUrlEncoded
    Observable<String> deleteProject(@Field("id") String id);
}
