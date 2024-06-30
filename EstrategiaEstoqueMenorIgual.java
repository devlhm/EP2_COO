public class EstrategiaEstoqueMenorIgual implements EstrategiaFiltragem {
	private int arg;

	@Override
	public void setArg(String arg) {
		this.arg = Integer.parseInt(arg);
	}

	@Override
	public boolean seleciona(Produto produto) {
		return produto.getQtdEstoque() <= arg;
	}
}
