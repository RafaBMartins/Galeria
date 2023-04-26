package martins.barbosa.rafael.galeria.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import martins.barbosa.rafael.galeria.R;
import martins.barbosa.rafael.galeria.activity.MainActivity;
import martins.barbosa.rafael.galeria.model.MyItem;

public class MyAdapter extends RecyclerView.Adapter {

    MainActivity mainActivity;
    List<MyItem> itens;

    public MyAdapter(MainActivity mainActivity, List<MyItem> itens) {
        this.mainActivity = mainActivity;
        this.itens = itens;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //criando o inflador que sera responsavel por criar os elementos de interface de um item da lista, como MyAdapter não é uma Activity, eu crio o inflador a partir de MainActivity
        LayoutInflater inflater = LayoutInflater.from(mainActivity);
        //falando para o inflador qual o layout que ele vai construir para o item, no meu caso é o layout item_list, guardando os elementos que o inflador criou do layout em uma view
        View v = inflater.inflate(R.layout.item_list, parent, false);
        //retornando o view que esta guardado no objeto MyViewHolder
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //obtendo o item da lista de itens que sera usado para preencher o layout de item
        MyItem myItem = itens.get(position);

        //obtendo o objeto view que estava guardado no ViewHolder, e ele contém o layout do item já criado pelo onCreateViewHolder
        View v = holder.itemView;

        //da linha 47 até a linha 56 preenchemos o loyout da nossa view com os dados do item da lista de itens
        ImageView imvPhoto = v.findViewById(R.id.imvPhoto);
        imvPhoto.setImageBitmap(myItem.photo);

        TextView tvTitle = v.findViewById(R.id.tvTitle);
        tvTitle.setText(myItem.title);

        TextView tvDesc = v.findViewById(R.id.tvDesc);
        tvDesc.setText(myItem.description);
    }

    //retorna o tamanho atual da lista de itens
    @Override
    public int getItemCount() {
        return itens.size();
    }
}
