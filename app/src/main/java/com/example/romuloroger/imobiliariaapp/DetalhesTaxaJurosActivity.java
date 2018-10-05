package com.example.romuloroger.imobiliariaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.romuloroger.imobiliariaapp.Database.DAOs.TaxaJurosDAO;
import com.example.romuloroger.imobiliariaapp.Models.TaxaJuros;

public class DetalhesTaxaJurosActivity extends AppCompatActivity {

    EditText edtValorInicial, edtValorFinal, edtTaxaJuros;
    Button btnAtualizar, btnVoltar;
    TaxaJuros taxaJuros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_taxa_juros);

        binding();

        taxaJuros = (TaxaJuros) getIntent().getExtras().getSerializable("taxaJuros");
                    if(taxaJuros != null){
                        edtValorInicial.setText(((Double)taxaJuros.getValorInicial()).toString());
                        edtValorFinal.setText(((Double)taxaJuros.getValorFinal()).toString());
                        edtTaxaJuros.setText(((Double)taxaJuros.getTaxaJuros()).toString());
                    }

        btnVoltar.setOnClickListener(backListHomes());
        btnAtualizar.setOnClickListener(update());
    }

    private View.OnClickListener update() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taxaJuros.setValorInicial(Double.parseDouble(edtValorInicial.getText().toString()));
                taxaJuros.setValorFinal(Double.parseDouble(edtValorFinal.getText().toString()));
                taxaJuros.setTaxaJuros(Double.parseDouble(edtTaxaJuros.getText().toString()));

                new TaxaJurosDAO(getApplicationContext()).edit(taxaJuros);

                Toast.makeText(getApplicationContext(),"Atualizado com Sucesso!!",Toast.LENGTH_LONG).show();
                setResult(1);
                finish();
            }
        };
    }

    private View.OnClickListener backListHomes() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(0);
                finish();
            }
        };
    }

    private void binding() {
        edtValorInicial = findViewById(R.id.edtDetalhesTaxaJurosValorInicial);
        edtValorFinal = findViewById(R.id.edtDetalhesTaxaJurosValorFinal);
        edtTaxaJuros = findViewById(R.id.edtDetalhesTaxaJurosTaxaJuros);
        btnAtualizar = findViewById(R.id.btnDetalhesTaxaJurosAtualizar);
        btnVoltar = findViewById(R.id.btnDetalhesTaxaJurosVoltar);
    }
}
