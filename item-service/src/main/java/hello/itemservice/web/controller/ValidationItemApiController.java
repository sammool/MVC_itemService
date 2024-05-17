package hello.itemservice.web.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hello.itemservice.web.controller.form.ItemSaveForm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController // @ReponseBody 포함
@RequestMapping("/validation/api/items")
public class ValidationItemApiController {
    
    //@RequestBody는 HttpMessageConverter 단계에서 데이터를 객체로 
    //변경하지 못하면 이후 단계 진행 불가
    
    @PostMapping("/add")
    public Object addItem(@RequestBody @Validated ItemSaveForm form, BindingResult bindingResult){
        
        log.info("API 컨트롤러 호출");
        if(bindingResult.hasErrors()){
            log.info("검증 오류 발생 errors={}", bindingResult);
            return bindingResult.getAllErrors();
        }

        log.info("성공 로직 실행");
        return form;
    }
}
