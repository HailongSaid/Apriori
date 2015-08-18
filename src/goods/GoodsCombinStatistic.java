package goods;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.StringTokenizer;
public class GoodsCombinStatistic {
        int cust = 0;
        int count = 0; //【0 count-1]
        int Nunber[] = new int[1000];//各种商品组合的次数
        String a[] = new String[1000]; //各种商品组合
        GoodsCombinStatistic(){
            for(int i=0;i<a.length;i++)
                a[i] = "";
            for(int i=0;i<Nunber.length;i++)
                Nunber[i] = 0;
            fileIO();
        }
        void dillwith(String ss){
            if(count==0){
                Nunber[0] = 1;
                a[0] = " "+ss;
                count++;
            }
            int i=0;
            for(;i<count;i++ )
                 if(a[i].equals(ss)){
                   Nunber[i] = Nunber[i]+1;
                   break;
                 }
            if(i==count){
                Nunber[count] = 1;
                a[count] = " "+ss;
                count++;
            }
                
        }
	void fileIO(){                         //文件IO
		File f1=new File("每个顾客买的商品编号.txt");          //在这种情况下为默认的路径（在该项目的下一级目录下）也可以
		File f2=new File("各种组合商品购买的次数.txt");
                RandomAccessFile ra ,ra1;
		String s;
		try{
			 ra = new RandomAccessFile(f1,"rw");
			 ra1 = new RandomAccessFile(f2,"rw");
			 long lp = 0,lLength=ra.length();
                         ra.readLine();
                         lp = ra.getFilePointer();
                         ra1.writeBytes("COUNTS                 NUMBER"+"\r\n");
			 while(lp<lLength){
			        String ss = ra.readLine();        //获得一行
				dillwith(ss);
                                lp = ra.getFilePointer();   //获得当前的位置
			 }
                         for(int i=0;i<count;i++)
                             ra1.writeBytes(Nunber[i]+"          "+a[i]+"\r\n");
			 ra.close();
                         ra1.close();
		}
		catch(Exception e){	
		}
	}   
}
