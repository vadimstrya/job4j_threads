package ru.job4j.pool.search;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ParallelSearchTest {

    @Test
    public void whenSizeMoreThan10AndFound() {
        String[] array = new String[]{
                "Privet", "Bonjour", "Hello",
                "Privet2", "Bonjour2", "Hello2",
                "Privet3", "Bonjour3", "Hello3",
                "Privet4", "Bonjour", "Hello4"
        };
        String obj = "Bonjour3";
        assertThat(ParallelSearch.search(obj, array)).isEqualTo(7);
    }

    @Test
    public void whenSizeMoreThan10AndFoundLast() {
        String[] array = new String[]{
                "Privet", "Bonjour", "Hello",
                "Privet2", "Bonjour2", "Hello2",
                "Privet3", "Bonjour3", "Hello3",
                "Privet4", "Bonjour", "Hello4"
        };
        String obj = "Hello4";
        assertThat(ParallelSearch.search(obj, array)).isEqualTo(11);
    }

    @Test
    public void whenSizeMoreThan10AndFoundFirst() {
        String[] array = new String[]{
                "Privet", "Bonjour", "Hello",
                "Privet2", "Bonjour2", "Hello2",
                "Privet3", "Bonjour3", "Hello3",
                "Privet4", "Bonjour", "Hello4"
        };
        String obj = "Privet";
        assertThat(ParallelSearch.search(obj, array)).isEqualTo(0);
    }

    @Test
    public void whenSizeLessThan10AndFound() {
        String[] array = new String[]{
                "Privet", "Bonjour", "Hello",
                "Privet2", "Bonjour2", "Hello2",
        };
        String obj = "Bonjour2";
        assertThat(ParallelSearch.search(obj, array)).isEqualTo(4);
    }

    @Test
    public void whenSizeMoreThan10AndNotFound() {
        String[] array = new String[]{
                "Privet", "Bonjour", "Hello",
                "Privet2", "Bonjour2", "Hello2",
                "Privet3", "Bonjour3", "Hello3",
                "Privet4", "Bonjour", "Hello4"
        };
        String obj = "Bonjour33";
        assertThat(ParallelSearch.search(obj, array)).isEqualTo(-1);
    }

    @Test
    public void whenSizeLessThan10AndNotFound() {
        String[] array = new String[]{
                "Privet", "Bonjour", "Hello",
                "Privet2", "Bonjour2", "Hello2",
        };
        String obj = "Bonjour22";
        assertThat(ParallelSearch.search(obj, array)).isEqualTo(-1);
    }

    @Test
    public void whenDifferentTypes() {
        String[] array = new String[]{
                "Privet", "Bonjour", "Hello",
                "Privet2", "Bonjour2", "Hello2",
        };
        BigDecimal obj = new BigDecimal(123);
        assertThatThrownBy(() -> ParallelSearch.search(obj, array)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void whenObjectNull() {
        String[] array = new String[]{
                "Privet", "Bonjour", "Hello",
                "Privet2", "Bonjour2", "Hello2",
        };
        assertThatThrownBy(() -> ParallelSearch.search(null, array)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void whenArrayNull() {
        String obj = "Bonjour";
        assertThatThrownBy(() -> ParallelSearch.search(obj, null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void whenArrayEmpty() {
        String[] array = new String[]{};
        String obj = "Bonjour";
        assertThatThrownBy(() -> ParallelSearch.search(obj, array)).isInstanceOf(IllegalArgumentException.class);
    }
}
