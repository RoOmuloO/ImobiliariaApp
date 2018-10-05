package com.example.romuloroger.imobiliariaapp.Database.DAOs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import com.example.romuloroger.imobiliariaapp.Database.Database;
import com.example.romuloroger.imobiliariaapp.Enums.ETipoImovel;
import com.example.romuloroger.imobiliariaapp.Models.Imovel;
import com.example.romuloroger.imobiliariaapp.Models.Usuario;

import java.util.ArrayList;
import java.util.List;

public class ImovelDAO {

    private Database database;
    private SQLiteDatabase data;
    private Context context;

    private void open(){
        data = database.getWritableDatabase();
    }

    private void close(){
        data.close();
        database.close();
    }

    public ImovelDAO(Context context) {
        this.context = context;
        database = new Database(context);
    }

    public void insert(Imovel imovel){
        ContentValues cv = new ContentValues();
        cv.put(Database.tableImovelNome, imovel.getNome());
        cv.put(Database.tableImovelValor, imovel.getPreco());
        cv.put(Database.tableImovelDescicao, imovel.getDescricao());
        cv.put(Database.tableImovelBairro, imovel.getBairro());
        cv.put(Database.tableImovelQtdeQuartos, imovel.getQtdeQuartos());
        cv.put(Database.tableImovelFotos, imovel.getListaFotos());
        cv.put(Database.tableImovelIsVendido, imovel.isEhVendido());
        cv.put(Database.tableImovelTipoImovel, imovel.getTipoImovel().toString());
        open();
        data.insert(Database.tableImovel,null, cv);
        close();
    }

    public void edit(Imovel imovel){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Database.tableImovelNome, imovel.getNome());
        contentValues.put(Database.tableImovelValor, imovel.getPreco());
        contentValues.put(Database.tableImovelDescicao, imovel.getDescricao());
        contentValues.put(Database.tableImovelBairro, imovel.getBairro());
        contentValues.put(Database.tableImovelQtdeQuartos, imovel.getQtdeQuartos());
        contentValues.put(Database.tableImovelFotos, imovel.getListaFotos());
        contentValues.put(Database.tableImovelIsVendido, imovel.isEhVendido());
        contentValues.put(Database.tableImovelTipoImovel, imovel.getTipoImovel().toString());
        open();
        data.update(Database.tableImovel,contentValues,Database.tableImovelId +"="+imovel.getId(),null);
        close();
    }

    public List<Imovel> findAll() {
        String sql = "SELECT * FROM "+Database.tableImovel+";";
        open();
        Cursor cursor = data.rawQuery(sql,null);

        List<Imovel> imoveis = new ArrayList<>();
        while(cursor.moveToNext()){
            Imovel imovel = new Imovel();
            imovel.setId(cursor.getInt(cursor.getColumnIndex(Database.tableImovelId)));
            imovel.setNome(cursor.getString(cursor.getColumnIndex(Database.tableImovelNome)));
            imovel.setPreco(cursor.getDouble(cursor.getColumnIndex(Database.tableImovelValor)));
            imovel.setQtdeQuartos(cursor.getInt(cursor.getColumnIndex(Database.tableImovelQtdeQuartos)));
            imovel.setBairro(cursor.getString(cursor.getColumnIndex(Database.tableImovelBairro)));
            imovel.setDescricao(cursor.getString(cursor.getColumnIndex(Database.tableImovelDescicao)));
            imovel.setEhVendido(cursor.getInt(cursor.getColumnIndex(Database.tableImovelIsVendido)) == 1);
            imovel.setListaFotos(cursor.getString(cursor.getColumnIndex(Database.tableImovelFotos)));
            imovel.setTipoImovel(ETipoImovel.valueOf(cursor.getString(cursor.getColumnIndex(Database.tableImovelTipoImovel))));
            imoveis.add(imovel);
        }
        close();
        return imoveis;
    }

    public List<Imovel> findAllnotSold() {
        String sql = "SELECT * FROM "+Database.tableImovel+" WHERE "+Database.tableImovelIsVendido+" = 0;";
        open();
        Cursor cursor = data.rawQuery(sql,null);

        List<Imovel> imoveis = new ArrayList<>();
        while(cursor.moveToNext()){
            Imovel imovel = new Imovel();
            imovel.setId(cursor.getInt(cursor.getColumnIndex(Database.tableImovelId)));
            imovel.setNome(cursor.getString(cursor.getColumnIndex(Database.tableImovelNome)));
            imovel.setPreco(cursor.getDouble(cursor.getColumnIndex(Database.tableImovelValor)));
            imovel.setQtdeQuartos(cursor.getInt(cursor.getColumnIndex(Database.tableImovelQtdeQuartos)));
            imovel.setBairro(cursor.getString(cursor.getColumnIndex(Database.tableImovelBairro)));
            imovel.setDescricao(cursor.getString(cursor.getColumnIndex(Database.tableImovelDescicao)));
            imovel.setEhVendido(cursor.getInt(cursor.getColumnIndex(Database.tableImovelIsVendido)) == 1);
            imovel.setListaFotos(cursor.getString(cursor.getColumnIndex(Database.tableImovelFotos)));
            imovel.setTipoImovel(ETipoImovel.valueOf(cursor.getString(cursor.getColumnIndex(Database.tableImovelTipoImovel))));
            imoveis.add(imovel);
        }
        close();
        return imoveis;
    }

    public int count(){
        try{
            String sql = "SELECT Count(*) from " + Database.tableImovel + ";";
            open();
            Cursor cursor = data.rawQuery(sql,null);

            if(cursor.moveToFirst()){
                return cursor.getInt(0);
            }else{
                return 0;
            }
        }finally{
            close();
        }
    }


}
