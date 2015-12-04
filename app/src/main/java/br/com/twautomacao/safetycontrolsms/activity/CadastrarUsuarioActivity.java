package br.com.twautomacao.safetycontrolsms.activity;

import br.com.twautomacao.safetycontrolsms.R;
import br.com.twautomacao.safetycontrolsms.controller.UsuarioController;
import br.com.twautomacao.safetycontrolsms.pojo.User;
import br.com.twautomacao.safetycontrolsms.util.AutoResizeTextView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CadastrarUsuarioActivity extends Activity {

	private EditText edNome;
	private EditText edSenha;
	private EditText edConfirm;
	private Button btInsert;
	private AutoResizeTextView tvSenha;
	private User usuario;
	private TextView tvSenha1;
	private TextView tvNome;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastrar_usuario);

		edNome = (EditText) findViewById(R.id.etNome);
		edSenha = (EditText) findViewById(R.id.etSenha);
		edConfirm = (EditText) findViewById(R.id.etConfirm);
		btInsert = (Button) findViewById(R.id.btSalvar);
		tvSenha = (AutoResizeTextView)findViewById(R.id.textView4);
		tvSenha1 = (TextView) findViewById(R.id.tvSenha);
		tvNome = (TextView) findViewById(R.id.tvNome);

		btInsert.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String nome=edNome.getText().toString();
				String senha=edSenha.getText().toString();
				String confirm=edConfirm.getText().toString();
				if(!(nome.equals("") ||  senha.equals("") || confirm.equals(""))){
					if(senha.equals(confirm)){
						usuario = new User( nome, senha);
						UsuarioController uContr = new UsuarioController();
							uContr.insert(usuario, getBaseContext());
							Toast.makeText(getApplicationContext(), "Usuario cadastrado com sucesso.", Toast.LENGTH_SHORT).show();
							Intent i = new Intent(CadastrarUsuarioActivity.this,AgendaaActivity.class);
							startActivity(i);
					}else{
						Toast.makeText(getApplicationContext(), "As senhas nao sao iguais, verifique.", Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(getApplicationContext(), "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		tvSenha.setMinTextSize(15);
		tvSenha.setSingleLine(true);
		tvSenha.setText("Confirme senha:");
		//tvSenha1.setTextSize(TypedValue.COMPLEX_UNIT_PX, tvSenha.getTextSize());
		//tvNome.setTextSize(TypedValue.COMPLEX_UNIT_PX, tvSenha.getTextSize());
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cadastrar_usuario, menu);
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
}
