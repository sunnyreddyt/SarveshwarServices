package com.services.sarveshwarservices.model;

import java.util.ArrayList;

public class UsersResponse {

    private String error;
    private ArrayList<UsersResponse> users;
    private UsersResponse usersResponse;
    private String id;
    private String admin_name;
    private String pin;
    private String created_at;
    private String updated_at;


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ArrayList<UsersResponse> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<UsersResponse> users) {
        this.users = users;
    }

    public UsersResponse getUsersResponse() {
        return usersResponse;
    }

    public void setUsersResponse(UsersResponse usersResponse) {
        this.usersResponse = usersResponse;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdmin_name() {
        return admin_name;
    }

    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
