/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cueva;

import java.util.ArrayList;

/**
 *
 * @author XAMAP
 */
public class Cueva {

    private CasillaAgente[][] cueva;
    private int tamaño;
    private Main prog;

    public Cueva(Main p, Tamanyo n) {
        this.prog = p;
        this.tamaño = tamanyo(n);
        cueva = new CasillaAgente[tamaño][tamaño];
        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                cueva[i][j] = new CasillaAgente(i, j);
                cueva[i][j].setEstados(Estado.VACIO);
            }
        }
    }

    public int getTamanyo() {
        return this.tamaño;
    }

    public int tamanyo(Tamanyo t) {
        switch (t) {
            case PEQUEÑO:
                return 8;

            case MEDIANO:
                return 12;

            case GRANDE:
                return 16;
        }
        return 0;
    }

    public void quitar(int i, int j, Estado e) {
        this.cueva[j][i].removeEstado(e);
        if (this.cueva[j][i].getEstados().isEmpty()) {
            this.cueva[j][i].setEstados(Estado.VACIO);
        }
        if (j > 0) {
            switch (e) {
                case MONSTRUO:
                    this.cueva[j - 1][i].removeEstado(Estado.HEDOR);
                    break;
                case PRECIPICIO:
                    this.cueva[j - 1][i].removeEstado(Estado.BRISA);
                    break;
            }

            if (this.cueva[j - 1][i].getEstados().isEmpty()) {
                this.cueva[j - 1][i].setEstados(Estado.VACIO);
            }
        }

        if (j < this.tamaño - 1) {
            switch (e) {
                case MONSTRUO:
                    this.cueva[j + 1][i].removeEstado(Estado.HEDOR);
                    break;
                case PRECIPICIO:
                    this.cueva[j + 1][i].removeEstado(Estado.BRISA);
                    break;
            }

            if (this.cueva[j + 1][i].getEstados().isEmpty()) {
                this.cueva[j + 1][i].setEstados(Estado.VACIO);
            }
        }

        if (i > 0) {
            switch (e) {
                case MONSTRUO:
                    this.cueva[j][i - 1].removeEstado(Estado.HEDOR);
                    break;
                case PRECIPICIO:
                    this.cueva[j][i - 1].removeEstado(Estado.BRISA);
                    break;
            }

            if (this.cueva[j][i - 1].getEstados().isEmpty()) {
                this.cueva[j][i - 1].setEstados(Estado.VACIO);
            }
        }

        if (i < this.tamaño - 1) {
            switch (e) {
                case MONSTRUO:
                    this.cueva[j][i + 1].removeEstado(Estado.HEDOR);
                    break;
                case PRECIPICIO:
                    this.cueva[j][i + 1].removeEstado(Estado.BRISA);
                    break;
            }

            if (this.cueva[j][i + 1].getEstados().isEmpty()) {
                this.cueva[j][i + 1].setEstados(Estado.VACIO);
            }
        }
    }

    public void setTesoro(int i, int j) {
        this.cueva[j][i].setEstados(Estado.TESORO);
    }

    public void setMonstruo(int i, int j) {
        this.cueva[j][i].setEstados(Estado.MONSTRUO);
        if (j > 0) {
            this.cueva[j - 1][i].setEstados(Estado.HEDOR);
        }

        if (j < this.tamaño - 1) {
            this.cueva[j + 1][i].setEstados(Estado.HEDOR);
        }

        if (i > 0) {
            this.cueva[j][i - 1].setEstados(Estado.HEDOR);
        }

        if (i < this.tamaño - 1) {
            this.cueva[j][i + 1].setEstados(Estado.HEDOR);
        }
    }

    public void setPrecipicio(int i, int j) {
        this.cueva[j][i].setEstados(Estado.PRECIPICIO);
        if (j > 0) {
            this.cueva[j - 1][i].setEstados(Estado.BRISA);
        }

        if (j < this.tamaño - 1) {
            this.cueva[j + 1][i].setEstados(Estado.BRISA);
        }

        if (i > 0) {
            this.cueva[j][i - 1].setEstados(Estado.BRISA);
        }

        if (i < this.tamaño - 1) {
            this.cueva[j][i + 1].setEstados(Estado.BRISA);
        }
    }

    public CasillaAgente getCasilla(int x, int y) {
        if (x < 0) {

        } else if (x >= cueva.length) {

        } else if (y < 0) {

        } else if (y >= cueva.length) {

        }
        return this.cueva[x][y];

    }

    public void setCueva(Tamanyo dimension) {
        this.tamaño = tamanyo(dimension);
        this.cueva = new CasillaAgente[tamaño][tamaño];
        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                cueva[i][j] = new CasillaAgente(i, j);
                cueva[i][j].setEstados(Estado.VACIO);
            }
        }
    }

    public void eliminarElemento(int x, int y, Estado estado, Estado estado2) {
        for (int i = 0; i < cueva[x][y].getEstados().size(); i++) {
            if (cueva[x][y].getEstados().get(i) == estado) {
                cueva[x][y].getEstados().remove(i);
                if (cueva[x][y].getEstados().size() == 0) {
                    cueva[x][y].setEstados(Estado.VACIO);
                }
                if (x > 0) {
                    for (int j = 0; j < cueva[x - 1][y].getEstados().size(); j++) {
                        if (cueva[x - 1][y].getEstados().get(j) == estado2) {
                            cueva[x - 1][y].getEstados().remove(j);

                            if (cueva[x - 1][y].getEstados().size() == 0) {
                                cueva[x - 1][y].setEstados(Estado.VACIO);
                            }
                        }

                    }
                }
                if (x < cueva.length - 1) {
                    for (int j = 0; j < cueva[x + 1][y].getEstados().size(); j++) {
                        if (cueva[x + 1][y].getEstados().get(j) == estado2) {
                            cueva[x + 1][y].getEstados().remove(j);
                            if (cueva[x + 1][y].getEstados().size() == 0) {
                                cueva[x + 1][y].setEstados(Estado.VACIO);
                            }
                        }

                    }
                }

                if (y > 0) {
                    for (int j = 0; j < cueva[x][y - 1].getEstados().size(); j++) {
                        if (cueva[x][y - 1].getEstados().get(j) == estado2) {
                            cueva[x][y - 1].getEstados().remove(j);
                            if (cueva[x][y - 1].getEstados().size() == 0) {
                                cueva[x][y - 1].setEstados(Estado.VACIO);
                            }
                        }

                    }
                }
                if (y < cueva.length - 1) {
                    for (int j = 0; j < cueva[x][y + 1].getEstados().size(); j++) {
                        if (cueva[x][y + 1].getEstados().get(j) == estado2) {
                            cueva[x][y + 1].getEstados().remove(j);
                            if (cueva[x][y + 1].getEstados().size() == 0) {
                                cueva[x][y + 1].setEstados(Estado.VACIO);
                            }
                        }

                    }

                }

            }

        }

    }
}
