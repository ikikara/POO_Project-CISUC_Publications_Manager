package com.company;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import static com.company.Main.procGrp;
import static com.company.Main.procInv;

/**
 * Classe Menu que é responsável por responder a todos os pontos do enunciado.
 * Apenas contém métodos para mostrar informação de um determinado objeto do tipo CISUC
 */
class Menu{
    private CISUC cisuc;

    /**
     * Inicializa um novo objeto do tipo Menu com a informação de um objeto do tipo CISUC
     */
    public Menu(CISUC cisuc){this.cisuc=cisuc;}

    /**
     * Apenas referente ao ponto 1 do enunciado.
     */
    public void s1(){
        System.out.print("CISUC:\n");
        System.out.printf(" %d membros\n", this.cisuc.totalInvs());
        System.out.print(" Membros:\n");
        System.out.printf("  %d membros efetivos\n  %d estudantes\n", this.cisuc.totalInvsCategoria()[0], this.cisuc.totalInvsCategoria()[1]);
        System.out.printf(" %d publicações nos últimos 5 anos\n", this.cisuc.totalPubs5anos());
        System.out.print(" Publicacoes:\n");
        System.out.printf("  %d artigos de conferencia\n  %d artigos de revista\n  %d livros\n  %d livros de conferência\n  %d capitulos de livro\n", this.cisuc.totalPubsTipo()[0], this.cisuc.totalPubsTipo()[1], this.cisuc.totalPubsTipo()[2], this.cisuc.totalPubsTipo()[3], this.cisuc.totalPubsTipo()[4]);
    }

    /**
     * Apenas referente ao ponto 2 do enunciado.
     */
    public void s2(){
        Scanner sc2 = new Scanner(System.in);
        System.out.print("Pretende consultar as publicações de que grupo(introduza o acrónimo): ");
        String nome = sc2.nextLine();
        GrupoInvest grp=procGrp(cisuc.getGrps(),nome);
        if(grp!=null){
            this.cisuc.mostraPOrganiza(grp.pubs(this.cisuc.getPubs()));
        }
        else{
            System.out.print("Grupo não existe\n");
        }
    }

    /**
     * Apenas referente ao ponto 3 do enunciado.
     */
    public void s3(){
        Scanner sc3 = new Scanner(System.in);
        System.out.print("Pretende consultar os investigadores de que grupo(introduza o acrónimo): ");
        String nome = sc3.nextLine();
        GrupoInvest grp=procGrp(cisuc.getGrps(),nome);
        if(grp!=null){
            this.cisuc.mostraIOrganizarPorCategoria(grp);
        }
        else{
            System.out.print("Grupo não existe");
        }
    }

    /**
     * Apenas referente ao ponto 4 do enunciado.
     */
    public void s4(){
        Scanner sc4 = new Scanner(System.in);
        System.out.print("Esse investigador faz parte de que grupo(introduza o acrónimo): ");
        String nome = sc4.nextLine();
        GrupoInvest grp=procGrp(cisuc.getGrps(),nome);
        if(grp!=null){
            System.out.print("Qual o nome desse investigador? ");
            nome = sc4.nextLine();
            Investigador inv=procInv(cisuc.getInvs(),nome);
            if(inv!=null){
                this.cisuc.mostraPOrganiza(inv.pubs(this.cisuc.getPubs()));
            }
            else {
                System.out.print("Investigador não existe ou não está neste grupo");
            }
        }
        else{
            System.out.print("Grupo não existe");
        }
    }

    /**
     * Apenas referente ao ponto 5 do enunciado.
     */
    public void s5(){
        this.cisuc.infoGrupos();
    }
}

/**
 * Class que representa um determinado Grupo de Grupos de Investigação e as suas publicações.
 * Contendo métodos para organizar e listar a sua informação.
 * Implementa Serializable.
 */
class CISUC implements Serializable {
    private ArrayList<GrupoInvest> grps;
    private ArrayList<Investigador> invs;
    private ArrayList<Publicacao> pubs;

