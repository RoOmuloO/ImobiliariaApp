package com.example.romuloroger.imobiliariaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.romuloroger.imobiliariaapp.Database.DAOs.UsuarioDAO;
import com.example.romuloroger.imobiliariaapp.Models.Usuario;

import java.util.ArrayList;
import java.util.List;

public class ListaUsuariosActivity extends AppCompatActivity {

    EditText edtPesquisar;
    Button btnPesquisar;
    ListView listaUsuarios;
    List<Usuario> usuarios;
    ArrayAdapter<String> listaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuarios);

        binding();

        listaAdapter = preencheListViewUsuarios();

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

    private ArrayAdapter<String> preencheListViewUsuarios() {
        usuarios = new UsuarioDAO(getApplicationContext()).findAll();

        ArrayList<String> listaString = new ArrayList<String>();
        for(Usuario usuario: usuarios)
        {
            listaString.add("Nome: " +usuario.getNome()+"\nE-mail: "+usuario.getEmail()+"\nLogin: "+usuario.getLogin());
        }

        ArrayAdapter<String> listaAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,listaString);

        listaUsuarios.setAdapter(listaAdapter);

        return listaAdapter;
    }

    private void binding() {
        edtPesquisar = findViewById(R.id.edtListaUsuariosPesquisar);
        btnPesquisar = findViewById(R.id.btnListaUsuariosPesquisar);
        listaUsuarios = findViewById(R.id.listViewListaUsuariosLista);
    }
}
