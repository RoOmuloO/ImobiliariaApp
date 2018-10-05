package com.example.romuloroger.imobiliariaapp;

import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.romuloroger.imobiliariaapp.Database.DAOs.UsuarioDAO;
import com.example.romuloroger.imobiliariaapp.Models.Usuario;

public class CadUsuarioActivity extends AppCompatActivity {

    Spinner spinTipoUsuario;
    EditText edtNome,edtEmail,edtCpf,edtLogin,edtSenha,edtConfirmaSenha;
    Button btnSalvar, btnVoltar;

    Vibrator vibrator;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_usuario);

        binding();

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(0);
                finish();
            }
        });

        btnSalvar.setOnClickListener(salvarUsuario());

    }

    private View.OnClickListener salvarUsuario() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String senha = edtSenha.getText().toString();
                    String confirmaSenha = edtConfirmaSenha.getText().toString();

                    boolean camposValidos = validaCampos();
                    if(camposValidos){
                        if(senha.equals(confirmaSenha)){
                            Usuario usuario = new Usuario(0,
                                    edtNome.getText().toString(),
                                    edtLogin.getText().toString(),
                                    edtCpf.getText().toString(),
                                    edtEmail.getText().toString(),
                                    edtSenha.getText().toString(),
                                    edtConfirmaSenha.getText().toString(),
                                    spinTipoUsuario.getSelectedItem().toString());

                            try {
                                new UsuarioDAO(getApplicationContext()).insert(usuario);
                                Toast.makeText(getApplicationContext(), "Salvo com Sucesso!!", Toast.LENGTH_LONG).show();
                                setResult(1);
                                finish();
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Erro ao salvar . " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }

                        }else {
                            Toast.makeText(getApplicationContext(),"As senhas não conferem!", Toast.LENGTH_LONG).show();
                            edtSenha.setText("");
                            edtConfirmaSenha.setText("");
                        }
                    }else {
                        vibrator.vibrate(200);
                        Toast.makeText(getApplicationContext(),"Informe todos os valores acima.",Toast.LENGTH_LONG).show();
                    }


            }
        };
    }

    private void binding() {
        edtNome = findViewById(R.id.edtCadUsuarioNome);
        edtEmail = findViewById(R.id.edtCadUsuarioEmail);
        edtCpf = findViewById(R.id.edtCadUsuarioCPF);
        edtLogin = findViewById(R.id.edtCadUsuarioLogin);
        edtSenha = findViewById(R.id.edtCadUsuarioSenha);
        edtConfirmaSenha = findViewById(R.id.edtCadUsuarioConfirmarSenha);
        btnSalvar = findViewById(R.id.btnCadUsuarioSalvar);
        btnVoltar = findViewById(R.id.btnCadUsuarioVoltar);
        spinTipoUsuario = findViewById(R.id.spinnerCadUsuarioTipoUsuario);
    }

    private boolean validaCampos() {

        if((edtNome.getText().toString().length() > 0) && (edtEmail.getText().toString().length() > 0) && (edtCpf.getText().toString().length() > 0) && (edtLogin.getText().toString().length() > 0) && (edtSenha.getText().toString().length() > 0) && (edtConfirmaSenha.getText().toString().length() > 0)){
            return true;
        }else{
            if(edtNome.getText().toString().length() < 1){
                edtNome.setError("Informe o Nome");
            }
            if(edtEmail.getText().toString().length() < 1){
                edtEmail.setError("Informe o Email");
            }
            if(edtCpf.getText().toString().length() < 1){
                edtCpf.setError("Informe o CPF");
            }
            if(edtLogin.getText().toString().length() < 1){
                edtLogin.setError("Informe o Login");
            }
            if(edtSenha.getText().toString().length() < 1){
                edtSenha.setError("Informe a Senha");
            }
            if(edtConfirmaSenha.getText().toString().length() < 1){
                edtConfirmaSenha.setError("Informe a Confirmação de Senha");
            }
            return false;
        }
    }
}
