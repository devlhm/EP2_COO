public class ProdutoCor extends ProdutoDecorado {
    private String cor;

    public ProdutoCor(Produto produtoBase, String cor) {
        super(produtoBase);
        this.cor = cor;
    }

    @Override
    public String formataParaImpressao() {
        return "<span style=\"color:" + cor + "\">" + produtoBase.formataParaImpressao() + "</span>";
    }
}
