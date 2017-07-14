package rf.digitworld.jobtest.data.local;

import android.content.ContentValues;
import android.database.Cursor;

import rf.digitworld.jobtest.data.model.User;

public class Db {

    public Db() { }

    public abstract static class UserTable {
        public static final String TABLE_NAME = "users";


        public static final String COLUMN_ID ="_id";
        public static final String COLUMN_NAME = "name";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_NAME + " TEXT NOT NULL " +
                " ); ";

        public static ContentValues toContentValues(User user) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, user.getId());
            values.put(COLUMN_NAME, user.getName());
            return values;
        }

        public static User parseCursor(Cursor cursor) {
            User user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
            user.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
            return user;
        }
    }
}
