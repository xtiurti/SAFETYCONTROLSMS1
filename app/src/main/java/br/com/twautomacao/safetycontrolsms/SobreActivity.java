package br.com.twautomacao.safetycontrolsms;

import br.com.twautomacao.safetycontrolsms.activity.AgendaaActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class SobreActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sobre);
		getSupportActionBar().setTitle("Sobre");
		
		Button btVoltar;
		
		btVoltar = (Button) findViewById(R.id.btVoltar);
		
		btVoltar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(SobreActivity.this, AgendaaActivity.class);
				startActivity(i);
			}
		});
	}
}
