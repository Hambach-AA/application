package com.example.myclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.myclient.Models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;

    Button btnSignIn, btnReg;
    RelativeLayout root;
// test
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRegisterWindow();
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignInWindow();
            }
        });
    }

    private void init(){
        btnSignIn = findViewById(R.id.btnSignIn);
        btnReg = findViewById(R.id.btnRegister);
        root = findViewById(R.id.root_element);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Clients");
    }

    private void showSignInWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Войти в систему");
        dialog.setMessage("Введите данные для авторизации");

        LayoutInflater inflater = LayoutInflater.from(this);
        View activity_signin = inflater.inflate(R.layout.activity_signin, null);
        dialog.setView(activity_signin);

        final MaterialEditText email = activity_signin.findViewById(R.id.emailField);
        final MaterialEditText pass = activity_signin.findViewById(R.id.passField);

        dialog.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialog.setPositiveButton("Войти", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (TextUtils.isEmpty(email.getText().toString())) {
                    Snackbar.make(root, "Введите E-mail", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (pass.getText().toString().length() < 5) {
                    Snackbar.make(root, "Ошибка пароля", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                auth.signInWithEmailAndPassword(email.getText().toString(),pass.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                startActivity(new Intent(MainActivity.this, MapActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(root, "Ошибка"+e.getMessage(),Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });
        dialog.show();
    }

    private void showRegisterWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Регистрация");
        dialog.setMessage("Введите данные для регистрации");

        LayoutInflater inflater = LayoutInflater.from(this);
        View activity_reg = inflater.inflate(R.layout.activity_reg, null);
        dialog.setView(activity_reg);

        final MaterialEditText email = activity_reg.findViewById(R.id.emailField);
        final MaterialEditText pass = activity_reg.findViewById(R.id.passField);
        final MaterialEditText name = activity_reg.findViewById(R.id.nameField);
        final MaterialEditText second_name = activity_reg.findViewById(R.id.second_nameField);
        final MaterialEditText phone = activity_reg.findViewById(R.id.phoneField);

        dialog.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialog.setPositiveButton("Зарегистрироваться", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (TextUtils.isEmpty(email.getText().toString())) {
                    Snackbar.make(root, "Укажите E-mail", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(name.getText().toString())) {
                    Snackbar.make(root, "Укажите имя", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phone.getText().toString()) && phone.getText().toString().length() == 11) {
                    Snackbar.make(root, "Укажите номер телефона", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (pass.getText().toString().length() < 5) {
                    Snackbar.make(root, "Ошибка пароля", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                //registration users
                auth.createUserWithEmailAndPassword(email.getText().toString(),pass.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                User user = new User();
                                user.setEmail(email.getText().toString());
                                user.setFirst_name(name.getText().toString());
                                user.setSecond_name(second_name.getText().toString());
                                user.setPhone(phone.getText().toString());
                                user.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                user.setFriends(new ArrayList<>());
                                //add in DataBase
                                users.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Snackbar.make(root,"Успешная регистрация",Snackbar.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        });
            }
        });
        dialog.show();
    }

}