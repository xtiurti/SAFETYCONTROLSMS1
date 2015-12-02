package br.com.twautomacao.safetycontrolsms.activity;

import java.security.spec.MGF1ParameterSpec;

import br.com.twautomacao.safetycontrolsms.CadastrarUsuario;
import br.com.twautomacao.safetycontrolsms.R;
import br.com.twautomacao.safetycontrolsms.R.id;
import br.com.twautomacao.safetycontrolsms.R.layout;
import br.com.twautomacao.safetycontrolsms.R.menu;
import br.com.twautomacao.safetycontrolsms.controller.UsuarioController;
import br.com.twautomacao.safetycontrolsms.dao.UsuarioDao;
import br.com.twautomacao.safetycontrolsms.pojo.User;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {

	private Button btEntrar;
	private EditText edLogin;
	private EditText edSenha;
	private UsuarioDao usuarioDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		usuarioDao = new UsuarioDao(getBaseContext());
		if(usuarioDao.temUser()){
			setContentView(R.layout.activity_login);

			btEntrar = (Button) findViewById(R.id.btnSingIn);
			edLogin = (EditText) findViewById(R.id.etUserName);
			edSenha = (EditText) findViewById(R.id.etPass);

			btEntrar.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					UsuarioController usuarioController = new UsuarioController();
					String login = edLogin.getText().toString();
					String senha = edSenha.getText().toString();
					if(usuarioController.login(login, senha, getBaseContext())){
						Intent i = new Intent(MainActivity.this, AgendaaActivity.class);
						startActivity(i);
					}else{
						Toast toast = Toast.makeText(getBaseContext(), "Usuário ou senha incorretos.", Toast.LENGTH_LONG);
						toast.show();
					}
				}
			});
			 
				
		}else{
			Intent i = new Intent(MainActivity.this, CadastrarUsuario.class);
			startActivity(i);
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public boolean confirmaSenha(String imei, String senha, String cadastros){
		Integer gerado = Integer.parseInt(imei)+Integer.parseInt(cadastros);
		gerado = gerado/57;
		if(gerado.toString().equals(senha))
			return true;
		else
			return false;
	}
}