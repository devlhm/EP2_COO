public class ProdutoItalico extends ProdutoDecorado {
	public ProdutoItalico(Produto produtoBase) {
		super(produtoBase);
	}

	@Override
	public String formataParaImpressao() {
		return "<span style=\"font-style:italic\">" + produtoBase.formataParaImpressao() + "</span>";
	}
}
