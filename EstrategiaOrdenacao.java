import java.util.List;

public interface EstrategiaOrdenacao {
    void setEstrategiaCriterio(EstrategiaCriterio estrategiaCriterio);
    void ordena(int ini, int fim, List<Produto> produtos);
}