package scriptbot.calc_area;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MENU extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // declara o Botão click
        View v = findViewById(R.id.bt_orcamento);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navegar telas
                Intent nav1 = new Intent(view.getContext(), menu_orcamento.class);
                startActivity(nav1);
            }
        });

        ImageButton btnVoltarOrcamento = findViewById(R.id.voltar_login);
        btnVoltarOrcamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Voltar para a atividade anterior
            }
        });

   }
}