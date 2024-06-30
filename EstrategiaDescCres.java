public class EstrategiaDescCres implements EstrategiaCriterio {


    @Override
    public boolean xDepoisDeY(Produto x, Produto y) {
        
        return x.getDescricao().compareToIgnoreCase(y.getDescricao()) > 0;
    }

    @Override
    public boolean xAntesDeY(Produto x, Produto y) {
        
        return x.getDescricao().compareToIgnoreCase(y.getDescricao()) < 0;
    }
}