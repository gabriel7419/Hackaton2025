import 'package:flutter/material.dart';
import '../services/api_service.dart';
import '../models/aluno.dart';
import '../models/prova.dart';
import '../theme/app_theme.dart';
import '../widgets/custom_button.dart';

class RespostaScreen extends StatefulWidget {
  final Aluno aluno;
  final Prova prova;

  RespostaScreen({required this.aluno, required this.prova});

  @override
  _RespostaScreenState createState() => _RespostaScreenState();
}

class _RespostaScreenState extends State<RespostaScreen> {
  List<String> _respostas = [];
  bool _isLoading = false;

  @override
  void initState() {
    super.initState();
    _respostas = List.filled(widget.prova.totalQuestoes, '');
  }

  _enviarRespostas() async {
    // Verifica se todas as questões foram respondidas
    for (int i = 0; i < _respostas.length; i++) {
      if (_respostas[i].isEmpty) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('Por favor, responda a questão ${i + 1}'),
            backgroundColor: Colors.orange,
          ),
        );
        return;
      }
    }

    setState(() {
      _isLoading = true;
    });

    final result = await ApiService.enviarRespostas(
      provaId: widget.prova.id,
      alunoId: widget.aluno.id,
      respostas: _respostas,
    );

    setState(() {
      _isLoading = false;
    });

    if (result['success']) {
      showDialog(
        context: context,
        barrierDismissible: false,
        builder:
            (context) => AlertDialog(
              title: Text('Sucesso!'),
              content: Column(
                mainAxisSize: MainAxisSize.min,
                children: [
                  Icon(Icons.check_circle, color: Colors.green, size: 64),
                  SizedBox(height: 16),
                  Text('Respostas enviadas com sucesso!'),
                  SizedBox(height: 8),
                  Text('Nota: ${result['nota']}/10.0'),
                  Text('Acertos: ${result['acertos']}/${result['total']}'),
                ],
              ),
              actions: [
                TextButton(
                  onPressed: () {
                    Navigator.of(context).popUntil((route) => route.isFirst);
                  },
                  child: Text('OK'),
                ),
              ],
            ),
      );
    } else {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text(result['message'] ?? 'Erro ao enviar respostas'),
          backgroundColor: Colors.red,
        ),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Responder Prova')),
      body: Column(
        children: [
          Card(
            margin: EdgeInsets.all(16),
            child: Padding(
              padding: EdgeInsets.all(16),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Row(
                    children: [
                      Icon(Icons.assignment, color: AppTheme.primaryColor),
                      SizedBox(width: 8),
                      Expanded(
                        child: Text(
                          widget.prova.titulo,
                          style: TextStyle(
                            fontSize: 18,
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                      ),
                    ],
                  ),
                  SizedBox(height: 8),
                  Text('Aluno: ${widget.aluno.nome}'),
                  Text('Disciplina: ${widget.prova.disciplinaNome}'),
                  Text('Total de questões: ${widget.prova.totalQuestoes}'),
                ],
              ),
            ),
          ),
          Expanded(
            child: ListView.builder(
              padding: EdgeInsets.symmetric(horizontal: 16),
              itemCount: widget.prova.totalQuestoes,
              itemBuilder: (context, index) {
                return Card(
                  margin: EdgeInsets.only(bottom: 12),
                  child: Padding(
                    padding: EdgeInsets.all(16),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          'Questão ${index + 1}',
                          style: TextStyle(
                            fontSize: 16,
                            fontWeight: FontWeight.bold,
                            color: AppTheme.primaryColor,
                          ),
                        ),
                        SizedBox(height: 12),
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                          children:
                              ['A', 'B', 'C', 'D', 'E'].map((opcao) {
                                return Expanded(
                                  child: Padding(
                                    padding: EdgeInsets.symmetric(
                                      horizontal: 4,
                                    ),
                                    child: ElevatedButton(
                                      onPressed: () {
                                        setState(() {
                                          _respostas[index] = opcao;
                                        });
                                      },
                                      style: ElevatedButton.styleFrom(
                                        backgroundColor:
                                            _respostas[index] == opcao
                                                ? AppTheme.primaryColor
                                                : Colors.grey[200],
                                        foregroundColor:
                                            _respostas[index] == opcao
                                                ? Colors.white
                                                : Colors.black,
                                        shape: CircleBorder(),
                                        padding: EdgeInsets.all(16),
                                      ),
                                      child: Text(
                                        opcao,
                                        style: TextStyle(
                                          fontWeight: FontWeight.bold,
                                        ),
                                      ),
                                    ),
                                  ),
                                );
                              }).toList(),
                        ),
                      ],
                    ),
                  ),
                );
              },
            ),
          ),
          Padding(
            padding: EdgeInsets.all(16),
            child: CustomButton(
              text: 'Enviar Respostas',
              onPressed: _isLoading ? null : _enviarRespostas,
              isLoading: _isLoading,
              width: double.infinity,
            ),
          ),
        ],
      ),
    );
  }
}
