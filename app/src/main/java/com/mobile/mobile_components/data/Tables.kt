package com.mobile.mobile_components.data

class Tables {

    abstract class Students {
        companion object{
            const val TABLE_NAME = "students"
            const val ID = "id"
            const val NAME = "name"
            const val LAST_NAME_1 = "lastname1"
            const val LAST_NAME_2 = "lastname2"
            const val AGE = "age"
            const val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
                    "$ID TEXT PRIMARY KEY, " +
                    "$NAME TEXT NOT NULL, " +
                    "$LAST_NAME_1 TEXT NOT NULL, " +
                    "$LAST_NAME_2 TEXT NOT NULL, " +
                    "$AGE INTEGER NOT NULL)"
        }
    }

    abstract class Courses {
        companion object{
            const val TABLE_NAME = "courses"
            const val ID = "id"
            const val DESCRIPTION = "description"
            const val CREDITS = "credits"
            const val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
                    "$ID TEXT PRIMARY KEY, " +
                    "$DESCRIPTION TEXT NOT NULL, " +
                    "$CREDITS INTEGER NOT NULL)"
        }
    }

    abstract class StudentsCourses {
        companion object{
            const val TABLE_NAME = "students_courses"
            const val STUDENT = "student"
            const val COURSE = "course"
            const val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
                    "$STUDENT TEXT NOT NULL, " +
                    "$COURSE TEXT NOT NULL, " +
                    "FOREIGN KEY ($STUDENT) REFERENCES ${Students.TABLE_NAME}(${Students.ID}), " +
                    "FOREIGN KEY ($COURSE) REFERENCES ${Courses.TABLE_NAME}(${Courses.ID}), " +
                    "PRIMARY KEY ($STUDENT, $COURSE))"
        }
    }
}