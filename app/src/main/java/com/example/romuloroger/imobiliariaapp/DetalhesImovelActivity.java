package com.example.romuloroger.imobiliariaapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.romuloroger.imobiliariaapp.Database.DAOs.ImovelDAO;
import com.example.romuloroger.imobiliariaapp.Models.Imovel;
import com.example.romuloroger.imobiliariaapp.Models.Usuario;
import com.example.romuloroger.imobiliariaapp.classes.Moeda;

import org.w3c.dom.Text;

import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class DetalhesImovelActivity extends AppCompatActivity {

    TextView tvNome, tvPreco, tvDescricao, tvBairro, tvQtdeQuartos,tvTipoImovel;
    Button btnSalvar,btnVoltar,btnRecolocarAVenda;
    Switch isVendido;
    Imovel imovel;
    ImageView imgImovel;
    static final int codeCadClentePage = 7;
    Moeda m = new Moeda();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_imovel);

        binding();
        Usuario usuario = (Usuario) getIntent().getExtras().getSerializable("usuario");


        imovel = (Imovel) getIntent().getExtras().getSerializable("imovel");
        if(imovel != null){
            tvNome.setText(imovel.getNome());
            tvPreco.setText(m.mascaraDinheiro(imovel.getPreco(),m.DINHEIRO_REAL));
            tvQtdeQuartos.setText(((Integer)imovel.getQtdeQuartos()).toString());
            tvDescricao.setText(imovel.getDescricao());
            tvBairro.setText(imovel.getBairro());
            isVendido.setChecked(imovel.isEhVendido());
            tvTipoImovel.setText(imovel.getTipoImovel().toString());

            if(imovel.getListaFotos() != null){
                String path = imovel.getListaFotos().toString();
                Bitmap myImage = BitmapFactory.decodeFile(path);
                imgImovel.setImageBitmap(myImage);
            }

            if(!usuario.getTipoUsuario().equals("Administrador")){
                btnRecolocarAVenda.setVisibility(View.INVISIBLE);
            }


            btnVoltar.setOnClickListener(backListHomes());
            btnSalvar.setOnClickListener(forSale());
            btnRecolocarAVenda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imovel.setEhVendido(false);
                    new ImovelDAO(getApplicationContext()).edit(imovel);
                    setResult(2);
                    finish();
                }
            });
        }
    }

    private View.OnClickListener forSale() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itn = new Intent(getApplicationContext(),CadClienteForSaleActivity.class);

                itn.putExtra("imovel",imovel);
                startActivityForResult(itn, codeCadClentePage);
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
        tvNome = findViewById(R.id.txtViewDetalhesImovelNome);
        tvPreco = findViewById(R.id.txtViewDetalhesImovelPreco);
        tvBairro = findViewById(R.id.txtViewDetalhesImovelBairro);
        tvDescricao = findViewById(R.id.txtViewDetalhesImovelDescricao);
        tvQtdeQuartos = findViewById(R.id.txtViewDetalhesImovelQtdeQuartos);
        btnSalvar = findViewById(R.id.btnDetalhesImovelSalvar);
        btnVoltar = findViewById(R.id.btnDetalhesImovelVoltar);
        isVendido = findViewById(R.id.switchDetalhesImovelVendido);
        imgImovel = findViewById(R.id.imgViewDetalhesImovelFoto);
        tvTipoImovel = findViewById(R.id.txtViewDetalhesImovelTipoImovel);
        btnRecolocarAVenda = findViewById(R.id.btnDetalhesImovelRecolocarVenda);
     }
}
