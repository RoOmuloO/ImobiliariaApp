package com.example.romuloroger.imobiliariaapp;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.romuloroger.imobiliariaapp.Database.DAOs.ImovelDAO;
import com.example.romuloroger.imobiliariaapp.Models.Imovel;
import com.example.romuloroger.imobiliariaapp.Models.Usuario;
import com.example.romuloroger.imobiliariaapp.classes.Moeda;

import java.util.ArrayList;
import java.util.List;

public class ListaImovelActivity extends AppCompatActivity {

    EditText edtPesquisar;
    Button btnPesquisar;
    ListView lstViewPesquisar;
    List<Imovel> imoveis;
    static final int codeDetalhesPage = 5;
    Usuario usuario;
    ArrayAdapter<String> listaAdapter;

    Moeda m = new Moeda();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_imovel);

        binding();

        listaAdapter = preencheListViewListaImovel();

        lstViewPesquisar.setOnItemClickListener(editItemSelectedList());

        edtPesquisar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listaAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private AdapterView.OnItemClickListener editItemSelectedList() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Imovel imovelSelecionado = imoveis.get(position);

                usuario  = (Usuario) getIntent().getExtras().getSerializable("usuario");

                    Intent itn = new Intent(getApplicationContext(),DetalhesImovelActivity.class);
                    itn.putExtra("usuario", usuario);
                    itn.putExtra("imovel",imovelSelecionado);
                    startActivityForResult(itn,codeDetalhesPage);

            }
        };
    }

    private void binding() {
        edtPesquisar = findViewById(R.id.edtListaImovelPesquisar);
        btnPesquisar = findViewById(R.id.btnListaImovelPesquisar);
        lstViewPesquisar = findViewById(R.id.lstViewListaImovel);
    }

    private ArrayAdapter<String> preencheListViewListaImovel(){

         usuario = (Usuario) getIntent().getExtras().getSerializable("usuario");
        if(usuario.getTipoUsuario().equals("Administrador")){
            imoveis = new ImovelDAO(getApplicationContext()).findAll();
        }else {
            imoveis = new ImovelDAO(getApplicationContext()).findAllnotSold();
        }


        ArrayList<String> listaString = new ArrayList<String>();
        for(Imovel i: imoveis)
        {
            listaString.add("Nome: "+i.getNome()+ "\nPreço: "+m.mascaraDinheiro(i.getPreco(),m.DINHEIRO_REAL)+ "\n Qtde Quartos: "+i.getQtdeQuartos());
        }

        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,listaString);

        lstViewPesquisar.setAdapter(listAdapter);

        return listAdapter;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        preencheListViewListaImovel();
    }
}
