package scriptbot.calc_area;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

public class menu_orcamento extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu_orcamento);

        // declara o Botão click
        View v = findViewById(R.id.bt_Pintura);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Navegar telas
                Intent nav2 = new Intent(view.getContext(), MainActivity.class);
                startActivity(nav2);
            }
        });

        ImageButton btnVoltarOrcamento = findViewById(R.id.voltar_menu2);
        btnVoltarOrcamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Voltar para a atividade anterior
            }
        });
    }
}