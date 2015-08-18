package goods;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
public class GoodsStatistic {
        File f1=new File("商品编号.txt");     
        File f2=new File("每个顾客买的商品编号.txt");
        RandomAccessFile ra ,ra1;
        int sum;
        int cust = 0;
        int count = 0;
        int goodsId[] = new int[20] ;
        String a[] = new String[1000];
        int all = 0 ;
        GoodsStatistic(){
            fileIO();
        }
        void insert(int data){
            if(count==0)
                goodsId[count] = data;
            else{
                int i = count-1;
                while(goodsId[i]>data){
                    goodsId[i+1] = goodsId[i];
                    i--;
                }
                goodsId[i+1] = data;
            }
        }
        void sort(){
            for(int i=1;i<count;i++){
                for(int j=0;j<count-1;j++)
                    if(goodsId[j]>goodsId[j+1]){
                        int temp = goodsId[j];
                        goodsId[j] = goodsId[j+1];
                        goodsId[j+1] = temp;
                    }
            }
        }
        void dillwith1(String ss){
           StringTokenizer st = new StringTokenizer(ss," 	");
           int goodid = Integer.parseInt(st.nextToken());
           all++;
           int newcunt = Integer.parseInt(st.nextToken());
           if(newcunt==cust){
               goodsId[count] = goodid;
               count++;
           }
           else{
                try {
                    sort();
                    for(int i=0;i<count;i++)
                       ra1.writeBytes(goodsId[i]+"  ");
                    ra1.writeBytes("\r\n");
                    goodsId[0] = goodid;
                    count = 1;
                    cust = newcunt;
                } catch (IOException ex) {
                    Logger.getLogger(GoodsStatistic.class.getName()).log(Level.SEVERE, null, ex);
                }
                               
           } 
         
        }
	void fileIO(){                         //文件IO
		String s;
		try{
                    ra = new RandomAccessFile(f1,"rw");
                    ra1 = new RandomAccessFile(f2,"rw");
                    long lp = 0,lLength=ra.length();
                    ra.readLine();
                    lp = ra.getFilePointer();
                    //ra1.writeBytes("CUSTOMER	  NUMBERS"+"\r\n");
                    ra1.writeBytes("NUMBERS:"+"\r\n");
                    while(lp<lLength){
                            String ss = ra.readLine();        //获得一行
                           dillwith1(ss) ;
                           lp = ra.getFilePointer();   //获得当前的位置
                    }
                    try {
                        sort();
                       for(int i=0;i<count;i++)
                          ra1.writeBytes(goodsId[i]+"  ");
                       ra1.writeBytes("\r\n");
                     } catch (IOException ex) {
                       Logger.getLogger(GoodsStatistic.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    ra.close();
                    ra1.close();
		}
		catch(Exception e){	
		}	
	}
}

