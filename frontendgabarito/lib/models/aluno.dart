class Aluno {
  final int id;
  final String nome;
  final String username;
  final String email;

  Aluno({
    required this.id,
    required this.nome,
    required this.username,
    required this.email,
  });

  factory Aluno.fromJson(Map<String, dynamic> json) {
    return Aluno(
      id: json['id'],
      nome: json['nome'],
      username: json['username'],
      email: json['email'],
    );
  }
}
