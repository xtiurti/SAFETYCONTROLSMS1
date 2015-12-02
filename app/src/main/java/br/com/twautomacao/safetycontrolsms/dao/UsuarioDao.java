package br.com.twautomacao.safetycontrolsms.dao;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import br.com.twautomacao.safetycontrolsms.connection.DatabaseHelper;
import br.com.twautomacao.safetycontrolsms.pojo.User;
import br.com.twautomacao.safetycontrolsms.util.Funcoes;

/**
 * Created by Ricardo on 18/08/2014.
 */
public class UsuarioDao {

	private SQLiteDatabase dataBase = null;
	public static final String NOME_TABELA = "users";
	private DatabaseHelper databaseHelper;
	public UsuarioDao(Context context) {
		databaseHelper = new DatabaseHelper(context);
		dataBase = databaseHelper.getWritableDatabase();
	}

	public int testeIncrementa(){
		int qtde=testeQuantidade();
		qtde++;

		ContentValues values = new ContentValues();
		values.put("comandos", qtde);
		dataBase=databaseHelper.getWritableDatabase();
		dataBase.update("teste", values, "comandos = ?", new String[]{String.valueOf((qtde-1))});
		dataBase.close();
		return qtde;
	}

	public int testeQuantidade(){
		Cursor cursor = null;

		String[] colunas = new String[] { "comandos"};

		cursor = dataBase.query("teste", colunas, null, null, null, null, null);

		if (cursor != null && cursor.moveToFirst()) {
			dataBase.close();
			return cursor.getInt(0);
		}

		if (cursor != null)
			dataBase.close();
		cursor.close();
		return 0;
	}

	public Boolean temUser(){

		Cursor cursor = null;

		String[] colunas = new String[] { "login", "Senha"};

		cursor = dataBase.query(NOME_TABELA, colunas, null, null, null, null, null);

		if (cursor != null && cursor.moveToFirst()) {
			cursor.close();
			dataBase.close();
			return true;
		}

		if (cursor != null)
			cursor.close();
		dataBase.close();
		return false;
	}

	public void salvar(User usuario) {
		ContentValues values = gerarContentValeuesUsuario(usuario);
		dataBase.insert(NOME_TABELA, null, values);
		dataBase.close();
	}

	public void deletar(User usuario) {
		dataBase.delete(NOME_TABELA, "login =  ?", new String[]{usuario.getLogin()});
		dataBase.close();
	}

	public void editar(User usuario) {
		ContentValues valores = gerarContentValeuesUsuario(usuario);

		dataBase.update(NOME_TABELA, valores, "login = ?", new String[]{usuario.getLogin()});
		dataBase.close();
	}

	public void fecharConexao() {
		if(dataBase != null && dataBase.isOpen())
			dataBase.close();
	}

	private ContentValues gerarContentValeuesUsuario(User usuario) {
		Funcoes f = new Funcoes();
		ContentValues values = new ContentValues();
		try {
			values.put("senha", f.gerarMD5(usuario.getPass()));
		}catch (Exception e){
			Log.e(e.getMessage(),"erro");
		}
		dataBase.close();
		return values;
	}

	public void insert(User usuario) {
		Funcoes f = new Funcoes();
		ContentValues content = new ContentValues();
		content.put("login", usuario.getLogin());
		try{
			String senha = f.gerarMD5(usuario.getPass());
			content.put("senha", senha);
		}catch(Exception e){
			Log.e("erro", e.getMessage());
		}
		dataBase.insert(NOME_TABELA, null, content);
		dataBase.close();
	}

	public User getOne(String userLogin) {
		User usuario = null;
		Cursor cursor = null;

		String where = "login = ?";

		String[] colunas = new String[] { "login", "Senha"};

		String argumentos[] = new String[] { userLogin};

		cursor = dataBase.query(NOME_TABELA, colunas, where, argumentos, null, null, null);

		if (cursor != null && cursor.moveToFirst()) {
			usuario = new User(cursor.getString(cursor.getColumnIndex("Login")),
					cursor.getString(cursor.getColumnIndex("Senha")));
		}

		if (cursor != null)
			cursor.close();
		dataBase.close();
		return usuario;
	}
}
