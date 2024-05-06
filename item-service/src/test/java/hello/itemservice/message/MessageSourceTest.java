package hello.itemservice.message;

import java.util.Locale;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

@SpringBootTest
public class MessageSourceTest {
    
    @Autowired
    MessageSource ms;
    
    @Test
    void helloMessage(){
        String result = ms.getMessage("hello", null,LocaleContextHolder.getLocale());
        Assertions.assertThat(result).isEqualTo("안녕");
    }

    @Test
    void def(){
        System.out.println(Locale.getDefault());
    }
}
