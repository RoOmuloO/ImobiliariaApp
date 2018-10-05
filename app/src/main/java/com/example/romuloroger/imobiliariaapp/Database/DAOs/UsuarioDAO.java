package com.example.romuloroger.imobiliariaapp.Database.DAOs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import com.example.romuloroger.imobiliariaapp.Database.Database;
import com.example.romuloroger.imobiliariaapp.Models.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
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

    public UsuarioDAO(Context context){
        this.context = context;
        database = new Database(context);
    }

    public int count(){
        try{
            String sql = "SELECT Count(*) from " + Database.tableUsuario + ";";
            open();
            Cursor cursor = data.rawQuery(sql,null);

            Usuario usuario = null;

            if(cursor.moveToFirst()){
                return cursor.getInt(0);
            }else{
                return 0;
            }
        }finally{
            close();
        }
    }

    public void insert(Usuario usuario){
        ContentValues cv = new ContentValues();
        cv.put(Database.tableUsuarioNome, usuario.getNome());
        cv.put(Database.tableUsuarioCPF, usuario.getCpf());
        cv.put(Database.tableUsuarioLogin, usuario.getLogin());
        cv.put(Database.tableUsuarioSenha, usuario.getSenha());
        cv.put(Database.tableUsuarioConfirmaSenha, usuario.getConfirmarSenha());
        cv.put(Database.tableUsuarioEmail, usuario.getEmail());
        cv.put(Database.tableUsuarioTipoUsuario, usuario.getTipoUsuario());

        open();
        data.insert(Database.tableUsuario,null,cv);
        close();

    }

    public List<Usuario> findAll() {
        String sql = "SELECT * FROM "+Database.tableUsuario+";";
        open();
        Cursor cursor = data.rawQuery(sql,null);

        List<Usuario> usuarios = new ArrayList<>();
        while(cursor.moveToNext()){
            Usuario usuario = new Usuario();
            usuario.setId(cursor.getInt(cursor.getColumnIndex(Database.tableUsuarioId)));
            usuario.setNome(cursor.getString(cursor.getColumnIndex(Database.tableUsuarioNome)));
            usuario.setEmail(cursor.getString(cursor.getColumnIndex(Database.tableUsuarioEmail)));
            usuario.setCpf(cursor.getString(cursor.getColumnIndex(Database.tableUsuarioCPF)));
            usuario.setLogin(cursor.getString(cursor.getColumnIndex(Database.tableUsuarioLogin)));
            usuario.setSenha(cursor.getString(cursor.getColumnIndex(Database.tableUsuarioSenha)));
            usuario.setConfirmarSenha(cursor.getString(cursor.getColumnIndex(Database.tableUsuarioConfirmaSenha)));
            usuario.setTipoUsuario(cursor.getString(cursor.getColumnIndex(Database.tableUsuarioTipoUsuario)));

            usuarios.add(usuario);
        }
        close();
        return usuarios;
    }

    public Usuario validaLogin(String login, String senha){
        String sql = "SELECT * FROM " + Database.tableUsuario +
                " WHERE "+Database.tableUsuarioLogin+" = '"+login+"' " +
                " and "+Database.tableUsuarioSenha+" = '"+senha+"'";

        open();
        Cursor cursor = data.rawQuery(sql, null);
        cursor.moveToFirst();
        Usuario usuario = null;
        if(cursor.moveToFirst()){
            usuario = new Usuario();
            usuario.setId(cursor.getInt(cursor.getColumnIndex(Database.tableUsuarioId)));
            usuario.setNome(cursor.getString(cursor.getColumnIndex(Database.tableUsuarioNome)));
            usuario.setSenha(cursor.getString(cursor.getColumnIndex(Database.tableUsuarioSenha)));
            usuario.setConfirmarSenha(cursor.getString(cursor.getColumnIndex(Database.tableUsuarioConfirmaSenha)));
            usuario.setLogin(cursor.getString(cursor.getColumnIndex(Database.tableUsuarioLogin)));
            usuario.setEmail(cursor.getString(cursor.getColumnIndex(Database.tableUsuarioEmail)));
            usuario.setCpf(cursor.getString(cursor.getColumnIndex(Database.tableUsuarioCPF)));
            usuario.setTipoUsuario(cursor.getString(cursor.getColumnIndex(Database.tableUsuarioTipoUsuario)));
        }
        close();
        return usuario;
    }
}
