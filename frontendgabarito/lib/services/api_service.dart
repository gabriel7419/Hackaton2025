import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/user.dart';
import '../models/prova.dart';
import '../models/aluno.dart';
import '../models/resultado.dart';

class ApiService {
  static const String baseUrl =
      //'http://10.0.2.2:8080/api'; // Para emulador Android
      'http://localhost:8080/api'; // Para iOS ou web

  Future<Map<String, dynamic>> login(String username, String password) async {
    try {
      final response = await http.post(
        Uri.parse('$baseUrl/login'),
        headers: {'Content-Type': 'application/json'},
        body: jsonEncode({
          'username': username,
          'password': password,
        }),
      );

      if (response.statusCode == 200) {
        return jsonDecode(response.body);
      } else {
        return {'success': false, 'message': 'Erro na autenticação'};
      }
    } catch (e) {
      return {'success': false, 'message': 'Erro de conexão: $e'};
    }
  }

  Future<List<Aluno>> getAlunos() async {
    try {
      final response = await http.get(
        Uri.parse('$baseUrl/alunos'),
        headers: {'Content-Type': 'application/json'},
      );

      if (response.statusCode == 200) {
        List<dynamic> data = jsonDecode(response.body);
        return data.map((json) => Aluno.fromJson(json)).toList();
      } else {
        throw Exception('Erro ao carregar alunos');
      }
    } catch (e) {
      throw Exception('Erro de conexão: $e');
    }
  }

  Future<List<Prova>> getProvas() async {
    try {
      final response = await http.get(
        Uri.parse('$baseUrl/provas'),
        headers: {'Content-Type': 'application/json'},
      );

      if (response.statusCode == 200) {
        List<dynamic> data = jsonDecode(response.body);
        return data.map((json) => Prova.fromJson(json)).toList();
      } else {
        throw Exception('Erro ao carregar provas');
      }
    } catch (e) {
      throw Exception('Erro de conexão: $e');
    }
  }

  Future<Prova?> getProva(int id) async {
    try {
      final response = await http.get(
        Uri.parse('$baseUrl/provas/$id'),
        headers: {'Content-Type': 'application/json'},
      );

      if (response.statusCode == 200) {
        return Prova.fromJson(jsonDecode(response.body));
      } else {
        return null;
      }
    } catch (e) {
      print('Erro ao carregar prova: $e');
      return null;
    }
  }

  Future<Map<String, dynamic>> submeterRespostas(
    int provaId,
    int alunoId,
    String respostas,
  ) async {
    try {
      final response = await http.post(
        Uri.parse('$baseUrl/respostas'),
        headers: {'Content-Type': 'application/json'},
        body: jsonEncode({
          'provaId': provaId,
          'alunoId': alunoId,
          'respostas': respostas,
        }),
      );

      return jsonDecode(response.body);
    } catch (e) {
      return {'success': false, 'message': 'Erro de conexão: $e'};
    }
  }

  Future<List<Map<String, dynamic>>> getNotasAluno(int alunoId) async {
    try {
      final response = await http.get(
        Uri.parse('$baseUrl/alunos/$alunoId/notas'),
        headers: {'Content-Type': 'application/json'},
      );

      if (response.statusCode == 200) {
        List<dynamic> data = jsonDecode(response.body);
        return data.cast<Map<String, dynamic>>();
      } else {
        throw Exception('Erro ao carregar notas');
      }
    } catch (e) {
      throw Exception('Erro de conexão: $e');
    }
  }
}
