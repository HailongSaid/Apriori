
package goods;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.StringTokenizer;

public class GoodsToNumber {
        int allGoodCount = 0;
        int cust = 0;
        int count = 0;
        int goodid[] = new int[20] ;
        String a[] = new String[1000];
        void print(){
            int i = 0;
             for(i=0;i<a.length;i++){
                if(!a[i].equals("")) 
                    System.out.print(a[i]+" ");
                else 
                    break;
             }
             System.out.println();
              System.out.println("�ܹ���"+i);
        }
        GoodsToNumber(){
            for(int i=0;i<a.length;i++)
                a[i] = "";
            fileIO();
        }
        int dillwith(String ss){
            StringTokenizer st = new StringTokenizer(ss,"	");
            int newcun = Integer.parseInt(st.nextToken());
            st.nextToken();
            String s = st.nextToken();
            for(int i=0;i<a.length;i++ ){
                if(a[i].equals("")){
                    a[i] = s;
                    return i;
                } 
                else if(a[i].equals(s))
                    return i;
            }
            return -1;    
        }
	void fileIO(){                         //�ļ�IO
		File f1=new File("assocs.txt");          //�����������ΪĬ�ϵ�·�����ڸ���Ŀ����һ��Ŀ¼�£�Ҳ����
		File f2=new File("��Ʒ���.txt");
                RandomAccessFile ra ,ra1;
		String s;
		try{
			 ra = new RandomAccessFile(f1,"rw");
			 ra1 = new RandomAccessFile(f2,"rw");
			 long lp = 0,lLength=ra.length();
                         String ss1 = ra.readLine();
                         lp = ra.getFilePointer();
                       
                        ra1.writeBytes("NUMBER     CUSTOMER	  TIME	     PRODUCT"+"\r\n");
			 while(lp<lLength){
				 String ss = ra.readLine();        //���һ��
				 allGoodCount++;
                                 int l = dillwith(ss) + 1;
                                 ra1.writeBytes(l+"  "+ss+"\r\n");
                                 lp = ra.getFilePointer();   //��õ�ǰ��λ��
			 }
			 ra.close();
                         ra1.close();
			 print();
		}
		catch(Exception e){	
		}
	}

}
