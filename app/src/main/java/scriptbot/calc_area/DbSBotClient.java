package scriptbot.calc_area;

import android.database.Cursor;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;




public class DbSBotClient extends SQLiteOpenHelper {

    public static final String databaseName = "Signup.db";

    public DbSBotClient(@Nullable Context context) {
        super(context, "Signup.db", null, 1);

    }

    // Criar as Tabelas da Base de Dados
    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        MyDatabase.execSQL("CREATE TABLE IF NOT EXISTS usuarios(coduser INTEGER PRIMARY KEY ASC, usuario TEXT, passwd TEXT, nome TEXT, email TEXT, whats TEXT, endereco TEXT)");
        //MyDatabase.execSQL("CREATE TABLE IF NOT EXISTS clientes(codcli INTEGER PRIMARY KEY ASC, coduser INTEGER, nome TEXT, email TEXT, whats TEXT, endereco TEXT)");
        // MyDatabase.execSQL("CREATE TABLE IF NOT EXISTS sessao(codsessao INTEGER PRIMARY KEY ASC, coduser INTEGER, codcli INTEGER, nome TEXT, whats TEXT, endereco TEXT)");


    }
    // Atualização de versão do banco de dados
    @Override
    public void onUpgrade(SQLiteDatabase MyDatabase, int i, int i1) {
        MyDatabase.execSQL("DROP TABLE IF EXISTS usuarios");
        /* MyDatabase.execSQL("DROP TABLE IF EXISTS clientes");
           MyDatabase.execSQL("DROP TABLE IF EXISTS sessao");
         */
        onCreate(MyDatabase);
    }


    // Inicio Tabela Usuário
    // Inserir usuario e senha na base, com verificação (True e False)
    public Boolean insertData(String usuario, String passwd, String nome, String email, String whats, String endereco){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("usuario", usuario);
        contentValues.put("passwd", passwd);
        contentValues.put("nome", nome);
        contentValues.put("email", email);
        contentValues.put("whats", whats);
        contentValues.put("endereco", endereco);

        long result = MyDatabase.insert("usuarios", null, contentValues);

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    // Verificar se o usuario existe ou nao na base
    public Boolean checkUsuario(String usuario){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM usuarios WHERE usuario = ?", new String[]{usuario});

        if(cursor.getCount() > 0) {
            return true;
        }else{
            return false;
        }
    }

    // Validar Usuario e Senha
    @SuppressLint("Range")
    public Boolean checkPasswd(String usuario, String passwd, Context context) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT coduser, nome, whats, endereco FROM usuarios WHERE coduser = ?", new String[]{usuario});

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int coduser = cursor.getInt(cursor.getColumnIndex("coduser"));
            String nome = cursor.getString(cursor.getColumnIndex("nome"));
            String whats = cursor.getString(cursor.getColumnIndex("whats"));
            String endereco = cursor.getString(cursor.getColumnIndex("endereco"));

            // Faça o que você precisa com os dados do usuário recuperados
            // Por exemplo, exiba uma mensagem com os detalhes do usuário
            String mensagem = "Bem-vindo, " + nome + "! Seu código de usuário é: " + coduser +
                    "\nWhats: " + whats +
                    "\nEndereço: " + endereco;
            Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public Cursor getUserData(String usuario, String passwd) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM usuarios WHERE usuario = ? AND passwd = ?", new String[]{usuario, passwd});
        return cursor;
    }

}