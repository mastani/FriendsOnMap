package mobina.com.uniiii;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import mobina.com.uniiii.Utility.ApplicationController;
import mobina.com.uniiii.Utility.Utilies;

public class RegisterActivity extends AppCompatActivity {

    TextInputLayout name;
    TextInputLayout email;
    TextInputLayout mobile;
    TextInputLayout password;
    TextInputLayout confirmPassword;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (TextInputLayout) findViewById(R.id.name);
        email = (TextInputLayout) findViewById(R.id.email);
        mobile = (TextInputLayout) findViewById(R.id.mobile);
        password = (TextInputLayout) findViewById(R.id.password);
        confirmPassword = (TextInputLayout) findViewById(R.id.confirmPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });
    }

    public void Register() {
        boolean nameError = false;
        boolean emailError = false;
        boolean mobileError = false;
        boolean passwordError = false;

        if (name.getEditText().getText().length() <= 0) {
            name.setErrorEnabled(true);
            name.setError("نام خود را وارد کنید");
            nameError = true;
        } else {
            name.setErrorEnabled(false);
            nameError = false;
        }

        if (email.getEditText().getText().length() <= 0) {
            email.setErrorEnabled(true);
            email.setError("ایمیل خود را وارد کنید");
            emailError = true;
        } else if (!Utilies.isEmailValid(email.getEditText().getText())) {
            email.setErrorEnabled(true);
            email.setError("ایمیل را به صورت صحیح وارد کنید");
            emailError = true;
        } else {
            email.setErrorEnabled(false);
            emailError = false;
        }

        if (mobile.getEditText().getText().length() <= 0) {
            mobile.setErrorEnabled(true);
            mobile.setError("شماره همراه خود را وارد کنید");
            mobileError = true;
        } else if (!Utilies.isMobileValid(mobile.getEditText().getText())) {
            mobile.setErrorEnabled(true);
            mobile.setError("شماره همراه را به صورت صحیح وارد کنید: 09xxxxxxxxx");
            mobileError = true;
        } else {
            mobile.setErrorEnabled(false);
            mobileError = false;
        }

        if (password.getEditText().getText().length() == 0 || !password.getEditText().getText().toString().equals(confirmPassword.getEditText().getText().toString())) {
            password.setErrorEnabled(true);
            password.setError(getString(R.string.password_error));
            confirmPassword.setErrorEnabled(true);
            confirmPassword.setError(getString(R.string.password_error));
            passwordError = true;
        } else {
            password.setErrorEnabled(false);
            confirmPassword.setErrorEnabled(false);
            passwordError = false;
        }

        if (nameError || emailError || mobileError || passwordError)
            return;

        String URL = Utilies.URL + "register.php";
        StringRequest req = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject mainObject = new JSONObject(response);
                            if (mainObject.has("success") && mainObject.getBoolean("success")) {
                                Toast.makeText(getBaseContext(), R.string.register_success, Toast.LENGTH_LONG).show();
                                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(getBaseContext(), R.string.register_fail, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {

                        }
                    }
                }
                ,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getBaseContext(), R.string.internet_error, Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name.getEditText().getText().toString());
                params.put("email", email.getEditText().getText().toString());
                params.put("mobile", Utilies.convertPhone(mobile.getEditText().getText().toString()));
                params.put("password", password.getEditText().getText().toString());
                return params;
            }
        };

        req.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        req.setShouldCache(false);
        ApplicationController.getInstance().addToRequestQueue(req);
    }
}
