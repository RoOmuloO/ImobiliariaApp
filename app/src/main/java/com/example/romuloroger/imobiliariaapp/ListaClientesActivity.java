package com.example.romuloroger.imobiliariaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.romuloroger.imobiliariaapp.Database.DAOs.ClienteDAO;
import com.example.romuloroger.imobiliariaapp.Models.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ListaClientesActivity extends AppCompatActivity {

    EditText edtPesquisar;
    Button btnPesquisar;
    ListView lstViewClientes;
    List<Cliente> clienteList;

    ArrayAdapter<String> listaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_clientes);

        binding();

        listaAdapter = preencheListViewClientes();

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

    private ArrayAdapter<String> preencheListViewClientes() {
        clienteList = new ClienteDAO(getApplicationContext()).findAll();

        ArrayList<String> listaString = new ArrayList<String>();
        for(Cliente cliente: clienteList)
        {
            listaString.add("Nome: " +cliente.getNome()+"\nE-mail: "+cliente.getEmail()+"\nTelefone: "+cliente.getTelefone());
        }

        ArrayAdapter<String> listaAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,listaString);

        lstViewClientes.setAdapter(listaAdapter);

        return listaAdapter;
    }

    private void binding() {

        edtPesquisar = findViewById(R.id.edtListaClientesPesquisar);
        btnPesquisar = findViewById(R.id.btnListaClientesPesquisar);
        lstViewClientes = findViewById(R.id.ListViewListaClientesPesquisar);
    }
}
