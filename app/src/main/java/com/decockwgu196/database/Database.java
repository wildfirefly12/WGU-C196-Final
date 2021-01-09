package com.decockwgu196.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.wgu196final.model.Assessment;
import com.example.wgu196final.model.Course;
import com.example.wgu196final.model.Note;
import com.example.wgu196final.model.Term;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@androidx.room.Database(entities = {Term.class, Course.class, Assessment.class, Note.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {

    public abstract TermDao termDao();
    public abstract CourseDao courseDao();

    public static final int NUMBER_OF_THREADS = 4;

    private static volatile  Database INSTANCE;
    public static final ExecutorService databaseWriteExecutor
            = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static Database getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (Database.class) {
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            Database.class, "terms_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    private static final RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);

                    databaseWriteExecutor.execute(() -> {
                        TermDao termDao = INSTANCE.termDao();

                        Term term = new Term("Test", "test", "test");
                        termDao.insert(term);

                        CourseDao courseDao = INSTANCE.courseDao();
                        Course course = new Course("Test", "test", "Test", "test", "test", "test", "test", 1);
                        courseDao.insert(course);
                    });
                }
            };
}
