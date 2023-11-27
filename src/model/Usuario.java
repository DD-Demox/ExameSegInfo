package model;

public class Usuario {
    private long id;
    private String senha;
    private Paciente paciente;

    private Senha senhaResultado;

    public Usuario(){

    }
    public Usuario(Paciente paciente){
        this.paciente = paciente;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSenha() {
        return senha;
    }
    public String getSenhaParaCriptografia(){
        if(senha.length()==8){
            return senha;
        }else{
            String senhaParaCriptografia = senha;
            while(senhaParaCriptografia.length()<8){
                senhaParaCriptografia += "0";
            }
            return senhaParaCriptografia;
        }
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
    public Senha getSenhaResultado() {
        return senhaResultado;
    }

    public void setSenhaResultado(Senha senhaResultado) {
        this.senhaResultado = senhaResultado;
    }

}
