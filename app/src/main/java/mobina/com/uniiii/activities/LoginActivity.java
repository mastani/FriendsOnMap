package mobina.com.uniiii.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.Manifest;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import mobina.com.uniiii.R;
import mobina.com.uniiii.abstracts.User;
import mobina.com.uniiii.Utility.ApplicationController;
import mobina.com.uniiii.Utility.Utilies;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout email;
    TextInputLayout password;
    Button btnLogin;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (TextInputLayout) findViewById(R.id.email);
        password = (TextInputLayout) findViewById(R.id.password);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });

        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    public void Login() {
        boolean emailError;
        boolean passwordError;

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

        if (password.getEditText().getText().length() == 0) {
            password.setErrorEnabled(true);
            password.setError(getString(R.string.password_error));
            passwordError = true;
        } else {
            password.setErrorEnabled(false);
            passwordError = false;
        }

        if (emailError || passwordError)
            return;

        String URL = Utilies.URL + "login.php";
        StringRequest req = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject mainObject = new JSONObject(response);
                            if (mainObject.has("success") && mainObject.getBoolean("success")) {
                                Utilies.me = new User(mainObject.getInt("id"), mainObject.getString("name"), mainObject.getString("email"), mainObject.getString("mobile"));
                                askForContactPermission();
                            } else {
                                Toast.makeText(getBaseContext(), R.string.login_fail, Toast.LENGTH_LONG).show();
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
                params.put("email", email.getEditText().getText().toString());
                params.put("password", password.getEditText().getText().toString());
                return params;
            }
        };

        req.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        req.setShouldCache(false);
        ApplicationController.getInstance().addToRequestQueue(req);
    }

    static final int PERMISSION_REQUEST_CONTACT = 10;

    public void askForContactPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSION_REQUEST_CONTACT);
                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, PERMISSION_REQUEST_CONTACT);
                }
            } else {
                getContact();
            }
        } else {
            getContact();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CONTACT: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContact();
                } else {
                    Toast.makeText(this, "No permission for contacts", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    void getContact() {
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (phones.moveToNext()) {
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            phoneNumber = Utilies.convertPhone(phoneNumber);
            Utilies.localUsers.put(name, phoneNumber);
        }
        phones.close();

        String URL = Utilies.URL + "syncContacts.php";
        StringRequest req = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject mainObject = new JSONObject(response);
                            if (mainObject.has("success") && mainObject.getBoolean("success")) {
                                JSONArray usersArray = mainObject.getJSONArray("users");
                                for (int i = 0; i < usersArray.length(); i++) {
                                    JSONObject user = usersArray.getJSONObject(i);
                                    int id = user.getInt("id");
                                    String name = user.getString("name");
                                    String email = user.getString("email");
                                    String mobile = user.getString("mobile");
                                    String latitude = user.getString("latitude");
                                    String longitude = user.getString("longitude");
                                    String update_time = user.getString("update_time");
                                    Utilies.syncedUsers.add(new User(id, name, email, mobile, latitude, longitude, update_time));
                                }

                                Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                                startActivity(i);

                                finish();
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

                params.put("email", Utilies.me.getEmail());

                String numbers = "";

                Set set = Utilies.localUsers.entrySet();
                for (Object aSet : set) {
                    Map.Entry entry = (Map.Entry) aSet;
                    numbers += entry.getValue() + "&";
                }

                params.put("numbers", numbers);
                return params;
            }
        };

        req.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        req.setShouldCache(false);
        ApplicationController.getInstance().addToRequestQueue(req);
    }
}