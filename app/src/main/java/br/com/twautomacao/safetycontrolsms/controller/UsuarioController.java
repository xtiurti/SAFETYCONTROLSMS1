package br.com.twautomacao.safetycontrolsms.controller;

import android.content.Context;
import android.util.Log;

import br.com.twautomacao.safetycontrolsms.dao.UsuarioDao;
import br.com.twautomacao.safetycontrolsms.pojo.User;
import br.com.twautomacao.safetycontrolsms.util.Funcoes;

/**
 * Created by Ricardo on 18/08/2014.
 */
public class UsuarioController {

    public Boolean login(String login, String senha, Context context){
        UsuarioDao usuarioDao = new UsuarioDao(context);
        User usuario = usuarioDao.getOne(login);
        Funcoes f = new Funcoes();
        if(usuario==null){
        	usuarioDao.fecharConexao();
            return false;
        }
        try {
            return (usuario.getPass().equals(f.gerarMD5(senha)));
        }catch (Exception e){
            Log.e(e.getMessage(),"erro");
        }
        usuarioDao.fecharConexao();
        return false;
    }

    public void insert(User usuario, Context context){
        UsuarioDao usuarioDao = new UsuarioDao(context);
        usuarioDao.insert(usuario);
        usuarioDao.fecharConexao();
    }
    
}
