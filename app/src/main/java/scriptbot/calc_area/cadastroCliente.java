package scriptbot.calc_area;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;


import scriptbot.calc_area.databinding.ActivityCadastroClienteBinding;


public class cadastroCliente extends AppCompatActivity {

    //nome = findViewById(R.id.inputCli);
    ActivityCadastroClienteBinding binding;
    DbSBotClient dbSBotClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = ActivityCadastroClienteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbSBotClient = new DbSBotClient(this);

        binding.btcadCli.setOnClickListener(new View.OnClickListener(){

            //Cadastra Usuário no SQLite
            @Override
            public void onClick(View view) {

                //Cadastro de Cliente
                String nome = binding.inputCli.getText().toString();
                String email = binding.inputEmail.getText().toString();
                String whats = binding.inputWhats.getText().toString();
                String endereco = binding.inputEnd.getText().toString();

                //Cadastrar Usuario e Senha - No cadastro de Cliente
                String usuario = binding.inputUsuario.getText().toString();
                String passwd = binding.inputSenha.getText().toString();
                String passwd2 = binding.inputCSenha.getText().toString();

                if(usuario.equals("") || passwd.equals("") || passwd2.equals("") || nome.equals("") || email.equals("") || whats.equals("") || endereco.equals(""))
                    Toast.makeText(cadastroCliente.this, "Campos Obrigatórios", Toast.LENGTH_SHORT).show();
                else{
                    //Checagem de igualdade - password
                    if(passwd.equals(passwd2)){
                        Boolean checkUsuarioPwd = dbSBotClient.checkUsuario(usuario);

                        if (checkUsuarioPwd == false){
                            // Inserir dados
                            Boolean insert = dbSBotClient.insertData(usuario, passwd, nome, email, whats, endereco);

                            // Inserir cliente
                            //Boolean insertClienteResult = dbSBotClient.insertCliente(nome, email, whats, endereco);


                            if(insert == true){
                                Toast.makeText(cadastroCliente.this, "Cadastro Realizado com Sucesso!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), SplashLogin.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(cadastroCliente.this, "Acesso Não Autorizado!", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(cadastroCliente.this, "Usuário Já existe!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(cadastroCliente.this, "Senha Inválida!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        ImageButton btnVoltarOrcamento = findViewById(R.id.voltar_cad);
        btnVoltarOrcamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Voltar para a atividade anterior
            }
        });



        binding.loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SplashLogin.class);
                startActivity(intent);
            }
        });
}}
