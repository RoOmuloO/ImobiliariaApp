package com.example.romuloroger.imobiliariaapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.romuloroger.imobiliariaapp.Database.DAOs.UsuarioDAO;
import com.example.romuloroger.imobiliariaapp.Models.Usuario;

public class LoginActivity extends AppCompatActivity {

    EditText login, senha;
    ImageButton acessar;

    private void binding() {
        login = findViewById(R.id.edtLoginLogin);
        senha = findViewById(R.id.edtLoginSenha);
        acessar = findViewById(R.id.imgbtnAcessar);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        binding();

        acessar.setOnClickListener(clickBotaoLogar());
    }

    @NonNull
    private View.OnClickListener clickBotaoLogar() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UsuarioDAO uDao = new UsuarioDAO(getApplicationContext());
                Usuario usuario = uDao.validaLogin(login.getText().toString(),senha.getText().toString());
                if(usuario != null){
                    Intent i = new Intent();
                    i.putExtra("nome",usuario.getNome());
                    i.putExtra("usuario",usuario);
                    setResult(10,i);
                    finish();

                }else{
                    Toast.makeText(getApplicationContext(), "Login e Senha Incorreta", Toast.LENGTH_SHORT).show();
                    login.setText("");
                    senha.setText("");
                    login.requestFocus();
                }
            }
        };
    }


}
