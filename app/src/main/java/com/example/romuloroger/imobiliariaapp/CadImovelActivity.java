package com.example.romuloroger.imobiliariaapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.romuloroger.imobiliariaapp.Database.DAOs.ImovelDAO;
import com.example.romuloroger.imobiliariaapp.Enums.ETipoImovel;
import com.example.romuloroger.imobiliariaapp.Models.Imovel;

import java.io.File;

public class CadImovelActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 5;

    RadioGroup rgTipoImovel;
    RadioButton rbCasa, rbApartamento;
    EditText nome, valor, qtdeQuartos, descricao, bairro;
    Switch isComprado;
    ImageButton adicionarFotos, salvar;
    ImageView imgView;
    private Uri uri;
    private String caminhoDaImagem;
    Vibrator vibrator;


    private void binding() {
        rbCasa = findViewById(R.id.rbCadImovelCasa);
        rbApartamento = findViewById(R.id.rbCadImovelApartamento);
        nome = findViewById(R.id.edtCadImovelNome);
        valor = findViewById(R.id.edtCadImovelValor);
        qtdeQuartos = findViewById(R.id.edtCadImovelQtdeQuartos);
        descricao = findViewById(R.id.edtCadImovelDescricao);
        bairro = findViewById(R.id.edtCadImovelBairro);
        isComprado = findViewById(R.id.switchCadImovelIsComprado);
        adicionarFotos = findViewById(R.id.imgbtnCadImovelAddFotos);
        salvar = findViewById(R.id.imgbtnCadImovelSalvar);
        imgView = findViewById(R.id.imgviewCadImovel);
        rgTipoImovel = findViewById(R.id.rgCadImovelTipoImovel);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_imovel);

        binding();

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        if(!hasCamera())
            adicionarFotos.setEnabled(false);

        adicionarFotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usarCamera(v);
            }
        });

        salvar.setOnClickListener(SaveImovel());



    }

    public void usarCamera(View view) {

        String path = "/sdcard/Pictures";
        File imagem = new File(path + "/" + System.currentTimeMillis() + ".jpg");
        uri  = FileProvider.getUriForFile(getApplicationContext(),
                "com.example.myapp.fileprovider",
                imagem);

        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intentCamera, REQUEST_IMAGE_CAPTURE);
    }

    private View.OnClickListener SaveImovel() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean camposValidos = validaCampos();
                if(camposValidos){
                    Imovel imovel = new Imovel(0,
                            Integer.parseInt(qtdeQuartos.getText().toString()),
                            nome.getText().toString(),
                            bairro.getText().toString(),
                            descricao.getText().toString(),
                            Double.parseDouble(valor.getText().toString()),
                            rbCasa.isChecked() ? ETipoImovel.Casa : ETipoImovel.Apartamento,
                            isComprado.isChecked(),
                            caminhoDaImagem);

                    try {
                        new ImovelDAO(getApplicationContext()).insert(imovel);
                        Toast.makeText(getApplicationContext(), "Salvo com Sucesso!!", Toast.LENGTH_LONG).show();
                        setResult(1);
                        finish();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Erro ao salvar . " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else{
                    vibrator.vibrate(200);
                    Toast.makeText(getApplicationContext(),"Informe todos os valores acima.",Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    private boolean validaCampos() {

        if((nome.getText().toString().length() > 0) && (valor.getText().toString().length() > 0) && (qtdeQuartos.getText().toString().length() > 0) && (descricao.getText().toString().length() > 0) && (bairro.getText().toString().length() > 0) && (rbCasa.isChecked() || rbApartamento.isChecked())){
            return true;
        }else{
            if(nome.getText().toString().length() < 1){
                nome.setError("Informe o Nome");
            }
            if(valor.getText().toString().length() < 1){
                valor.setError("Informe o Valor");
            }
            if(qtdeQuartos.getText().toString().length() < 1){
                qtdeQuartos.setError("Informe a Quantidade de Quartos");
            }
            if(descricao.getText().toString().length() < 1){
                descricao.setError("Informe a Descrição");
            }
            if(bairro.getText().toString().length() < 1){
                bairro.setError("Informe o Bairro");
            }
            if(!rbApartamento.isChecked() && !rbCasa.isChecked()){
                rbApartamento.setError("Informe um tipo de Imóvel");
            }
            return false;
        }
    }


    private boolean hasCamera() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE){
            if(resultCode == RESULT_OK){

                Intent novaIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                sendBroadcast(novaIntent);

                caminhoDaImagem = "/sdcard/Pictures/"+uri.getLastPathSegment();
                Bundle extras = data.getExtras();
                Bitmap photo = (Bitmap) extras.get("data");
                imgView.setImageBitmap(photo);
            }
        }
    }
}
