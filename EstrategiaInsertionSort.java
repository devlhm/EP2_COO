import java.util.List;

public class EstrategiaInsertionSort implements EstrategiaOrdenacao {
    private EstrategiaCriterio estrategiaCriterio;
    @Override
    public void ordena(int ini, int fim, List<Produto> produtos) {

        for(int i = ini; i <= fim; i++){

            Produto x = produtos.get(i);	
            int j = (i - 1);

            while(j >= ini){
                Produto y = produtos.get(j);
                if(estrategiaCriterio.xAntesDeY(x, y)) {
                    produtos.set(j+1, y);
                    j--;
                } else break;
            }

            produtos.set(j+1, x);
        }
    }
    @Override
    public void setEstrategiaCriterio(EstrategiaCriterio estrategiaCriterio) {
        this.estrategiaCriterio = estrategiaCriterio;
    }
}