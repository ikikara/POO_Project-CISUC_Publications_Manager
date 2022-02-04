package com.company;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Classe usada para representar um Grupo de Investigação.
 * Implementa Serializable.
 */
class GrupoInvest implements Serializable {
    private String nome;
    private String acronimo;
    private MembEfet membEfet;
    private ArrayList<Investigador> membros;

    /**
     * Inicializa um novo objeto do tipo GrupoInvest com recurso a 2 Strings.
     */
    public GrupoInvest(String nome, String acronimo) {
        this.nome = nome;
        this.acronimo = acronimo;
        this.membEfet = null;
        this.membros = new ArrayList<>();
    }

    public ArrayList<Investigador> getMembros() {
        return membros;
    }

    public String getNome() {
        return nome;
    }

    public String getAcronimo() {
        return acronimo;
    }

    /**
     * Adiciona um objeto do tipo Investigador ao ArrayList de membros(do tipo Investigador).
     * @param inv Investigador a adicionar.
     */
    public void addInvest(Investigador inv){ membros.add(inv);}

    public MembEfet getMembEfet() {
        return membEfet;
    }

    /**
     * Compara os objetos do tipo GrupoInvest através do acronimo e nome
     * @param grp2 GrupoInvest a ser comparado
     * @return Um inteiro diferente de 0 se for os objetos forem diferentes um do outro e 0 se forem iguais.
     */
    public boolean compareTo(GrupoInvest grp2){return this.nome.equalsIgnoreCase(grp2.nome) && this.acronimo.equalsIgnoreCase(grp2.acronimo); }

    public void setMembEfet(MembEfet membEfet) {
        this.membEfet = membEfet;
    }

    public String toString(){
        return this.nome;
    }

    /**
     * Retorna as publicações de um objeto do tipo GrupoInvest.
     * @param pubs ArrayList do tipo Publicacao onde se vai buscar as Publicações.
     * @return ArrayList do tipo Publicacao com as publicações de um objeto do tipo GrupoInvest.
     */
    public ArrayList<Publicacao> pubs(ArrayList<Publicacao> pubs){
        int aux=0;

        ArrayList<Publicacao> ps = new ArrayList<>();
        for(Publicacao p: pubs){
            for(Investigador inv:p.getAutores()){
                for(Investigador inv2: this.membros){
                    if(inv2.getNome().equalsIgnoreCase(inv.getNome())){
                        ps.add(p);
                        aux++;
                    }
                }
                if(aux>0){
                    aux=0;
                    break;
                }
            }
        }
        return ps;
    }

    /**
     * Retorna o número de investigadores de cada categoria(Membro Efetivo ou Estudante).
     * @return Array de inteiros com o número de investigadores de cada categoria.
     */
    public int[] numMembrosCategoria(){
        int me=0, e=0;
        for(Investigador inv:this.membros){
            if(inv.tipo().equalsIgnoreCase("Membro efetivo")){
                me++;
            }
            else if(inv.tipo().equalsIgnoreCase("Estudante")){
                e++;
            }
        }
        return new int[]{me,e};
    }

    /**
     * Retorna o número de publicações nos últimos 5 anos de um objeto do tipo GrupoInvest.
     * @param pubs ArrayList do tipo Publicacao onde se vai buscar as Publicações.
     * @return Inteiro correspondente ao número de publicações nos últimos 5 anos de um objeto do tipo GrupoInvest.
     */
    public int numPublicacoes5anos(ArrayList<Publicacao> pubs){
        int count=0;

        for(Publicacao p: pubs){
            if(2020-p.getAnoPub()<=5){
                count++;
            }
        }

        return count;
    }
}

/**
 * Classe usada para representar um Investigador.
 * Implementa Serializable.
 */
abstract class Investigador implements Serializable, Comparable<Investigador>{
    protected String nome;
    protected String email;
    protected GrupoInvest grupoInvest;

    /**
     * Inicializa um novo objeto do tipo Investigador com recurso a 2 Strings e um objeto do tipo GrupoInvest.
     */
    public Investigador(String nome, String email, GrupoInvest grupoInvest) {
        this.nome = nome;
        this.email = email;
        this.grupoInvest = grupoInvest;
    }

