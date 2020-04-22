package com.example.myapplication.ui.gallery;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.myapplication.Clases.*;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;

import org.json.JSONObject;





public class GalleryFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    //estos edtiText son los que se usaran para referenciar los obejtos del .xml
    EditText name,owner,author,tipo;
    Button subir,descargar;

    RequestQueue request;
    JsonObjectRequest jeison;

    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);


        View root = inflater.inflate(R.layout.fragment_gallery, container, false);



        //en esta parte del codigo se hace referencia a los campos de texto del .xml correspondiente a esta clase
        name=(EditText) root.findViewById(R.id.Name);
        owner=(EditText) root.findViewById(R.id.dueno);
        author=(EditText) root.findViewById(R.id.autor);
        tipo=(EditText) root.findViewById(R.id.tipo);
        subir=(Button) root.findViewById((R.id.subir));
        descargar=(Button) root.findViewById(R.id.descargar);

        //en request se cargara la busqueda que se ejecutara en el objeto JsonObjectRequest
        request= Volley.newRequestQueue(getContext());

        //se le agrega una accion al boton
        subir.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                mostrarOpciones();
            }
        });
        descargar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


                descargarArchivo();

            }

        });


        return root;
    }

    //en esta funcion se mostraran las opociones para la subida de archivos a la app y correspondientemente a la base de datos o servidor
    public void mostrarOpciones(){

        final CharSequence[]opciones={"Elegir archivo","Cancelar"};
        final AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
        System.out.println("holamundo");
        builder.setTitle("Opciones");
        Toast.makeText(getContext(),"cargar",Toast.LENGTH_SHORT).show();
        builder.setItems(opciones, new DialogInterface.OnClickListener() {



            @Override
            public void onClick(DialogInterface dialog, int i) {

                if (opciones[i].equals("Elegir archivo")) {
                    new Intent(Intent.ACTION_GET_CONTENT);


                    Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                    Uri uri = Uri.parse(Environment.DIRECTORY_DOWNLOADS);
                    intent.setDataAndType(uri,"*/*");
                    startActivityForResult(intent.createChooser(intent, "Seleccione"),10);
                }else{
                    dialog.dismiss();
                }
            }
            public void onActivityResult(int requestCode, int resultCode, Intent data){
                GalleryFragment.super.onActivityResult(requestCode,resultCode,data);
                switch (requestCode){
                    case 10:
                        Uri path=data.getData();
                    break;
                }
            }
        });
        builder.show();


    };

    public void descargarArchivo(){
        String url=Coneccion.url+"index.php";
        //Request.Method.GET,url,null,this,this
        jeison=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jeison);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getContext(),"No se pudo registrar  "+error.toString(),Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onResponse(JSONObject response) {

        Toast.makeText(getContext(),"Se descargo exitosamente",Toast.LENGTH_SHORT).show();

    }
}