package com.epam.izh.rd.online.repository;

import com.epam.izh.rd.online.entity.User;
import com.epam.izh.rd.online.service.CurrentUserManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Готовый класс репозитория.
 * Можно добавлять свои методы, при необходимости, но нельзя исправлять текущие.
 * <p>
 * Позволяет сохранять\обновлять пользователей.
 * Позволяет находить пользователя по логину.
 * Позволяет удалять пользователей по логину (есть ограничения).
 */
public class UserRepository implements IUserRepository {

    /**
     * Поскольку мы еще не прошли тему по работе с базами данных, сохранять пользователей будем в списке
     */
    private List<User> userDatabase = new ArrayList<>();

    /**
     * Позволяет сохранить пользователя, либо обновить пароль текущего, если пользователь с таким логином уже
     * есть в нашей базе данных.
     */
    @Override
    public User save(User user) {
        Objects.requireNonNull(user, "Отсутствуют данные для сохранения или обновления пользователя");
        Objects.requireNonNull(user.getLogin(), "Отсутствует логин сохраняемого пользователя");
        Objects.requireNonNull(user.getPassword(), "Отсутствует пароль сохраняемого пользователя");

        User userWithSameLogin = findByLogin(user.getLogin());

        if (userWithSameLogin == null) {
            userDatabase.add(user);
        } else {
            userWithSameLogin.setPassword(user.getPassword());
        }

        return user;
    }

    /**
     * Находит пользователя в базе данных по логину.
     * <p>
     * Важно - если пользователь не найден - возвращается null.
     */
    @Override
    public User findByLogin(String login) {
        Objects.requireNonNull(login, "Отсутствует логин для поиска пользователя");

        return userDatabase.stream()
                .filter(user -> user.getLogin().equalsIgnoreCase(login))
                .findFirst().orElse(null);
    }

    /**
     * Метод удаления пользователя с определенным логином.
     * <p>
     * Если вызов метода удаления происходит под пользователем не админом (считаем, что админ имеет логин Admin)
     * или неавторизованным пользователем (если не был вызван метод
     * {@link com.epam.izh.rd.online.service.AuthenticationService#login(User)}), то будет выброшено исключение.
     */
    @Override
    public void deleteByLogin(String login) {
        Objects.requireNonNull(login, "Отсутствует логин для удаления пользователя");

        if (CurrentUserManager.getCurrentLoggedInUser() == null || !CurrentUserManager.getCurrentLoggedInUser()
                .getLogin().equalsIgnoreCase("admin")) {
            throw new UnsupportedOperationException("You have no access to call this method!!! Noooooo!!!");
        }

        userDatabase.removeIf(user -> user.getLogin().equalsIgnoreCase(login));
    }
}
