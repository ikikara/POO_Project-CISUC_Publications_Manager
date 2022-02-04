package com.company;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe usada para representar uma Publicação.
 * Implementa um Serializable e Comparable<Publicacao>.
 */
abstract class Publicacao implements Serializable, Comparable<Publicacao> {
    protected ArrayList<Investigador> autores;
    protected String titulo;
    protected String[] palavraskey;
    protected String tipo;
    protected int anoPub;
    protected int audiencia;
    protected String resumo;
    protected String fatorImp;

    /**
     * Inicializa um novo objeto do tipo Publicacao com recurso a uma String, um Array de Strings, 2 inteiros e outra String.
     */
    public Publicacao(String titulo, String[] palavraskey, int anoPub, int audiencia, String resumo) {
        this.titulo = titulo;
        this.palavraskey = palavraskey;
        this.anoPub = anoPub;
        this.audiencia = audiencia;
        this.resumo = resumo;
        this.autores = new ArrayList<>();
    }

    /**
     * Compara os objetos do tipo Publicacao por ano, depois pelo tipo e finalmente por fator.
     * @param p1 Publicacao a ser comparada.
     * @return Um inteiro maior que 0 se a Publicacao tiver "prioridade", um inteiro menor que 0 se não tiver "prioridade" e 0 se tiverem a mesma "prioridade".
     */
    public int compareTo(Publicacao p1) {
        int anoCompara = p1.getAnoPub()- this.getAnoPub();
        int tipoCompara = this.getTipo().compareTo(p1.getTipo() == null ? "" : p1.getTipo());
        if(anoCompara == 0) {
            if (tipoCompara == 0) {
                return this.getFatorImp().compareTo(p1.getFatorImp() == null ? "" : p1.getFatorImp());
            }
            return tipoCompara;
        }
        return anoCompara;
    }

    /**
     * Adiciona um objeto do tipo Investigador ao ArrayList de autores(do tipo Investigador).
     * @param inv Investigador a adicionar.
     */
    public void addAutores(Investigador inv){this.autores.add(inv);}

    public ArrayList<Investigador> getAutores() {
        return autores;
    }


    public String[] getPalavraskey() {
        return palavraskey;
    }

    public String getTipo() {
        return tipo;
    }

    public int getAnoPub() {
        return anoPub;
    }

    public int getAudiencia() {
        return audiencia;
    }

    public String getResumo() {
        return resumo;
    }

    public String getFatorImp() {
        return fatorImp;
    }

    /**
     * Responsável por calcular o Fator de Importância.
     */
    abstract public void calcFator();

    public String toString() {
        return this.titulo;
    }
}

/**
 * Classe usada para representar um Artigo de Conferência.
 * Implementa um Serializable.
 */
class ArtigoConferencia extends Publicacao implements Serializable{
    private Conferencia conferencia;

    /**
     * Inicializa um novo objeto do tipo ArtigoConferencia com recurso a uma String, um Array de Strings, 2 inteiros e outra String e um objeto do tipo Conferencia.
     */
    public ArtigoConferencia(String titulo, String[] palavraskey, int anoPub, int audiencia, String resumo, Conferencia conferencia){
        super(titulo, palavraskey, anoPub, audiencia, resumo);
        this.conferencia=conferencia;
        this.tipo = "Artigo de Conferencia";
        this.calcFator();
    }

    /**
     * Responsável por calcular o Fator de Importância.
     */
    @Override
    public void calcFator(){
        if(this.audiencia>=500){
            this.fatorImp="A";
        }
        else if(this.audiencia>=200){
            this.fatorImp="B";
        }
        else{
            this.fatorImp="C";
        }
    }
}

/**
 * Classe usada para representar um Artigo de Revista.
 * Implementa um Serializable.
 */
class ArtigoRevista extends Publicacao implements Serializable{
    private Revista revista;

    /**
     * Inicializa um novo objeto do tipo ArtigoRevista com recurso a uma String, um Array de Strings, 2 inteiros e outra String e um objeto do tipo Revista.
     */
    public ArtigoRevista(String titulo, String[] palavraskey, int anoPub, int audiencia, String resumo, Revista revista){
        super(titulo, palavraskey, anoPub, audiencia, resumo);
        this.revista=revista;
        this.tipo = "Artigo de Revista";
        this.calcFator();
    }

    /**
     * Responsável por calcular o Fator de Importância.
     */
    @Override
    public void calcFator(){
        if(this.audiencia>=1000){
            this.fatorImp="A";
        }
        else if(this.audiencia>=500){
            this.fatorImp="B";
        }
        else{
            this.fatorImp="C";
        }
    }
}

