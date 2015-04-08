package es.udc.psi14.lab07trabazo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NotasDataBaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "notas.db";
    private static final int DB_VERSION = 1;

    public static final String TABLA_NOTAS = "notas";
    public static final String COL_ID = "id";
    public static final String COL_NOMBRE = "nombre";
    public static final String COL_APELLIDO = "apellido";
    public static final String COL_MATERIA = "materia";
    public static final String COL_MENCION = "mención";
    public static final String COL_NOTA = "nota";
    public static final String [] NOTAS_TODAS_COL =
        {COL_ID, COL_NOMBRE, COL_APELLIDO, COL_MATERIA, COL_MENCION, COL_NOTA};

    public NotasDataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String DATABASE_CREATE = "create table" + TABLA_NOTAS + " ( "
        + COL_ID + " integer primary key autoincrement, "
        + COL_NOMBRE + " text not null, "
        + COL_APELLIDO + " text not null, "
        + COL_MATERIA + " text not null, "
        + COL_MENCION + " numeric not null "
        + COL_NOTA + " real not null);";

        db.execSQL(DATABASE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(NotasDataBaseHelper.class.getName(), "Upgrading db from version "
                + oldVersion + " to " + newVersion + ", which will destroy old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_NOTAS);
        onCreate(db);
    }

    public long insertNota(Notas notas) {
        ContentValues cv = new ContentValues();
        cv.put(COL_NOMBRE, notas.getNombre());
        cv.put(COL_APELLIDO, notas.getApellido());
        cv.put(COL_MATERIA, notas.getMateria());
        cv.put(COL_MENCION, notas.getMencion());
        cv.put(COL_NOTA, notas.getNota());

        return getWritableDatabase().insert(TABLA_NOTAS, null, cv);
    }

    public void deleteNota(int id) {

        getWritableDatabase().delete(TABLA_NOTAS, COL_ID + "=?", new String[]
                { String.valueOf(id) });
    }

    public void updateNota(Notas notas) {
        long idNot = notas.getId();
        ContentValues cv = new ContentValues();
        cv.put(COL_NOMBRE, notas.getNombre());
        cv.put(COL_APELLIDO, notas.getApellido());
        cv.put(COL_MATERIA, notas.getMateria());
        cv.put(COL_MENCION, notas.getMencion());
        cv.put(COL_NOTA, notas.getNota());

        getWritableDatabase().update(TABLA_NOTAS, cv, COL_ID+ " = " + idNot, null);
    }

    public Cursor getNotas() {
        return getWritableDatabase().query(TABLA_NOTAS, null, null, null,
                null,null, null);
    }

    public Cursor getNota(int nota) {
        return getWritableDatabase().query(TABLA_NOTAS, null, COL_NOTA+"=?",
                new String[] { String.valueOf(nota) }, null, null, null);
    }

    public Cursor getNotas(String COL, String value) {
        return getWritableDatabase().query(true, TABLA_NOTAS, NOTAS_TODAS_COL,
                COL + " like ’%" + value + "%’", null, null, null, null, null);
    }

    public Cursor getNotas2(String COL, String value) {
        return getWritableDatabase().query(TABLA_NOTAS, null, COL +
                " like ? ", new String[] {"%"+value+"%"}, null, null, null);
    }


}