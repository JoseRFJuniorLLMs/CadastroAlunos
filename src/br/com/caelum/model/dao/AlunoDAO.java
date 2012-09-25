package br.com.caelum.model.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.caelum.model.bean.AlunoBean;

public class AlunoDAO extends SQLiteOpenHelper {

	private static int VERSION = 1;
	private static String TABELA = "Aluno";
	private static String[] COLS = { "id", "end", "foto", "nome", "nota",
			"site", "fone" };

	public AlunoDAO(Context context) {
		super(context, TABELA, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql = "CREATE TABLE " + TABELA + " (id INTEGER PRIMARY KEY,"
				+ " end TEXT, foto TEXT, nome TEXT UNIQUE NOT NULL,"
				+ " nota REAL, site TEXT, fone TEXT);";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String sql = "DROP TABLE IF EXISTS " + TABELA;
		db.execSQL(sql);

		this.onCreate(db);
	}

	public void onAdd(AlunoBean ent) {
		ContentValues cV = new ContentValues();
		cV.put("nome", ent.getNome());
		cV.put("nota", ent.getNota());
		cV.put("site", ent.getSite());
		cV.put("foto", ent.getFoto());
		cV.put("fone", ent.getFone());
		cV.put("end", ent.getEnd());

		super.getWritableDatabase().insert(TABELA, null, cV);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getList() {
		Cursor c = super.getWritableDatabase().query(TABELA, COLS, null, null,
				null, null, null);

		List lista = new ArrayList();

		while (c.moveToNext()) {
			AlunoBean ent = new AlunoBean();
			ent.setId(c.getInt(0));
			ent.setEnd(c.getString(1));
			ent.setFoto(c.getString(2));
			ent.setNome(c.getString(3));
			ent.setNota(c.getDouble(4));
			ent.setSite(c.getString(5));
			ent.setFone(c.getString(6));

			lista.add(ent);
		}

		c.close();

		return lista;
	}

	public AlunoBean getById(int posicao) {
		Cursor c = super.getWritableDatabase().query(TABELA, COLS, "id=?",
				new String[] { "" + posicao }, null, null, null);

		c.moveToFirst();

		AlunoBean ent = new AlunoBean();
		ent.setId(c.getInt(0));
		ent.setEnd(c.getString(1));
		ent.setFoto(c.getString(2));
		ent.setNome(c.getString(3));
		ent.setNota(c.getDouble(4));
		ent.setSite(c.getString(5));
		ent.setFone(c.getString(6));

		c.close();

		return ent;
	}

	public void onEdit(AlunoBean ent) {
		ContentValues cV = new ContentValues();
		cV.put("nome", ent.getNome());
		cV.put("nota", ent.getNota());
		cV.put("site", ent.getSite());
		cV.put("foto", ent.getFoto());
		cV.put("fone", ent.getFone());
		cV.put("end", ent.getEnd());

		super.getWritableDatabase().update(TABELA, cV, "id=?",
				new String[] { "" + ent.getId() });
	}

}