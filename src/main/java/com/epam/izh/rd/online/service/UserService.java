package com.epam.izh.rd.online.service;

import com.epam.izh.rd.online.entity.*;
import com.epam.izh.rd.online.exception.*;
import com.epam.izh.rd.online.repository.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserService implements IUserService {

    private IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Необходимо доработать данный метод следующим функционалом:
     * <p>
     * 1) Необходимо проверять наличие заполнения всех полей сущности User. Если же поле с логином или паролем не
     * заполнено или заполнено пустой строкой. Необходимо выбрасывать существующее непроверяемое исключение
     * {@link IllegalArgumentException} с текстом "Ошибка в заполнении полей".
     * <p>
     * 2) Необходимо запрещать регистрацию пользователя, если другой пользователь с подобным логином уже
     * зарегистрирован. Необходимо в таком случае выбрасывать проверяемое исключение с названием UserAlreadyRegisteredException
     * и текстом - "Пользователь с логином 'login' уже зарегистрирован", где login - логин пользователя.
     * <p>
     * 3) Необходимо запрещать регистрацию пользователя, если он пытается установить пароль, состоящий из цифр.
     * В случае, если это происходит (например пароль = "123432") необходимо выбрасывать
     * исключение с названием SimplePasswordException и текстом - "Пароль не соответствует требованиям безопасности"
     *
     * @param user - данные регистрирующегося пользователя
     */
    @Override
    public User register(User user) throws UserAlreadyRegisteredException, SimplePasswordException {
        String login = user.getLogin();
        String password = user.getPassword();
        if ((0 == login.length()) || (0 == password.length())) {
            throw new IllegalArgumentException("Ошибка в заполнении полей");
        } else {
            User foundUser = userRepository.findByLogin(login);
            if (foundUser == null) {
                Pattern pattern = Pattern.compile("^[0-9]+$");
                Matcher matcher = pattern.matcher(password);
                if (matcher.find()) {
                    throw new SimplePasswordException("Пароль не соответствует требованиям безопасности");
                } else {
                    return userRepository.save(user);
                }
            } else {
                throw new UserAlreadyRegisteredException("Пользователь с логином " + login + " уже зарегистрирован");
            }
        }
    }

    /**
     * Необходимо доработать данный метод следующим функционалом:
     * <p>
     * Если мы попытаемся вызвать метод удаления {@link UserRepository#deleteByLogin(String)}
     * пользователем не админом (считаем, что админ имеет логин Admin), то будет выброшено исключение
     * {@link UnsupportedOperationException} из репозитория.
     * <p>
     * При этом, текст этого исключения выглядит очень непрезентабельно.
     * <p>
     * Поэтому, если мы не перехватим (через try-catch) это исключение, то оно уйдет пользователю с данным текстом.
     * Нам необходимо это предотвратить, перехватив данное исключение и выбросив в ответ на него свое,
     * но уже с понятным для пользователя сообщением.
     * <p>
     * Название вашего исключения должно быть следующее - NotAccessException и текст сообщения должен
     * быть "Недостаточно прав для выполнения операции"
     *
     * @param login
     */
    public void delete(String login) throws NotAccessException {
        try {
            userRepository.deleteByLogin(login);
        } catch (UnsupportedOperationException e) {
            throw new NotAccessException("Недостаточно прав для выполнения операции");
        }
    }
}
