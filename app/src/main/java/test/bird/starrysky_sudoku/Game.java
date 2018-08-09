package test.bird.starrysky_sudoku;


/**
 * Created by root on 18-7-10.
 */

public class Game {

    private  String data[] = new String[40];
    private static int [][] oldData = new int[9][9];
    private static int sudoku[][]= new int[9][9] ;
    private int usedNumber[] = new int[81] ;
    private static int finalNumber[][] = new int[9][9] ;
    private int smallCell[][] = new int[81][9] ;
    private static int undoNumber[][] = new int[81][81];
    private MainActivity mainActivity = new MainActivity();
    private static int count ;
    protected static int starPassConut = 0;
    private static boolean flag = false ;

    public Game (){
        initData();
        chooseLevel(mainActivity.getGameLevel());
        count = 0;
        for(int m = 0; m < 9; m++){
            for (int n=0;n<9;n++){
                finalNumber[m][n] = data[mainActivity.getGameLevel()].charAt(m+n*9)-'0';
            }
        }
        for (int i=0;i<81;i++){
            undoNumber[count][i] = data[mainActivity.getGameLevel()].charAt(i)-'0';
        }
    }

    public void chooseLevel(int j){
        for (int i=0;i<13;i++){
            if (i == j){
                for(int m = 0; m < 9; m++){
                    for (int n=0;n<9;n++){
                        sudoku[m][n] = data[i].charAt(m+n*9)-'0';
                    }
                }
            }
        }
    }

    public String getNumber (int x, int y){
        if (undoNumber[count][x+9*y] == 0){
            return "";
        }else {
            return ""+undoNumber[count][x+9*y];
        }
    }

    public int getIntNumber(int x, int y){
        if (undoNumber[count][x+9*y] == 0){
            return 0;
        }else {
            return undoNumber[count][x+9*y];
        }
    }

    //set number
    public void setTitle(int n,int x,int y){
        usedNumber[x+9*y] = n;
        int[] c = getUsed(x, y);
        for(int t : c){
            if (n == t){
                flag = true ;
            }
        }
        if (!flag){
            finalNumber[x][y] = n;
        }
        count++;
        sudoku[x][y] = n;
        for (int i=0;i<9;i++){
            for (int j=0;j<9;j++){
                undoNumber[count][i+9*j] = sudoku[i][j] ;
            }
        }
    }

    public int usedNumber(int x, int y) {
        return usedNumber[x+9*y];
    }

    public boolean getOldNumber(int x,int y){
        boolean state = true ;
        for(int i = 0; i < 9; i++){
            for (int j=0;j<9;j++){
                oldData[i][j] = data[mainActivity.getGameLevel()].charAt(i+j*9)-'0';
            }
        }
        if (oldData[x][y] == 0){
            state = false ;
        }
        return state ;
    }
    // win or lose
    public boolean youWin(){
        boolean win=false;
        int t = 0 ;
        for (int i=0;i<9;i++){
            for (int j=0;j<9;j++){
                if (finalNumber[i][j] != 0  ){
                    t++;
                    if (t == 81){
                       win = true ;
                       if (starPassConut <mainActivity.getGameLevel()){
                           if (mainActivity.gameLevelPass<mainActivity.getGameLevel()) {
                               mainActivity.gameLevelPass++;
                           }
                           starPassConut = mainActivity.gameLevelPass;
                       }
                    }
                }
            }
        }
        return win ;
    }
    // get used number;
    public int[] getUsed(int x,int y){
        int c[]=new int[9];
        //x列
        for(int i=0;i<9;i++)
        {
            if(sudoku[x][i]!=0)
            {
                c[sudoku[x][i]-1]=sudoku[x][i];
            }
        }
        //y排
        for(int i=0;i<9;i++)
        {
            if(sudoku[i][y]!=0)
            {
                c[sudoku[i][y]-1]=sudoku[i][y];
            }
        }
        //小九宫格
        x=(x/3)*3;
        y=(y/3)*3;
        for(int i=0;i<9;i++)
        {
            if(sudoku[x+i%3][y+i/3]!=0)
            {
                c[sudoku[x+i%3][y+i/3]-1]=sudoku[x+i%3][y+i/3];
            }
        }
        return c;
    }

    public int getUndoNumber(int x,int y) {
        if (undoNumber[count][x+9*y] == 0){
            return 0 ;
        }else {
            return undoNumber[count][x+9*y] ;
        }
    }

    public boolean isFlag() {
        return flag;
    }

    public  void setFlag(boolean flag) {
        Game.flag = flag;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        for (int i=0;i<9;i++){
            for (int j=0;j<9;j++){
              this.sudoku[i][j] = undoNumber[count][i+9*j];
            }
        }
    }


    // sudoku mark
    public String getSmallCell(int x,int y) {
        return smallCell[x][y]+"";
    }

    public void setCellTitle(int n, int x, int y){
        if (smallCell[x][y]!=0){
            smallCell[x][y]=0;
        }else
        smallCell[x][y] = n;
    }

    public void initData(){
        data[1] = "352476189168952734749813625425697813683241597971538462897365241214789356536124900" ;
        data[4] = "030647080709000206010903040301070804800304002402050603080501020103000409020439060";
        data[3] = "030607000506001400091000050300010067000306000650020004060000240003500609000709010";
        data[2] = "052006000160900004049803620400000800083201590001000002097305240200009056000100970" ;
        data[5] = "048000920003815400500000001001506800070209010002108500700000003009751200014000750";
        data[6] = "000001000004008500020790010008010062007805300430060700008072050005400100000100000";
        data[7] = "720000003000008040060000890100830005004010900300045002051000020030600000200000018";
        data[8] = "007040091200009500580000006090050000700203005000010020400000081001700009065020700";
        data[9] = "040500000060200370010073000005102049007030800280907600000760080471005030000004090";
        data[10] = "037002040400096003001300607810000200020000060006000051905007800300560007070900530";
        data[11] = "613507004000009007007004806789000002000000000400000683802600700500900000100802495";
        data[12] = "301845609000060000500309002109000305630000091807000204200504003000010000403297806";
    }
}
