import java.util.List;

public interface EstrategiaOrdenacao {
    public void setEstrategiaCriterio(EstrategiaCriterio estrategiaCriterio);
    public void ordena(int ini, int fim, List<Produto> produtos);
}