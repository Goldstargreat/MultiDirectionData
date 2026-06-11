package kr.ac.kopo.multidirectiondata;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;

public class SecondActivity extends AppCompatActivity
{
    TextView textQuestion;
    double result;
    boolean isError = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        int    num1     = intent.getIntExtra("num1", 0);
        int    num2     = intent.getIntExtra("num2", 0);
        String operator = intent.getStringExtra("operator");

        textQuestion = findViewById(R.id.text_question);
        Button btnCalc = findViewById(R.id.btn_calc);

        // 연산자에 따라 계산
        switch (operator)
        {
            case "+":
                result = num1 + num2; break;
            case "-":
                result = num1 - num2; break;
            case "*":
                result = num1 * num2; break;
            case "/":
                if (num2 == 0)
                {
                    textQuestion.setText("0으로 나눌 수 없습니다!");
                    isError = true;
                }
                else
                {
                    result = (double) num1 / num2;
                }
                break;
        }

        if (!isError)
        {
            textQuestion.setText(num1 + " " + operator + " " + num2 + " = ?");
        }

        btnCalc.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (isError)
                {
                    // 오류 상태면 결과 반환 없이 그냥 종료
                    setResult(RESULT_CANCELED);
                    finish();
                    return;
                }
                Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                mainIntent.putExtra("result", result);
                setResult(RESULT_OK, mainIntent);
                finish();
            }
        });
    }
}