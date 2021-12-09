package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

//    @PostMapping("/add")
    public String addItemV4(Item item){ //@ModelAttribute 생략
        //클래스 Item -> item 이름으로(자동으로 소문자화) model 속성을 자동으로 넣어준다
        itemRepository.save(item);
        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV5(Item item){ //@ModelAttribute 생략
        //클래스 Item -> item 이름으로(자동으로 소문자화) model 속성을 자동으로 넣어준다
        itemRepository.save(item);

        //URL 인코딩이 안되므로 RedirectAttributes를 사용해야한다.
        return "redirect:/basic/items/"+item.getId();
    }


    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes){
        Item savedItem = itemRepository.save(item);

        //redirectAttributes로 생성된 itemId가 return {itemId}로 치환된다.
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        //등록 완료 text 추가하기 위해 선언
        redirectAttributes.addAttribute("status", true);
        //URL 인코딩이 안되므로 RedirectAttributes를 사용해야한다.
        return "redirect:/basic/items/{itemId}";
    }


    /**
     * 상품 수정 form
     */
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    /**
     * 상품 수정 post
     * redirect
     */
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item){
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
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

