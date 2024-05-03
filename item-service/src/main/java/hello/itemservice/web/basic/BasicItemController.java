package hello.itemservice.web.basic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String add(Model model){

        model.addAttribute("item", new Item());
        return "basic/addForm";
    }
   
    //@PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model
    ){
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);
        
        return "basic/item";
    }
   
    //@PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item, Model model){
    
        itemRepository.save(item);
        //model.addAttribute("item", item);
        
        return "basic/item";
    }

    //@PostMapping("/add")
    public String addItemV3(@ModelAttribute("item") Item item, Model model){
    
        itemRepository.save(item);
        //model.addAttribute("item", item);
        
        return "redirect:http://super-spoon-q5w94jx5xxph645p-8080.app.github.dev/basic/items/" + item.getId();
    }

    @PostMapping("/add")
    public String addItemV4(@ModelAttribute("item") Item item, RedirectAttributes redirectAttributes){
        
        log.info("item.open={}", item.getOpen());
        Item saveItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId",saveItem.getId());
        redirectAttributes.addAttribute("status", true);
        
        return "redirect:http://super-spoon-q5w94jx5xxph645p-8080.app.github.dev/basic/items/{itemId}";
    }


    @GetMapping("{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute("item") Item item){
        itemRepository.update(itemId, item);
        return "redirect:http://super-spoon-q5w94jx5xxph645p-8080.app.github.dev/basic/items/{itemId}";

    }

    //테스트용 데이터 추가
    @PostConstruct //의존성 주입이 완료된 후에 실행되어야 하는 method에 사용
    public void init() {
        itemRepository.save(new Item("testA", 10000, 10));
        itemRepository.save(new Item("testB", 20000, 20));
    }
}
