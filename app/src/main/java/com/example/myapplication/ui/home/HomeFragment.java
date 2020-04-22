package com.example.myapplication.ui.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;

import org.json.JSONObject;

public class  HomeFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    private HomeViewModel homeViewModel;

    ProgressDialog progress;

    private EditText nombre, documento, profesion;
    private Button boton;

    RequestQueue request;
    JsonObjectRequest jeison;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        nombre=(EditText) root.findViewById(R.id.nombre);
        documento=(EditText) root.findViewById(R.id.documento);
        profesion=(EditText) root.findViewById(R.id.profesion);
        boton=(Button) root.findViewById(R.id.boton);

        request= Volley.newRequestQueue(getContext());
        boton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                cargarWebService();
            }
        });


        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    private void cargarWebService() {
        progress=new ProgressDialog(getContext());
        progress.show();
        String url="https://estructurastrabajo.000webhostapp.com?documen    to="+documento.getText().toString()+
                "&nombre="+ nombre.getText().toString()+"&profesion="+profesion.getText().toString();
        url.replace(" ","%20");

        jeison=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jeison);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progress.hide();
        Toast.makeText(getContext(),"No se pudo registrar  "+error.toString(),Toast.LENGTH_SHORT).show();
        documento.setText("");
        profesion.setText("");
        nombre.setText("");

    }

    @Override
    public void onResponse(JSONObject response) {
        progress.hide();
        Toast.makeText(getContext(),"Se registro exitosamente",Toast.LENGTH_SHORT).show();

        documento.setText("");
        profesion.setText("");
        nombre.setText("");
    }
}