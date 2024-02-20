package persistence.sql.ddl;

import jakarta.persistence.Transient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import persistence.domain.Person;
import persistence.sql.domain.Table;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@DisplayName("ColumnQueries 의")
class TableTest {

    @Nested
    @DisplayName("toQuery 메서드는")
    class ToQuery {

        @Test
        @DisplayName("클래스의 필드값이 Transient인 경우 포함하지 않는다.")
        void fields() {
            //given
            Class<Person> personClass = Person.class;
            Table table = Table.of(personClass);
            List<String> fieldNames = Arrays.stream(personClass.getDeclaredFields())
                    .filter(it -> it.isAnnotationPresent(Transient.class))
                    .map(Field::getName)
                    .collect(Collectors.toList());

            //when
            String query = table.getFieldQueries();

            //then
            Assertions.assertThat(query).doesNotContain(fieldNames);
        }
    }

}
