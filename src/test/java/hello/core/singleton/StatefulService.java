package hello.core.singleton;

public class StatefulService {

//    private int price; // 상태를 유지하는 필드 <= 여러 클라이언트가 접근할 수 있어 위험함

    public int order(String name, int price) {
        System.out.println("name = " + name + " price = " + price);
        //this.price = price; // <= 여기가 문제 : 상태를 저장
        return price; // 단순 price return으로 변경
    }

//    public int getPrice() {
//        return price;
//    }
}
