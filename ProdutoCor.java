public class ProdutoCor extends ProdutoDecorado {
    private String color;

    public ProdutoCor(Produto produtoBase) {
        super(produtoBase);
    }

    @Override
    public String formataParaImpressao() {
        return "<span style=\"color:" + color + "\">" + produtoBase.formataParaImpressao() + "</span>";
    }

    public void setColor(String color) {
        this.color = color;
    }
}
