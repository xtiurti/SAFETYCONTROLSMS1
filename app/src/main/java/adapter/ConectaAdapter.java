package adapter;

import java.util.ArrayList;

import br.com.twautomacao.safetycontrolsms.ComandoDetailFragment;
import br.com.twautomacao.safetycontrolsms.R;
import br.com.twautomacao.safetycontrolsms.pojo.Agenda;
import br.com.twautomacao.safetycontrolsms.pojo.Comando;
import android.app.FragmentManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ConectaAdapter extends ArrayAdapter<Comando> {
	
	OnClickListener onXXXClickListener;
	public ConectaAdapter(Context context, ArrayList<Comando> comandos) {
		super(context, R.layout.item_list_conecta, comandos);
	}
	
	public void setOnXXXClickListener(final OnClickListener onClickListener) {
	    this.onXXXClickListener = onClickListener;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Get the data item for this position
		Comando c = getItem(position);    
		// Check if an existing view is being reused, otherwise inflate the view
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_conecta, parent, false);
		}
		// Lookup view for data population
		TextView tvName = (TextView) convertView.findViewById(R.id.tvNome);
		tvName.setText(c.getNome());
		TextView tvConecta = (TextView) convertView.findViewById(R.id.tvConecta);
		tvConecta.setText(c.getDescricao());
		Button btEnvia = (Button) convertView.findViewById(R.id.btEnvia);
		btEnvia.setTag(c);
		btEnvia.setOnClickListener(this.onXXXClickListener);
			
		// Populate the data into the template view using the data object

		// Return the completed view to render on screen
		return convertView;
	}

}