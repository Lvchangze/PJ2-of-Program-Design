import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Project2 extends Application {
     private nowblock block = new nowblock();//当前方块
     private nextblock nextblock = new nextblock();//下一个方块
     private downblock downblock = new downblock();//底部提醒的方块
     private Iblock Iblock = new Iblock();
     private Jblock Jblock = new Jblock();
     private Lblock Lblock = new Lblock();
     private Oblock Oblock = new Oblock();
     private Sblock Sblock = new Sblock();
     private Tblock Tblock = new Tblock();
     private Zblock Zblock = new Zblock();
     private Bigtable Bigtable = new Bigtable(); //界面数组 16 * 13
     private Smalltable Smalltable = new Smalltable();//下一个方块提示界面数组  4 * 4
     int score = 0;

     private String url = getClass().getResource("游戏音效.mp3").toString();
     private Media media= new Media(url);
     private MediaPlayer mediaPlayer = new MediaPlayer(media);

     private String url1 = getClass().getResource("云烟成雨.mp3").toString();
     private Media media1= new Media(url1);
     private MediaPlayer mediaPlayer1 = new MediaPlayer(media1);
     private String url2 = getClass().getResource("岁月神偷.mp3").toString();

     private Media media2= new Media(url2);
     private MediaPlayer mediaPlayer2 = new MediaPlayer(media2);

     private String url3 = getClass().getResource("往后余生.mp3").toString();
     private Media media3= new Media(url3);
     private MediaPlayer mediaPlayer3 = new MediaPlayer(media3);

     private String url4 = getClass().getResource("月牙湾.mp3").toString();
     private Media media4= new Media(url4);
     private MediaPlayer mediaPlayer4 = new MediaPlayer(media4);

     private String url5 = getClass().getResource("消行音效.mp3").toString();
     private Media media5= new Media(url5);

     private String url6 = getClass().getResource("旋转音效.mp3").toString();
     private Media media6= new Media(url6);

     private String url7 = getClass().getResource("下坠音效.mp3").toString();
     private Media media7= new Media(url7);

     private ImageView imageView = new ImageView("游戏开始界面.jpg");

     private Button btStart = new Button("开始游戏");
     private Button btEnd = new Button("结束游戏");
     private ComboBox btMeal = new ComboBox();
     private Button bthelp = new Button("游戏帮助");
     private Button btPause = new Button("暂停");
     private Button btplay = new Button("开始");
     private Button btReplay = new Button("重玩");

     private Stage primaryStage = new Stage();

     private Image blockColor = new Image("黑色.jpg");
     private Image backgroundColor = new Image("绿色.jpg");
     private Image noteColor = new Image("黄色.jpg");

     private BorderPane mainBorderPane = new BorderPane();//父节点
     private BorderPane primaryborderPane = new BorderPane();//初始场景
     private GridPane mainGridPane = new GridPane();//主界面
     private GridPane smallGridPane = new GridPane();//下一方块提示界面

     private Scene primaryscene = new Scene(primaryborderPane, 1200, 600);
     private Scene mainscene = new Scene(mainBorderPane,1200,600);

     int [] rank = new int[11];

     @Override
    public void start (Stage stage){
         btStart.setMinSize(175,75);
         btStart.setStyle("-fx-text-fill: red" );

         btEnd.setMinSize(175,50);
         bthelp.setMinSize(175,50);

         btMeal.setMinWidth(175);
         btMeal.setMinHeight(50);
         btMeal.setValue("游戏设置");
         btMeal.getItems().addAll("切换背景音乐1","切换背景音乐2");

         HBox hBox = new HBox(335);
         hBox.getChildren().addAll(btEnd,btMeal);

         primaryborderPane.getChildren().add(imageView);
         mediaPlayer.play();
         mediaPlayer.setAutoPlay(true);
         mediaPlayer.setCycleCount(mediaPlayer1.INDEFINITE);
         imageView.setFitWidth(1200);
         imageView.setFitHeight(600);

         primaryborderPane.setBottom(btStart);
         primaryborderPane.setPadding(new Insets(0,0,80,517));
         primaryborderPane.setTop(hBox);
         bthelp.setStyle("-fx-text-fill: GREEN" );
         primaryborderPane.setRight(bthelp);

         clickbtEnd();
         clickbtStart();
         clickbtMeal();
         clickbthelp();

         this.primaryStage.setTitle("Tetris");
         this.primaryStage.setScene(primaryscene);
         this.primaryStage.show();
    }

    private void addBlock() {
        mainGridPane.setAlignment(Pos.CENTER);
        mainGridPane.setGridLinesVisible(true);
        mainBorderPane.setCenter(mainGridPane);

        VBox rigntvBox =new VBox(25);//右边
        btPause.setMinSize(175,50);
        btplay.setMinSize(175,50);
        btReplay.setMinSize(175,50);

        Text textNext = new Text("下一个方块为");
        textNext.setFont(Font.font(30));
        rigntvBox.getChildren().addAll(textNext,smallGridPane,btMeal,btPause,btplay,btReplay,btEnd);
        mainBorderPane.setRight(rigntvBox);

        initialize();
        initializesmall();
        random();
        nextrandom();
        printtable();
        printsmalltable();
        run();
    }

    private void run(){
        int second1 = getsecond();
        EventHandler eventHandler = e -> {
            Text textScore = new Text("您的游戏得分为:" + score);
            textScore.setFont(Font.font(27));

            int second2 = getsecond();
            int playTime = second2 - second1;

            Text textPlayTime =  new Text("您已玩游戏:"+playTime+"秒");
            textPlayTime.setFont(Font.font(27));

            VBox leftvBox = new VBox(100);//左边
            leftvBox.getChildren().addAll(textScore,textPlayTime);
            mainBorderPane.setLeft(leftvBox);

            clear();
            clearline();
            Bigtable.cleardown();
            block.centerX += 1;
            command();
            getdownblock();
            gettable();
            getsmalltable();
            printtable();
            printsmalltable();
            choose();
            if (stopBlock()) {
                block.Block = nextblock.getBlock();
                initializesmall();nextrandom();
                block.centerX = 0;
                block.centerY = 5;
            }
            for(int i = 0 ; i<= rank.length; i++){
                if(over()) {
                    rank[i] = score;
                }
            }
//            writeFile();
//            readFile();
        };
        Timeline drop = new Timeline(new KeyFrame(Duration.millis(400), eventHandler));
        drop.setCycleCount(Timeline.INDEFINITE);
        drop.play();

        btPause.setOnMouseClicked(e -> {
            mediaPlayer.stop();
            mediaPlayer1.stop();
            mediaPlayer2.stop();
            mediaPlayer3.stop();
            mediaPlayer4.stop();
            drop.stop();
        });

        btplay.setOnMouseClicked(e ->{
            getRandomBGM();
            drop.play();
        });

        btReplay.setOnMouseClicked(e ->{
            initialize();
            initializesmall();
            random();
            nextrandom();
            printtable();
            printsmalltable();
            score = 0 ;
            block.centerX = 0;
            block.centerY = 5;
            drop.play();
        });
    }//游戏逻辑运行

    private void random(){
        int random = (int)(Math.random()* 7);
        switch (random){
            case 0: {
                block.Block= Iblock.getBlock();
                break;
            }
            case 1:{
                block.Block = Jblock.getBlock();
                break;
            }
            case 2:{
                block.Block = Lblock.getBlock();
                break;
            }
            case 3:{
                block.Block = Oblock.getBlock();
                break;
            }
            case 4:{
                block.Block =Sblock.getBlock();
                break;
            }
            case 5:{
                block.Block = Tblock.getBlock();
                break;
            }
            case 6:{
                block.Block = Zblock.getBlock();
                break;
            }
        }
    }//随机获得方块

    private void nextrandom(){
        int random = (int)(Math.random()* 7);
        switch (random){
            case 0: {
                nextblock.Block = Iblock.getBlock();
                break;
            }
            case 1:{
                nextblock.Block = Jblock.getBlock();
                break;
            }
            case 2:{
                nextblock.Block = Lblock.getBlock();
                break;
            }
            case 3:{
                nextblock.Block = Oblock.getBlock();
                break;
            }
            case 4:{
                nextblock.Block =Sblock.getBlock();
                break;
            }
            case 5:{
                nextblock.Block = Tblock.getBlock();
                break;
            }
            case 6:{
                nextblock.Block = Zblock.getBlock();
                break;
            }
        }
    }

    private void initialize(){
         for(int i =0 ; i <= 15 ; i++){
             for (int j =0 ;j <= 12 ; j++){
                 Bigtable.gettable()[i][j] = 0;
             }
         }
    }//初始化主界面

    private void initializesmall(){
        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                Smalltable.gettable() [i][j] =0;
            }
        }
    }//初始化小界面

    private void getsmalltable(){
        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                if (nextblock.getBlock()[i][j] == 1)
                    Smalltable.gettable()[i][j] = 1;
            }
        }
    }

    private void gettable() {
        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                if (block.getBlock()[i][j] == 1)
                    Bigtable.gettable()[block.centerX + i][block.centerY + j] = 1;
            }
        }
    }

    private void printsmalltable() {
         smallGridPane.getChildren().clear();
         for(int i = 0;i <= 3 ;i++){
             for (int j = 0;j<=3 ; j++){
                 if(Smalltable.gettable()[i][j] == 1){
                     ImageView BlockColor = new ImageView(blockColor);
                     BlockColor.setFitHeight(37.5);
                     BlockColor.setFitWidth(37.5);
                     smallGridPane.add(BlockColor,j,i);
                 }
                 else{
                     ImageView BackgroundColor = new ImageView(backgroundColor);
                     BackgroundColor.setFitHeight(37.5);
                     BackgroundColor.setFitWidth(37.5);
                     smallGridPane.add(BackgroundColor,j,i);
                 }
             }
         }
     }//打印提示下一方块的界面

    private void printtable() {
         mainGridPane.getChildren().clear();
        for (int i=1;i<=14;i++) {
            for (int j = 0; j <= 12; j++) {
                if(Bigtable.gettable()[i][j] == 0){
                    ImageView BackgroundColor = new ImageView(backgroundColor);
                    BackgroundColor.setFitHeight(42.5);
                    BackgroundColor.setFitWidth(42.5);
                    mainGridPane.add(BackgroundColor,j,i);
                }
                else if(Bigtable.gettable()[i][j] == 1){
                    ImageView BlockColor = new ImageView(blockColor);
                    BlockColor.setFitHeight(42.5);
                    BlockColor.setFitWidth(42.5);
                    mainGridPane.add(BlockColor,j,i);
                }
                else {
                    ImageView NoteColor = new ImageView(noteColor);
                    NoteColor.setFitHeight(42.5);
                    NoteColor.setFitWidth(42.5);
                    mainGridPane.add(NoteColor,j,i);
                }
            }
        }
     }//打印

    private void command() {
         mainscene.setOnKeyPressed
                 (e -> {
                     switch (e.getCode()) {
                         case A: {
                             block.centerY += judgement();
                             clear();
                             block.centerY -= 1;
                             break;
                         }
                         case D: {
                             block.centerY += judgement();
                             clear();
                             block.centerY += 1;
                             break;
                         }
                         case S: {
                             clear();
                             block.centerX += 1;
                             break;
                         }
                         case W: {
                             clear();
                             if (block.getBlock() != Oblock.getBlock())
                                 spin();
                             MediaPlayer mediaPlayer6 = new MediaPlayer(media6);
                             mediaPlayer6.play();
                             break;
                         }
                         case X: {
                             clear();
                             block.centerX += fall()-1;
                             MediaPlayer mediaPlayer7 = new MediaPlayer(media7);
                             mediaPlayer7.play();
                             break;
                         }
                         case C: {
                             clear();
                             cleardownline();
                             break;
                         }
                     }
                 });
    }//命令

    private void clear() {
        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                if (block.getBlock()[i][j] == 1)
                    Bigtable.gettable()[block.centerX + i][block.centerY + j] = 0;
            }
        }
    }//清除上次操作的影响

    private boolean over() {
        boolean [] over = new boolean[15];
        for(int i =0 ; i<= 14 ; i++){
            if (Bigtable.gettable()[i][1] == 1|| Bigtable.gettable() [i][2] == 1 ||Bigtable.gettable()[i][3] == 1
                    ||Bigtable.gettable()[i][4] == 1 ||Bigtable.gettable()[i][5] == 1 ||Bigtable.gettable()[i][6] == 1
                    ||Bigtable.gettable()[i][7] == 1 ||Bigtable.gettable()[i][8] == 1 ||Bigtable.gettable()[i][9] == 1
                    ||Bigtable.gettable()[i][10] == 1 ||Bigtable.gettable()[i][11] == 1)
                over [i] = true;
        }
        if(over[1] &&over[2] &&over[3] &&over[4] &&over[5] &&over[6] &&over[7]
                &&over[8] &&over[9] &&over[10] &&over[11] &&over[12] &&over[13] &&over[14])
            return true;
        else
            return false;
    }//判断游戏结束

    private void spin() {//旋转
        int[][] temp = new int[3][3];
        int[][] tempI = new int [4][4];
        if (block.getBlock() != Iblock.getBlock()){
            for (int i = 0; i <= 2; i++) {
                for (int j = 0; j <= 2; j++) {
                    temp[i][j] = block.getBlock()[i][j];
                }
            }
            for (int i = 0; i <= 2; i++) {
                for (int j = 0; j <= 2; j++) {
                    block.getBlock()[i][j] = temp[(2 - j)][i];
                }
            }
            if ( (rightboundary() && leftboundary())) {
                for (int i = 0; i <= 2; i++) {
                    for (int j = 0; j <= 2; j++) {
                        block.getBlock()[i][j] = temp[i][j];
                    }
                }
            }
        }
        else {
            for (int i = 0; i <= 3; i++) {
                for (int j = 0; j <= 3; j++) {
                    tempI[i][j] = block.getBlock()[i][j];
                }
            }
            for (int i = 0; i <= 3; i++) {
                for (int j = 0; j <= 3; j++) {
                    block.getBlock()[i][j] = tempI[(3 - j)][i];
                }
            }
            if ( (rightboundary() && leftboundary())) {
                for (int i = 0; i <= 3; i++) {
                    for (int j = 0; j <= 3; j++) {
                        block.getBlock()[i][j] = tempI[i][j];
                    }
                }
            }
        }
    }//旋转的实现

    private boolean stopBlock() {
        for (int i = 0 ;i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                if (block.getBlock()[i][j] == 1){
                    if ((block.centerX + i ) >= 14 ) {
                        return true;
                    }
                    if (Bigtable.gettable()[thelastdig(j)+ 1 ][block.centerY + j] == 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }//判定方块停止

    private int thelastdig (int j) {
        int last = 0;
        for (int i = 0; i <= 3; i++) {
            if (block.getBlock()[i][j] == 1) {
                last = block.centerX + i;
            }
        }
        return last;
    }//当前方块最下端的方块

    private boolean leftboundary(){
        for (int i = 0 ;i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                if (block.getBlock()[i][j] == 1) {
                        if ((block.centerY + j) < 0 ){
                            return true;
                    }else
                        if ((block.centerY +j) != 0){
                            if (Bigtable.gettable()[block.centerX + i][theleftdig(i)-1] == 1) {
                                return true;
                        }
                    }
                }
            }
        }
        return false;
    }//判断能否向左移动

    private int theleftdig(int i ){
        int left = 0;
        for (int j = 3 ;j >= 0; j--) {
            if (block.getBlock()[i][j] == 1) {
                left = block.centerY + j;
            }
        }
        return left;
    }//当前方块最左端的方块

    private boolean rightboundary(){
        for (int i = 0 ;i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                if (block.getBlock()[i][j] == 1 ) {
                    if ((block.centerY + j + 1 ) >= 14){
                        return true;
                    }else
                        if ((block.centerY + j) != 12){
                            if (Bigtable.gettable()[block.centerX + i][therightdig(i) + 1] == 1) {
                                return true;
                        }
                    }
                }
            }
        }
        return false;
    }//判断能否向右移动

    private int therightdig(int i){
        int right = 0;
        for (int j = 0 ;j <= 3; j++) {
            if (block.getBlock()[i][j] == 1) {
                right = block.centerY + j ;
            }
        }
        return right;
    }//当前方块最右端的方块

    private int judgement (){
        int judge = 0 ;
        if(rightboundary())
            judge --;
        if(leftboundary())
            judge ++ ;
        return judge;
    }//逆操作判断

    private void clearline() {
        for (int i = 2; i <= 14; i++) {
            if(Bigtable.gettable()[i][0] == 1 && Bigtable.gettable()[i][1] == 1 &&Bigtable.gettable()[i][2] == 1 &&Bigtable.gettable()[i][3] == 1
                    &&Bigtable.gettable()[i][4] == 1 &&Bigtable.gettable()[i][5] == 1 &&Bigtable.gettable()[i][6] == 1
                    &&Bigtable.gettable()[i][7] == 1 &&Bigtable.gettable()[i][8] == 1 &&Bigtable.gettable()[i][9] == 1
                    &&Bigtable.gettable()[i][10] == 1 &&Bigtable.gettable()[i][11] == 1&&Bigtable.gettable()[i][12] == 1) {
                score += 10;
                MediaPlayer mediaPlayer5 = new MediaPlayer(media5);
                mediaPlayer5.play();
                for (int j = 0; j <= 12; j++) {
                    for (int m = i; m >= 3; m--) {
                        Bigtable.gettable()[m][j] = Bigtable.gettable()[m- 1][j];
                    }
                }
            }
        }
        for(int j =0; j<=12;j++){
            Bigtable.gettable()[2][j] =0;
        }
    }//消行

    private int fall(){
        int fall = 0;
        int beforeCenttrX = block.centerX;
        while(!stopBlock()){
            fall ++;
            block.centerX ++;
        }
        block.centerX = beforeCenttrX;
        return fall;
    }//获得应该下坠的格数

    private void getdownblock(){
        downblock.Block = block.getBlock();
        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                if (downblock.getBlock()[i][j] == 1) {
                    Bigtable.gettable()[block.centerX + fall()+ i ][block.centerY + j] = 2;
                }
            }
        }
    }

    private void cleardownline(){
        if (Bigtable.gettable()[14][1] == 1|| Bigtable.gettable()[14][2] == 1 ||Bigtable.gettable()[14][3] == 1
                ||Bigtable.gettable()[14][4] == 1||Bigtable.gettable()[14][5] == 1 ||Bigtable.gettable()[14][6] == 1
                ||Bigtable.gettable()[14][7] == 1 ||Bigtable.gettable()[14][8] == 1 ||Bigtable.gettable()[14][9] == 1
                ||Bigtable.gettable()[14][10] == 1||Bigtable.gettable()[14][11] == 1) {
            score += 10;
            String url5 = getClass().getResource("消行音效.mp3").toString();
            Media media5= new Media(url5);
            MediaPlayer mediaPlayer5 = new MediaPlayer(media5);
            mediaPlayer5.play();
        }
        for (int j = 0; j <= 12; j++) {
            for (int m =14 ; m >= 3; m--) {
                Bigtable.gettable()[m][j] = Bigtable.gettable()[m - 1][j];
            }
        }
        for(int j =0; j<=12;j++){
            Bigtable.gettable()[2][j] = 0;
        }
    }//消除最后一行

    private void clickbtStart(){
        btStart.setOnMouseClicked(e ->{
            getRandomBGM();
            addBlock();
            this.primaryStage.setTitle("Tetris");
            this.primaryStage.setScene(mainscene);
            this.primaryStage.show();
        });
    }

    private void clickbtMeal(){
        btMeal.setOnAction(e -> {
            getRandomBGM();
        });
    }

    private void clickbtEnd(){
        btEnd.setOnMouseClicked(e ->{
            int res = JOptionPane.showConfirmDialog(null,
                    "是否退出游戏？",
                    "退出游戏选择", JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }

    private void clickbthelp(){
         bthelp.setOnMouseClicked(e ->{
             JOptionPane.showMessageDialog(null,"按下A向左移动，按下D向右移动，按下W逆时针旋转，按下S向下移动，" +
                     "按下X直接下落，按下C清除最后一行");
         });
    }

    private void getRandomBGM(){
        int i = (int)(Math.random()* 4 + 1);
        mediaPlayer.stop();
        mediaPlayer1.stop();
        mediaPlayer2.stop();
        mediaPlayer3.stop();
        mediaPlayer4.stop();
        if (i == 1){
            mediaPlayer1.play();
            mediaPlayer1.setCycleCount(mediaPlayer1.INDEFINITE);
        }
        else if (i == 2){
            mediaPlayer2.play();
            mediaPlayer2.setCycleCount(mediaPlayer2.INDEFINITE);
        }
        else if (i == 3){
            mediaPlayer3.play();
            mediaPlayer3.setCycleCount(mediaPlayer3.INDEFINITE);
        }
        else {
            mediaPlayer4.play();
            mediaPlayer4.setCycleCount(mediaPlayer4.INDEFINITE);
        }
    }//获得随机音乐

    private int getsecond () {
        Calendar time = new GregorianCalendar();
        int hour = time.get(Calendar.HOUR_OF_DAY);
        int minute = time.get(Calendar.MINUTE);
        int second = time.get(Calendar.SECOND);
        return  3600 * hour + 60 * minute + second;
    }//获得当前的秒数

    private void choose() {
        if (over()) {
            initialize();
            initializesmall();
            random();
            nextrandom();
            int res = JOptionPane.showConfirmDialog(null,
                    "本次游戏结束，您的得分为 "+ score+" 分，是否再次进行游戏？",
                    "选择", JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.YES_OPTION) {
                initialize();
                initializesmall();
                block.centerX = 0;
                block.centerY = 5;
                score = 0;
            } else {
                System.exit(0);
            }
        }
    }//游戏结束时供玩家选择

    private void writeFile() {
        try {
            File writeName = new File("output.txt"); //相对路径
            writeName.createNewFile(); //创建新文件,覆盖同名文件
            try (FileWriter writer = new FileWriter(writeName);
                 BufferedWriter out = new BufferedWriter(writer)
            ) {
                for (int i=0;i<10;i++){
                    out.write("第"+i+"名：      "+rank[i] + "\n");
                }
                out.flush(); //把缓存区内容压入文件
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private  void readFile() {
        String pathname = "output.txt"; // 相对路径
        try (FileReader reader = new FileReader(pathname);
             BufferedReader result = new BufferedReader(reader)
        ) {
            String line;
            int i = 0;
            while ((line = result.readLine()) != null) {
                rank[i]=Integer.parseInt(line);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}//类括号