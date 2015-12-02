package br.com.twautomacao.safetycontrolsms.dao;

import java.util.ArrayList;

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
public class AgendaDao {

	private SQLiteDatabase dataBase = null;
	public static final String NOME_TABELA = "Agenda";

	public AgendaDao(Context context) {
		DatabaseHelper databaseHelper = new DatabaseHelper(context);
		dataBase = databaseHelper.getWritableDatabase();
	}

	public void salvar(Agenda agenda) {
		ContentValues values = gerarContentValeuesAgenda(agenda);
		dataBase.insert(NOME_TABELA, null, values);
	}

	public void deletar(Agenda agenda) {
		dataBase.delete(NOME_TABELA, "numero =  ?", new String[]{agenda.getNumero()});
	}

	public void editar(String n, Agenda agenda) {
		ContentValues valores = gerarContentValeuesAgenda(agenda);

		dataBase.update(NOME_TABELA, valores, "numero = ?", new String[]{n});
	}

	public void fecharConexao() {
		if(dataBase != null && dataBase.isOpen())
			dataBase.close();
	}
	
	public Agenda getOne(String numero) {
		Agenda agenda = null;
		Cursor cursor = null;

		String where = "numero = ?";

		String[] colunas = new String[] { "nome", "numero", "senha"};

		String argumentos[] = new String[] {numero};

		cursor = dataBase.query(NOME_TABELA, colunas, where, argumentos, null, null, null);

		if (cursor != null && cursor.moveToFirst()) {
			agenda = new Agenda(cursor.getString(cursor.getColumnIndex("Numero")), 
					cursor.getString(cursor.getColumnIndex("Nome")), cursor.getString(cursor.getColumnIndex("Senha")));
		}

		if (cursor != null)
			cursor.close();

		return agenda;
	}

	private ContentValues gerarContentValeuesAgenda(Agenda agenda) {		
		ContentValues values = new ContentValues();
		values.put("numero", agenda.getNumero());
		values.put("nome", agenda.getNome());
		values.put("senha", agenda.getSenha());
		

		return values;
	}

	public void insert(Agenda agenda) {
		
		ContentValues content = new ContentValues();
		content = gerarContentValeuesAgenda(agenda);
		
		dataBase.insert(NOME_TABELA, null, content);
	}

	public ArrayList<Agenda> listAll(){
		ArrayList<Agenda> agendas = new ArrayList<Agenda>();
		
		Cursor cursor = null;
		
		String[] colunas = new String[] { "Nome", "Numero","Senha"};

		cursor = dataBase.query(NOME_TABELA, colunas, null, null, null, null, null);
	    if (cursor.moveToFirst()) {
	        do {
	        	Agenda a = new Agenda(cursor.getString(cursor.getColumnIndex("Numero")), 
	        			cursor.getString(cursor.getColumnIndex("Nome")), cursor.getString(cursor.getColumnIndex("Senha")));
	            agendas.add(a);
	        } while (cursor.moveToNext());
	    }
	    if (cursor != null && !cursor.isClosed()) {
	        cursor.close();
	    }
	    return agendas;
	}
	
	public ArrayList<Agenda> listAllLike(String s){
		ArrayList<Agenda> agendas = new ArrayList<Agenda>();
		
		Cursor cursor = null;
		
		String[] colunas = new String[] { "Nome", "Numero","Senha"};

		cursor = dataBase.query(NOME_TABELA, colunas, "Nome like ?", new String[]{s+"%"}, null, null, null);
	    if (cursor.moveToFirst()) {
	        do {
	        	Agenda a = new Agenda(cursor.getString(cursor.getColumnIndex("Numero")), 
	        			cursor.getString(cursor.getColumnIndex("Nome")), cursor.getString(cursor.getColumnIndex("Senha")));
	            agendas.add(a);
	        } while (cursor.moveToNext());
	    }
	    if (cursor != null && !cursor.isClosed()) {
	        cursor.close();
	    }
	    return agendas;
	}
}
