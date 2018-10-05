package com.example.romuloroger.imobiliariaapp.Database.DAOs;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.romuloroger.imobiliariaapp.Database.Database;
import com.example.romuloroger.imobiliariaapp.Models.Financiamento;

public class FinanciamentoDAO {

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

    public FinanciamentoDAO(Context context){
        this.context = context;
        database = new Database(context);
    }

    public void insert(Financiamento financiamento){
        ContentValues cv = new ContentValues();
        cv.put(Database.tableFinanciamentoId, financiamento.getId());
        cv.put(Database.tableFinanciamentoQtdeParcelas, financiamento.getQtdeParcelas());
        cv.put(Database.tableFinanciamentoValorFinanciado, financiamento.getValorFinanciado());
        cv.put(Database.tableFinanciamentoValorTotal, financiamento.getValorTotal());
        open();
        data.insert(Database.tableFinanciamento,null, cv);
        close();
    }
}
