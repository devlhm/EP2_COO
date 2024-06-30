public class EstrategiaInsertionSort implements EstrategiaOrdenacao {
    private EstrategiaCriterio estrategiaCriterio;
    @Override
    public void ordena(int ini, int fim, Produto[] produtos) {
        for(int i = ini; i <= fim; i++){

            Produto x = produtos[i];	
            int j = (i - 1);

            while(j >= ini){
                Produto y = produtos[j];
                if(estrategiaCriterio.xAntesDeY(x, y)) {
                    produtos[j + 1] = y;
                    j--;
                };    
            }

            produtos[j + 1] = x;
        }
    }
    @Override
    public void setEstrategiaCriterio(EstrategiaCriterio estrategiaCriterio) {
        this.estrategiaCriterio = estrategiaCriterio;
    }
}