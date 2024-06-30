public class EstrategiaPrecoCres implements EstrategiaCriterio {
    @Override
    public boolean xDepoisDeY(Produto x, Produto y) {
        return x.getPreco() > y.getPreco();
    }

    @Override
    public boolean xAntesDeY(Produto x, Produto y) {
        return x.getPreco() < y.getPreco();
    }
    
}