    /**
     * Inicializa um novo objeto do tipo CISUC com recurso a um determinado ArrayList do tipo GrupoInvect, outro
     * do tipo Investigador e ainda outro do tipo Publicacao.
     */
    public CISUC(ArrayList<GrupoInvest> grps, ArrayList<Investigador> invs, ArrayList<Publicacao> pubs) {
        this.grps = grps;
        this.invs = invs;
        this.pubs = pubs;
    }

    public ArrayList<GrupoInvest> getGrps() {
        return grps;
    }

    public ArrayList<Investigador> getInvs() {
        return invs;
    }

    public ArrayList<Publicacao> getPubs() {
        return pubs;
    }

    /**
     * Retorna o número de investigadores.
     * @return Inteiro correspondente ao número de investigadores.
     */
    public int totalInvs(){
        return invs.size();
    }

    /**
     * Retorna o número de investigadores de cada categoria(Membro Efetivo ou Estudante).
     * @return Array de inteiros com o número de investigadores de cada categoria.
     */
    public int[] totalInvsCategoria(){
        int countE=0, countME=0;
        for(Investigador investigador: this.invs){
            if(investigador.tipo().equalsIgnoreCase("Membro efetivo")){
                countME++;
            }
            else if(investigador.tipo().equalsIgnoreCase("Estudante")){
                countE++;
            }
        }
        return new int[]{countME,countE};
    }

    /**
     * Retorna o número de publicações nos últimos 5 anos.
     * @return Inteiro correspondente ao número de publicações nos últimos 5 anos.
     */
    public int totalPubs5anos(){
        int count=0;
        for(Publicacao publicacao: this.pubs){
            if(2020-publicacao.getAnoPub()<=5){
                count++;
            }
        }
        return count;
    }

    /**
     * Retorna o número de publicações de cada tipo(A.Conferência, A.Revista,...).
     * @return Array de inteiros com o número de de publicações de cada tipo.
     */
    public int[] totalPubsTipo(){
        int countAC=0,countAR=0,countL=0,countCL=0,countLC=0;
        for(Publicacao publicacao: this.pubs){
            if(publicacao.getTipo().equalsIgnoreCase("Artigo de Conferencia")){
                countAC++;
            }
            else if(publicacao.getTipo().equalsIgnoreCase("Artigo de Revista")){
                countAR++;
            }
            else if(publicacao.getTipo().equalsIgnoreCase("Livro")){
                countL++;
            }
            else if(publicacao.getTipo().equalsIgnoreCase("Capitulo de Livro")){
                countCL++;
            }
            else if(publicacao.getTipo().equalsIgnoreCase("Livro de Conferencias")){
                countLC++;
            }
        }
        return new int[]{countAC,countAR,countL,countLC,countCL};
    }

    /**
     * Método que lista as publicações organizadas por ano, seguido do tipo e finalmente por fator.
     * Funciona tanto para as publicações de um grupo de investigação tanto para um investigador, a única coisa que
     * mudaria seria o ArrayList de publicações recebido.
     * @param ps ArrayList do tipo Publicacao.
     */
    public void mostraPOrganiza(ArrayList<Publicacao> ps){
        Collections.sort(ps);

        int ano=-1;
        String tipo="";
        String fator="";

        for(Publicacao p: ps){
            if(2020-p.getAnoPub()<=5) {
                if(p.getAnoPub()!=ano){
                    ano=p.getAnoPub();
                    System.out.printf("\nAno %d:\n", ano);
                    if(!p.getTipo().equalsIgnoreCase(tipo)) {
                        tipo = p.getTipo();
                    }
                    System.out.println(" "+tipo+":");
                    if(!p.getFatorImp().equalsIgnoreCase(fator)) {
                        fator = p.getFatorImp();
                    }
                    System.out.println("  "+fator+":");
                }
                if(!p.getTipo().equalsIgnoreCase(tipo)){
                    tipo=p.getTipo();
                    System.out.println(" "+tipo+":");
                    if(!p.getFatorImp().equalsIgnoreCase(fator)) {
                        fator = p.getFatorImp();
                    }
                    System.out.println("  "+fator+":");
                }
                if(!p.getFatorImp().equalsIgnoreCase(fator)){
                    fator=p.getFatorImp();
                    System.out.println("  "+fator+":");
                }


                System.out.print("  " + p);

                System.out.println();
            }
        }
    }

