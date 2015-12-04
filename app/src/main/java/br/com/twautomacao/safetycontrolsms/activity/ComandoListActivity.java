package br.com.twautomacao.safetycontrolsms.activity;

import br.com.twautomacao.safetycontrolsms.R;
import br.com.twautomacao.safetycontrolsms.dao.ComandosDao;
import br.com.twautomacao.safetycontrolsms.pojo.Agenda;
import br.com.twautomacao.safetycontrolsms.pojo.Comando;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.app.Dialog;

/**
 * An activity representing a list of Comandos. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link ComandoDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ComandoListFragment} and the item details (if present) is a
 * {@link ComandoDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link ComandoListFragment.Callbacks} interface to listen for item
 * selections.
 */
public class ComandoListActivity extends ActionBarActivity implements
ComandoListFragment.Callbacks {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;
	private Agenda a;
	private Comando c;

	public Agenda getAgenda(){
		return a;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comando_list);

		Intent intent=this.getIntent();
		Bundle bundle=intent.getExtras();
		if(bundle!=null){
			if(bundle.containsKey("agenda")){
				a = (Agenda) bundle.getSerializable("agenda");

			}
		}


		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		if (findViewById(R.id.comando_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((ComandoListFragment) getSupportFragmentManager().findFragmentById(
					R.id.comando_list)).setActivateOnItemClick(true);
		}

		// TODO: If exposing deep links into your app, handle intents here.
	}

	/**
	 * Callback method from {@link ComandoListFragment.Callbacks} indicating
	 * that the item with the given ID was selected.
	 */
	@Override
	protected void onResume() {
		super.onResume();
		Intent intent=this.getIntent();
		Bundle bundle=intent.getExtras();
		if(bundle!=null){
			if(bundle.containsKey("agenda")){
				a = (Agenda) bundle.getSerializable("agenda");

			}
		}
	}

	// Normal case behavior follows

	@Override
	public void onItemSelected(String id) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			ComandosDao cDao = new ComandosDao();
			c = cDao.getOne(id);
			Bundle arguments = new Bundle();
			arguments.putString(ComandoDetailFragment.ARG_ITEM_ID, id);
			arguments.putSerializable("agenda", a);
			ComandoDetailFragment fragment = new ComandoDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
			.replace(R.id.comando_detail_container, fragment).commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, ComandoDetailActivity.class);
			detailIntent.putExtra(ComandoDetailFragment.ARG_ITEM_ID, id);
			detailIntent.putExtra("agenda", a);
			startActivity(detailIntent);
		}
	}

	public boolean onOptionsItemSelected(MenuItem item){
		int id = item.getItemId();
		if (id == android.R.id.home) {
			Intent i = new Intent(getBaseContext(), AgendaaActivity.class);
			startActivity(i); 
			NavUtils.navigateUpTo(this, i);
			return true;
		}
		if (id == R.id.action_help) {
			if(getSupportActionBar().getTitle().toString().equals("Localizar")){
				// custom dialog
				final Dialog dialog = new Dialog(this);
				dialog.setContentView(R.layout.dialog_localizar);
				dialog.setTitle("Localizar:");

				// set the custom dialog components - text, image and button

				Button dialogButton = (Button) dialog.findViewById(R.id.btVoltar);
				// if button is clicked, close the custom dialog
				dialogButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});

				dialog.show();
			}else{
				if(c!=null){
					final Dialog dialog = new Dialog(this);
					dialog.setContentView(R.layout.dialog_defaut);
					dialog.setTitle(c.getNome());

					// set the custom dialog components - text, image and button

					Button dialogButton = (Button) dialog.findViewById(R.id.btVoltar);
					// if button is clicked, close the custom dialog
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
			}
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed(){
		
		Intent i = new Intent(getBaseContext(), AgendaaActivity.class);
		startActivity(i);
		return;
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main1, menu);
		MenuItem item = menu.findItem(R.id.action_nome);
	    item.setTitle(a.getNome()+"\n"+a.getNumero());
		return true;
	}
}
