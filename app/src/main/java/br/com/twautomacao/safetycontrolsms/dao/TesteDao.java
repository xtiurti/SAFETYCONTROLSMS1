package br.com.twautomacao.safetycontrolsms.dao;

import java.util.ArrayList;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import br.com.twautomacao.safetycontrolsms.connection.DatabaseHelper;
import br.com.twautomacao.safetycontrolsms.pojo.Agenda;
import br.com.twautomacao.safetycontrolsms.pojo.User;
import br.com.twautomacao.safetycontrolsms.util.Funcoes;

/**
 * Created by Ricardo on 18/08/2014.
 */
public class TesteDao {

	private SQLiteDatabase dataBase = null;
	public static final String NOME_TABELA = "teste";

	public TesteDao(Context context) {
		DatabaseHelper databaseHelper = new DatabaseHelper(context);
		dataBase = databaseHelper.getWritableDatabase();
	}

	public Boolean registrar(Integer numeros) {
		ContentValues values = new ContentValues();
		values.put("ativado", "1");
		values.put("comandos", "0");
		values.put("numeros", numeros);
		return dataBase.update(NOME_TABELA, values, null, null)>0;
	}

	public void fecharConexao() {
		if(dataBase != null && dataBase.isOpen())
			dataBase.close();
	}
	
	public int cadastros(){
		String[] colunas = new String[] { "numeros"};
		Cursor cursor=null;
		Boolean ativado=false;
		cursor = dataBase.query(NOME_TABELA, colunas, null, null, null, null, null);

		if (cursor != null && cursor.moveToFirst()) {
			return cursor.getInt(cursor.getColumnIndex("numeros"));
		}

		if (cursor != null)
			cursor.close();

		return 0;
	}
	
	public Boolean getAtivado() {

		String[] colunas = new String[] { "ativado"};
		Cursor cursor=null;
		Boolean ativado=false;
		cursor = dataBase.query(NOME_TABELA, colunas, null, null, null, null, null);

		if (cursor != null && cursor.moveToFirst()) {
			if (cursor.getInt(cursor.getColumnIndex("ativado"))==1)
				ativado=true;
		}

		if (cursor != null)
			cursor.close();

		return ativado;
	}
}
