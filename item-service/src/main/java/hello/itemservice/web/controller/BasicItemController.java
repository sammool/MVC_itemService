package hello.itemservice.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hello.itemservice.domain.item.DeliveryCode;
import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import hello.itemservice.domain.item.ItemType;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Controller
//@RequestMapping("/basic/items")
public class BasicItemController {
    
    private final ItemRepository itemRepository;

    //모든 model에 다 담김
    @ModelAttribute("regions")
    public Map<String, String> regions(){
        Map<String, String> regions = new LinkedHashMap<>();
        regions.put("SEOUL", "서울");
        regions.put("BUSAN","부산");
        regions.put("JEJU","제주");
        return regions;
    }

    @ModelAttribute("itemTypes")
    public ItemType[] itemTypes(){
        ItemType[] values = ItemType.values();
        return values;
    }

    @ModelAttribute("deliveryCodes")
    public List<DeliveryCode> deliveryCodes(){
        List<DeliveryCode> deliveryCodes = new ArrayList<>();
        deliveryCodes.add(new DeliveryCode("FAST", "빠른 배송"));
        deliveryCodes.add(new DeliveryCode("NORMAL", "일반 배송"));
        deliveryCodes.add(new DeliveryCode("SLOW", "느린 배송"));
        return deliveryCodes;
    }

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
    public String addItemV4(@ModelAttribute("item") Item item, RedirectAttributes redirectAttributes, Model model){

        //검증 오류 결과를 보관
        Map<String, String> errors = new HashMap<>();

        //검증 로직
        if(!StringUtils.hasText(item.getItemName())){
            errors.put("itemName", "상품 이름은 필수입니다");
        }
        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000){
            errors.put("price", "가격은 1,000 ~ 1,000,000 까지 허용합니다.");
        }
        if(item.getQuantity() == null || item.getQuantity()>=9999){
            errors.put("quantity","수량은 최대 9,999 까지 허용합니다.");
        }

        //특정 필드가 아닌 복합 룰 검증
        if(item.getPrice() != null && item.getQuantity() != null){
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice < 10000){
                errors.put("globalError", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice);
            }
        }

        //검증에 실패하면 다시 입력 폼으로
        if(!errors.isEmpty()){
            log.info("errors={}",errors);
            model.addAttribute("errors", errors);
            return "basic/addForm";
        }

        //성공 로직
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
