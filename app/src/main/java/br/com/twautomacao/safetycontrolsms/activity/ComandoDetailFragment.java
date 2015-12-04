package br.com.twautomacao.safetycontrolsms.activity;

import java.util.ArrayList;

import adapter.ConectaAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.CheckBox;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import br.com.twautomacao.safetycontrolsms.R;
import br.com.twautomacao.safetycontrolsms.dao.ComandosDao;
import br.com.twautomacao.safetycontrolsms.dummy.DummyContent;
import br.com.twautomacao.safetycontrolsms.pojo.Agenda;
import br.com.twautomacao.safetycontrolsms.pojo.Comando;
import br.com.twautomacao.safetycontrolsms.util.Funcoes;

/**
 * A fragment representing a single Comando detail screen. This fragment is
 * either contained in a {@link ComandoListActivity} in two-pane mode (on
 * tablets) or a {@link ComandoDetailActivity} on handsets.
 */
public class ComandoDetailFragment extends Fragment {

	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * The dummy content this fragment is presenting.
	 */
	private BroadcastReceiver sendBroadcastReceiver;
	private BroadcastReceiver deliveryBroadcastReceiver;
	String SENT = "SMS_SENT";
	String DELIVERED = "SMS_DELIVERED";
	private DummyContent.DummyItem mItem;
	private String Numero;
	private Comando c;
	private Agenda a;
	private AlertDialog alerta;

	String cerca="1";
	String dist="M";
	String entrar="1";
	String tempo="S";

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ComandoDetailFragment() {
	}

