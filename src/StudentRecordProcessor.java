import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class StudentRecordProcessor {
    // Поля для хранения данных
    private final List<Student> students = new ArrayList<>();

    // _____реализуйте класс Student ниже в этом же файле______

    private double averageScore;
    private Student highestStudent;


    /**
     * Task 1 + Task 2 + Task 5 + Task 6
     */
    public void readFile() {
        // TODO: реализуйте чтение файла здесь
        try (BufferedReader reader = new BufferedReader(new FileReader("output/student.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 2) {
                    System.out.println("Invalid data:" + line);
                    continue;
                }
                String name = parts[0];
                int score;
                try {
                    score = Integer.parseInt(parts[1]);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid data:" + line);
                    continue;
                }
                try {
                    if (score < 0 || score > 100) {
                        throw new InvalidScoreException("балл не соответсвует:" + score);
                    }
                } catch (InvalidScoreException e) {
                    System.out.println("Invalid data: " + line + " --> " + e.getMessage());
                    continue;
                }
                students.add(new Student(name, score));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        } catch (IOException e) {
            System.out.println("Проблема при читении:" + e.getMessage());
        }
    }

    /**
     * Task 3 + Task 8
     */

    public void processData() {
        // TODO: обработка данных и сортировка здесь
        int sum = 0;
        highestStudent = students.get(0);
        for (Student s : students) {
            sum += s.score;
            if (s.score > highestStudent.score) {
                highestStudent = s;
            }
        }
        averageScore = (double) sum / students.size();

        students.sort(new Comparator<Student>() {
            public int compare(Student a, Student b) {
                return b.score - a.score;
            }
        });
    }

    /**
     * Task 4 + Task 5 + Task 8
     */
    public void writeFile() {
        // TODO: запись результата в файл здесь
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output/report.txt"))) {
            writer.write("Average: " + averageScore);
            writer.newLine();
            writer.write("Highest: " + highestStudent.name + " - " + highestStudent.score);
            writer.newLine();
            int rank = 1;
            for (Student s : students) {
                writer.write(rank + ". " + s.name + " - " + s.score);
                writer.newLine();
                rank++;
            }
        } catch (IOException e) {
            System.out.println("Ошибка при записи: " + e.getMessage());
        }
    }




    public static void main(String[] args) {
        StudentRecordProcessor processor = new StudentRecordProcessor();

        try {
            processor.readFile();
            processor.processData();
            processor.writeFile();
            System.out.println("Processing completed. Check output/report.txt");
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}

// class InvalidScoreException реализуйте меня
// class Student (name, score)
class Student{
     String name;
     int score;

    public Student(String name,int score){
        this.name=name;
        this.score=score;
    }
}
class InvalidScoreException extends Exception{
    public InvalidScoreException(String message){
        super(message);
    }
}