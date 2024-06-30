public class ProdutoNegrito extends ProdutoDecorado {
	public ProdutoNegrito(Produto produtoBase) {
		super(produtoBase);
	}

	@Override
	public String formataParaImpressao() {
		return "<span style=\"font-weight:bold\">" + produtoBase.formataParaImpressao() + "</span>";
	}
}
