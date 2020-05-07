

abstract class Table {
    int [][] Table ;

    Table(int [][] Table){
        this.Table = Table;
    }

    int [][] gettable(){
        return Table;
    }
}

class Smalltable extends  Table {
    Smalltable(){
        super(new int[4][4]);
    }
}

class Bigtable extends Table{
    Bigtable(){
        super(new int[16][13] );
    }

    void cleardown() {
        for (int i = 2; i <= 14; i++) {
            for (int j = 0; j <= 12; j++) {
                if(Table[i][j] == 2)
                    Table[i][j] = 0;
            }
        }
    }
}