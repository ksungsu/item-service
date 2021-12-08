package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor //constructor 자동 생성
public class BasicItemController {

    private final ItemRepository itemRepository;

    /**
     * itemRepository에 items 속성 추가
     */
    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    /**
     * 상품상세용 페이지 GetMapping
     * 경로변수 PathVariable
     */
    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model){

        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    /**
     * 상품 등록 form(조회)
     */
    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }

    /**
     * 상품 등록
     * 쿼리 파라미터 방식, @RequestParam
     */

//    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model){
        Item item = new Item(itemName, price, quantity);

        itemRepository.save(item);

        //model 속성을 추가해서 html view템플릿에 item 값으로 치환하여 화면에 출력해준다.
        model.addAttribute("item", item);

        return "basic/item";
    }

    /**
     * @ModelAttribute를 이용한 item 객체 생성
     * set
     * model 속성 자동 생성 @ModelAttribute("item")
     */

//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item){

        itemRepository.save(item);
//        model.addAttribute("item", item);

        return "basic/item";
    }


//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item){

        //클래스 Item -> item 이름으로(자동으로 소문자화) model 속성을 자동으로 넣어준다

        itemRepository.save(item);
//        model.addAttribute("item", item);

        return "basic/item";
    }

    @PostMapping("/add")
    public String addItemV4(Item item){ //@ModelAttribute 생략
        //클래스 Item -> item 이름으로(자동으로 소문자화) model 속성을 자동으로 넣어준다
        itemRepository.save(item);
        return "basic/item";
    }


    /**
     * 테스트용 데이터 추가
     */

    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}

