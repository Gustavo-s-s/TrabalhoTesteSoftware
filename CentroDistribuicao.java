public class CentroDistribuicao {
    public enum SITUACAO {
        NORMAL, SOBRAVISO, EMERGENCIA
    }

    public enum TIPOPOSTO {
        COMUM, ESTRATEGICO
    }

    public static final int MAX_ADITIVO = 500;
    public static final int MAX_ALCOOL = 2500;
    public static final int MAX_GASOLINA = 10000;

    public int ad;
    public int gas;
    public int al1;
    public int al2;
    public CentroDistribuicao.SITUACAO situacao;

    public CentroDistribuicao(int tAditivo, int tGasolina, int tAlcool1, int tAlcool2) {
        ad = 0;
        gas = 0;
        al1 = 0;
        al2 = 0;

        recebeAditivo(tAditivo);
        recebeGasolina(tGasolina);
        recebeAlcool((tAlcool1 + tAlcool2) / 2);
        defineSituacao();
    }

    public void defineSituacao() {
        if (ad < MAX_ADITIVO * 0.25 || gas < MAX_GASOLINA * 0.25 || al1 < MAX_ALCOOL / 2 * 0.25
                || al2 < MAX_ALCOOL / 2 * 0.25) {
            situacao = SITUACAO.EMERGENCIA;
        } else if (ad < MAX_ADITIVO * 0.5 || gas < MAX_GASOLINA * 0.5 || al1 < MAX_ALCOOL / 2 * 0.5
                || al2 < MAX_ALCOOL / 2 * 0.5) {
            situacao = SITUACAO.SOBRAVISO;
        } else {
            situacao = SITUACAO.NORMAL;
        }
    }

    public SITUACAO getSituacao() {
        return situacao;
    }

    public int gettGasolina() {
        return gas;
    }

    public int gettAditivo() {
        return ad;
    }

    public int gettAlcool1() {
        return al1;
    }

    public int gettAlcool2() {
        return al2;
    }

    public int recebeAditivo(int qtdade) {
        if (qtdade < 0) {
            return -1;
        } else if (qtdade + ad >= MAX_ADITIVO) {
            ad = MAX_ADITIVO;
            return MAX_ADITIVO - ad;
        } else {
            ad = qtdade + ad;
            return qtdade;
        }
    }

    public int recebeGasolina(int qtdade) {
        if (qtdade < 0) {
            return -1;
        } else if (qtdade + gas >= MAX_GASOLINA) {
            gas = MAX_GASOLINA;
            return MAX_GASOLINA - gas;
        } else {
            gas = qtdade + gas;
            return qtdade;
        }
    }

    public int recebeAlcool(int qtdade) {
        if (qtdade < 0) {
            return -1;
        } else if (qtdade + al1 >= MAX_ALCOOL / 2) {
            al1 = MAX_ALCOOL / 2;
            al2 = MAX_ALCOOL / 2;
            return MAX_ALCOOL / 2 - al1;
        } else {
            al1 = qtdade + al1;
            al2 = qtdade + al2;
            return qtdade;
        }
    }

    public int[] encomendaCombustivel(int qtdade, TIPOPOSTO tipoPosto) {
        if (qtdade <= 0)
            return new int[] { -7 };

        switch (situacao) {
            case NORMAL: {
                if (!validaQtd(qtdade))
                    return new int[] { -21 };
                break;
            }
            case SOBRAVISO: {
                if (tipoPosto == TIPOPOSTO.COMUM) {
                    qtdade = qtdade / 2;
                    if (!validaQtd(qtdade))
                        return new int[] { -21 };
                } else {
                    if (!validaQtd(qtdade))
                        return new int[] { -21 };
                }
                break;
            }
            case EMERGENCIA: {
                if (tipoPosto == TIPOPOSTO.COMUM) {
                    return new int[] { -14 };
                } else {
                    if (!validaQtd(qtdade))
                        return new int[] { -21 };
                }
                break;
            }
            default:
                break;
        }
        atualizaTanque(qtdade);
        return new int[] { ad, gas, al1, al2 };
    }

    private boolean validaQtd(int qtdade) {
        if ((qtdade * 0.7) > gas || ((qtdade * 0.25) / 2) > al1 || ((qtdade * 0.25) / 2) > al2 || (qtdade * 0.05) > al2)
            return false;
        return true;
    }

    private void atualizaTanque(int qtdade) {
        ad -= qtdade * 0.05;
        al1 -= (qtdade * 0.25) / 2;
        al2 -= (qtdade * 0.25) / 2;
        gas -= qtdade * 0.7;
        defineSituacao();
    }
}
