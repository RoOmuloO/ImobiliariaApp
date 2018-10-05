package com.example.romuloroger.imobiliariaapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.romuloroger.imobiliariaapp.Database.DAOs.ClienteDAO;
import com.example.romuloroger.imobiliariaapp.Database.DAOs.FinanciamentoDAO;
import com.example.romuloroger.imobiliariaapp.Database.DAOs.ImovelDAO;
import com.example.romuloroger.imobiliariaapp.Database.DAOs.TaxaJurosDAO;
import com.example.romuloroger.imobiliariaapp.Models.Cliente;
import com.example.romuloroger.imobiliariaapp.Models.Financiamento;
import com.example.romuloroger.imobiliariaapp.Models.Imovel;
import com.example.romuloroger.imobiliariaapp.Models.TaxaJuros;
import com.example.romuloroger.imobiliariaapp.classes.Moeda;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CadClienteForSaleActivity extends AppCompatActivity {

    Imovel imovel;
    List<TaxaJuros> taxasJuros;
    List<TaxaJuros> taxasJurosCarregados = new ArrayList<>();
    TaxaJuros taxaJurosSelectedSpinner;
    double valorTaxaJurosSelecionada;
    Moeda m = new Moeda();

    Vibrator vibrator;


    Spinner taxaJuros,qtdeParcelas;
    TextView valorTotalFinanciado,valorFinanciado,valorImovel;
    EditText valorEntrada,email,telefone, nome;
    Button btnCalculaValorFinanciado, btnSimularFinanciamento, btnConfirmarVenda, btnVoltar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_cliente_for_sale);

        binding();
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        imovel = (Imovel) getIntent().getExtras().getSerializable("imovel");
        valorImovel.setText(m.mascaraDinheiro(imovel.getPreco(),m.DINHEIRO_REAL));

        carregarSpinnerTaxaJuros();
        popularSpinnerQtdeParcelas();

        btnCalculaValorFinanciado.setOnClickListener(calcularValorFinanciado());
        taxaJuros.setOnItemSelectedListener(selectedItemTaxaJuros());
        btnSimularFinanciamento.setOnClickListener(calcularValorTotalFinanciado());
        btnConfirmarVenda.setOnClickListener(confirmarVenda());
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(0);
                finish();
            }
        });


    }

    private View.OnClickListener confirmarVenda() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean camposValidos = validaCampos();
                if(camposValidos){
                    Cliente cliente = new Cliente(0,
                            nome.getText().toString(),
                            email.getText().toString(),
                            telefone.getText().toString(),
                            Double.parseDouble(valorEntrada.getText().toString()));


                    Financiamento financiamento = new Financiamento(Double.parseDouble(valorFinanciado.getText().toString()),
                            Double.parseDouble(valorTotalFinanciado.getText().toString()),
                            Integer.parseInt(qtdeParcelas.getSelectedItem().toString()),
                            0);

                    try{
                        new ClienteDAO(getApplicationContext()).insert(cliente);
                        new FinanciamentoDAO(getApplicationContext()).insert(financiamento);
                        imovel.setEhVendido(true);
                        new ImovelDAO(getApplicationContext()).edit(imovel);
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

    private View.OnClickListener calcularValorTotalFinanciado() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valorEntrada.getText().toString().length() < 1){
                    valorEntrada.setError("Informe o Valor de Entrada");

                }else{
                    int qtdeParcelasSelecionada = Integer.parseInt(qtdeParcelas.getSelectedItem().toString());
                    double entrada = Double.parseDouble(valorEntrada.getText().toString());
                    double valorFinanciadoParaCalcular = Double.parseDouble(valorFinanciado.getText().toString());

                    double valorTotalImovel = entrada +  (valorFinanciadoParaCalcular +(valorFinanciadoParaCalcular / qtdeParcelasSelecionada) * ((valorTaxaJurosSelecionada / 100) + 1));


                    valorTotalFinanciado.setText(String.valueOf(valorTotalImovel));
                }

            }
        };
    }

    private AdapterView.OnItemSelectedListener selectedItemTaxaJuros() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    taxaJurosSelectedSpinner = taxasJuros.get(position);
                    valorTaxaJurosSelecionada = taxaJurosSelectedSpinner.getTaxaJuros();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }

    private void carregarSpinnerTaxaJuros() {
        taxasJuros = new TaxaJurosDAO(getApplicationContext()).findAll();

        for(int i = 0;i < taxasJuros.size();i++) {
            TaxaJuros taxaJurosSelecionado = taxasJuros.get(i);
            taxasJurosCarregados.add(taxaJurosSelecionado);
        }


        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item, taxasJurosCarregados);
        taxaJuros.setAdapter(adapter);
    }

    private void popularSpinnerQtdeParcelas() {
        List<Integer> list = new ArrayList<Integer>();
        list.add(3);list.add(6);list.add(12);list.add(24);list.add(36);list.add(60);

        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        qtdeParcelas.setAdapter(dataAdapter);
    }

    private View.OnClickListener calcularValorFinanciado() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(valorEntrada.getText().toString().length() < 1){
                    valorEntrada.setError("Informe o Valor de Entrada");

                }else{
                    double valorImovelSelecionado = imovel.getPreco();
                    double valorEntradaDesejada = Double.parseDouble(valorEntrada.getText().toString());

                    double valorFinanciadoCalculado = valorImovelSelecionado - valorEntradaDesejada;


                    String valorASerFinanciado = String.valueOf(valorFinanciadoCalculado);


                    valorFinanciado.setText(valorASerFinanciado);
                }
            }
        };
    }

    private void binding() {

        valorTotalFinanciado = findViewById(R.id.txtViewCadCliFinValorTotalFinanciado);
        taxaJuros = findViewById(R.id.spinnerCadCliFinTaxaJuros);
        valorFinanciado = findViewById(R.id.txtViewCadCliFinValorFinanciado);
        qtdeParcelas = findViewById(R.id.spinnerCadCliFinQtdeParcelas);
        valorEntrada = findViewById(R.id.edtCadClienteValorEntrada);
        email = findViewById(R.id.edtCadClienteEmail);
        telefone = findViewById(R.id.edtCadClienteTelefone);
        nome = findViewById(R.id.edtCadClienteNome);
        btnCalculaValorFinanciado = findViewById(R.id.btnCadClienteFinVerificaTaxaJuros);
        btnSimularFinanciamento = findViewById(R.id.btnCadClienteFinSimularFinanciamento);
        btnConfirmarVenda = findViewById(R.id.btnCadClienteFinConfirmarVenda);
        btnVoltar = findViewById(R.id.btnCadCliFinVoltarTelaAnterior);
        valorImovel = findViewById(R.id.txtViewCadCliFinValorImovel);
    }

    private boolean validaCampos() {

        if((nome.getText().toString().length() > 0) && (email.getText().toString().length() > 0) && (telefone.getText().toString().length() > 0) && (valorFinanciado.getText().toString().length() > 0) && (valorEntrada.getText().toString().length() > 0)){
            return true;
        }else{
            if(nome.getText().toString().length() < 1){
                nome.setError("Informe o Nome");
            }
            if(email.getText().toString().length() < 1){
                email.setError("Informe o Email");
            }
            if(telefone.getText().toString().length() < 1){
                telefone.setError("Informe o Telefone");
            }
            if(valorFinanciado.getText().toString().length() < 1){
                valorFinanciado.setError("Informe o Valor Financiado");
            }
            if(valorEntrada.getText().toString().length() < 1){
                valorEntrada.setError("Informe o Valor de Entrada");
            }
            return false;
        }
    }
}
