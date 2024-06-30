public class EstrategiaEstoqueCres implements EstrategiaCriterio {

    @Override
    public boolean xDepoisDeY(Produto x, Produto y) {
        return x.getQtdEstoque() > y.getQtdEstoque();
    }

    @Override
    public boolean xAntesDeY(Produto x, Produto y) {
        return x.getQtdEstoque() < y.getQtdEstoque();
    }
    
}
