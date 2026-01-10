## 回调地狱
```javascript
A(function(res){
    console.log(res);
    B(function(res){
        console.log(res);
        C(function(res){
            console.log(res);
        })
    })
})
```
## Promise,链式调用
```javascript
A().then(res=>{
    console.log(res);
    return B()
}).then(res=>{
    console.log(res);
    return C()
}).then(res=>{
    console.log(res);
})

```
## Async Await
```javascript
async function test(){
    const resA = await A();
    console.log(resA);
    const resB = await B();
    console.log(resB);
    const resC = await C();
    console.log(resC);
}
```