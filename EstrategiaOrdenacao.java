public interface EstrategiaOrdenacao {
    public void setEstrategiaCriterio(EstrategiaCriterio estrategiaCriterio);
    public void ordena(int ini, int fim, Produto[] produtos);
}