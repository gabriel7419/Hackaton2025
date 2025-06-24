class User {
  final int id;
  final String username;
  final String nome;
  final String email;
  final String role;

  User({
    required this.id,
    required this.username,
    required this.nome,
    required this.email,
    required this.role,
  });

  factory User.fromJson(Map<String, dynamic> json) {
    return User(
      id: json['id'],
      username: json['username'],
      nome: json['nome'],
      email: json['email'],
      role: json['role'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'username': username,
      'nome': nome,
      'email': email,
      'role': role,
    };
  }
}
