package br.com.twautomacao.safetycontrolsms.activity;

import java.util.ArrayList;

import br.com.twautomacao.safetycontrolsms.R;
import br.com.twautomacao.safetycontrolsms.SobreActivity;
import br.com.twautomacao.safetycontrolsms.dao.AgendaDao;
import br.com.twautomacao.safetycontrolsms.pojo.Agenda;
import adapter.AgendaAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class AgendaaActivity extends Activity {
	
	private Button btCadastrar, btSobre;
	ListView lvAgenda;
    ArrayList<Agenda> agenda;
    AgendaAdapter agendaAdapter;
    EditText edBusca;
    
    @Override
    public void onBackPressed() {
    	Intent intent = new Intent(Intent.ACTION_MAIN);
    	intent.addCategory(Intent.CATEGORY_HOME);
    	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	startActivity(intent);
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agenda);
		
		btCadastrar = (Button) findViewById(R.id.btCadastrar);
		btSobre = (Button) findViewById(R.id.btSobre);
		lvAgenda = (ListView) findViewById(R.id.lvAgenda);
		edBusca = (EditText) findViewById(R.id.eTBusca);
		
		final AgendaDao aDao = new AgendaDao(getApplicationContext());
		
		agenda = new ArrayList<Agenda>();
		agenda = aDao.listAll();
		agendaAdapter = new AgendaAdapter(AgendaaActivity.this, agenda);
		lvAgenda.setAdapter(agendaAdapter);
		lvAgenda.setOnTouchListener(new ListView.OnTouchListener() {
	        @Override
	        public boolean onTouch(View v, MotionEvent event) {
	            int action = event.getAction();
	            switch (action) {
	            case MotionEvent.ACTION_DOWN:
	                // Disallow ScrollView to intercept touch events.
	                v.getParent().requestDisallowInterceptTouchEvent(true);
	                break;

	            case MotionEvent.ACTION_UP:
	                // Allow ScrollView to intercept touch events.
	                v.getParent().requestDisallowInterceptTouchEvent(false);
	                break;
	            }

	            // Handle ListView touch events.
	            v.onTouchEvent(event);
	            return true;
	        }
	    });
		
		btSobre.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(AgendaaActivity.this, SobreActivity.class);
				startActivity(i);
			}
		});
		
		lvAgenda.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Agenda a = agenda.get(arg2);
				Bundle params = new Bundle();
				params.putSerializable("agenda", a);
				Intent i = new Intent(AgendaaActivity.this, ComandoListActivity.class);
				i.putExtras(params);
				startActivity(i);
			}
		});
		//setListViewHeightBasedOnChildren(lvAgenda);
		lvAgenda.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Agenda a = agenda.get(arg2);
				Bundle params = new Bundle();
				params.putSerializable("agenda", a);
				Intent i = new Intent(AgendaaActivity.this, CadastrarActivity.class);
				i.putExtras(params);
				startActivity(i);
				return false;
			}
		});
		
		edBusca.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
            	agenda.clear();
                agenda.addAll(aDao.listAllLike(s.toString()));
                agendaAdapter.notifyDataSetChanged();
            }
        });
		
		btCadastrar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new  Intent(AgendaaActivity.this, CadastrarActivity.class);
				startActivity(i);
			}
		});
	}

}
