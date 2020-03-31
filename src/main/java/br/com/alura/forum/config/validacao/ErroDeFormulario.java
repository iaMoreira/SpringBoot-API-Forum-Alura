package br.com.alura.forum.config.validacao;

public class ErroDeFormulario {
	
	private String campo;
	private String erro;
	
	public String getErro() {
		return erro;
	}

	public String getCampo() {
		return campo;
	}

	public ErroDeFormulario(String campo, String erro) {
		super();
		this.campo = campo;
		this.erro = erro;
	}
	
	
}
