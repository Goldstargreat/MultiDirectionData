package kr.ac.kopo.multidirectiondata;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.activity.EdgeToEdge;

public class MainActivity extends AppCompatActivity
{
    EditText editNum1, editNum2;
    TextView textResult;
    RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editNum1   = findViewById(R.id.edit_num1);
        editNum2   = findViewById(R.id.edit_num2);
        textResult = findViewById(R.id.text_result);
        Button btnMake = findViewById(R.id.btn_make);
        rg         = findViewById(R.id.radio_group);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(@NonNull RadioGroup group, int checkedId)
            {
                String operatorName = "계산하기";
                if (checkedId == R.id.rb_plus)
                {
                    operatorName = "더하기";
                } else if (checkedId == R.id.rb_minus)
                {
                    operatorName = "빼기";
                } else if (checkedId == R.id.rb_multiply)
                {
                    operatorName = "곱하기";
                } else if (checkedId == R.id.rb_divide)
                {
                    operatorName = "나누기";
                }
                btnMake.setText(operatorName + " 문제 만들기");
            }
        });

        btnMake.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int num1 = Integer.parseInt(editNum1.getText().toString());
                int num2 = Integer.parseInt(editNum2.getText().toString());
                editNum1.setText("");
                editNum2.setText("");

                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                intent.putExtra("num1", num1);
                intent.putExtra("num2", num2);

                int selectedId = rg.getCheckedRadioButtonId();
                String operator;

                if (selectedId == R.id.rb_plus)
                {
                    operator = "+";
                }
                else if (selectedId == R.id.rb_minus)
                {
                    operator = "-";
                }
                else if (selectedId == R.id.rb_multiply)
                {
                    operator = "*";
                }
                else
                {
                    operator = "/";
                }

                intent.putExtra("operator", operator);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null)
        {
            double result = data.getDoubleExtra("result", 0);
            if (result == (long) result)
            {
                textResult.setText("결과 : " + (long) result);
            }
            else
            {
                textResult.setText("결과 : " + result);
            }
        }
    }
}