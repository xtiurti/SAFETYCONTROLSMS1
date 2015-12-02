package adapter;

import java.util.ArrayList;

import br.com.twautomacao.safetycontrolsms.R;
import br.com.twautomacao.safetycontrolsms.pojo.Agenda;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AgendaAdapter extends ArrayAdapter<Agenda> {
    public AgendaAdapter(Context context, ArrayList<Agenda> agenda) {
       super(context, R.layout.item_list, agenda);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // Get the data item for this position
       Agenda a = getItem(position);    
       // Check if an existing view is being reused, otherwise inflate the view
       if (convertView == null) {
          convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
       }
       // Lookup view for data population
       TextView tvName = (TextView) convertView.findViewById(R.id.tvNome);
       TextView tvHome = (TextView) convertView.findViewById(R.id.tvNumero);
       // Populate the data into the template view using the data object
       tvName.setText(a.getNome());
       tvHome.setText(a.getNumero());
       tvName.setTextColor(android.graphics.Color.BLACK);
       tvHome.setTextColor(android.graphics.Color.GRAY);
       // Return the completed view to render on screen
       return convertView;
   }
}