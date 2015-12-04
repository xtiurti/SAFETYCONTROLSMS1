package br.com.twautomacao.safetycontrolsms.activity;

import br.com.twautomacao.safetycontrolsms.R;
import br.com.twautomacao.safetycontrolsms.controller.UsuarioController;
import br.com.twautomacao.safetycontrolsms.dao.UsuarioDao;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {

	private Button btEntrar;
	private EditText edLogin;
	private EditText edSenha;
	private UsuarioDao usuarioDao;
	public static String PREFS_NAME = "mypre";
	public static String PREF_USERNAME = "username";
	public static String PREF_PASSWORD = "password";

	@Override
	protected void onCreate(Bundle savedInstanceState) {


		super.onCreate(savedInstanceState);
		usuarioDao = new UsuarioDao(getBaseContext());
		if (usuarioDao.temUser()) {
			setContentView(R.layout.activity_login);
			btEntrar = (Button) findViewById(R.id.btnSingIn);

			btEntrar.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					doLogin();
				}
			});


		} else {
			Intent i = new Intent(MainActivity.this, CadastrarUsuarioActivity.class);
			startActivity(i);
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return true;
	}

	public void rememberMe(String user, String password) {
		//save username and password in SharedPreferences
		getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
				.edit()
				.putString(PREF_USERNAME, user)
				.putString(PREF_PASSWORD, password)
				.commit();
	}

	public void getUser() {
		SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		String username = pref.getString(PREF_USERNAME, null);
		String password = pref.getString(PREF_PASSWORD, null);

		if (username != null || password != null) {
			edLogin.setText(username);
			edSenha.setText(password);

		}
	}

	public void onStart(){
		super.onStart();
		//read username and password from SharedPreferences

		edLogin = (EditText) findViewById(R.id.etUserName);
		edSenha = (EditText) findViewById(R.id.etPass);
		getUser();
	}

	public void doLogin() {
		UsuarioController usuarioController = new UsuarioController();
		String login = edLogin.getText().toString();
		String senha = edSenha.getText().toString();
		if (usuarioController.login(login, senha, getBaseContext())) {
			CheckBox ch = (CheckBox) findViewById(R.id.ch_rememberme);
			if (ch.isChecked())
				rememberMe(login, senha);
			else{
				SharedPreferences sharedPrefs =getSharedPreferences(MainActivity.PREFS_NAME,MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedPrefs.edit();
				editor.clear();
				editor.commit();
			}
			Intent i = new Intent(MainActivity.this, AgendaaActivity.class);
			startActivity(i);
		} else {
			Toast toast = Toast.makeText(getBaseContext(), "Usuário ou senha incorretos.", Toast.LENGTH_LONG);
			toast.show();
		}


	}
}