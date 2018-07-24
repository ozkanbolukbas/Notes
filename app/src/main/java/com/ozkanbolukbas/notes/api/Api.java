package com.ozkanbolukbas.notes.api;

import com.ozkanbolukbas.notes.model.LoginResponse;
import com.ozkanbolukbas.notes.model.Note;
import com.ozkanbolukbas.notes.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Api {
    @POST("users")
    Call<Void> signUp(@Body User user);

    @POST("users/login")
    Call<LoginResponse> login(@Body User user);

    @GET("users/me")
    Call<User> getMe();

    @GET("notes")
    Call<List<Note>> getNotes();

    @POST("notes")
    Call<Note> createNote(@Body Note note);

    @PUT("notes")
    Call<Note> updateNote(@Body Note note);

    @DELETE("notes/{id}")
    Call<Void> deleteNote(@Path("id") String id);

    @GET("public-notes")
    Call<List<Note>> getPublicNotes();

    @POST("public-notes")
    Call<Note> createPublicNote(@Body Note note);

    @DELETE("public-notes/{id}")
    Call<Void> deletePublicNote(@Path("id") String id);

    @PUT("public-notes")
    Call<Note> updatePublicNote(@Body Note note);
}
