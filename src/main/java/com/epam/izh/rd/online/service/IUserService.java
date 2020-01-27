package com.epam.izh.rd.online.service;

import com.epam.izh.rd.online.entity.User;
import com.epam.izh.rd.online.exception.NotAccessException;
import com.epam.izh.rd.online.exception.SimplePasswordException;
import com.epam.izh.rd.online.exception.UserAlreadyRegisteredException;
import com.epam.izh.rd.online.exception.UserNotFoundException;

public interface IUserService {

    User register(User user) throws UserNotFoundException, UserAlreadyRegisteredException, SimplePasswordException;

    void delete(String login) throws NotAccessException;
}
