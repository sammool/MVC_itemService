package hello.itemservice.web.basic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;

@Controller
@RequestMapping("/basic/items")
public class BasicItemController {
    
    private final ItemRepository itemRepository;

    @Autowired
    public BasicItemController(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();

        model.addAttribute("items", items);
        return "basic/items";
    }

    //테스트용 데이터 추가
    @PostConstruct //의존성 주입이 완료된 후에 실행되어야 하는 method에 사용
    public void init() {
        itemRepository.save(new Item("testA", 10000, 10));
        itemRepository.save(new Item("testB", 20000, 20));
    }
}
