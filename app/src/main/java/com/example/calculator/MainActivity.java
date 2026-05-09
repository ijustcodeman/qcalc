package com.example.calculator;

import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    // buttons for the numbers
    Button zero;
    Button one;
    Button two;
    Button three;
    Button four;
    Button five;
    Button six;
    Button seven;
    Button eight;
    Button nine;

    // buttons for the operations
    Button plus;
    Button minus;
    Button multiply;
    Button division;

    // buttons for advanced operations
    Button sin;
    Button cos;
    Button tan;
    Button sqrt;

    // delete buttons
    Button clear;
    Button clearAll;

    // memory buttons
    Button memoryRead;
    Button memorySave;

    // other buttons
    Button equal;
    Button pm;
    Button advanced;

    Button leftBracket;
    Button rightBracket;

    // display field
    TextView display;

    // flag to check if an operator was placed
    boolean operator;

    // flag to check if an error happened
    boolean error;

    // flag to check if the equals button has been pressed
    boolean equals;

    // flag to check, if the advanced functions had been triggered
    boolean isAdvancedVisible;

    private String input;

    private ArrayList<String> memoryList;

    // length of the input appended advanced operations for string manipulation
    private int sintanLength = 4;
    private int cosLength = 4;
    private int sqrtLength = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // force dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initializeUI();
        initializeVariables();
    }


    private void initializeUI(){
        zero = findViewById(R.id.zero);
        zero.setOnClickListener(numberListener);
        one = findViewById(R.id.one);
        one.setOnClickListener(numberListener);
        two = findViewById(R.id.two);
        two.setOnClickListener(numberListener);
        three = findViewById(R.id.three);
        three.setOnClickListener(numberListener);
        four = findViewById(R.id.four);
        four.setOnClickListener(numberListener);
        five = findViewById(R.id.five);
        five.setOnClickListener(numberListener);
        six = findViewById(R.id.six);
        six.setOnClickListener(numberListener);
        seven = findViewById(R.id.seven);
        seven.setOnClickListener(numberListener);
        eight = findViewById(R.id.eight);
        eight.setOnClickListener(numberListener);
        nine = findViewById(R.id.nine);
        nine.setOnClickListener(numberListener);

        plus = findViewById(R.id.plus);
        plus.setOnClickListener(operatorListener);
        minus = findViewById(R.id.minus);
        minus.setOnClickListener(operatorListener);
        multiply = findViewById(R.id.multiply);
        multiply.setOnClickListener(operatorListener);
        division = findViewById(R.id.division);
        division.setOnClickListener(operatorListener);

        sin = findViewById(R.id.sin);
        sin.setOnClickListener(advancedOperationsListener);
        cos = findViewById(R.id.cos);
        cos.setOnClickListener(advancedOperationsListener);
        tan = findViewById(R.id.tan);
        tan.setOnClickListener(advancedOperationsListener);
        sqrt = findViewById(R.id.sqrt);
        sqrt.setOnClickListener(advancedOperationsListener);

        clear = findViewById(R.id.ce);
        clear.setOnClickListener(deleteListener);
        clearAll = findViewById(R.id.c);
        clearAll.setOnClickListener(deleteListener);

        memoryRead = findViewById(R.id.mr);
        memoryRead.setOnClickListener(memoryShortListener);
        memoryRead.setOnLongClickListener(memoryLongListener);
        memorySave = findViewById(R.id.ms);
        memorySave.setOnClickListener(memoryShortListener);
        memorySave.setOnLongClickListener(memoryLongListener);

        equal = findViewById(R.id.equal);
        equal.setOnClickListener(equalsListener);
        pm = findViewById(R.id.pm);
        pm.setOnClickListener(pmListener);
        advanced = findViewById(R.id.advanced);
        if (advanced != null) {
            advanced.setOnClickListener(advancedListener);
        }
        leftBracket = findViewById(R.id.leftBracket);
        leftBracket.setOnClickListener(bracketListener);
        rightBracket = findViewById(R.id.rightBracket);
        rightBracket.setOnClickListener(bracketListener);


        display = findViewById(R.id.display);
    }

    private void initializeVariables(){
        input = "";
        operator = true;
        error = false;
        equals = false;
        isAdvancedVisible = false;
        memoryList = new ArrayList<>();
        memoryList.add(0, "0");
        memoryList.add(1, "0");
    }

    private View.OnClickListener numberListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();

            applyClickAnimation(v);

            if (error){
                error = false;
            }

            if (equals){
                input = "";
                findButton(id);
                formattedDisplay();
                equals = false;
                return;
            }

            findButton(id);
            formattedDisplay();
        }
    };

    private void applyClickAnimation(View v) {
        v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        v.animate().cancel();
        v.setScaleX(1f);
        v.setScaleY(1f);

        v.animate()
                .scaleX(0.9f)
                .scaleY(0.9f)
                .setDuration(100)
                .withEndAction(() -> v.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(100)
                );
    }

    private void findButton(int id){
        if (id == R.id.zero){
            input += "0";
            operator = false;
        }
        if (id == R.id.one){
            input += "1";
            operator = false;
        }
        if (id == R.id.two){
            input += "2";
            operator = false;
        }
        if (id == R.id.three){
            input += "3";
            operator = false;
        }
        if (id == R.id.four){
            input += "4";
            operator = false;
        }
        if (id == R.id.five){
            input += "5";
            operator = false;
        }
        if (id == R.id.six){
            input += "6";
            operator = false;
        }
        if (id == R.id.seven){
            input += "7";
            operator = false;
        }
        if (id == R.id.eight){
            input += "8";
            operator = false;
        }
        if (id == R.id.nine){
            input += "9";
            operator = false;
        }
    }

    private void findAdvancedButton(int id){
        if (id == R.id.sin){
            input += "sin(";
            operator = false;
        }
        if (id == R.id.cos){
            input += "cos(";
            operator = false;
        }
        if (id == R.id.tan){
            input += "tan(";
            operator = false;
        }
        if (id == R.id.sqrt){
            input += "sqrt(";
            operator = false;
        }
    }

    private View.OnClickListener operatorListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();

            applyClickAnimation(v);

            if (error){
                operator = true;
            }

            if (id == R.id.plus && !operator && !input.isEmpty()){
                input += "+";
                equals = false;
                operator = true;
            }

            if (id == R.id.minus && !operator && !input.isEmpty()){
                input += "-";
                equals = false;
                operator = true;
            }

            if (id == R.id.multiply && !operator && !input.isEmpty()){
                input += "*";
                equals = false;
                operator = true;
            }

            if (id == R.id.division && !operator && !input.isEmpty()){
                input += "/";
                equals = false;
                operator = true;
            }

            formattedDisplay();
        }
    };

    private View.OnClickListener equalsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            applyClickAnimation(v);

            if (!isEvenBrackets()){
                return;
            }
            if (!operator && !input.isEmpty()) {
                try{
                    input = formatResult(calculateResult(input));
                    input = normalizeInput(input);
                    display.setText(input);
                    equals = true;
                } catch (Exception e){
                    display.setText("ERROR");
                    input = "";
                    error = true;
                }
            }
        }
    };

    private View.OnClickListener deleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();

            applyClickAnimation(v);

            if (id == R.id.c){
                input = "";
                display.setText(input);
            }

            if (id == R.id.ce && !input.isEmpty() && !error){
                equals = false;

                if (input.charAt(input.length() - 1) == '(' && isAdvancedSymbol()){
                    deleteAdvancedSymbol(input.charAt(input.length() - 2));
                    formattedDisplay();
                    return;
                }

                input = input.substring(0, input.length() - 1);

                if (!input.isEmpty()) {
                    char last = input.charAt(input.length() - 1);
                    operator = isOperator(last);
                } else {
                    operator = true;
                }

                input = normalizeInput(input);
                formattedDisplay();
            }
        }
    };

    private View.OnClickListener memoryShortListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();

            applyClickAnimation(v);

            if (id == R.id.mr){
                if (isMemoryReady()){
                    input = memoryList.get(0);
                }
                else if (isOperator(input.charAt(input.length() - 1))){
                    input += memoryList.get(0);
                }
                else{
                    int lastOpIndex = Math.max(
                            Math.max(input.lastIndexOf('+'), input.lastIndexOf('-')),
                            Math.max(input.lastIndexOf('*'), input.lastIndexOf('/'))
                    );
                    input = input.substring(0, lastOpIndex + 1) + memoryList.get(0);
                }
                operator = false;
                formattedDisplay();
            }

            if (id == R.id.ms){
                if (isMemoryReady()){
                    memoryList.set(0, input);
                }
            }
        }
    };

    private View.OnLongClickListener memoryLongListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            int id = v.getId();

            applyClickAnimation(v);

            if (id == R.id.mr){
                if (isMemoryReady()){
                    input = memoryList.get(1);
                }
                else if (isOperator(input.charAt(input.length() - 1))){
                    input += memoryList.get(1);
                }
                else{
                    int lastOpIndex = Math.max(
                            Math.max(input.lastIndexOf('+'), input.lastIndexOf('-')),
                            Math.max(input.lastIndexOf('*'), input.lastIndexOf('/'))
                    );
                    input = input.substring(0, lastOpIndex + 1) + memoryList.get(1);
                }
                operator = false;
                formattedDisplay();
            }

            if (id == R.id.ms){
                if (isMemoryReady()){
                    memoryList.set(1, input);
                }
            }

            return true;
        }
    };

    private View.OnClickListener advancedListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            applyClickAnimation(v);

            if (isAdvancedVisible) {
                isAdvancedVisible = false;

                sin.setVisibility(View.GONE);
                cos.setVisibility(View.GONE);
                tan.setVisibility(View.GONE);
                sqrt.setVisibility(View.GONE);

                advanced.setText("ADVANCED");

            } else {
                isAdvancedVisible = true;

                sin.setVisibility(View.VISIBLE);
                cos.setVisibility(View.VISIBLE);
                tan.setVisibility(View.VISIBLE);
                sqrt.setVisibility(View.VISIBLE);

                advanced.setText("BASIC");
            }
        }
    };

    private View.OnClickListener advancedOperationsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();

            applyClickAnimation(v);

            if (error){
                error = false;
            }

            if (equals){
                input = "";
                findAdvancedButton(id);
                formattedDisplay();
                equals = false;
                return;
            }

            findAdvancedButton(id);
            formattedDisplay();
        }
    };

    private View.OnClickListener bracketListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();

            applyClickAnimation(v);

            if (id == R.id.leftBracket){
                input += "(";
            }

            if (id == R.id.rightBracket){
                input += ")";
            }
            formattedDisplay();
        }
    };

    private View.OnClickListener pmListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            applyClickAnimation(v);

            if (input.isEmpty() || !hasOperator()){
                input = "(-" + input;
                formattedDisplay();
                return;
            }

            for (int i = input.length(); i > 0; i--){
                if (isOperator(input.charAt(i - 1))){
                    if (input.charAt(i - 1) == '-'){
                        StringBuilder sb = new StringBuilder(input);
                        sb.setCharAt(i - 1, '+');
                        input = sb.toString();
                    }
                    else{
                        StringBuilder sb = new StringBuilder();
                        String temp1 = input.substring(0, i);
                        String temp2 = input.substring(i);
                        input = sb.append(temp1).append("(-").append(temp2).toString();
                    }
                    formattedDisplay();
                    return;
                }
            }
        }
    };


    private boolean isOperator(char c){
        return c == '+' || c == '-' || c == '/' || c == '*';
    }

    private boolean isNotNumber(char c){
        return !(c >= '0' && c <= '9');
    }

    private double calculateResult(String input) throws Exception {
        try {
            Expression e = new ExpressionBuilder(input).build();
            return e.evaluate();
        } catch (ArithmeticException | IllegalArgumentException ex) {
            throw new Exception("Error evaluating expression: " + ex.getMessage(), ex);
        }
    }

    private void formattedDisplay(){
        String str = input
                .replace("+", " + ")
                .replace("-", " - ")
                .replace("*", " * ")
                .replace("/", " / ");
        display.setText(str);
    }

    private String normalizeInput(String input) {
        if (input.isEmpty()) return input;

        int lastOpIndex = Math.max(
                Math.max(input.lastIndexOf('+'), input.lastIndexOf('-')),
                Math.max(input.lastIndexOf('*'), input.lastIndexOf('/'))
        );

        String prefix = "";
        String number = input;

        if (lastOpIndex != -1) {
            prefix = input.substring(0, lastOpIndex + 1);
            number = input.substring(lastOpIndex + 1);
        }

        if (number.endsWith(".")) {
            number = number.substring(0, number.length() - 1);
        }

        if (number.contains(".")) {
            while (number.endsWith("0")) {
                number = number.substring(0, number.length() - 1);
            }

            if (number.endsWith(".")) {
                number = number.substring(0, number.length() - 1);
            }
        }

        return prefix + number;
    }

    private boolean isMemoryReady(){
        int counter = 0;
        char temp = '\0';
        for (int i = 0; i < input.length(); i++){
            if (isNotNumber(input.charAt(i))){
                if (counter < 1){
                    temp = input.charAt(i);
                }
                counter++;
            }
        }
        return counter == 0 || (counter == 1 && input.charAt(0) == temp);
    }

    private boolean hasOperator(){
        return input.contains("+") || input.contains("-") || input.contains("*") || input.contains("/");
    }

    private String formatResult(double value) {
        return java.math.BigDecimal.valueOf(value)
                .stripTrailingZeros()
                .toPlainString();
    }

    private boolean isAdvancedSymbol(){
        if (input.length() < 2){
            return false;
        }
        char c = input.charAt(input.length() - 2);
        return c == 't' || c == 'n' || c == 's';
    }

    private void deleteAdvancedSymbol(char c){
        switch (c){
            case 't':
                input = input.substring(0, input.length() - sqrtLength);
                return;
            case 'n':
                input = input.substring(0, input.length() - sintanLength);
                return;
            case 's':
                input = input.substring(0, input.length() - cosLength);
                return;
            default:
                System.out.println("Character doesnt match any advanved one.");
        }
    }

    private boolean isEvenBrackets(){
        int leftBrackets = 0;
        int rightBrackets = 0;
        for (int i = 0; i < input.length(); i++){
            if (input.charAt(i) == '('){
                leftBrackets += 1;
            }
            if (input.charAt(i) == ')'){
                rightBrackets += 1;
            }
        }
        return leftBrackets == rightBrackets;
    }
}
