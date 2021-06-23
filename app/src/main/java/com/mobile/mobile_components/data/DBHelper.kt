package com.mobile.mobile_components.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.mobile.mobile_components.model.Course
import com.mobile.mobile_components.model.Student

class DBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    private val db : SQLiteDatabase = writableDatabase
   // private val values : ContentValues = ContentValues()

    companion object{
        private const val DB_NAME = "courses_students_db"
        private const val DB_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(Tables.Students.CREATE_TABLE)
        db?.execSQL(Tables.Courses.CREATE_TABLE)
        db?.execSQL(Tables.StudentsCourses.CREATE_TABLE)
        db?.execSQL("INSERT INTO ${Tables.Students.TABLE_NAME} VALUES ('123','Kevin','Floress','Delgado', 21);")
        db?.execSQL("INSERT INTO ${Tables.Students.TABLE_NAME} VALUES ('admin','Javier','Amador','Delgado', 21);")
        db?.execSQL("INSERT INTO ${Tables.Courses.TABLE_NAME} VALUES ('MAT-030','Matemática para Informática', 3);")
        db?.execSQL("INSERT INTO ${Tables.Courses.TABLE_NAME} VALUES ('ING-040','Ingeniería de Sistemas III', 4);")
        db?.execSQL("INSERT INTO ${Tables.Courses.TABLE_NAME} VALUES ('LIX-411','Inglés Integrado II', 4);")
        db?.execSQL("INSERT INTO ${Tables.Courses.TABLE_NAME} VALUES ('EIF-203','Matemática Discreta', 3);")

        db?.execSQL("INSERT INTO ${Tables.StudentsCourses.TABLE_NAME} VALUES ('123','EIF-203');")
        db?.execSQL("INSERT INTO ${Tables.StudentsCourses.TABLE_NAME} VALUES ('123','LIX-411');")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${Tables.Students.TABLE_NAME}")
        db?.execSQL("DROP TABLE IF EXISTS ${Tables.Courses.TABLE_NAME}")
        db?.execSQL("DROP TABLE IF EXISTS ${Tables.StudentsCourses.TABLE_NAME}")
        onCreate(db)
    }

    private fun parseStudent(c : Cursor) : Student {
        return Student(
            c.getString(0), c.getString(1), c.getString(2),
            c.getString(3), c.getInt(4)
        )
    }

    private fun parseCourse(c : Cursor) : Course {
        return Course(c.getString(0), c.getString(1), c.getInt(2))
    }

    fun getStudentById(id : String) : Student? {
        var result : Student? = null
        val cursor = db.rawQuery(
            "SELECT * FROM ${Tables.Students.TABLE_NAME} " +
                    "WHERE ${Tables.Students.ID} = '$id'"
            , null)
        if(cursor.moveToFirst()){
            result = parseStudent(cursor)
        }
        return result
    }

    fun insertStudent(student: Student){
        val values = ContentValues().apply {
            put(Tables.Students.ID, student.id)
            put(Tables.Students.NAME, student.name)
            put(Tables.Students.LAST_NAME_1, student.lastname1)
            put(Tables.Students.LAST_NAME_2, student.lastname2)
            put(Tables.Students.AGE, student.age)
        }
        db.insert(Tables.Students.TABLE_NAME, null, values)
    }

    fun addCourse(course: Course){
        val values = ContentValues().apply {
            put(Tables.Courses.ID, course.id)
            put(Tables.Courses.DESCRIPTION, course.description)
            put(Tables.Courses.CREDITS, course.credits)
        }
        db.insert(Tables.Courses.TABLE_NAME, null, values)
    }

    fun updateCourse(course: Course){
        val values = ContentValues().apply {
            put(Tables.Courses.ID, course.id)
            put(Tables.Courses.DESCRIPTION, course.description)
            put(Tables.Courses.CREDITS, course.credits)
        }
        db.update(
            Tables.Courses.TABLE_NAME,
            values,
            "${Tables.Courses.ID} = ?",
            Array(1) { course.id }
        )
    }

    fun getAllCourses() : ArrayList<Course>{
        val result = ArrayList<Course>()
        val cursor = db.rawQuery("SELECT * FROM ${Tables.Courses.TABLE_NAME}", null)
        if(cursor.moveToFirst()){
            do{
                result.add(parseCourse(cursor))
            }while (cursor.moveToNext())
        }
        return result
    }

    fun getAllStudents() : ArrayList<Student>{
        val result = ArrayList<Student>()
        val cursor = db.rawQuery("SELECT * FROM ${Tables.Students.TABLE_NAME}", null)
        if(cursor.moveToFirst()){
            do{
                result.add(parseStudent(cursor))
            }while (cursor.moveToNext())
        }
        return result
    }

    fun getStudent(id: String) : Student? {
        var result : Student? = null
        val cursor = db.rawQuery(
            "SELECT * FROM ${Tables.Students.TABLE_NAME} " +
                    "WHERE ${Tables.Students.ID} = '$id'"
            , null)
        if(cursor.moveToFirst()){
            result = parseStudent(cursor)
        }
        return result
    }

    fun deleteStudent(id : String){
        db.execSQL(
            "DELETE FROM ${Tables.Students.TABLE_NAME} WHERE ${Tables.Students.ID} = '$id'"
        )
    }

    fun getCoursesByStudent(id: String) : ArrayList<Course>{
        val result = ArrayList<Course>()
        val cursor = db.rawQuery(
            "SELECT * FROM ${Tables.StudentsCourses.TABLE_NAME} " +
                    "WHERE ${Tables.StudentsCourses.STUDENT} = '$id'",
            null
        )
        var course : Course?
        if(cursor.moveToFirst()){
            do{
                course = getCourse(cursor.getString(1))
                if (course != null) result.add(course)
            }while (cursor.moveToNext())
        }
        return result
    }

    fun getNotCoursesByStudent(id: String) : ArrayList<Course>{
        val result = ArrayList<Course>()
        val cursor = db.rawQuery(
            "SELECT ${Tables.Courses.ID} " +
                    "FROM ${Tables.Courses.TABLE_NAME} EXCEPT " +
                    "SELECT ${Tables.StudentsCourses.COURSE} " +
                    "FROM ${Tables.StudentsCourses.TABLE_NAME} " +
                    "WHERE ${Tables.StudentsCourses.STUDENT} = '$id'",
            null
        )
        var course : Course?
        if(cursor.moveToFirst()){
            do{
                course = getCourse(cursor.getString(0))
                if (course != null) result.add(course)
            }while (cursor.moveToNext())
        }
        return result
    }

    fun getCourse(id: String) : Course? {
        var result : Course? = null
        val cursor = db.rawQuery(
            "SELECT * FROM ${Tables.Courses.TABLE_NAME} " +
                    "WHERE ${Tables.Courses.ID} = '$id'"
            , null)
        if(cursor.moveToFirst()){
            result = parseCourse(cursor)
        }
        return result
    }

    fun deleteCourse(id : String){
        db.execSQL(
            "DELETE FROM ${Tables.Courses.TABLE_NAME} WHERE ${Tables.Courses.ID} = '$id'"
        )
    }

    fun unregisterCourse(idStudent : String, idCourse : String){
        db.execSQL(
            "DELETE FROM ${Tables.StudentsCourses.TABLE_NAME} WHERE " +
                    "${Tables.StudentsCourses.STUDENT} = '$idStudent' AND " +
                    "${Tables.StudentsCourses.COURSE} = '$idCourse'"
        )
    }

    fun registerCourse(idStudent : String, idCourse : String){
        val values = ContentValues().apply {
            put(Tables.StudentsCourses.STUDENT, idStudent)
            put(Tables.StudentsCourses.COURSE, idCourse)
        }
        db.insert(Tables.StudentsCourses.TABLE_NAME, null, values)
    }

}