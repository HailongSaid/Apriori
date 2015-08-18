package goods;
import java.util.StringTokenizer;
import java.util.Vector;
class Node{ 
    Node(String good ,int number){
        this.good = good;
        this.number = number;
    }
    Node(String good){
        this.good = good;
    }
    Node(){
    }
    String good;
    int number;
}
public class GoodsMain {
    GoodsStatistic g;
    GoodsCombinStatistic g1;
    GoodsToNumber aa;
    double minconf;
    GoodsMain(double fazhi,double minconf){
        this.minconf =  minconf;
        for(int i=1;i<=20;i++)
            goods.add(new Node(" "+i+" ",0));
        aa = new GoodsToNumber();
        g = new GoodsStatistic();
        g1 = new GoodsCombinStatistic();
//        allNumber = g.sum;
        allNumber = 1001;
        Vector pv = new Vector();
        while(goods.size()>=1){
            count();
            System.out.println("组合成新的商品组合后：");
            printByGoodName();
            deletegoods(fazhi);
            if(goods.size()>1){
                System.out.println("删除不够的商品后：");
                printByGoodName();
                pv = goods;
                goods = combinGoods(goods);
            }
            else if(goods.size()==1){
                 System.out.println("处理后的商品组合为：");
                 printByGoodName();
                 break;
            }
            else{
                goods = pv;
                System.out.println("由于新的组合的商品不满足最小支持度，故处理后的频繁项集为：");
                printByGoodName();
                break;
            }
         }
        if(goods.size()==0){
                goods = pv;
                System.out.println("由于新的组合的商品不满足最小支持度，故处理后的频繁项集为：");
                printByGoodName();
        }
        System.out.println("所得到的关联规则如下：");
        System.out.println("关联规则                       置信度");
        for(int i=0;i<goods.size();i++){
            Node node = get(goods,i);
            GoodRule goodrule = new GoodRule(node,minconf);
        }
       
     }
    
    Node get(Vector pv,int index){
        return  (Node)pv.get(index);
    }
    void printByGoodName(){
        for(int i=0;i<goods.size();i++){
            String na = get(goods,i).good;
            StringTokenizer st = new StringTokenizer(na," ");
            while(st.hasMoreTokens()){
                int nn = Integer.parseInt(st.nextToken());
                System.out.print(aa.a[nn-1]+" ");
            }
            System.out.println((1.0*get(goods,i).number/allNumber));
         }
    }
     int allNumber = 0;
     int count = 0;
     Vector deletegoods = new Vector();
     Vector goods = new Vector();
     void deletegoods(double fazhi){
         for(int i=0;i<goods.size();){
             if(get(goods,i).number<allNumber*fazhi){
                 deletegoods.add(get(goods,i));
                 goods.remove(i);
             }
             else
                 i++;
         }
     }
     String combinString(String s1,String s2){//对两个商品字符串合并
         String s = s1; 
         StringTokenizer st = new StringTokenizer(s2," ");
         while(st.hasMoreTokens()){
            String s3 = st.nextToken();
            if(s1.indexOf(" "+s3+" ")==-1)
                s = s+" "+s3;
         }
          return s;
     }
     Vector combinGoods(Vector goods){
         Vector pv = new Vector();
         for(int i=0;i<goods.size()-1;i++){
             for(int j=i+1;j<goods.size();j++){
                 String cs = combinString(get(goods,i).good,get(goods,j).good);
                 int k = 0;
                 for(;k<deletegoods.size();k++)
                     if(containString(cs,get(deletegoods,k).good))
                         break;
                 if(k==deletegoods.size()){
                     int m=0;
                     for(;m<pv.size();m++)
                         if(containString(get(pv,m).good," "+cs+" "))
                             break;
                     if(m==pv.size())
                         pv.add(new Node(" "+cs+" "));
                 }
             }
         }
         
         return pv;   
     }
     void getallNumber(){
         for(int i=0;i<goods.size();i++){
             allNumber = allNumber+get(goods,i).number;
         }
     }
     boolean containString(String s,String s1){ //s包含s1
         StringTokenizer st = new StringTokenizer(s1," ");
         String gg[] = new String[100];
         int j =0;//[0 j-1]
         while(st.hasMoreTokens()){
            gg[j] = " "+st.nextToken()+" ";
            j++;
         }
         int j1 =0;
        while(j1<j&&s.indexOf(gg[j1])!=-1)
            j1++;
        if(j1==j)
           return true;
        return false;
               
     }
    void count(){                //计算出每种商品组合的数量   //总数没对上 
         for(int i=0;i<goods.size();i++){
             StringTokenizer st = new StringTokenizer(get(goods,i).good," ");
             String gg[] = new String[100];
             int j =0;//[0 j-1]
             while(st.hasMoreTokens()){
                 gg[j] = " "+st.nextToken()+" ";
                 j++;
             }
             for(int k=0;k<g1.count;k++){
                 int j1 =0;
                 while(j1<j&&g1.a[k].indexOf(gg[j1])!=-1)
                     j1++;
                 if(j1==j)
                     get(goods,i).number = get(goods,i).number+g1.Nunber[k];
             }
             
         }
             
             
     }
    int count(String s){
        int countnumber = 0;
         StringTokenizer st = new StringTokenizer(s," ");
             String gg[] = new String[100];
             int j =0;//[0 j-1]
             while(st.hasMoreTokens()){
                 gg[j] = " "+st.nextToken()+" ";
                 j++;
             }
             for(int k=0;k<g1.count;k++){
                 int j1 =0;
                 while(j1<j&&g1.a[k].indexOf(gg[j1])!=-1)
                     j1++;
                 if(j1==j)
                     countnumber = countnumber+g1.Nunber[k];
             }
             return countnumber;
    }
     public static void main(String[] args) {
          double d = 0.2;
          double minconf = 0.8;
          GoodsMain gf = new GoodsMain(d,minconf);
      }
     
     
 class GoodRule {
    double minconf;
    Vector H = new Vector();
    Vector deleteH = new Vector();
    Node f = new Node();
    int numberToal ;//f总得数量
    GoodRule(Node node,double minconf){
       f.good = node.good;
       this.minconf = minconf;
       H = stringToVector(node.good);
       f.number = H.size();
       numberToal = node.number;
       genRule(H);
    }
    Node deleteString(Node s1,Node s2){ //s1-s2
         StringTokenizer st = new StringTokenizer(s2.good," ");
         while(st.hasMoreTokens())
             s1.good.replaceFirst(" "+st.nextToken()+" "," ");
         s1.number = s1.number - s2.number;
         return s1;
    }
     String deleteString(String s1,String s2){ //s1-s2
         StringTokenizer st = new StringTokenizer(s2," ");
         while(st.hasMoreTokens())
             s1 = s1.replaceAll(" "+st.nextToken()+" "," ");
         return s1;
    }
    Node get(Vector pv,int index){
        return (Node)pv.elementAt(index);
    }
        
