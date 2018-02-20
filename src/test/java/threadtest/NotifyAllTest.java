package threadtest;
/**
 * main sleep(3000)
 * t1 wait
 * t3 wait
 * t2 wait
 * main notifyAll()
 * t2 continue
 * t3 continue
 * t1 continue
 *
 */
public class NotifyAllTest {
	private static Object obj = new Object();  
    public static void main(String[] args) {  
  
    	ThreadC t1 = new ThreadC("t1",obj);  
    	ThreadC t2 = new ThreadC("t2",obj);  
    	ThreadC t3 = new ThreadC("t3",obj);  
        t1.start();
        
        t2.start();  
        
        t3.start();  
        
        try {  
            System.out.println(Thread.currentThread().getName()+" sleep(3000)");  
            Thread.sleep(3000);  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
  
        synchronized(obj) {  
            // 主线程等待唤醒。  
            System.out.println(Thread.currentThread().getName()+" notifyAll()");  
            obj.notifyAll();  
        }  
    }  
}

class ThreadC extends Thread{  
	static Object obj;
    public ThreadC(String name,Object obj){  
        super(name);  
        this.obj = obj;
    }  

    public void run() {
        synchronized (obj) {  
            try {  
                // 打印输出结果  
                System.out.println(Thread.currentThread().getName() + " wait");  

                // 唤醒当前的wait线程  
                obj.wait();  

                // 打印输出结果  
                System.out.println(Thread.currentThread().getName() + " continue");  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
}  


