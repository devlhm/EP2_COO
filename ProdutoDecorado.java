public abstract class ProdutoDecorado implements Produto {

	Produto produtoBase;

	public ProdutoDecorado(Produto produtoBase) {
		this.produtoBase = produtoBase;
	}

	@Override
	public void setQtdEstoque(int qtdEstoque) {
		produtoBase.setQtdEstoque(qtdEstoque);
	}

	@Override
	public void setPreco(double preco) {
		produtoBase.setPreco(preco);
	}

	@Override
	public int getId() {
		return produtoBase.getId();
	}

	@Override
	public String getDescricao() {
		return produtoBase.getDescricao();
	}

	@Override
	public String getCategoria() {
		return produtoBase.getCategoria();
	}

	@Override
	public int getQtdEstoque() {
		return produtoBase.getQtdEstoque();
	}

	@Override
	public double getPreco() {
		return produtoBase.getPreco();
	}
}
