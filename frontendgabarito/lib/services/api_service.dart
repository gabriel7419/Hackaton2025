import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/aluno.dart';
import '../models/prova.dart';

class ApiService {
  static const String baseUrl = 'http://localhost:8080/api';

  static Future<List<Aluno>> getAlunos() async {
    try {
      final response = await http.get(Uri.parse('$baseUrl/alunos'));

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

  static Future<List<Prova>> getProvas() async {
    try {
      final response = await http.get(Uri.parse('$baseUrl/provas'));

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

  static Future<Map<String, dynamic>> enviarRespostas({
    required int provaId,
    required int alunoId,
    required List<String> respostas,
  }) async {
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
}
