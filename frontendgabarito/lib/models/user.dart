class User {
  final int id;
  final String nome;
  final String email;
  final String perfil;

  User({
    required this.id,
    required this.nome,
    required this.email,
    required this.perfil,
  });

  factory User.fromJson(Map<String, dynamic> json) {
    return User(
      id: json['id'],
      nome: json['nome'],
      email: json['email'],
      perfil: json['perfil'],
    );
  }

  Map<String, dynamic> toJson() {
    return {'id': id, 'nome': nome, 'email': email, 'perfil': perfil};
  }
}
