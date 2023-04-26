package martins.barbosa.rafael.galeria.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import martins.barbosa.rafael.galeria.R;
import martins.barbosa.rafael.galeria.model.NewItemActivityViewModel;

public class NewItemActivity extends AppCompatActivity {

    static int PHOTO_PICKER_REQUEST=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);


        NewItemActivityViewModel vm = new ViewModelProvider( this ).get(NewItemActivityViewModel.class);
        Uri photoSelected = vm.getSelectedPhotoLocation();

        if(photoSelected != null) {
            ImageView imvfotoPreview = findViewById(R.id.imvPhotoPreview);
            imvfotoPreview.setImageURI(photoSelected);
        }

        //evento de clique no imagebutton
        ImageButton imgCI = findViewById(R.id.imbCI);
        imgCI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intenção implicita para resolver a ação de abrir um documento
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                //colocando o tipo do documento como somente imagens
                photoPickerIntent.setType("image/*");
                //iniciando a intenção esperando um resultado(URI da foto selecionada)
                startActivityForResult(photoPickerIntent, PHOTO_PICKER_REQUEST);
            }
        });

        Button btnAddItem = findViewById(R.id.btnAddItem);
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri photoSelected = vm.getSelectedPhotoLocation();

                //da linha 47 até a linha 65 apenas verifico se todos os dados foram preenchidos antes de enviar para MainActivity, se não foram eu aviso o usuario
                if(photoSelected == null) {
                    Toast.makeText(NewItemActivity.this, "É necessário selecionar uma imagem!", Toast.LENGTH_LONG).show();
                    return;
                }

                EditText etTitle = findViewById(R.id.etTitle);
                String title = etTitle.getText().toString();
                if(title.isEmpty()) {
                    Toast.makeText(NewItemActivity.this, "É necessário inserir um título!", Toast.LENGTH_LONG).show();
                    return;
                }

                EditText etDesc = findViewById(R.id.etDesc);
                String desc = etDesc.getText().toString();
                if(desc.isEmpty()) {
                    Toast.makeText(NewItemActivity.this, "É necessário inserir uma descrição!", Toast.LENGTH_LONG).show();
                    return;
                }

                //criando uma intenção e armazenando a foto o titulo e a descrição do item, retornando para MainActivity, com RESULT_OK a intenção que acabamos de criar e finalizando a Activity
                Intent i = new Intent();
                i.setData(photoSelected);
                i.putExtra("title", title);
                i.putExtra("description", desc);
                setResult(Activity.RESULT_OK, i);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //verificamos se o codigo do resultado é o codigo da intenção que pega a URI da foto
        if(requestCode == PHOTO_PICKER_REQUEST) {
            //vendo se a operação de pegar a foto foi de fato realizada, e não cancelada
            if(resultCode == Activity.RESULT_OK) {
                //pegando a URI da foto e guardando dentro de um atributo da classe NewItemActivity
                Uri photoSelected = data.getData();
                //mostrando a foto no imv do layout de NewItemActivity
                ImageView imvPhotoPreview = findViewById(R.id.imvPhotoPreview);
                imvPhotoPreview.setImageURI(photoSelected);

                NewItemActivityViewModel vm = new ViewModelProvider( this ).get(NewItemActivityViewModel.class);
                vm.setSelectedPhotoLocation(photoSelected);
            }
        }
    }
}