package com.example.romuloroger.imobiliariaapp.Database.DAOs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.romuloroger.imobiliariaapp.Database.Database;
import com.example.romuloroger.imobiliariaapp.Enums.ETipoImovel;
import com.example.romuloroger.imobiliariaapp.Models.Imovel;
import com.example.romuloroger.imobiliariaapp.Models.TaxaJuros;
import com.example.romuloroger.imobiliariaapp.Models.Usuario;

import java.util.ArrayList;
import java.util.List;

public class TaxaJurosDAO {

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

    public TaxaJurosDAO(Context context){
        this.context = context;
        database = new Database(context);
    }


    public void insert(TaxaJuros taxaJuros){
        ContentValues cv = new ContentValues();
        cv.put(Database.tableTaxaJurosValorInicial, taxaJuros.getValorInicial());
        cv.put(Database.tableTaxaJurosValorFinal, taxaJuros.getValorFinal());
        cv.put(Database.tableTaxaJurosTaxaJuros, taxaJuros.getTaxaJuros());

        open();
        data.insert(Database.tableTaxaJuros,null, cv);
        close();
    }

    public List<TaxaJuros> findAll() {
        String sql = "SELECT * FROM "+Database.tableTaxaJuros+";";
        open();
        Cursor cursor = data.rawQuery(sql,null);

        List<TaxaJuros> taxasJuros = new ArrayList<>();
        while(cursor.moveToNext()){
            TaxaJuros taxaJuros = new TaxaJuros();
            taxaJuros.setId(cursor.getInt(cursor.getColumnIndex(Database.tableTaxaJurosId)));
            taxaJuros.setValorInicial(cursor.getDouble(cursor.getColumnIndex(Database.tableTaxaJurosValorInicial)));
            taxaJuros.setValorFinal(cursor.getDouble(cursor.getColumnIndex(Database.tableTaxaJurosValorFinal)));
            taxaJuros.setTaxaJuros(cursor.getDouble(cursor.getColumnIndex(Database.tableTaxaJurosTaxaJuros)));

            taxasJuros.add(taxaJuros);
        }
        close();
        return taxasJuros;
    }


    public void edit(TaxaJuros taxaJuros){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Database.tableTaxaJurosValorInicial,taxaJuros.getValorInicial());
        contentValues.put(Database.tableTaxaJurosValorFinal,taxaJuros.getValorFinal());
        contentValues.put(Database.tableTaxaJurosTaxaJuros,taxaJuros.getTaxaJuros());
        open();
        data.update(Database.tableTaxaJuros,contentValues,Database.tableTaxaJurosId +"="+taxaJuros.getId(),null);
        close();
    }

    public int count(){
        try{
            String sql = "SELECT Count(*) from " + Database.tableTaxaJuros + ";";
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