    void genRule(Vector H){
        if(!H.isEmpty()&&f.number-get(H,0).number>=1){
            for(int i=0;i<H.size();){
                String f_h = deleteString(f.good,get(H,i).good);
                double conf = 1.0*numberToal/count(f_h);
                if(conf>minconf){
                    printByRule(f_h,get(H,i).good,conf);
                    i++;
                }
                else{
                    deleteH.add(get(H,i));
                    H.remove(i);
                }
           }
            H = addH(H);
            genRule(H);
        }
    }
    void printByRule(String s1,String s2,double db){
            StringTokenizer st = new StringTokenizer(s1," ");
            while(st.hasMoreTokens()){
                int nn = Integer.parseInt(st.nextToken());
                System.out.print(aa.a[nn-1]+" ");
            }
            System.out.print("==>");
            st = new StringTokenizer(s2," ");
            while(st.hasMoreTokens()){
                int nn = Integer.parseInt(st.nextToken());
                System.out.print(aa.a[nn-1]+" ");
            }
             System.out.println("   "+db);
         }
    Vector addH(Vector H){                //关联规则中H集合进行组合
        Vector pv = new Vector();
         for(int i=0;i<H.size()-1;i++){
             for(int j=i+1;j<H.size();j++){
                 String cs = combinString(get(H,i).good,get(H,j).good);
                 int k = 0;
                 for(;k<deleteH.size();k++)
                     if(containString(cs,get(deleteH,k).good))
                         break;
                 if(k==deleteH.size())
                         pv.add(new Node(" "+cs+" ",countStringNunmber(cs)));
             }
         }
         return pv;   
     } 
    int countStringNunmber(String s){
        int count = 0;
        StringTokenizer st = new StringTokenizer(s," ");
        while(st.hasMoreTokens()){
            count++;
            st.nextToken();
        }
        return count;   
    }
    Vector stringToVector(String s){
        Vector pv = new Vector();
        StringTokenizer st = new StringTokenizer(s," ");
        while(st.hasMoreTokens())
            pv.addElement(new Node(" "+st.nextToken()+" ",1));
        return pv;
    }
}

}
