package com.example.segundoprojeto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class Tela2 extends Activity {
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela2);
		
		ReadFile();
		
		Button btn = (Button) findViewById(R.id.buttonSalvar);
		btn.setOnClickListener(mClickListener);
	}
	
	private View.OnClickListener mClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			TextView lblStatus = (TextView) findViewById(R.id.textViewStatus);
			EditText txtNome = (EditText) findViewById(R.id.editTextNome);
			RadioButton rbMasculino = (RadioButton) findViewById(R.id.radioMasculino);
			RadioButton rbFeminino = (RadioButton) findViewById(R.id.radioFeminino);
			EditText txtIdade = (EditText) findViewById(R.id.editTextIdade);
			
			String nome = txtNome.getText().toString();
			String idade = txtIdade.getText().toString();
			String sexo = "";
			
			if(rbMasculino.isChecked())
				sexo = "Masculino";
			else if(rbFeminino.isChecked())
				sexo = "Feminino";
			
			SaveFile(nome, sexo, idade);
			
			lblStatus.setText("Status: Preferências salvas com sucesso");
						
		}
	};
	
	private void ReadFile(){
		EditText txtNome = (EditText) findViewById(R.id.editTextNome);
		RadioButton rbMasculino = (RadioButton) findViewById(R.id.radioMasculino);
		RadioButton rbFeminino = (RadioButton) findViewById(R.id.radioFeminino);
		EditText txtIdade = (EditText) findViewById(R.id.editTextIdade);
		
		String nome = "";
		String sexo = "";
		String idade = "";
		try{
			String state = Environment.getExternalStorageState();
			if (state.equals(Environment.MEDIA_MOUNTED)){
				File tf = new File(getExternalFilesDir(null),"dados.txt");
				if(tf.exists()){
					FileInputStream fis = new FileInputStream(tf);
					byte[] buffer = new byte[(int)tf.length()];
					
					while(fis.read(buffer) != -1){
						String texto = new String(buffer);
						if(texto.indexOf("nome")!=-1){
							int indice = texto.indexOf("=");
							int indicefinal = texto.indexOf("\n");
							nome = texto.substring(indice+1,indicefinal);
							texto = texto.substring(indicefinal + 1);
						}
						if(texto.indexOf("idade")!=-1){
							int indice = texto.indexOf("=");
							int indicefinal = texto.indexOf("\n");
							idade = texto.substring(indice+1, indicefinal);
							texto = texto.substring(indicefinal+1);
						}
						if(texto.indexOf("sexo")!=-1){
							int indice = texto.indexOf("=");
							sexo = texto.substring(indice+1);
						}
					}
				}
			}
		}catch(Exception e){
			Log.i("error aquivo", e.getMessage());
		}
		
		txtNome.setText(nome);
		if(sexo.contains("Masculino"))
			rbMasculino.setChecked(true);
		else
			rbFeminino.setChecked(true);
		txtIdade.setText(idade);
	}
	
	private void SaveFile(String nome, String sexo, String idade){
		String dados = "";
		dados += "nome=" + nome;
		dados += "\n";
		dados += "idade=" + idade;
		dados += "\n";
		dados += "sexo=" + sexo;
		dados += "\n";
		try{
			String state = Environment.getExternalStorageState();
			if(state.equals(Environment.MEDIA_MOUNTED)){
				File file = new File(getExternalFilesDir(null),"/dados.txt");
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(dados.getBytes());
				fos.close();
			}
		}catch(Exception e){
			Log.i("Error arquivo", e.getMessage());
		}
	}

}
