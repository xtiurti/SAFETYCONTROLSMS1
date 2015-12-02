package br.com.twautomacao.safetycontrolsms.pojo;

/**
 * Created by Ricardo on 18/08/2014.
 */
public class User {
    String login;
    String pass;

    public User(String login, String senha) {
        this.login = login;
        pass = senha;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPass(String pass) {
        pass = this.pass;
    }
}
