package scriptbot.calc_area;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.content.Context;
import scriptbot.calc_area.databinding.ActivitySplashLoginBinding;


public class SplashLogin extends AppCompatActivity {
    ActivitySplashLoginBinding binding;
    DbSBotClient dbSBotClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbSBotClient = new DbSBotClient(this);

        binding.btLogin.setOnClickListener(this::onClick);
        // Redirecionamento para CADASTRO DO CLIENTE / CADASTRO DE CONTA
        binding.redirectcriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashLogin.this, cadastroCliente.class);
                startActivity(intent);
            }
        });

        getSupportActionBar();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Link na Logo para a página Scriptbot.com.br
        binding.logoScriptbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.scriptbot.com.br";

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }
    @SuppressLint("Range")
    private void onClick(View view) {
        String usuario = binding.inputUser.getText().toString();
        String passwd = binding.inputSenha.getText().toString();

        Log.d("SplashLogin", "Usuario: " + usuario + ", Senha: " + passwd);

        if (usuario.equals("") || passwd.equals("")) {
            Toast.makeText(SplashLogin.this, "Campos Obrigatórios!", Toast.LENGTH_SHORT).show();
        } else {
            Cursor cursor = dbSBotClient.getUserData(usuario, passwd);

            if (cursor != null && cursor.moveToFirst()) {
                String nomeUsuario = cursor.getString(cursor.getColumnIndex("usuario"));
                int codUsuario = cursor.getInt(cursor.getColumnIndex("coduser"));
                String whatsUsuario = cursor.getString(cursor.getColumnIndex("whats"));
                String emailUsuario = cursor.getString(cursor.getColumnIndex("email"));
                String enderecoUsuario = cursor.getString(cursor.getColumnIndex("endereco"));

                // Construa a string com os dados do usuário

                StringBuilder mensagemBuilder = new StringBuilder();
                mensagemBuilder.append("Código de Cliente: ").append(codUsuario)
                        .append("\n- Nome: ").append(nomeUsuario)
                        .append("\n- Whats: ").append(whatsUsuario)
                        .append("\n- Email: ").append(emailUsuario)
                        .append("\n- Endereço: ").append(enderecoUsuario);

                String dadoscli = mensagemBuilder.toString();

                SharedPreferences sharedPreferences = getSharedPreferences("mensagem_data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("mensagem", dadoscli);
                editor.apply();


                // Navegar para MENU e envia String com dados do usuario para MainActivity

                Intent intent = new Intent(SplashLogin.this, MENU.class);
                intent.putExtra("mensagem", dadoscli);
                intent.putExtra("nomeUsuario", nomeUsuario);
                intent.putExtra("codUsuario", codUsuario);
                startActivity(intent);

            } else {
                Toast.makeText(SplashLogin.this, "Acesso Inválido!", Toast.LENGTH_SHORT).show();
            }

            if (cursor != null) {
                cursor.close();
            }
        }
    }

}
