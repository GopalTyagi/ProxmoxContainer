package com.tech.task.DTO;

import java.util.List;

public class StudentMarksDTO {
    private int studentId;
    private String name;
    private String email;
    private List<SubjectMarks> subjectMarks;

    public StudentMarksDTO(int studentId, String name, String email, List<SubjectMarks> subjectMarks) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.subjectMarks = subjectMarks;
    }

    // Getters and Setters
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<SubjectMarks> getSubjectMarks() {
        return subjectMarks;
    }

    public void setSubjectMarks(List<SubjectMarks> subjectMarks) {
        this.subjectMarks = subjectMarks;
    }

    public static class SubjectMarks {
        private String subjectName;
        private int marks;

        public SubjectMarks(String subjectName, int marks) {
            this.subjectName = subjectName;
            this.marks = marks;
        }

        // Getters and Setters
        public String getSubjectName() {
            return subjectName;
        }

        public void setSubjectName(String subjectName) {
            this.subjectName = subjectName;
        }

        public int getMarks() {
            return marks;
        }

        public void setMarks(int marks) {
            this.marks = marks;
        }
    }
}