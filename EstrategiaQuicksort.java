import java.util.List;

public class EstrategiaQuicksort implements EstrategiaOrdenacao {
	private EstrategiaCriterio estrategiaCriterio;

	@Override
	public void setEstrategiaCriterio(EstrategiaCriterio estrategiaCriterio) {
		this.estrategiaCriterio = estrategiaCriterio;
	}

    private int particiona(int ini, int fim, List<Produto> produtos) {

		Produto x = produtos.get(ini);
		int i = (ini - 1);
		int j = (fim + 1);
	
		while (true) {

			do {
				j--;

			} while (estrategiaCriterio.xDepoisDeY(produtos.get(j), x));

			do {
				i++;

			} while (estrategiaCriterio.xAntesDeY(produtos.get(i), x));

			if (i < j) {
				Produto temp = produtos.get(i);
				produtos.set(i, produtos.get(j));
				produtos.set(j, temp);
			} else
				return j;
		}
	}

	@Override
    public void ordena(int ini, int fim, List<Produto> produtos) {
        if (ini < fim) {

            int q = particiona(ini, fim, produtos);

            ordena(ini, q, produtos);
            ordena(q + 1, fim, produtos);
        }
    }
}