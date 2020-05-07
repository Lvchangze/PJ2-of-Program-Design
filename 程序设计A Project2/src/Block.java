
 abstract class Block {
     int [][] Block;
     int centerX;
     int centerY;

     Block(int [][] Block,int centerX,int centerY){
        this.Block = Block;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    int [][] getBlock(){
         return Block;
     }
}

 class Iblock extends Block {

     Iblock(){
        super(new int[][]{
                {0, 0, 0, 0},
                {1, 1, 1, 1},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        },0,5);
     }

 }

 class Jblock extends Block {

     Jblock() {
        super(new int[][]{
                {1, 0, 0, 0},
                {1, 1, 1, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        },0,5);
     }

 }

 class Lblock extends Block {

     Lblock() {
        super(new int[][]{
                {0, 0, 1, 0},
                {1, 1, 1, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        },0,5);
     }

 }

 class Oblock extends Block {

     Oblock() {
        super(new int[][]{
                {0, 1, 1, 0},
                {0, 1, 1, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        },0,5);
     }

 }

 class Sblock extends Block {

     Sblock() {
        super(new int[][]{
                {0, 1, 1, 0},
                {1, 1, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        },0,5);
     }

 }

 class Tblock extends Block {

     Tblock() {
        super(new int[][]{
                {1, 1, 0, 0},
                {0, 1, 1, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        },0,5);
     }

 }

 class Zblock extends Block {

     Zblock() {
        super(new int[][]{
                {0, 1, 0, 0},
                {1, 1, 1, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        },0,5);
     }

 }

 class nowblock extends Block{
     nowblock(){
         super(new int[][]{
                 {0, 0, 0, 0},
                 {0, 0, 0, 0},
                 {0, 0, 0, 0},
                 {0, 0, 0, 0}
         },0,5);
     }
 }

 class nextblock extends Block{
    nextblock(){
        super(new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        },0,5);
    }

 }

 class downblock extends Block{
     downblock(){
         super(new int[][]{
                 {0, 0, 0, 0},
                 {0, 0, 0, 0},
                 {0, 0, 0, 0},
                 {0, 0, 0, 0}
         },0,5);
     }
 }

