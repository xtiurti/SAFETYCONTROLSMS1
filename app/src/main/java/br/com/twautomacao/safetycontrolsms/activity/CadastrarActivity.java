package br.com.twautomacao.safetycontrolsms.activity;

import br.com.twautomacao.safetycontrolsms.R;
import br.com.twautomacao.safetycontrolsms.R.id;
import br.com.twautomacao.safetycontrolsms.R.layout;
import br.com.twautomacao.safetycontrolsms.R.menu;
import br.com.twautomacao.safetycontrolsms.dao.AgendaDao;
import br.com.twautomacao.safetycontrolsms.pojo.Agenda;
import br.com.twautomacao.safetycontrolsms.util.AutoResizeTextView;
import br.com.twautomacao.safetycontrolsms.util.DialogHandler;
import br.com.twautomacao.safetycontrolsms.util.Mask;
import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CadastrarActivity extends Activity {

	private EditText edNome;
	private EditText edNumero;
	private EditText edSenha;
	private EditText edConfirm;
	private Button btInsert;
	private Button btCancelar;
	private Button btExcluir;
	private AutoResizeTextView tvSenha;
	private AutoResizeTextView tvNumero;
	private TextView tvSenha1;
	private TextView tvNome;
	private Agenda a;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastrar);

		edNome = (EditText) findViewById(R.id.etNome);
		edNumero = (EditText) findViewById(R.id.etNumero);
		edSenha = (EditText) findViewById(R.id.etSenha);
		edConfirm = (EditText) findViewById(R.id.etConfirm);
		btCancelar = (Button) findViewById(R.id.btCancelar);
		btExcluir = (Button) findViewById(R.id.btExcluir);
		btInsert = (Button) findViewById(R.id.btSalvar);
		tvSenha = (AutoResizeTextView)findViewById(R.id.textView4);
		tvNumero =  (AutoResizeTextView) findViewById(R.id.textView2);
		tvSenha1 = (TextView) findViewById(R.id.tvSenha);
		tvNome = (TextView) findViewById(R.id.tvNome);

		Intent intent=this.getIntent();
		Bundle bundle=intent.getExtras();
		if(bundle!=null){
			if(bundle.containsKey("agenda")){
				a = (Agenda) bundle.getSerializable("agenda");
				edNome.setText(a.getNome());
				edNumero.setText(a.getNumero());
				edSenha.setText(a.getSenha());
				edConfirm.setText(a.getSenha());
				btInsert.setText("Alterar");
				btExcluir.setVisibility(View.VISIBLE);
			}
		}
		final TextWatcher phone=Mask.insert("(##)#####-####", edNumero);
		edNumero.addTextChangedListener(phone);
		edNumero.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(!hasFocus){
					if(edNumero.getText().toString().length()==13){
						String numero=edNumero.getText().toString();	
						String prefix=numero.substring(0, 8);
						String sufix=String.valueOf(numero.charAt(8))+numero.substring(10, numero.length());
						numero = prefix+"-"+sufix;
						edNumero.removeTextChangedListener(phone);
						edNumero.setText(numero);
						edNumero.addTextChangedListener(phone);
					}
				}
			}
		});

		btInsert.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String nome=edNome.getText().toString();
				String numero=edNumero.getText().toString();
				String senha=edSenha.getText().toString();
				String confirm=edConfirm.getText().toString();
				if(!(nome.equals("") || numero.equals("") || senha.equals("") || confirm.equals(""))){
					if(senha.length()==6){
						if(numero.length()>=13){
							if(senha.equals(confirm)){
								if(btInsert.getText().equals("Salvar")){
									a = new Agenda(numero, nome, senha);
									AgendaDao agendaDao = new AgendaDao(getApplicationContext());
									if(agendaDao.getOne(numero)==null){
										agendaDao.insert(a);
										Toast.makeText(getApplicationContext(), "Numero cadastrado com sucesso.", Toast.LENGTH_SHORT).show();
										Intent i = new Intent(CadastrarActivity.this,AgendaaActivity.class);
										startActivity(i);
									}else{
										Toast.makeText(getApplicationContext(), "Numero já cadastrado, verifique.", Toast.LENGTH_SHORT).show();
									}
								}else{
									Agenda nova = new Agenda(numero,nome,senha);
									AgendaDao agendaDao = new AgendaDao(getApplicationContext());
									agendaDao.editar(a.getNumero(), nova);
									Toast.makeText(getApplicationContext(), "Registro alterado com sucesso.", Toast.LENGTH_SHORT).show();
									Intent i = new Intent(CadastrarActivity.this,AgendaaActivity.class);
									startActivity(i);
								}
							}else{
								Toast.makeText(getApplicationContext(), "As senhas não são iguais, verifique.", Toast.LENGTH_SHORT).show();
							}
						}else{
							Toast.makeText(getApplicationContext(), "Numero incorreto. Verifique.", Toast.LENGTH_SHORT).show();
						}

					}else{
						Toast.makeText(getApplicationContext(), "A senha deve conter 6 numeros. Verifique.", Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(getApplicationContext(), "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
				}
			}
		});

		btExcluir.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog diaBox = AskOption();
				diaBox.show();
			}
		});

		btCancelar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(CadastrarActivity.this,AgendaaActivity.class);
				startActivity(i);
			}
		});
		
		tvSenha.setMinTextSize(15);
		tvSenha.setSingleLine(true);
		tvSenha.setText("Confirme senha:");
		tvNumero.setMinTextSize(15);
		tvNumero.setSingleLine(true);
		tvNumero.setText("Numero do Chip:");
		//tvSenha1.setTextSize(TypedValue.COMPLEX_UNIT_PX, tvSenha.getTextSize());
		//tvNome.setTextSize(TypedValue.COMPLEX_UNIT_PX, tvSenha.getTextSize());
	}

	private AlertDialog AskOption()
	{
		AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this) 
		//set message, title, and icon
		.setTitle("Excluir") 
		.setMessage("Tem certeza que deseja excluir este numero") 
		.setIcon(R.drawable.cancel)

		.setPositiveButton("Excluir", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int whichButton) { 
				//your deleting code
				AgendaDao aDao = new AgendaDao(getBaseContext());
				aDao.deletar(a);
				Toast.makeText(getApplicationContext(), "Numero excluido com sucesso.", Toast.LENGTH_SHORT).show();
				Intent i = new Intent(CadastrarActivity.this,AgendaaActivity.class);
				startActivity(i);
				dialog.dismiss();
			}   

		})



		.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				dialog.dismiss();

			}
		})
		.create();
		return myQuittingDialogBox;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cadastrar, menu);
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
