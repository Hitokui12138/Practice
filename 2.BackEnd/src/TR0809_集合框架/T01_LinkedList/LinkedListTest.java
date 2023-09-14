package TR0809_集合框架.T01_LinkedList;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Vector;

/**
 * 1.ArrayList,LinkedList,Vector的区别
 * 看截图
 * 相同点:都实现了List接口,有序可重复
 *
 * 2.LinkedList jdk1.2
 * 插入删除快,遍历效率低
 *
 * 3.Vector jdk1.0,线程安全
 * ArrayList可以替代它,就算追求线程安全,也不会使用这个
 * 将来可能会被废弃掉
 */
public class LinkedListTest {
    /*
    LinkedList底层是一个双向链表,以Node类为节点
    有prev和next两个对象,add时2的prev指向1的next
    插入时3的prev=1的next,2的prev=1的next
     */
    @Test
    public void test1(){
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        /*
        比起ArrayList多了几个操作首尾的方法
         */
        list.addFirst(4);
        list.addLast(5);
        list.getFirst();
        list.getLast();
        list.removeFirst();
        list.removeLast();
    }

    /*
    消息队列,kafka
     */
    @Test
    public void test2(){
        MyQueue<Integer> myQueue = new MyQueue();
        myQueue.offer(1);
        myQueue.offer(2);
        myQueue.offer(3);
        System.out.println(myQueue);
        System.out.println(myQueue.peek());
        System.out.println(myQueue.poll());
        System.out.println(myQueue.poll());
        System.out.println(myQueue);
    }
    /*
    栈
     */
    @Test
    public void test3(){
        MyStack myStack = new MyStack();
        myStack.push(1);
        myStack.push(2);
        myStack.push(3);
        System.out.println(myStack);
        System.out.println(myStack.peek());
        System.out.println(myStack.poll());
        System.out.println(myStack.poll());
        System.out.println(myStack);
    }
    /*
    LinkedList相当于双向队列+栈
     */


    /*
    Vector jdk1.0 很多类都是线程安全的,
    默认大小也是10,两倍扩容,线程都安全
    追求效率应该使用ArrayList,不过就算追求线程安全,也不会使用这个
     */
    public void test4(){
        Vector vector = new Vector();
        vector.addElement(1);
    }
}
/*
用LinkedList模拟单向队列,先进先出
入列 void offer
出列 Object poll
仅查看下一个要出列的 Object peek
 */
class MyQueue<T>{
    LinkedList<T> list;

    public MyQueue() {
        list = new LinkedList<>();
    }
    public void offer(T o){
        list.addFirst(o);
    }
    public T poll(){
        return list.removeLast();
    }
    public T peek(){
        return list.getLast();
    }

    @Override
    public String toString() {
        return list.toString();//集合重写过toString
    }
}
/*
栈,
void push
Object poll
Object peek
 */
class MyStack<T>{
    LinkedList<T> list;
    public MyStack() {
        list = new LinkedList<>();
    }
    public void push(T o){
        list.addFirst(o);
    }
    public T poll(){
        return list.removeFirst();
    }
    public T peek(){
        return list.getFirst();
    }
    @Override
    public String toString() {
        return list.toString();//集合重写过toString
    }
}