    public GrupoInvest getGrupoInvest() {
        return grupoInvest;
    }

    public String getNome() {
        return nome;
    }

    /**
     * Retorna o nome do Investigador na investigação.
     * @return String correspondente ao nome do Investigador na investigação.
     */
    abstract public String nomePublicacao();

    /**
     * Retorna o tipo de investigador.
     * @return String correspondente ao tipo de investigador.
     */
    abstract public String tipo();

    /**
     * Comparador apenas serve para ordenar objetos do tipo Investigador por ordem(1º os membros efetivos e depois os estudantes).
     */
    public int compareTo(Investigador i1) {
        return this.tipo().compareTo(i1.tipo() == null ? "" : i1.tipo());
    }

    /**
     * Retorna as publicações de um objeto do tipo Investigador.
     * @param pubs ArrayList do tipo Publicacao onde se vai buscar as Publicações.
     * @return ArrayList do tipo Publicacao com as publicações de um objeto do tipo Investigador.
     */
    public ArrayList<Publicacao> pubs(ArrayList<Publicacao> pubs){
        ArrayList<Publicacao> ps = new ArrayList<>();
        for(Publicacao p: pubs){
            for(Investigador inv:p.getAutores()){
                if(this.getNome().equalsIgnoreCase(inv.getNome())){
                    ps.add(p);
                }
            }
        }

        return ps;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}

/**
 * Classe usada para representar um MembEfet.
 * Implementa Serializable.
 */
class MembEfet extends Investigador implements Serializable{
    private int numGab;
    private int numTelefone;

    /**
     * Inicializa um novo objeto do tipo MembEfet com recurso a 2 Strings, um objeto do tipo GrupoInvest e 2 inteiros.
     */
    public MembEfet(String nome, String email, GrupoInvest grupoPert, int numGab, int numTelefone) {
        super(nome, email, grupoPert);
        this.numGab=numGab;
        this.numTelefone=numTelefone;
    }

    /**
     * Retorna o nome do Investigador na investigação.
     * @return String correspondente ao nome do Investigador na investigação.
     */
    @Override
    public String nomePublicacao(){
        String [] cortes = nome.split(" ");
        return "Professor "+cortes[0]+" "+cortes[cortes.length-1];
    }

    /**
     * Retorna o tipo de investigador.
     * @return String correspondente ao tipo de investigador.
     */
    @Override
    public String tipo(){
        return "Membro efetivo";
    }
}

/**
 * Classe usada para representar um Estudante.
 * Implementa Serializable.
 */
class Estudante extends Investigador implements Serializable{
    private String tituloTese;
    private Data dataConc;
    private Investigador investOrient;

    /**
     * Inicializa um novo objeto do tipo Estudante com recurso a 2 Strings, um objeto do tipo GrupoInvest, uma String, um objeto do tipo Data e um objeto do tipo Investigador.
     */
    public Estudante(String nome, String email, GrupoInvest grupoInvest, String tituloTese, Data dataConc,Investigador investOrient) {
        super(nome, email, grupoInvest);
        this.tituloTese=tituloTese;
        this.dataConc=dataConc;
        if(grupoInvest.getNome().equalsIgnoreCase(investOrient.grupoInvest.getNome())){
            this.investOrient=investOrient;
        }
        else {
            System.out.print("O investigador responsável tem de pertencer ao mesmo grupo de investigação, programa a encerrar");
            System.exit(-1);
        }
    }

    /**
     * Retorna o nome do Investigador na investigação.
     * @return String correspondente ao nome do Investigador na investigação.
     */
    @Override
    public String nomePublicacao(){
        String[] cortes = nome.split(" ");
        return cortes[0].charAt(0)+". "+cortes[cortes.length-1];
    }

    /**
     * Retorna o tipo de investigador.
     * @return String correspondente ao tipo de investigador.
     */
    @Override
    public String tipo(){
        return "Estudante";
    }
}
