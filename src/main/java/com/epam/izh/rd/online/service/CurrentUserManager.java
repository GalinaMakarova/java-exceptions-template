package com.epam.izh.rd.online.service;

import com.epam.izh.rd.online.entity.User;

/**
 * Данный класс хранит информацию о текущем авторизованном пользователе (пользователе, который вошел в систему).
 * Считаем, что в систему может войти только один пользователь.
 * <p>
 * После вызова метода {@link AuthenticationService#login(User)}, если все проверки прошли успешно,
 * пользователь устанавливается в данное поле.
 * <p>
 * Поскольку методы статические и поле статическое, его можно доставать в других классах, если понадобится проверить,
 * какой пользователь сейчас находится в системе.
 * <p>
 * Это необходимо, например, при выполнении метода {@link com.epam.izh.rd.online.repository.UserRepository#deleteByLogin(String)}
 * <p>
 * Если установить значение поля currentUser обратно в null, будем считать, что пользователь вышел из системы.
 */
public class CurrentUserManager {

    private static User currentUser;

    private CurrentUserManager() {}

    public static User getCurrentLoggedInUser() {
        return currentUser;
    }

    public static void setCurrentLoggedInUser(User currentUser) {
        CurrentUserManager.currentUser = currentUser;
    }
}
