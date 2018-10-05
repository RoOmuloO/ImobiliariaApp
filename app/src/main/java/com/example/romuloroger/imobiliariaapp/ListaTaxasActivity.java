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

import com.example.romuloroger.imobiliariaapp.Database.DAOs.TaxaJurosDAO;
import com.example.romuloroger.imobiliariaapp.Models.Imovel;
import com.example.romuloroger.imobiliariaapp.Models.TaxaJuros;
import com.example.romuloroger.imobiliariaapp.classes.Moeda;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ListaTaxasActivity extends AppCompatActivity {

    EditText edtPesquisar;
    Button btnPesquisar;
    ListView lstViewPesquisar;
    List<TaxaJuros> taxasJuros;
    static final int codeDetalhesTaxaPage = 6;
    ArrayAdapter<String> listaAdapter;
    Moeda m = new Moeda();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_taxas);

        binding();

        listaAdapter = preencheListViewListaTaxas();

        lstViewPesquisar.setOnItemClickListener(editItemSelectedItem());

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

    private AdapterView.OnItemClickListener editItemSelectedItem() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TaxaJuros taxaJurosSelecionada = taxasJuros.get(position);
                Intent itn = new Intent(getApplicationContext(), DetalhesTaxaJurosActivity.class);
                itn.putExtra("taxaJuros",taxaJurosSelecionada);
                startActivityForResult(itn,codeDetalhesTaxaPage);

            }
        };
    }

    private ArrayAdapter<String> preencheListViewListaTaxas() {
         taxasJuros = new TaxaJurosDAO(getApplicationContext()).findAll();

        ArrayList<String> listaString = new ArrayList<String>();
        for(TaxaJuros taxaJuros: taxasJuros)
        {
            listaString.add("Valor Inicial: " +m.mascaraDinheiro(taxaJuros.getValorInicial(),m.DINHEIRO_REAL)+"\nValor Final: "+m.mascaraDinheiro(taxaJuros.getValorFinal(),m.DINHEIRO_REAL)+"\nTaxa de Juros: "+taxaJuros.getTaxaJuros()+"%");
        }

        ArrayAdapter<String> listaAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,listaString);

        lstViewPesquisar.setAdapter(listaAdapter);

        return listaAdapter;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        preencheListViewListaTaxas();
    }

    private void binding() {
        edtPesquisar = findViewById(R.id.edtListaTaxasPesquisar);
        btnPesquisar = findViewById(R.id.btnListaTaxasPesquisar);
        lstViewPesquisar = findViewById(R.id.lstViewListaTaxas);
    }
}
