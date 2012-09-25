package br.com.caelum.cadastroaluno;

import java.util.List;

import org.json.JSONStringer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import br.com.caelum.control.Sync;
import br.com.caelum.model.bean.AlunoBean;
import br.com.caelum.model.dao.AlunoDAO;

public class CadastroAluno extends Activity {

	private ListView lista;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista);

		lista = (ListView) findViewById(R.id.lista);

		carregaLista();

		lista.setClickable(true);
		lista.setLongClickable(true);
		lista.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				// Toast.makeText(CadastroAluno.this, ":::: " + arg2,
				// Toast.LENGTH_LONG).show();

				SharedPreferences sP = getSharedPreferences("as", MODE_PRIVATE);
				SharedPreferences.Editor editor = sP.edit();
				editor.putInt("posicao", arg2);
				editor.commit();

				Intent intent = new Intent(CadastroAluno.this, Formulario.class);
				startActivity(intent);
			}

		});

		lista.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				// Toast.makeText(CadastroAluno.this, ":::: long " + arg2,
				// Toast.LENGTH_LONG).show();
				registerForContextMenu(lista);

				return false;
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuItem novo = menu.add(0, 0, 0, "Novo");
		novo.setIcon(R.drawable.novo); // android.R.drawable.btn_plus

		MenuItem sync = menu.add(0, 1, 1, "Sync");
		sync.setIcon(android.R.drawable.btn_plus);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == 0) {
			// Toast.makeText(CadastroAluno.this, "...",
			// Toast.LENGTH_LONG).show();
			SharedPreferences sP = getSharedPreferences("as", MODE_PRIVATE);
			SharedPreferences.Editor editor = sP.edit();
			editor.remove("posicao");
			editor.commit();

			startActivity(new Intent(CadastroAluno.this, Formulario.class));
		}

		if (item.getItemId() == 1) {
			final ProgressDialog progress = ProgressDialog.show(
					CadastroAluno.this, "Aguardar ...", "Enviar ...", true);

			final Toast aviso = Toast.makeText(CadastroAluno.this,
					"Enviado ...", Toast.LENGTH_LONG);

			new Thread(new Runnable() {
				public void run() {
					try {
						Thread.sleep(2000);

						AlunoDAO dao = new AlunoDAO(CadastroAluno.this);
						@SuppressWarnings("unchecked")
						List<AlunoBean> lista = dao.getList();

						JSONStringer j = new JSONStringer();
						j.object().key("alunos").array();

						for (AlunoBean ab : lista) {
							j.value(ab.toJSON());
						}

						j.endArray().endObject();

						dao.close();

						Sync s = new Sync();
						String retorno = s.enviarDado(j.toString());

						Log.i("Retorno", retorno);
					} catch (Exception e) {
						// TODO: handle exception
					}

					aviso.show();
					progress.dismiss();
				}
			}).start();
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unused")
		MenuItem novo = menu.add(0, 0, 0, "Ligar");

		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == 0) {
			Toast.makeText(CadastroAluno.this, " ::::>> ", Toast.LENGTH_LONG)
					.show();
		}

		return super.onContextItemSelected(item);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	private void carregaLista() {
		AlunoDAO dao = new AlunoDAO(this);
		@SuppressWarnings("rawtypes")
		List alunos = dao.getList();
		dao.close();

		@SuppressWarnings("unchecked")
		ArrayAdapter<AlunoBean> adapter = new ArrayAdapter<AlunoBean>(this,
				android.R.layout.simple_list_item_1, alunos);

		lista.setAdapter(adapter);
	}

}