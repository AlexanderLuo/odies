# odies
orm-redis

1.springboot based

2.using redis jpa-likely 



```
   @Repository
   public interface OrderRepository extends OdiesRepository<Order> {}
   
   1.save
    orderRepository.save(new Order())
   2.find
    orderRepository.findById("orderId")
   3.findAll
    orderRepository.findAll()
   4.findByPage
    orderRepository.findAll(PageOf.of(0,10))
   
```


## TODO

    1.more feature
    2.doc

