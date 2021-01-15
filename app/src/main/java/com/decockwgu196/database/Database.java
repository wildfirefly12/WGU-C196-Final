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
                        Term term1 = new Term("Term 1", "9/1/2020", "2/28/2021");
                        termDao.insert(term1);

                        Term term2 = new Term("Term 2", "3/1/2020", "8/31/2021");
                        termDao.insert(term2);

                        Term term3 = new Term("Term 3", "9/1/2021", "2/28/2022");
                        termDao.insert(term3);

                        CourseDao courseDao = INSTANCE.courseDao();
                        Course course1 = new Course("Course 1", "9/1/2020", "9/1/2020", "In Progress", "Instructor", "555-555-5555", "instructor@wgu.edu", 1);
                        courseDao.insert(course1);

                        Course course2 = new Course("Course 2", "9/1/2020", "9/1/2020", "In Progress", "Instructor", "555-555-5555", "instructor@wgu.edu", 1);
                        courseDao.insert(course2);

                        Course course3 = new Course("Course 3", "9/1/2020", "9/1/2020", "In Progress", "Instructor", "555-555-5555", "instructor@wgu.edu", 2);
                        courseDao.insert(course3);

                        Course course4 = new Course("Course 4", "9/1/2020", "9/1/2020", "In Progress", "Instructor", "555-555-5555", "instructor@wgu.edu", 2);
                        courseDao.insert(course4);

                        Course course5 = new Course("Course 5", "9/1/2020", "9/1/2020", "In Progress", "Instructor", "555-555-5555", "instructor@wgu.edu", 3);
                        courseDao.insert(course5);

                        Course course6 = new Course("Course 6", "9/1/2020", "9/1/2020", "In Progress", "Instructor", "555-555-5555", "instructor@wgu.edu", 3);
                        courseDao.insert(course6);


                        AssessmentDao assessmentDao = INSTANCE.assessmentDao();
                        Assessment assessment1 = new Assessment("Assessment 1", "9/1/2020", "9/1/2020", "Performance",1);
                        assessmentDao.insert(assessment1);

                        Assessment assessment2 = new Assessment("Assessment 1", "9/1/2020", "9/1/2020", "Performance",2);
                        assessmentDao.insert(assessment2);

                        Assessment assessment3 = new Assessment("Assessment 1", "9/1/2020", "9/1/2020", "Performance",3);
                        assessmentDao.insert(assessment3);

                        Assessment assessment4 = new Assessment("Assessment 1", "9/1/2020", "9/1/2020", "Performance",4);
                        assessmentDao.insert(assessment4);

                        Assessment assessment5 = new Assessment("Assessment 1", "9/1/2020", "9/1/2020", "Performance",5);
                        assessmentDao.insert(assessment5);

                        Assessment assessment6 = new Assessment("Assessment 1", "9/1/2020", "9/1/2020", "Performance",6);
                        assessmentDao.insert(assessment6);


                        NoteDao noteDao = INSTANCE.noteDao();
                        Note note1 = new Note("Note 1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.", 1);
                        noteDao.insert(note1);

                        Note note2 = new Note("Note 1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.", 2);
                        noteDao.insert(note2);

                        Note note3 = new Note("Note 1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.", 3);
                        noteDao.insert(note3);

                        Note note4 = new Note("Note 1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.", 4);
                        noteDao.insert(note4);

                        Note note5 = new Note("Note 1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.", 5);
                        noteDao.insert(note5);

                        Note note6 = new Note("Note 1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.", 6);
                        noteDao.insert(note6);
                    });
                }
            };
}
