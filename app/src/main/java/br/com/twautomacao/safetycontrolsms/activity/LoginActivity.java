package br.com.twautomacao.safetycontrolsms.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import br.com.twautomacao.safetycontrolsms.controller.UsuarioController;
import br.com.twautomacao.safetycontrolsms.pojo.User;
import br.com.twautomacao.safetycontrolsms.R;


public class LoginActivity extends Activity {

    private Button btEntrar;
    private EditText edLogin;
    private EditText edSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
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
                User usuario = new User(login, senha);
                usuarioController.insert(usuario,getBaseContext());
                //if
            }
        });
        
       
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
}