/**
 * Classe usada para representar um Livro.
 * Implementa um Serializable.
 */
class Livro extends Publicacao implements Serializable{
    private InfoLivro infoLivro;

    /**
     * Inicializa um novo objeto do tipo Livro com recurso a uma String, um Array de Strings, 2 inteiros e outra String e um objeto do InfoLivro.
     */
    public Livro(String titulo, String[] palavraskey, int anoPub, int audiencia, String resumo, InfoLivro infoLivro){
        super(titulo, palavraskey, anoPub, audiencia, resumo);
        this.infoLivro=infoLivro;
        this.tipo = "Livro";
        this.calcFator();
    }

    /**
     * Responsável por calcular o Fator de Importância.
     */
    @Override
    public void calcFator(){
        if(this.audiencia>=10000){
            this.fatorImp="A";
        }
        else if(this.audiencia>=5000){
            this.fatorImp="B";
        }
        else{
            this.fatorImp="C";
        }
    }
}

/**
 * Classe usada para representar um Capítulo de Livro.
 * Implementa um Serializable.
 */
class CapituloLivro extends Livro implements Serializable{
    private String nomeCap;
    private int pagI;
    private int pagF;

    /**
     * Inicializa um novo objeto do tipo CapituloLivro com recurso a uma String, um Array de Strings, 2 inteiros e outra String, um objeto do InfoLivro, outra Stringe e por último 2 inteiros.
     */
    public CapituloLivro(String titulo, String[] palavraskey, int anoPub, int audiencia, String resumo, InfoLivro infoLivro, String nomeCap, int pagI, int pagF){
        super(titulo, palavraskey, anoPub, audiencia, resumo, infoLivro);
        this.nomeCap=nomeCap;
        this.pagI=pagI;
        this.pagF=pagF;
        this.tipo = "Capitulo de Livro";
        this.calcFator();
    }
}

/**
 * Classe usada para representar um Livro de Conferência.
 * Implementa um Serializable.
 */
class LivroConferencia extends Livro implements Serializable{
    private String conferencia;
    private int nrArtigos;

    /**
     * Inicializa um novo objeto do tipo LivroConferencia com recurso a uma String, um Array de Strings, 2 inteiros e outra String, um objeto do InfoLivro, outra String e outro inteiro.
     */
    public LivroConferencia(String titulo, String[] palavraskey, int anoPub, int audiencia, String resumo, InfoLivro infoLivro,  String conferencia,int nrArtigos){
        super(titulo, palavraskey, anoPub, audiencia, resumo, infoLivro);
        this.nrArtigos=nrArtigos;
        this.tipo = "Livro de Conferencias";
        this.calcFator();
        this.conferencia=conferencia;
    }

    /**
     * Responsável por calcular o Fator de Importância.
     */
    @Override
    public void calcFator(){
        if(this.audiencia>=7500){
            this.fatorImp="A";
        }
        else if(this.audiencia>=2500){
            this.fatorImp="B";
        }
        else{
            this.fatorImp="C";
        }
    }
}

/**
 * Classe usada para representar uma Conferência.
 * Implementa um Serializable.
 */
class Conferencia implements Serializable{
    private String nome;
    private Data data;
    private String localizacao;

    /**
     * Inicializa um novo objeto do tipo Conferência com recurso a uma String, um objeto do tipo Data e outra String.
     */
    public Conferencia(String nome, Data data, String localizacao) {
        this.nome = nome;
        this.data = data;
        this.localizacao = localizacao;
    }

    public String getNome() {
        return nome;
    }

    public Data getData() {
        return data;
    }
}

/**
 * Classe usada para representar uma Revista.
 * Implementa um Serializable.
 */
class Revista implements Serializable{
    private String nome;
    private Data data;
    private int num;

    /**
     * Inicializa um novo objeto do tipo Revista com recurso a uma String, um objeto do tipo Data e outra String.
     */
    public Revista(String nome, Data data, int num) {
        this.nome = nome;
        this.data = data;
        this.num = num;
    }

    public String getNome() {
        return nome;
    }

    public Data getData() {
        return data;
    }
}

/**
 * Classe usada para representar a Informação dum Livro.
 * Implementa um Serializable.
 */
class InfoLivro implements Serializable{
    private String editora;
    private int isbn;

    /**
     * Inicializa um novo objeto do tipo InfoLivro com recurso a uma String e um inteiro.
     */
    public InfoLivro(String editora, int isbn){
        this.editora=editora;
        this.isbn=isbn;
    }
}