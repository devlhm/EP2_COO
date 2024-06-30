public class EstrategiaQuicksort implements EstrategiaOrdenacao {
	private EstrategiaCriterio estrategiaCriterio;

	@Override
	public void setEstrategiaCriterio(EstrategiaCriterio estrategiaCriterio) {
		this.estrategiaCriterio = estrategiaCriterio;
	}

    private int particiona(int ini, int fim, Produto[] produtos) {

		Produto x = produtos[ini];
		int i = (ini - 1);
		int j = (fim + 1);
	
		while (true) {

			do {
				j--;

			} while (estrategiaCriterio.xDepoisDeY(produtos[j], x));

			do {
				i++;

			} while (estrategiaCriterio.xAntesDeY(produtos[i], x));

			if (i < j) {
				Produto temp = produtos[i];
				produtos[i] = produtos[j];
				produtos[j] = temp;
			} else
				return j;
		}
	}

	@Override
    public void ordena(int ini, int fim, Produto[] produtos) {
        if (ini < fim) {

            int q = particiona(ini, fim, produtos);

            ordena(ini, q, produtos);
            ordena(q + 1, fim, produtos);
        }
    }
}