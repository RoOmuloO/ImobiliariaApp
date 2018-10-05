package com.example.romuloroger.imobiliariaapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.jetbrains.annotations.NonNls;

public class Database extends SQLiteOpenHelper{

    static final String databaseName = "imobiliaria.db";
    static final int databaseVersion = 5;

    public static final String tableImovel = "tableImovel";
    public static final String tableImovelId = "id";
    public static final String tableImovelNome = "nome";
    public static final String tableImovelQtdeQuartos = "qtdeQuartos";
    public static final String tableImovelValor = "valor";
    public static final String tableImovelTipoImovel = "tipoImovel";
    public static final String tableImovelIsVendido = "isVendido";
    public static final String tableImovelFotos = "fotos";
    public static final String tableImovelDescicao = "descricao";
    public static final String tableImovelBairro = "bairro";
    static final String createTableImovel = "CREATE TABLE " + tableImovel + " (" +
            " " + tableImovelId + " integer primary key autoincrement, " +
            " " + tableImovelNome + " text not null, " +
            " " + tableImovelQtdeQuartos + " int not null, " +
            " " + tableImovelValor + " real not null, " +
            " " + tableImovelTipoImovel + " text not null," +
            " " + tableImovelIsVendido + " int not null," +
            " " + tableImovelFotos + " text, " +
            " " + tableImovelDescicao + " text, " +
            " " + tableImovelBairro + " text not null " +
            ")";

    public static final String tableUsuario = "tableUsuario";
    public static final String tableUsuarioId = "id";
    public static final String tableUsuarioNome = "nome";
    public static final String tableUsuarioCPF = "cpf";
    public static final String tableUsuarioLogin = "login";
    public static final String tableUsuarioSenha = "senha";
    public static final String tableUsuarioConfirmaSenha = "confirmaSenha";
    public static final String tableUsuarioEmail = "email";
    public static final String tableUsuarioTipoUsuario = "tipoUsuario";
    static final String createTableUsuario = "CREATE TABLE " + tableUsuario + " (" +
            " " + tableUsuarioId + " integer primary key autoincrement, " +
            " " + tableUsuarioNome + " text not null, " +
            " " + tableUsuarioCPF + " text not null, " +
            " " + tableUsuarioLogin + " text not null, " +
            " " + tableUsuarioSenha + " text not null, " +
            " " + tableUsuarioConfirmaSenha + " text not null, " +
            " " + tableUsuarioEmail + " text not null, " +
            " " + tableUsuarioTipoUsuario + " text not null " +
            ")";


    public static final String tableTaxaJuros = "tableTaxaJuros";
    @NonNls
    public static final String tableTaxaJurosId = "id";
    @NonNls
    public static final String tableTaxaJurosValorInicial = "valorInicial";
    @NonNls
    public static final String tableTaxaJurosValorFinal = "valorFinal";
    @NonNls
    public static final String tableTaxaJurosTaxaJuros = "taxaJuros";
    static final String createTableTaxaJuros = "CREATE TABLE " + tableTaxaJuros + " (" +
            " " + tableTaxaJurosId + " integer primary key autoincrement, " +
            " " + tableTaxaJurosValorInicial + " real not null, " +
            " " + tableTaxaJurosValorFinal + " real not null, " +
            " " + tableTaxaJurosTaxaJuros + " real not null" +
            ")";


    @NonNls
    public static final String tableCliente = "tableCliente";
    @NonNls
    public static final String tableClienteId = "id";
    @NonNls
    public static final String tableClienteNome = "nome";
    @NonNls
    public static final String tableClienteEmail = "email";
    @NonNls
    public static final String tableClienteTelefone = "telefone";
    @NonNls
    public static final String tableClienteValorEntrada = "valorEntrada";
    static final String createTableCliente = "CREATE TABLE " + tableCliente + " (" +
            " " + tableClienteId + " integer not null, " +
            " " + tableClienteNome + " text not null, " +
            " " + tableClienteEmail + " text not null, " +
            " " + tableClienteTelefone + " text not null, " +
            " " + tableClienteValorEntrada + " real not null" +
            " )";


    @NonNls
    public static final String tableFinanciamento = "tableFinanciamento";
    @NonNls
    public static final String tableFinanciamentoId = "id";
    @NonNls
    public static final String tableFinanciamentoValorFinanciado = "valorFinanciado";
    @NonNls
    public static final String tableFinanciamentoValorTotal = "valorTotal";
    @NonNls
    public static final String tableFinanciamentoQtdeParcelas = "qtdeParcelas";
    static final String createTableFinanciamento = "CREATE TABLE " + tableFinanciamento + " (" +
            " " + tableFinanciamentoId + " integer not null, " +
            " " + tableFinanciamentoValorFinanciado + " real not null, " +
            " " + tableFinanciamentoValorTotal + " real not null, " +
            " " + tableFinanciamentoQtdeParcelas + " integet not null" +
            " )";

    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *
     * @param context to use for locating paths to the the database
     *                {@link #onUpgrade} will be used to upgrade the database; if the database is
     *                newer, {@link #onDowngrade} will be used to downgrade the database
     */
    public Database(Context context) {super(context, databaseName, null, databaseVersion);}

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param sqLiteDatabase The database.
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createTableImovel);
        sqLiteDatabase.execSQL(createTableUsuario);
        sqLiteDatabase.execSQL(createTableTaxaJuros);
        sqLiteDatabase.execSQL(createTableCliente);
        sqLiteDatabase.execSQL(createTableFinanciamento);
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param sqLiteDatabase         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tableImovel);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tableUsuario);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tableTaxaJuros);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tableCliente);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tableFinanciamento);

        onCreate(sqLiteDatabase);
    }
}
