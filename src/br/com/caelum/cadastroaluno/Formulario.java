package br.com.caelum.cadastroaluno;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import br.com.caelum.model.bean.AlunoBean;
import br.com.caelum.model.dao.AlunoDAO;

public class Formulario extends Activity {

	AlunoBean ent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formulario);

		SharedPreferences sP = getSharedPreferences("as", MODE_PRIVATE);
		final int posicao = sP.getInt("posicao", -1);

		if (posicao != -1) {
			AlunoDAO dao = new AlunoDAO(Formulario.this);
			ent = dao.getById(posicao + 1);
			dao.close();

			((EditText) findViewById(R.id.nome)).setText(ent.getNome());
			((EditText) findViewById(R.id.end)).setText(ent.getEnd());
			((EditText) findViewById(R.id.fone)).setText(ent.getFone());
			((EditText) findViewById(R.id.site)).setText(ent.getSite());
			((RatingBar) findViewById(R.id.nota)).setRating((float) ent
					.getNota());
			((Button) findViewById(R.id.inserir)).setText("Alterar");
		} else {
			ent = new AlunoBean();

			((Button) findViewById(R.id.inserir)).setText("Inserir");
		}

		Button botao = (Button) findViewById(R.id.inserir);
		botao.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(Formulario.this, "Formulario ...",
						Toast.LENGTH_LONG).show();

				EditText etNome = (EditText) findViewById(R.id.nome);
				EditText etFone = (EditText) findViewById(R.id.fone);
				EditText etSite = (EditText) findViewById(R.id.site);
				@SuppressWarnings("unused")
				EditText etEnd = (EditText) findViewById(R.id.end);

				RatingBar nota = (RatingBar) findViewById(R.id.nota);

				ent = new AlunoBean();
				ent.setNome(etNome.getEditableText().toString());
				ent.setEnd(etNome.getEditableText().toString());
				ent.setFone(etFone.getEditableText().toString());
				ent.setFoto(etFone.getEditableText().toString());
				ent.setNota(nota.getRating());
				ent.setSite(etSite.getEditableText().toString());

				AlunoDAO dao = new AlunoDAO(Formulario.this);

				if (posicao != -1) {
					ent.setId(posicao + 1);
					dao.onEdit(ent);
				} else {
					dao.onAdd(ent);
				}

				dao.close();

				startActivity(new Intent(Formulario.this, CadastroAluno.class));

				finish();
			}
		});
	}

}