    /**
     * Método que lista os investigadores de determinado grupo organizados por tipo (Membro Efetivo/Estudante).
     * @param grp ArrayList do tipo GrupoInvest.
     */
    public void mostraIOrganizarPorCategoria(GrupoInvest grp){
        Collections.sort(grp.getMembros());
        String tipo="";

        for(Investigador i:grp.getMembros()){
            if(!tipo.equalsIgnoreCase(i.tipo())){
                tipo=i.tipo();
                System.out.printf("\n%s:\n", tipo);
            }
            System.out.print(" "+i+"\n");
        }
    }

    /**
     * Método que mostra toda a informação de todos os grupos.
     */
    public void infoGrupos(){
        for(GrupoInvest grp: this.grps){
            System.out.print(grp.getNome()+":\n");
            System.out.printf(" %d membros\n",grp.getMembros().size());
            System.out.print(" Membros:\n");
            System.out.printf("  %d membros efetivos\n  %d estudantes\n", grp.numMembrosCategoria()[0],grp.numMembrosCategoria()[1]);
            System.out.printf(" %d publicações nos últimos 5 anos\n", grp.numPublicacoes5anos(grp.pubs(this.pubs)));

            ArrayList<Publicacao> ps = grp.pubs(this.pubs);
            Collections.sort(ps);

            int anos5=0;

            for(Publicacao p: ps){
                if(2020-p.getAnoPub()<=5){
                    anos5++;
                }
            }

            int ano=-1;
            String tipo="";
            String fator="";
            int count=0;

            if(anos5!=0){
                System.out.print("\n Publicações no últimos 5 anos :\n");

                for(Publicacao p: ps){
                    if(2020-p.getAnoPub()<=5){
                        if(p.getAnoPub()!=ano){
                            if(count!=0){
                                System.out.print("    "+ count+" publicação(ões) \n");
                                count=0;
                            }
                            ano=p.getAnoPub();
                            System.out.printf(" Ano %d:\n", ano);
                            if(!p.getTipo().equalsIgnoreCase(tipo)) {
                                tipo = p.getTipo();
                            }
                            System.out.println("  "+tipo+":");
                            if(!p.getFatorImp().equalsIgnoreCase(fator)) {
                                fator = p.getFatorImp();
                            }
                            System.out.println("   "+fator+":");
                        }
                        if(!p.getTipo().equalsIgnoreCase(tipo)){
                            if(count!=0){
                                System.out.print("    "+ count+" publicação(ões) \n");
                                count=0;
                            }
                            tipo=p.getTipo();
                            System.out.println("  "+tipo+":");
                            if(!p.getFatorImp().equalsIgnoreCase(fator)) {
                                fator = p.getFatorImp();
                            }
                            System.out.println("   "+fator+":");
                        }
                        if(!p.getFatorImp().equalsIgnoreCase(fator)){
                            if(count!=0){
                                System.out.print("    "+ count+" publicação(ões) \n");
                                count=0;
                            }
                            fator=p.getFatorImp();
                            System.out.println("   "+fator+":");
                        }

                        count++;
                    }
                    if(ps.indexOf(p)+1==ps.size()){
                        System.out.print("    "+ count+" publicação(ões) \n\n");
                    }
                }
            }
        }
    }
}

/**
 * Classe usada para representar datas.
 * Implementa Serializable.
 */
class Data implements Serializable{
    private int dia;
    private int mes;
    private int ano;

    /**
     * Inicializa um novo objeto do tipo Data com recurso a 3 inteiros (dia, mês, ano).
     */
    public Data(int dia, int mes, int ano){
        this.mes=mes;
        this.ano=ano;
        if(dia>0){
            if((mes==1 || mes==3 || mes==5 || mes==7 || mes==10 || mes==12) && dia<=31){
                this.dia=dia;
            }
            else if((mes==4 || mes==6 || mes==8 || mes==9 || mes==11) && dia <=30){
                this.dia=dia;
            }
            else if(mes==2 && dia<=29){
                this.dia=dia;
            }
            else{
                System.out.print("Data introduzida incorreta, programa a encerrar");
                System.exit(-1);
            }
        }
        else{
            System.out.print("Data introduzida incorreta, programa a encerrar");
            System.exit(-1);
        }
    }
}