	public View cercaAtiva(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstaceState){
		final View rootView;
		rootView = inflater.inflate(R.layout.fragment_comando_detail_cerca,
				container, false);
		final EditText distancia;
		RadioGroup rgCerca;
		RadioGroup rgDistancia;
		RadioGroup rgEntrar;
		//RadioButton rbCerca;
		//RadioButton rbDistancia;
		//RadioButton rbEntrar;

		Button btEnviar;
		Button btCancelar;

		rgCerca = (RadioGroup) rootView.findViewById(R.id.rgCerca);
		rgDistancia=(RadioGroup) rootView.findViewById(R.id.rgDistancia);
		rgEntrar=(RadioGroup) rootView.findViewById(R.id.rgEntrar);
		btEnviar = (Button) rootView.findViewById(R.id.btS);
		btCancelar = (Button) rootView.findViewById(R.id.btC);
		distancia=(EditText) rootView.findViewById(R.id.etDistancia);

		rgCerca.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				cerca = (String) rootView.findViewById(checkedId).getTag();
			}
		});

		rgDistancia.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				dist = (String) rootView.findViewById(checkedId).getTag();
			}
		});

		rgEntrar.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				entrar = (String) rootView.findViewById(checkedId).getTag();
			}
		});

		btEnviar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if ((distancia.getText().toString().equals(""))) {
					Toast.makeText(getActivity().getBaseContext(), "Favor preencher a distância", Toast.LENGTH_LONG).show();
					distancia.requestFocus();
				} else {
					if (Integer.parseInt(distancia.getText().toString()) < 100 && dist.equals("M")) {
						Toast.makeText(getActivity().getBaseContext(), "A distância dever deve ser maior que 100m", Toast.LENGTH_LONG).show();
						distancia.requestFocus();
					} else {
						String d = distancia.getText().toString();
						while (d.length() < 3) {
							d = "0" + d;
						}
						String comando = a.getSenha() + "I" + cerca + ",1," + entrar + "," + d + dist;
						sendSMS(Funcoes.removeMascara(a.getNumero()), comando);
					}
				}
			}
		});

		btCancelar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Bundle params = new Bundle();
				params.putSerializable("agenda", a);
				Intent i = new Intent(getActivity().getBaseContext(), ComandoListActivity.class);
				i.putExtras(params);
				startActivity(i);
			}
		});
		return rootView;
	}

	public View senhaAlterar(LayoutInflater inflater,ViewGroup container,
			Bundle savedInstanceState){
		final View rootView;
		rootView = inflater.inflate(R.layout.fragment_comando_detail_senha,
				container, false);
		final EditText senhaAtual;
		final EditText senhaNova;
		final EditText senhaConfirm;
		Button btEnviar;
		Button btCancelar;

		senhaAtual = (EditText) rootView.findViewById(R.id.etSenhaAtual);
		senhaNova = (EditText) rootView.findViewById(R.id.etSenha);
		senhaConfirm=(EditText) rootView.findViewById(R.id.etConfirm);
		btEnviar = (Button) rootView.findViewById(R.id.btS);
		btCancelar = (Button) rootView.findViewById(R.id.btC);

		btEnviar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(senhaAtual.getText().toString().equals(a.getSenha())){
					if(senhaNova.getText().toString().length()==6){
						if(senhaNova.getText().toString().equals(senhaConfirm.getText().toString())){
							sendSMS(Funcoes.removeMascara(a.getNumero()), senhaAtual.getText().toString()+"H"+senhaNova.getText().toString());
						}else{
							Toast.makeText(getActivity().getBaseContext(),"A nova senha e a confirmação nao são iguais.", Toast.LENGTH_LONG).show();
							senhaNova.requestFocus();
						}
					}else{
						Toast.makeText(getActivity().getBaseContext(),"A nova senha deve conter 6 caracteres.", Toast.LENGTH_LONG).show();
						senhaNova.requestFocus();
					}
				}else{
					Toast.makeText(getActivity().getBaseContext(),"A senha anterior errada.", Toast.LENGTH_LONG).show();
					senhaAtual.requestFocus();
				}
			}
		});

		btCancelar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Bundle params = new Bundle();
				params.putSerializable("agenda", a);
				Intent i = new Intent(getActivity().getBaseContext(), ComandoListActivity.class);
				i.putExtras(params);
				startActivity(i);
			}
		});
		return rootView;
	}



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		sendBroadcastReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context arg0, Intent arg1) {
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					Toast.makeText(getActivity(), "SMS Enviado.",
							Toast.LENGTH_SHORT).show();
					if (sendBroadcastReceiver != null){
						getActivity().unregisterReceiver(sendBroadcastReceiver);
						sendBroadcastReceiver = null;
					}

					break;
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
					Toast.makeText(getActivity(), "Erro generico",
							Toast.LENGTH_SHORT).show();
					if (sendBroadcastReceiver != null){
						getActivity().unregisterReceiver(sendBroadcastReceiver);
						sendBroadcastReceiver = null;
					}
					break;
				case SmsManager.RESULT_ERROR_NO_SERVICE:
					Toast.makeText(getActivity(), "Sem servi�o",
							Toast.LENGTH_SHORT).show();
					if (sendBroadcastReceiver != null){
						getActivity().unregisterReceiver(sendBroadcastReceiver);
						sendBroadcastReceiver = null;
					}
					break;
				case SmsManager.RESULT_ERROR_NULL_PDU:
					Toast.makeText(getActivity(), "Null PDU",
							Toast.LENGTH_SHORT).show();
					if (sendBroadcastReceiver != null){
						getActivity().unregisterReceiver(sendBroadcastReceiver);
						sendBroadcastReceiver = null;
					}
					break;
				case SmsManager.RESULT_ERROR_RADIO_OFF:
					Toast.makeText(getActivity(), "Radio off",
							Toast.LENGTH_SHORT).show();
					if (sendBroadcastReceiver != null){
						getActivity().unregisterReceiver(sendBroadcastReceiver);
						sendBroadcastReceiver = null;
					}
					break;

				}
			}
		};
		deliveryBroadcastReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					Toast.makeText(getActivity(), "SMS entregue.",
							Toast.LENGTH_SHORT).show();
					if (deliveryBroadcastReceiver != null){
						getActivity().unregisterReceiver(deliveryBroadcastReceiver);
						deliveryBroadcastReceiver = null;
					}
					break;
				case Activity.RESULT_CANCELED:
					Toast.makeText(getActivity(), "SMS n�o entregue.",
							Toast.LENGTH_SHORT).show();
					if (deliveryBroadcastReceiver != null){
						getActivity().unregisterReceiver(deliveryBroadcastReceiver);
						deliveryBroadcastReceiver = null;
					}
					break;
				}
			}
		};


		a = (Agenda) getArguments().getSerializable("agenda");
		if (getArguments().containsKey(ARG_ITEM_ID)) {
			ComandosDao cDao = new ComandosDao();
			c = cDao.getOne(getArguments().getString(ARG_ITEM_ID));
			a = (Agenda) getArguments().getSerializable("agenda");
		}

	}





	public void send(Object object){
		Comando c = (Comando) object;
		String comando =a.getSenha()+c.getComando();
		Toast.makeText(getActivity().getBaseContext(), comando, Toast.LENGTH_LONG).show();
		sendSMS(Funcoes.removeMascara(a.getNumero()), comando);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View rootView;
		if(c.getNome().equals("Senha Alterar")){
			rootView = senhaAlterar(inflater, container, savedInstanceState);
		}else{
			if(c.getNome().equals("Cerca Ativar")){
				rootView = cercaAtiva(inflater, container, savedInstanceState);
			}else{
				if(c.getNome().equals("Velocidade Ativar")){
					rootView = velocidadeAtivar(inflater,container,savedInstanceState);
				}else{
					if(c.getNome().equals("Vibração Ativar")){
						rootView = vibracaoAtiva(inflater,container,savedInstanceState);
					}else{
						if(c.getNome().equals("Cerca Cancelar")){
							rootView = cercaCancelar(inflater,container,savedInstanceState);
						}else{
							if(c.getNome().equals("Conecta Site Ativar")){
								rootView=conectaSiteAtiva(inflater,container,savedInstanceState);
							}else{
								if(c.getNome().equals("Conecta Site Ativar 2")){
									rootView=conectaSiteAtiva2(inflater, container, savedInstanceState);
								}else{
									rootView=defaut(inflater,container,savedInstanceState);
								}
							}
						}
					}
				}
			}
		}

		// Show the dummy content as text in a TextView.
		if (c != null) {

			//((EditText) rootView.findViewById(R.id.edHelp))
			//.setText(c.getDescricao());
		}
		((ActionBarActivity)getActivity()).getSupportActionBar().setTitle(c.getNome());
		((ActionBarActivity)getActivity()).getSupportActionBar().setIcon(c.getImageId());
		return rootView;

	}

	private View defaut(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView;
		rootView = inflater.inflate(R.layout.fragment_comando_detail_basic,
				container, false);
		Button btEnviar;
		Button btCancelar;

		btEnviar = (Button) rootView.findViewById(R.id.btS);
		btCancelar = (Button) rootView.findViewById(R.id.btC);

		btEnviar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(c.getNome().equals("Reset")){
					sendSMS(Funcoes.removeMascara(a.getNumero()), c.getComando());
				}else{
					sendSMS(Funcoes.removeMascara(a.getNumero()), a.getSenha()+c.getComando());
				}
			}
		});

		btCancelar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Bundle params = new Bundle();
				params.putSerializable("agenda", a);
				Intent i = new Intent(getActivity().getBaseContext(), ComandoListActivity.class);
				i.putExtras(params);
				startActivity(i);
			}
		});
		return rootView;
	}


	private View conectaSiteAtiva(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView;
		rootView = inflater.inflate(R.layout.fragment_comando_detail_conectar,
				container, false);
		ListView lvComandos;
		ArrayList<Comando> comandos;
		ConectaAdapter conectaAdapter;

		lvComandos = (ListView) rootView.findViewById(R.id.lvConecta);

		final ComandosDao cDao = new ComandosDao();

		comandos = new ArrayList<Comando>();
		comandos = cDao.listAllComandosEndereco();
		conectaAdapter = new ConectaAdapter(getActivity(), comandos);
		lvComandos.setAdapter(conectaAdapter);
		OnClickListener o = new OnClickListener() {

			@Override
			public void onClick(View v) {
				send(v.getTag());
			}
		};
		conectaAdapter.setOnXXXClickListener(o);

		Button btCancelar;
		btCancelar = (Button) rootView.findViewById(R.id.btC);

		btCancelar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Bundle params = new Bundle();
				params.putSerializable("agenda", a);
				Intent i = new Intent(getActivity().getBaseContext(), ComandoListActivity.class);
				i.putExtras(params);
				startActivity(i);
			}
		});
		return rootView;
	}

	private View cercaCancelar(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		final View rootView;
		rootView = inflater.inflate(R.layout.fragment_comando_detail_cerca_desativar,
				container, false);
		RadioGroup rgCerca;

		Button btEnviar;
		Button btCancelar;

		rgCerca = (RadioGroup) rootView.findViewById(R.id.rgCerca);

		btEnviar = (Button) rootView.findViewById(R.id.btS);
		btCancelar = (Button) rootView.findViewById(R.id.btC);


		rgCerca.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				cerca = (String) rootView.findViewById(checkedId).getTag();
			}
		});



		btEnviar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				String comando =a.getSenha()+"I"+cerca+",0";
				sendSMS(Funcoes.removeMascara(a.getNumero()), comando);

			}
		});

		btCancelar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Bundle params = new Bundle();
				params.putSerializable("agenda", a);
				Intent i = new Intent(getActivity().getBaseContext(), ComandoListActivity.class);
				i.putExtras(params);
				startActivity(i);
			}
		});
		return rootView;
	}

	private View vibracaoAtiva(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		final View rootView;
		rootView = inflater.inflate(R.layout.fragment_comando_detail_vibracao,
				container, false);
		final EditText edIntervalo;
		RadioGroup rgTempo;

		Button btEnviar;
		Button btCancelar;

		rgTempo = (RadioGroup) rootView.findViewById(R.id.rgTempo);

		btEnviar = (Button) rootView.findViewById(R.id.btS);
		btCancelar = (Button) rootView.findViewById(R.id.btC);
		edIntervalo=(EditText) rootView.findViewById(R.id.etIntervalo);

		rgTempo.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				tempo = (String) rootView.findViewById(checkedId).getTag();
			}
		});


		btEnviar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if((edIntervalo.getText().toString().equals(""))){
					Toast.makeText(getActivity().getBaseContext(), "Favor preencher o intervalo de tempo", Toast.LENGTH_LONG).show();
					edIntervalo.requestFocus();
				}else{
					if(Integer.parseInt(edIntervalo.getText().toString())<=0){
						Toast.makeText(getActivity().getBaseContext(), "O intervalo dever deve ser maior que 0", Toast.LENGTH_LONG).show();
						edIntervalo.requestFocus();
					}else{
						String t = edIntervalo.getText().toString();
						while (t.length()<2) {
							t="0"+t;
						}
						String comando =a.getSenha()+"W1,"+t+tempo;
						sendSMS(Funcoes.removeMascara(a.getNumero()), comando);
					}
				}
			}
		});

		btCancelar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Bundle params = new Bundle();
				params.putSerializable("agenda", a);
				Intent i = new Intent(getActivity().getBaseContext(), ComandoListActivity.class);
				i.putExtras(params);
				startActivity(i);
			}
		});
		return rootView;
	}

	private View conectaSiteAtiva2(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView;
		rootView = inflater.inflate(R.layout.fragment_comando_detail_conectar2,
				container, false);

		final CheckBox chVivo;
		final CheckBox chTim;
		final CheckBox chClaro;
		final CheckBox chOi;
		final CheckBox chTim2;
		final EditText etApn;
		final EditText etuser;
		final EditText etSenha;
		Button btCancelar,btEnviarDefaut,btCancelarDefaut,btEnviar;
		btCancelar = (Button) rootView.findViewById(R.id.btC);
		btCancelarDefaut = (Button) rootView.findViewById(R.id.btCDefaut);
		btEnviar = (Button) rootView.findViewById(R.id.btS);
		btEnviarDefaut = (Button) rootView.findViewById(R.id.btSDefaut);
		chClaro = (CheckBox) rootView.findViewById(R.id.chClaro);
		chOi=(CheckBox) rootView.findViewById(R.id.chOi);
		chTim=(CheckBox) rootView.findViewById(R.id.chTim);
		chVivo=(CheckBox) rootView.findViewById(R.id.chVivo);
		chTim2=(CheckBox) rootView.findViewById(R.id.chTim2);
		etApn=(EditText) rootView.findViewById(R.id.etApn);
		etSenha=(EditText) rootView.findViewById(R.id.etSenha);
		etuser=(EditText) rootView.findViewById(R.id.etUser);


		CheckBox.OnCheckedChangeListener listener = (new CheckBox.OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				if(isChecked){
					chOi.setChecked(false);
					chTim.setChecked(false);
					chTim2.setChecked(false);
					chVivo.setChecked(false);
					chClaro.setChecked(false);
					switch(arg0.getId())
					{
					case R.id.chClaro:
						chClaro.setChecked(true);
						break;
					case R.id.chOi:
						chOi.setChecked(true);
						break;
					case R.id.chTim:
						chTim.setChecked(true);
						break;
					case R.id.chVivo:
						chVivo.setChecked(true);
						break;
					case R.id.chTim2:
						chTim2.setChecked(true);
						break;
					}
				}

			}
		});

		btCancelar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Bundle params = new Bundle();
				params.putSerializable("agenda", a);
				Intent i = new Intent(getActivity().getBaseContext(), ComandoListActivity.class);
				i.putExtras(params);
				startActivity(i);
			}
		});

		btCancelarDefaut.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Bundle params = new Bundle();
				params.putSerializable("agenda", a);
				Intent i = new Intent(getActivity().getBaseContext(), ComandoListActivity.class);
				i.putExtras(params);
				startActivity(i);
			}
		});

		btEnviarDefaut.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(chClaro.isChecked()){
					String comando =a.getSenha()+"S2,"+"claro.com.br,claro,claro";
					sendSMS(Funcoes.removeMascara(a.getNumero()), comando);
				}
				if(chOi.isChecked()){
					String comando =a.getSenha()+"S1,"+"gprs.oi.com.br,oiwap,oioioi";
					sendSMS(Funcoes.removeMascara(a.getNumero()), comando);
				}
				if(chTim.isChecked()){
					String comando =a.getSenha()+"S1,"+"tim.com,tim,tim";
					sendSMS(Funcoes.removeMascara(a.getNumero()), comando);
				}
				if(chTim2.isChecked()){
					String comando =a.getSenha()+"S1,"+"timbrasil.br,tim,tim";
					sendSMS(Funcoes.removeMascara(a.getNumero()), comando);
				}
				if(chVivo.isChecked()){
					String comando =a.getSenha()+"S1,"+"zap.vivo.com.br,vivo,vivo";
					sendSMS(Funcoes.removeMascara(a.getNumero()), comando);
				}
			}
		});

		btEnviar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!(etApn.getText().toString().equals(""))&&
						!(etuser.getText().toString().equals(""))&&
						!(etSenha.getText().toString().equals(""))){
					String comando =a.getSenha()+"S1,"+
							etApn.getText().toString().toLowerCase().trim()+
							","+etuser.getText().toString().toLowerCase().trim()+
							","+etSenha.getText().toString().toLowerCase().trim();
					sendSMS(Funcoes.removeMascara(a.getNumero()), comando);
				}else{
					Toast.makeText(getActivity().getBaseContext(), "Preencha todos os campos.", Toast.LENGTH_LONG).show();
				}
			}
		});
		chClaro.setOnCheckedChangeListener(listener);
		chOi.setOnCheckedChangeListener(listener);
		chTim.setOnCheckedChangeListener(listener);
		chTim2.setOnCheckedChangeListener(listener);
		chVivo.setOnCheckedChangeListener(listener);

		btCancelar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Bundle params = new Bundle();
				params.putSerializable("agenda", a);
				Intent i = new Intent(getActivity().getBaseContext(), ComandoListActivity.class);
				i.putExtras(params);
				startActivity(i);
			}
		});

		return rootView;
	}

	private View velocidadeAtivar(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView;
		rootView = inflater.inflate(R.layout.fragment_comando_detail_vel,
				container, false);
		final EditText edVelocidade;

		Button btEnviar;
		Button btCancelar;

		btEnviar = (Button) rootView.findViewById(R.id.btS);
		btCancelar = (Button) rootView.findViewById(R.id.btC);
		edVelocidade=(EditText) rootView.findViewById(R.id.etVelocidade);

		btEnviar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if((edVelocidade.getText().toString().equals(""))){
					Toast.makeText(getActivity().getBaseContext(), "Favor preencher a velocidade", Toast.LENGTH_LONG).show();
					edVelocidade.requestFocus();
				}else{
					if(Integer.parseInt(edVelocidade.getText().toString())<=0){
						Toast.makeText(getActivity().getBaseContext(), "A velocidade dever deve ser maior que 0", Toast.LENGTH_LONG).show();
						edVelocidade.requestFocus();
					}else{
						String t = edVelocidade.getText().toString();
						while (t.length()<3) {
							t="0"+t;
						}
						String comando =a.getSenha()+"J1,"+t;
						sendSMS(Funcoes.removeMascara(a.getNumero()), comando);
					}
				}
			}
		});

		btCancelar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Bundle params = new Bundle();
				params.putSerializable("agenda", a);
				Intent i = new Intent(getActivity().getBaseContext(), ComandoListActivity.class);
				i.putExtras(params);
				startActivity(i);
			}
		});
		return rootView;
	}


	public void sendSMS(final String phoneNumber, final String message) {

		LayoutInflater li = getActivity().getLayoutInflater();

		View view = li.inflate(R.layout.dialog_demo, null);

		TextView comandos = (TextView) view.findViewById(R.id.tVComandos);

		Toast.makeText(getActivity().getBaseContext(), message, Toast.LENGTH_LONG).show();
		if (phoneNumber.length() > 0 && message.length() > 0) {
			TelephonyManager telMgr = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
			int simState = telMgr.getSimState();
			switch (simState) {
			case TelephonyManager.SIM_STATE_ABSENT:
				Toast.makeText(getActivity(), "Nenhum cartão SIM está disponível no dispositivo.",
						Toast.LENGTH_SHORT).show();
				break;
			case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
				Toast.makeText(getActivity(), "Requer um PIN de rede para desbloquear.",
						Toast.LENGTH_SHORT).show();
				break;
			case TelephonyManager.SIM_STATE_PIN_REQUIRED:
				Toast.makeText(getActivity(), "Requer o PIN do SIM do usuário para desbloquear.",
						Toast.LENGTH_SHORT).show();
				// requer o PIN SIM do usu�rio para desbloquear
				break;
			case TelephonyManager.SIM_STATE_PUK_REQUIRED:
				Toast.makeText(getActivity(), "Requer o PUK do SIM do usuário para desbloquear.",
						Toast.LENGTH_SHORT).show();
				break;
			case TelephonyManager.SIM_STATE_READY:
				// do something
				sendSMSEnvia(phoneNumber, message);
				break;
			case TelephonyManager.SIM_STATE_UNKNOWN:
				Toast.makeText(getActivity(), "Estado do SIM card desconhecido.",
						Toast.LENGTH_SHORT).show();
				break;
			}

		} else {
			Toast.makeText(getActivity(),
					"Please enter both phone number and message.",
					Toast.LENGTH_SHORT).show();
		}
	}



	private void sendSMSEnvia(String phoneNumber, String message) {

		String SENT = "SMS_SENT";
		String DELIVERED = "SMS_DELIVERED";

		PendingIntent sentPI = PendingIntent.getBroadcast(getActivity(), 0,
				new Intent(SENT), 0);

		PendingIntent deliveredPI = PendingIntent.getBroadcast(getActivity(),
				0, new Intent(DELIVERED), 0);

		// ---when the SMS has been sent---

		getActivity().registerReceiver(sendBroadcastReceiver, new IntentFilter(SENT));

		// ---when the SMS has been delivered---
		getActivity().registerReceiver(deliveryBroadcastReceiver, new IntentFilter(DELIVERED));

		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
		Bundle params = new Bundle();
		params.putSerializable("agenda", a);
		Intent i = new Intent(getActivity(), ComandoListActivity.class);
		i.putExtras(params);
		startActivity(i);
	}

	@Override 
	public  void onConfigurationChanged ( Configuration newConfig )  { 
		super . onConfigurationChanged ( newConfig ); 
		Bundle params = new Bundle();
		params.putSerializable("agenda", a);
		params.putString("item_id", c.getNome());
		if  ( newConfig . orientation ==  Configuration . ORIENTATION_LANDSCAPE )  { 
			onSaveInstanceState (params); 
		}  else  if  ( newConfig . orientation ==  Configuration . ORIENTATION_PORTRAIT )  { 
			onSaveInstanceState ( params ); 
		} 
	}

	@Override 
	public  void onSaveInstanceState ( Bundle outState )  { 
		super . onSaveInstanceState ( outState );
		outState.putString(ARG_ITEM_ID, c.getNome());
		outState.putSerializable("agenda", a);
	}
}
