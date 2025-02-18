package com.barry.product.service;

import com.barry.product.dto.request.UserRequest;
import com.barry.product.dto.response.UserResponse;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);
}
