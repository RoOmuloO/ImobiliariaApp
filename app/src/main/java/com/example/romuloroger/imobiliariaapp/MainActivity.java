package com.example.romuloroger.imobiliariaapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.romuloroger.imobiliariaapp.Database.DAOs.ImovelDAO;
import com.example.romuloroger.imobiliariaapp.Database.DAOs.TaxaJurosDAO;
import com.example.romuloroger.imobiliariaapp.Database.DAOs.UsuarioDAO;
import com.example.romuloroger.imobiliariaapp.Enums.ETipoImovel;
import com.example.romuloroger.imobiliariaapp.Models.Imovel;
import com.example.romuloroger.imobiliariaapp.Models.TaxaJuros;
import com.example.romuloroger.imobiliariaapp.Models.Usuario;

public class MainActivity extends AppCompatActivity {

    static final int codeCadImovel = 1;
    static final int codeLoginPage = 2;
    static final int codeMainActivityPage = 3;
    static final int codeListaImoveisPage = 4;
    static final int codeCadTaxaJuros = 5;
    static final int codeListaTaxaJuros = 5;
    static final int codeCadUsuario = 6;
    static final int codeListaUsuarios = 7;
    static final int codeListaClientes = 8;

    Usuario usuarioLogado = null;
    Button novoImovel,listaImoveis, novaTaxa,listaTaxas,novoUsuario,listaUsuarios,listaClientes;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("usuario",usuarioLogado);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding();

        UsuarioDAO uDao = new UsuarioDAO(getApplicationContext());
        TaxaJurosDAO tDao = new TaxaJurosDAO(getApplicationContext());
        ImovelDAO iDao = new ImovelDAO(getApplicationContext());

        if(uDao.count() <= 0){
            Usuario usuario = new Usuario(1,"Administrador","admin","123456789","admin@admin.com","123456","123456", "Administrador");
            uDao.insert(usuario);
            usuario = new Usuario(2,"Vendedor","vendedor","987654321","vendedor@vendedor.com","654321","654321", "Vendedor");
            uDao.insert(usuario);
        }

        if(tDao.count() <= 0){
            TaxaJuros taxaJuros = new TaxaJuros(0,0,100000,2);
            tDao.insert(taxaJuros);
            taxaJuros = new TaxaJuros(0,100001,250000,5);
            tDao.insert(taxaJuros);
            taxaJuros = new TaxaJuros(0,250001,500000,10);
            tDao.insert(taxaJuros);
        }

        if(iDao.count() <= 0){
            Imovel imovel = new Imovel(0,2,"Casa Bonita","Democrata","Casa bem localizada",250000, ETipoImovel.Casa,false,null);
            iDao.insert(imovel);
            imovel = new Imovel(0,3,"Apartamento Bonito","Sao Mateus","Apartamento bem localizado",300000, ETipoImovel.Apartamento,false,null);
            iDao.insert(imovel);
            imovel = new Imovel(0,3,"Casa Grande","Cascatinha","Casa bem localizada",400000, ETipoImovel.Casa,false,null);
            iDao.insert(imovel);
        }


        callLoginActivity();

        novoImovel.setOnClickListener(createNewImovel());
        listaImoveis.setOnClickListener(listHomeForSales());
        novaTaxa.setOnClickListener(createNewTaxa());
        listaTaxas.setOnClickListener(listTaxas());
        novoUsuario.setOnClickListener(createNewUser());
        listaUsuarios.setOnClickListener(listUsuarios());
        listaClientes.setOnClickListener(listClientes());

    }

    private View.OnClickListener listClientes() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itn = new Intent(getApplicationContext(),ListaClientesActivity.class);

                startActivityForResult(itn,codeListaClientes);
            }
        };
    }

    private View.OnClickListener listUsuarios() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itn = new Intent(getApplicationContext(), ListaUsuariosActivity.class);
                startActivityForResult(itn,codeListaUsuarios);
            }
        };
    }

    private View.OnClickListener createNewUser() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itn = new Intent(getApplicationContext(), CadUsuarioActivity.class);
                startActivityForResult(itn,codeCadUsuario);
            }
        };
    }

    private View.OnClickListener listTaxas() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itn = new Intent(getApplicationContext(),ListaTaxasActivity.class);
                startActivityForResult(itn,codeListaTaxaJuros);
            }
        };
    }

    private View.OnClickListener createNewTaxa() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent itn = new Intent(getApplicationContext(),CadTaxaJurosActivity.class);
                    startActivityForResult(itn,codeCadTaxaJuros);
            }
        };
    }

    private View.OnClickListener listHomeForSales() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent itn = new Intent(getApplicationContext(),ListaImovelActivity.class);
                    itn.putExtra("usuario",usuarioLogado);
                    startActivityForResult(itn,codeListaImoveisPage);
            }
        };
    }

    private View.OnClickListener createNewImovel() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itn = new Intent(getApplicationContext(), CadImovelActivity.class);
                startActivityForResult(itn, codeCadImovel);
            }
        };
    }

    private void binding() {
        novoImovel = findViewById(R.id.btnMenuPrincipalNovoImovel);
        listaImoveis = findViewById(R.id.btnMenuPrincipalListarImoveis);
        novaTaxa = findViewById(R.id.btnMenuPrincipalCadastrarTaxas);
        listaTaxas = findViewById(R.id.btnMenuPrincipalListarTaxas);
        novoUsuario = findViewById(R.id.btnMenuPrincipalCadastrarUsuarios);
        listaUsuarios = findViewById(R.id.btnMenuPrincipalListarUsuarios);
        listaClientes = findViewById(R.id.btnMenuPrincipalListarClientes);
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_cadastro_produto, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.btnMenuSair){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void callLoginActivity() {
        Intent itn = new Intent(getApplicationContext(), LoginActivity.class);

        startActivityForResult(itn, codeLoginPage);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == codeCadImovel){
            if(resultCode == 0){
                finish();
            }else if(resultCode == 1){

            }else if(resultCode == 2){

            }
        }else if(requestCode == codeLoginPage){
            retornaTelaLogin(resultCode,data);
        }
    }

    private void retornaTelaLogin(int resultCode, Intent data) {
        if(resultCode == 0){
            finish();
        }else if (resultCode == 10){
            String nomeLogado = (String) data.getExtras().get("nome");
            usuarioLogado = (Usuario) data.getExtras().get("usuario");

            if(usuarioLogado.getTipoUsuario().equals("Administrador")){
                novaTaxa.setVisibility(View.VISIBLE);
                listaTaxas.setVisibility(View.VISIBLE);
                novoUsuario.setVisibility(View.VISIBLE);
                listaUsuarios.setVisibility(View.VISIBLE);
            }else{
                novaTaxa.setVisibility(View.INVISIBLE);
                listaTaxas.setVisibility(View.INVISIBLE);
                novoUsuario.setVisibility(View.INVISIBLE);
                listaUsuarios.setVisibility(View.INVISIBLE);
            }
            Toast.makeText(getApplicationContext(), "Bem Vindo " +nomeLogado, Toast.LENGTH_SHORT).show();
        }
    }
}
