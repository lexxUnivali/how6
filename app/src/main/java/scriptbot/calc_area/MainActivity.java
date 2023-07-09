package scriptbot.calc_area;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements TextWatcher {

    /* Componente Spinner */
    Spinner sistemas;

    private SharedPreferences sharedPreferences;

    private EditText larg_input;
    private EditText comp_input;
    private TextView resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Recuperar as referências das views
        larg_input = findViewById(R.id.larg_input);
        comp_input = findViewById(R.id.comp_input);
        resultado = findViewById(R.id.resultado);

        // Adicionar os listeners de alteração de texto
        larg_input.addTextChangedListener(this);
        comp_input.addTextChangedListener(this);

        // Spinner Cores Base
        sistemas = (Spinner) findViewById(R.id.cbCores);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.Cores, android.R.layout.simple_spinner_item);
        sistemas.setAdapter(adapter);

        View v = findViewById(R.id.btEnviar);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Capturando texto no edittext (recebendo string)
                String valorCampoA = ((EditText) findViewById(R.id.larg_input)).getText().toString();
                String valorCampoB = ((EditText) findViewById(R.id.comp_input)).getText().toString();

                /* entrada de dados - Cliente*/

                // Tratar campos em branco ou vazios

                if (TextUtils.isEmpty(valorCampoA)) {
                    Toast.makeText(getApplicationContext(), "Campo LARGURA, \nesta vazio!", Toast.LENGTH_SHORT).show();
                    return; // Retorna imediatamente se o campo estiver vazio
                }

                if (TextUtils.isEmpty(valorCampoB)) {
                    Toast.makeText(getApplicationContext(), "Campo ALTURA, \nesta vazio!.", Toast.LENGTH_SHORT).show();
                    return; // Retorna imediatamente se o campo estiver vazio
                }

                // Processamento de dados (texto para inteiro)
                int valorA = Integer.parseInt(valorCampoA);
                int valorB = Integer.parseInt(valorCampoB);

                if (valorA <= 0) {
                    Toast.makeText(getApplicationContext(), "LARGURA \nValor inválido (0).", Toast.LENGTH_SHORT).show();
                    return; // Retorna o campo for menor ou igual a 0
                }

                if (valorB <= 0) {
                    Toast.makeText(getApplicationContext(), "ALTURA\nValor inválido (0).", Toast.LENGTH_SHORT).show();
                    return; // Retorna o campo for menor ou igual a 0
                }

                // Calcula a Área
                int valorArea = valorA * valorB;

                // Converter Area Int em String
                String resultadoCalculo = String.valueOf(valorArea);

                // **********************************************

                // Obter valores do Spinner (cbCores)
                Spinner cbCores = findViewById(R.id.cbCores);
                String corSelecionada = cbCores.getSelectedItem().toString();

                // **********************************************

                // Verifica se o CheckBox chkInterno está marcado
                CheckBox chkInterno = findViewById(R.id.chkInterno);
                boolean isInternoChecked = chkInterno.isChecked();

                // Verifica se o CheckBox chkExterno está marcado
                CheckBox chkExterno = findViewById(R.id.chkExterno);
                boolean isExternoChecked = chkExterno.isChecked();

                // Verificar seleção dos CheckBoxes
                String ambiente = "";

                if (isInternoChecked && isExternoChecked) {
                    ambiente = "INTERNO e EXTERNO";
                } else if (isInternoChecked) {
                    ambiente = "Interno";
                } else if (isExternoChecked) {
                    ambiente = "Externo";
                }

                // Obter valor do EditText (txtObs)
                EditText txtObs = findViewById(R.id.txtObs);
                String observacoes = txtObs.getText().toString();

                // Concatenar todos os dados em uma única string
                String os_servico = "\n- COR BASE: " + corSelecionada +
                        "\n- AMBIENTE: " + ambiente +
                        "\n- OBS.: " + observacoes +
                        "\n- LARGURA: " + valorCampoA +
                        "\n- ALTURA: " + valorCampoB +
                        "\n- ÁREA²: " + resultadoCalculo;

                // **********************************************

                // Monta a String dados do cliente >> SplashLogin
                SharedPreferences sharedPreferences = getSharedPreferences("mensagem_data", Context.MODE_PRIVATE);
                String dados_cliente = sharedPreferences.getString("mensagem", "");

                int codUsuario = sharedPreferences.getInt("codUsuario", 0);


                // Menssagens concatenada
                String mensagemCompleta = dados_cliente + "\n\n.: Meu Orçamento :. " + os_servico;


                // Resultado Area² Dinamico
                TextView tv = (TextView) findViewById(R.id.resultado);
                String valorAreaText = String.valueOf(valorArea);
                tv.setText(valorAreaText);

                // whatsapp
                String phoneNumber = "5547984733549";
                //String message = "Olá! Estou entrando em contato pelo WhatsApp.";
                String url = "https://wa.me//" + phoneNumber + "?text=" + Uri.encode(mensagemCompleta);
                Intent webIntent = new Intent(Intent.ACTION_VIEW);
                webIntent.setData(Uri.parse(url));

                Intent menuIntent = new Intent(view.getContext(), MENU.class);

                Intent chooserIntent = Intent.createChooser(webIntent, "Abrir com");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {menuIntent});

                //meu LOG
                Log.i("calcu lo_area", "O botão foi Clicado. TOTAL: " + valorAreaText);

                startActivity(chooserIntent);
                finish();

            }
        });

        // Monta a String dados do cliente >> SplashLogin
        SharedPreferences sharedPreferences = getSharedPreferences("mensagem_data", Context.MODE_PRIVATE);
        String dados_cliente = sharedPreferences.getString("mensagem", "");

        int codUsuario = sharedPreferences.getInt("codUsuario", 0);

        // Menssagens concatenadas
        String mensagemCompleta = dados_cliente;

        // Obter o valor do codUsuario da mensagemCompleta
        String codUsuarioTexto = "";
        if (mensagemCompleta.contains("")) {
            int startIndex = mensagemCompleta.indexOf("") + 18;
            int endIndex = mensagemCompleta.indexOf("\n", startIndex);
            if (startIndex != -1 && endIndex != -1) {
                codUsuarioTexto = mensagemCompleta.substring(startIndex, endIndex).trim();
            }
        }

        // Recuperar o TextView cod_cli
        TextView codCliTextView = findViewById(R.id.cod_cli);

        // Definir o valor do codUsuario no TextView cod_cli
        codCliTextView.setText(codUsuarioTexto);

        //*************************************
        // Mostra na tela a cor Escolhida.

        AdapterView.OnItemSelectedListener escolha = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /* Cor Base*/
                String item = sistemas.getSelectedItem().toString();
                Toast.makeText( getApplicationContext(), "Cor Escolhida: "+item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        sistemas.setOnItemSelectedListener(escolha);

        ImageButton btnVoltarOrcamento = findViewById(R.id.voltar_menu);
        btnVoltarOrcamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Voltar para a atividade anterior
            }
        });
    }


    // Método para calcular o resultado e atualizar o TextView "resultado"
    private void calcularResultado() {
        String larguraStr = larg_input.getText().toString().trim();
        String comprimentoStr = comp_input.getText().toString().trim();

        if (!TextUtils.isEmpty(larguraStr) && !TextUtils.isEmpty(comprimentoStr)) {
            int valorA = Integer.parseInt(larguraStr);
            int valorB = Integer.parseInt(comprimentoStr);
            int resultadoCalculo = valorA * valorB;
            resultado.setText(String.valueOf(resultadoCalculo));
        }
    }

    // Métodos da interface TextWatcher
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        calcularResultado();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}