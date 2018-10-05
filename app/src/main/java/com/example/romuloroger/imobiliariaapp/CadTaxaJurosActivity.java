package com.example.romuloroger.imobiliariaapp;

import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.romuloroger.imobiliariaapp.Database.DAOs.ImovelDAO;
import com.example.romuloroger.imobiliariaapp.Database.DAOs.TaxaJurosDAO;
import com.example.romuloroger.imobiliariaapp.Models.TaxaJuros;

public class CadTaxaJurosActivity extends AppCompatActivity {

    FloatingActionButton salvar;
    EditText valorInicial, valorFinal, taxaJuros;
    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_taxa_juros);

        binding();
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        salvar.setOnClickListener(saveTaxaJuros());

    }

    private View.OnClickListener saveTaxaJuros() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean camposValidos = validaCampos();
                if(camposValidos){
                    TaxaJuros tJuros = new TaxaJuros(0,
                            Double.parseDouble(valorInicial.getText().toString()),
                            Double.parseDouble(valorFinal.getText().toString()),
                            Double.parseDouble(taxaJuros.getText().toString())
                    );

                    try{
                        new TaxaJurosDAO(getApplicationContext()).insert(tJuros);
                        Toast.makeText(getApplicationContext(),"Salvo com Sucesso!!", Toast.LENGTH_LONG).show();
                        setResult(1);
                        finish();
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Erro ao salvar . " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }else {
                    vibrator.vibrate(200);
                    Toast.makeText(getApplicationContext(),"Informe todos os valores acima.",Toast.LENGTH_LONG).show();
                }

            }
        };
    }

    private void binding() {
        salvar = findViewById(R.id.floatBtnCadTaxaJurosSalvar);
        valorInicial = findViewById(R.id.edtCadTaxaJurosValorInicial);
        valorFinal = findViewById(R.id.edtCadTaxaJurosValorFinal);
        taxaJuros = findViewById(R.id.edtCadTaxaJurosTaxa);
    }

    private boolean validaCampos() {

        if((valorInicial.getText().toString().length() > 0) && (valorFinal.getText().toString().length() > 0) && (taxaJuros.getText().toString().length() > 0)){
            return true;
        }else{
            if(valorInicial.getText().toString().length() < 1){
                valorInicial.setError("Informe o Valor Inicial");
            }
            if(valorFinal.getText().toString().length() < 1){
                valorFinal.setError("Informe o Valor Final");
            }
            if(taxaJuros.getText().toString().length() < 1){
                taxaJuros.setError("Informe a Taxa de Juros");
            }
            return false;
        }
    }
}
