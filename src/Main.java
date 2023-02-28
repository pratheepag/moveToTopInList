import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class Main {

    public static void main(String[] args) {

        List<Student> students = new ArrayList<>();
        students.add(new Student("14829659"));
        students.add(new Student("14829485"));
        students.add(new Student("14829484"));
        String studentId = "14829485";

        List<Student> sortedstudents1 = moveMonitorFirstInListMethod1(students, studentId);
        System.out.println(sortedstudents1);

        List<Student> sortedstudents2 = students.stream().sorted(moveMonitorFirstInListMethod2(studentId)).collect(Collectors.toUnmodifiableList());
        System.out.println(sortedstudents2);

        List<Student> sortedstudents3 = moveMonitorFirstInListMethod3(students, studentId);
        System.out.println(sortedstudents3);

        List<Student> sortedstudents4 = moveRequestedStudentFirstInList3(students, studentId);
        System.out.println(sortedstudents4);

        List<Student> sortedstudents5 = moveRequestedStudentFirstInList4(students, studentId);
        System.out.println(sortedstudents5);
    }

    public static List<Student> moveMonitorFirstInListMethod1(List<Student> students, final String studentId) {
        IntStream.range(0, students.size()).filter(index -> students.get(index).getStudentId().equals(studentId))
                .forEach(index -> Collections.swap(students, 0, index));
        return students;
    }

    private static List<Student> moveMonitorFirstInListMethod3(List<Student> students, String studentId) {
        Comparator<Student> StudentOrder = (s1, s2) -> studentId.equals(s1.getStudentId()) ? -1
                : studentId.equals(s2.getStudentId()) ? 1 : 0;
        return students.stream().sorted(StudentOrder).collect(Collectors.toUnmodifiableList());
    }

    private static Comparator<Student> moveMonitorFirstInListMethod2(final String studentId) {
        Predicate<Student> predicate = Student -> Student.getStudentId().equals(studentId);
        ToIntFunction<Student> function1 = Student -> Student.getStudentId().equals(studentId) ? 1 : 0;

        return (p1, p2) -> predicate.test(p1) ? -1 : function1.applyAsInt(p2);
    }

    public static List<Student> moveRequestedStudentFirstInList3(List<Student> students, final String studentId) {
        return Stream
                .of(students.stream().filter(Student -> Student.getStudentId().equals(studentId)),
                        students.stream().filter(Student -> !Student.getStudentId().equals(studentId)))
                .flatMap(Function.identity()).collect(Collectors.toList());
    }

    public static List<Student> moveRequestedStudentFirstInList4(List<Student> students, final String studentId) {
        return Stream.of(
                        students.stream().filter(Student -> Student.getStudentId().equals(studentId)).collect(Collectors.toList()),
                        students.stream().filter(Student -> !Student.getStudentId().equals(studentId)).collect(Collectors.toList())
                )
                .reduce((a, b) -> {
                    a.addAll(b);
                    return a;
                })
                .orElseGet(Collections::emptyList);
    }

}