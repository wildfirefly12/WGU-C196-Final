package com.decockwgu196.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.decockwgu196.model.Assessment;
import com.decockwgu196.model.Course;
import com.decockwgu196.model.Note;
import com.decockwgu196.model.Term;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@androidx.room.Database(entities = {Term.class, Course.class, Assessment.class, Note.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {

    public abstract TermDao termDao();
    public abstract CourseDao courseDao();
    public abstract AssessmentDao assessmentDao();
    public abstract NoteDao noteDao();

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

                        AssessmentDao assessmentDao = INSTANCE.assessmentDao();
                        Assessment assessment = new Assessment("test", "test", "test", "test",1);
                        assessmentDao.insert(assessment);

                        NoteDao noteDao = INSTANCE.noteDao();
                        Note note = new Note("test", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.", 1);
                        noteDao.insert(note);
                    });
                }
            };
}
