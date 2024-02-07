package pick.box;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import pick.box.PickerBox;
import pick.box.PickerUtil;
import pick.box.domain.doubleNestedExample.ClassDays;
import pick.box.domain.doubleNestedExample.Clazz;
import pick.box.domain.doubleNestedExample.Professor;
import pick.box.domain.doubleNestedExample.Student;
import pick.box.domain.doubleNestedExample.University;
import pick.box.domain.doubleNestedExample.resolvers.ClassDaysResolver;
import pick.box.domain.doubleNestedExample.resolvers.ClazzResolver;
import pick.box.domain.doubleNestedExample.resolvers.ProfessorResolver;
import pick.box.domain.doubleNestedExample.resolvers.StudentResolver;
import pick.box.domain.doubleNestedExample.resolvers.UniversityResolver;
import pick.box.domain.nestedExample.Child;
import pick.box.domain.nestedExample.ChildResolver;
import pick.box.domain.nestedExample.Gender;
import pick.box.domain.nestedExample.Parent;
import pick.box.domain.nestedExample.ParentResolver;




public class PickerBoxTest {
    

    @Test
    public void mapNestedObject() {
        Parent request = new Parent();
        request.name = "";

        Child child = new Child();
        child.age = 0;
        child.gender = Gender.MALE;
        child.name = "";

        request.children = List.of(child);


        ParentResolver parentResolver = new ParentResolver();
        ChildResolver childResolver = new ChildResolver();

        PickerBox pickerBox = new PickerBox(
            List.of(parentResolver, childResolver)
        );

        Parent result = pickerBox.resolveRequest(request, 1);

        assertNull(result.gender);
        assertEquals("Stinky Dad", result.name);
        Child firstChild = result.children.get(0);
        assertEquals(11, firstChild.age);
        assertEquals(Gender.MALE, firstChild.gender);
        //assertNull(firstChild.name);


        Map<String, Object> resMap = PickerUtil.mapify(result);
        System.out.println(
            PickerUtil.jsonMap(resMap)
        );
    }


    @Test
    public void mapDoubleNestedObject() {
        

        University request = new University(
            "",
            "",
            "",
            "",
            "",
            List.of(
                new Clazz(
                    "",
                    "",
                    "",
                    "",
                    List.of(
                        new ClassDays(
                            "",
                            "",
                            List.of(DayOfWeek.MONDAY),
                            LocalTime.now().toString(),
                            LocalTime.now().toString(),
                            "",
                            "",
                            "",
                            "",
                            List.of("")
                        )
                    )
                )
            ),
            List.of(
                new Student(
                    "",
                    "",
                    List.of(""),
                    Map.of("", "")
                )
            ),
            List.of(
                new Professor(
                    "",
                    "",
                    "",
                    List.of(""),
                    List.of("")
                )
            )
        );

        

        PickerBox pickerBox = new PickerBox(
            List.of(new UniversityResolver(), new ClazzResolver(), new ClassDaysResolver(), new StudentResolver(), new ProfessorResolver())
        );

        University result = pickerBox.resolveRequest(request, "univ_1");

        Map<String, Object> resMap = PickerUtil.mapify(result);
        System.out.println(
            PickerUtil.jsonMap(resMap)
        );
    }


    public static void main(String[] args) {
        PickerBoxTest pickerBoxTest = new PickerBoxTest();
        pickerBoxTest.mapDoubleNestedObject();
    }


}
