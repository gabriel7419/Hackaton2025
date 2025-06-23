class Aluno {
  final int id;
  final String nome;
  final String email;

  Aluno({required this.id, required this.nome, required this.email});

  factory Aluno.fromJson(Map<String, dynamic> json) {
    return Aluno(id: json['id'], nome: json['nome'], email: json['email']);
  }
}
