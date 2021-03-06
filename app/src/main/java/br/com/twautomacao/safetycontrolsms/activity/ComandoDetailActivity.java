package br.com.twautomacao.safetycontrolsms.activity;

import br.com.twautomacao.safetycontrolsms.R;
import br.com.twautomacao.safetycontrolsms.dao.ComandosDao;
import br.com.twautomacao.safetycontrolsms.pojo.Agenda;
import br.com.twautomacao.safetycontrolsms.pojo.Comando;
import android.content.Intent;
import android.os.Bundle;
import android.app.Dialog;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * An activity representing a single Comando detail screen. This activity is
 * only used on handset devices. On tablet-size devices, item details are
 * presented side-by-side with a list of items in a {@link ComandoListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing more than
 * a {@link ComandoDetailFragment}.
 */
public class ComandoDetailActivity extends ActionBarActivity {

	Agenda a;
	Comando c;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comando_detail);


		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	    
		if (savedInstanceState == null) {
			// Create the detail fragment and add it to the activity
			// using a fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(ComandoDetailFragment.ARG_ITEM_ID, getIntent()
					.getStringExtra(ComandoDetailFragment.ARG_ITEM_ID));
			a=(Agenda) getIntent().getExtras().getSerializable("agenda");
			ComandosDao cDao = new ComandosDao();
			c = cDao.getOne(getIntent()
					.getStringExtra(ComandoDetailFragment.ARG_ITEM_ID));
			
			arguments.putSerializable("agenda", getIntent().getExtras().getSerializable("agenda"));
			ComandoDetailFragment fragment = new ComandoDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
			.add(R.id.comando_detail_container, fragment).commit();

		}else{
			a=(Agenda) getIntent().getExtras().getSerializable("agenda");
			ComandosDao cDao = new ComandosDao();
			c = cDao.getOne(getIntent()
					.getStringExtra(ComandoDetailFragment.ARG_ITEM_ID));
		}

	}

	@Override
	public void onBackPressed(){
		Bundle params = new Bundle();
		params.putSerializable("agenda", a);
		Intent i = new Intent(getBaseContext(), ComandoListActivity.class);
		i.putExtras(params);
		startActivity(i);
		return;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			Bundle params = new Bundle();
			params.putSerializable("agenda", a);
			Intent i = new Intent(getBaseContext(), ComandoListActivity.class);
			i.putExtras(params);
			//startActivity(i); 
			NavUtils.navigateUpTo(this, i);
			return true;
		}
		if (id == R.id.action_help) {
			if(getSupportActionBar().getTitle().toString().equals("Localizar")){

				final Dialog dialog = new Dialog(this);
				dialog.setContentView(R.layout.dialog_localizar);
				dialog.setTitle("Localizar:");
	 

	 
				Button dialogButton = (Button) dialog.findViewById(R.id.btVoltar);

				dialogButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
	 
				dialog.show();
			}else{
				final Dialog dialog = new Dialog(this);
				dialog.setContentView(R.layout.dialog_defaut);
				dialog.setTitle(c.getNome());
	 

	 
				Button dialogButton = (Button) dialog.findViewById(R.id.btVoltar);
				TextView text = (TextView) dialog.findViewById(R.id.tvDcp);
				text.setText(c.getDescricao());
				dialogButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
	 
				dialog.show();
			}
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main1, menu);
		MenuItem item = menu.findItem(R.id.action_nome);
		item.setTitle(a.getNome()+"\n"+a.getNumero());
		return true;
		
	}

	@Override 
	public  void onSaveInstanceState ( Bundle outState )  { 
		super . onSaveInstanceState ( outState );
		outState.putString("item_id", c.getNome());
		outState.putSerializable("agenda", a);
	}
	

}
