package com.example.romuloroger.imobiliariaapp.Database.DAOs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.romuloroger.imobiliariaapp.Database.Database;
import com.example.romuloroger.imobiliariaapp.Models.Cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ClienteDAO {

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

    public ClienteDAO(Context context){
        this.context = context;
        database = new Database(context);
    }

    public void insert(Cliente cliente){
        ContentValues cv = new ContentValues();
        cv.put(Database.tableClienteId, cliente.getId());
        cv.put(Database.tableClienteNome, cliente.getNome());
        cv.put(Database.tableClienteEmail, cliente.getEmail());
        cv.put(Database.tableClienteTelefone, cliente.getTelefone());
        cv.put(Database.tableClienteValorEntrada, cliente.getValorEntrada());
        open();
        data.insert(Database.tableCliente,null, cv);
        close();
    }

    public List<Cliente> findAll() {
        String sql = "SELECT * FROM "+Database.tableCliente+";";
        open();
        Cursor cursor = data.rawQuery(sql,null);

        List<Cliente> clientes = new ArrayList<>();
        while(cursor.moveToNext()){
            Cliente cliente = new Cliente();
            cliente.setId(cursor.getInt(cursor.getColumnIndex(Database.tableClienteId)));
            cliente.setNome(cursor.getString(cursor.getColumnIndex(Database.tableClienteNome)));
            cliente.setTelefone(cursor.getString(cursor.getColumnIndex(Database.tableClienteTelefone)));
            cliente.setEmail(cursor.getString(cursor.getColumnIndex(Database.tableClienteEmail)));
            cliente.setValorEntrada(cursor.getDouble(cursor.getColumnIndex(Database.tableClienteValorEntrada)));
            clientes.add(cliente);
        }
        close();
        return clientes;
    }
}
