package com.alnajim.osama.library.UI.Authentication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alnajim.osama.library.R;
import com.alnajim.osama.library.UI.MainActivity;
import com.alnajim.osama.library.Utilites.SessionManager;
import com.alnajim.osama.library.ViewModels.LibraryViewModel;

public class Signup extends AppCompatActivity {
    LibraryViewModel libraryViewModel;
    private SessionManager session;
    ProgressBar progressBar ;
    Context context ;
    Button register ,btnToMan;

    EditText fullName,userName,password,univeristyName,collageName,bio,imageUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        progressBar = findViewById(R.id.progressbar);
        register = findViewById(R.id.btnRegister);
        btnToMan = findViewById(R.id.btnToMan);
        context = this;
        libraryViewModel = ViewModelProviders.of(this).get(LibraryViewModel.class);
        session = new SessionManager(getApplicationContext());
        InitView();
        if(fullName.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

    }



        public void SignUp(View view)
        {

            register.setEnabled(false);
            final String fullNameStr = fullName.getText().toString().toLowerCase();
            String userNameStr1 = userName.getText().toString();
            final String userNameStr = userNameStr1.replace(" ", "");
            final String passwordStr = password.getText().toString();
            final String univsersityNameStr = univeristyName.getText().toString();
            final String collageNameStr = collageName.getText().toString();
            final String bioStr = bio.getText().toString();

            if (fullNameStr.equals("") || userNameStr.equals("") || passwordStr.equals("") || univsersityNameStr.equals("") ||collageNameStr.equals(""))
            {
                Toast.makeText(this, "الرجاء ملىْ كل الحقول", Toast.LENGTH_SHORT).show();
                register.setEnabled(true);

            }
            else{
                register.setEnabled(true);

                if (userNameStr.length()<5)
                {
                    Toast.makeText(context, "اسم المستخدم يجب ان لا يكون اصغر من خمس احرف", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    progressBar.setVisibility(View.VISIBLE);
                    libraryViewModel.Checkusername(userNameStr);
                    libraryViewModel.UserNameLiveData.observe(this, new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            if (s.equals("1")) {
                                Toast.makeText(Signup.this, "إسم المستخدم موجود مسبقاً!\n رجاءا أدخل أسم مستخدم أخر  ", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);

                                userName.setText("");
                            }
                            else
                            {

                                libraryViewModel.SignUp(fullNameStr,userNameStr,passwordStr,univsersityNameStr,collageNameStr,bioStr);
                                libraryViewModel.SignUpLiveData.observe((LifecycleOwner) context, new Observer<String>() {
                                    @Override
                                    public void onChanged(String s)
                                    {

                                        try
                                        {
                                            Toast.makeText(context, "XXXX", Toast.LENGTH_SHORT).show();

                                            //session.setLogin(true);
                                            Toast.makeText(Signup.this, "تم تسجيل الحساب بنجاح", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(Signup.this, Login.class);
                                            intent.putExtra("userName",userNameStr);
                                            intent.putExtra("password",passwordStr);

                                            startActivity(intent);

                                            finish();

                                        }
                                        catch (Exception e )
                                        {
                                            progressBar.setVisibility(View.GONE);

                                            Toast.makeText(Signup.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                            }
                        }
                    });
                }

            }
        }


    public void goToLogin(View view ){startActivity(new Intent(this, Login.class));}
    public void GoToMain(View view ){startActivity(new Intent(this, MainActivity.class));}





    public void InitView()
    {
        fullName = findViewById(R.id.etFullName);
        userName = findViewById(R.id.etUserName);
        password = findViewById(R.id.etPassword);
        univeristyName = findViewById(R.id.etUserUniversity);
        collageName = findViewById(R.id.etCollage);
        bio         = findViewById(R.id.etBio);
    }
}