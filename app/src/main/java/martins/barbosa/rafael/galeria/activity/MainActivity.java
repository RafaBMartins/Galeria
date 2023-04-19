package martins.barbosa.rafael.galeria.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import martins.barbosa.rafael.galeria.R;
import martins.barbosa.rafael.galeria.adapter.MyAdapter;
import martins.barbosa.rafael.galeria.model.MyItem;
import martins.barbosa.rafael.galeria.util.Util;

public class MainActivity extends AppCompatActivity {

    static int NEW_ITEM_REQUEST =1;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //evento de clique no botão flutuante
        FloatingActionButton fabAddItem = findViewById(R.id.fabAddNewItem);
        fabAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intenção de ir a NewItemActivity esperando um resultado(dados do item adicionado), codigo NEW_ITEM_REQUEST
                Intent i = new Intent(MainActivity.this, NewItemActivity.class);
                startActivityForResult(i, NEW_ITEM_REQUEST);
            }
        });

        //obtendo o RecyclerView do nosso layout
        RecyclerView rvItens = findViewById(R.id.rvItens);

        //criando o adptador que sera responsavel por criar e preencher nosso RecyclerView, passamos para o adptador MainActivity(sera usado para criar o inflador) e
        //itens, itens é a lista que preenchemos com os dados retornados de NewItemActivity
        myAdapter = new MyAdapter(MainActivity.this, itens);
        //setando como adptador de nosso RecyclerView o adptador que acabamos de criar
        rvItens.setAdapter(myAdapter);

        //falando para o RecyclerView que todos os itens tem o mesmo tamanho
        rvItens.setHasFixedSize(true);

        //criando um gerenciador de layouts do tipo linear e setando como gerenciador do nosso RecyclerView, para ele dispor os elementos de forma horizontal
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        rvItens.setLayoutManager(layoutManager);

        //criando uma linha decorativa e setando no nosso RecyclerView, para separar cada item
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvItens.getContext(), DividerItemDecoration.VERTICAL);
        rvItens.addItemDecoration(dividerItemDecoration);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //verifico se esse resultado veio de NewItemActivity, e se o resultado foi positivo
        if(requestCode == NEW_ITEM_REQUEST) {
            if(resultCode == Activity.RESULT_OK) {
                //criando um objeto da classe MyItem e armazeno nele os dados retornados de NewItemActivity
                MyItem myItem = new MyItem();
                myItem.title = data.getStringExtra("title");
                myItem.description = data.getStringExtra("description");
                Uri selectedPhotoURI = data.getData();

                try {
                    Bitmap photo = Util.getBitmap(MainActivity.this,selectedPhotoURI, 100, 100);
                    myItem.photo = photo;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                //armazeno esse item em uma lista de itens, que é um atributo de MainActivity
                itens.add(myItem);

                //notificando o meu adptador que um novo item foi adicionado na lista de itens, assim ele podendo o construir e ser mostrado no RecyclerView
                myAdapter.notifyItemInserted(itens.size()-1);
            }
        }
    }
}