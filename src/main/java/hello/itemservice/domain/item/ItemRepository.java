package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DB역할을할 Repository 클래스 생성
 */

@Repository
public class ItemRepository {

    //DB역할을 할 해쉬 맵 생성(실무에선 이런 방식 x)
    //ConcurrentHashMap<>()을 사용하는것이 좋다.(멀티 쓰레드로 동시에 접근할 시에 문제가 발생할 수 있다.)
    private static final Map<Long, Item> store = new HashMap<>(); //static
    private static long sequence = 0L;

    public Item save(Item item){
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id){
        return store.get(id);
    }

    public List<Item> findAll(){
     return new ArrayList<>(store.values());
    }

    //ItemParamDto class를 하나더 만들어서 객체화 시키는게 설계상 더 좋다
    //작은 프로젝트이기 때문에 아래와 같이 설정한다.
    public void update(Long itemId, Item updateParam){
        // item을 찾는다.
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    //test에서 사용될 메서드
    public void clearStore(){
        store.clear();
    }
}
