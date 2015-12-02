package adapter;

import java.util.ArrayList;

import br.com.twautomacao.safetycontrolsms.R;
import br.com.twautomacao.safetycontrolsms.pojo.Agenda;
import br.com.twautomacao.safetycontrolsms.pojo.Comando;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ComandosAdapter extends ArrayAdapter<Comando> {
    public ComandosAdapter(Context context, ArrayList<Comando> comandos) {
       super(context, R.layout.comando_list, comandos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // Get the data item for this position
       Comando c = getItem(position);    
       // Check if an existing view is being reused, otherwise inflate the view
       if (convertView == null) {
          convertView = LayoutInflater.from(getContext()).inflate(R.layout.comando_list, parent, false);
       }
       // Lookup view for data population
       TextView tvName = (TextView) convertView.findViewById(R.id.title);
       tvName.setText(c.getNome());
       ImageView img = (ImageView)  convertView.findViewById(R.id.icon);
       img.setImageResource(c.getImageId());
       
       // Populate the data into the template view using the data object
       
       // Return the completed view to render on screen
       return convertView;
   }
}