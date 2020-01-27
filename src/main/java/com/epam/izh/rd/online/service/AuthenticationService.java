package com.epam.izh.rd.online.service;

import com.epam.izh.rd.online.entity.*;
import com.epam.izh.rd.online.exception.*;
import com.epam.izh.rd.online.repository.*;

public class AuthenticationService implements IAuthenticationService {

    private IUserRepository userRepository;

    public AuthenticationService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Необходимо доработать данный метод следующим функционалом:
     * <p>
     * 1) Необходимо проверять наличие авторизующегося пользователя в списке зарегистрированных пользователей.
     * Если пользователь не найден, необходимо выбрасывать проверяемое исключение с названием UserNotFoundException
     * и текстом ошибки "Пользователь с таким логином не найден".
     * <p>
     * 2) Необходимо проверять, что пароль, который ввел пользователь совпадает с тем, что хранится в базе.
     * Если пароли не совпадают, необходимо выбрасывать исключение с названием NotCorrectPasswordException и
     * текстом "Пароль введен неверно!"
     *
     * @param user - пользователь проходящий авторизацию
     */
    @Override
    public User login(User user) throws NullPointerException, UserNotFoundException, NotCorrectPasswordException {
        User foundUser = userRepository.findByLogin(user.getLogin());
        if (foundUser == null) {
            throw new UserNotFoundException("Пользователь с таким логином не найден");
        } else {
            if (foundUser.getPassword().equals(user.getPassword())) {
                CurrentUserManager.setCurrentLoggedInUser(foundUser);
            } else {
                throw new NotCorrectPasswordException("Пароль введен неверно!");
            }
        }
        return foundUser;
    }

    /**
     * Данный метод очищает данные о текущем (активном) пользователе.
     */
    @Override
    public void logout() {
        CurrentUserManager.setCurrentLoggedInUser(null);
    }
}