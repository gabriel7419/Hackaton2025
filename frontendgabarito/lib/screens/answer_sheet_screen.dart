import 'package:flutter/material.dart';
import '../services/api_service.dart';
import '../models/aluno.dart';
import '../models/prova.dart';
import '../widgets/custom_button.dart';

class AnswerSheetScreen extends StatefulWidget {
  final Aluno aluno;
  final Prova prova;

  const AnswerSheetScreen({
    super.key,
    required this.aluno,
    required this.prova,
  });

  @override
  State<AnswerSheetScreen> createState() => _AnswerSheetScreenState();
}

class _AnswerSheetScreenState extends State<AnswerSheetScreen> {
  final ApiService _apiService = ApiService();
  List<String?> respostas = [];
  bool isLoading = false;

  @override
  void initState() {
    super.initState();
    // Inicializar lista de respostas
    respostas = List.filled(widget.prova.quantidadeQuestoes, null);
  }

  void _selectAnswer(int questionIndex, String answer) {
    setState(() {
      respostas[questionIndex] = answer;
    });
  }

  bool _isFormComplete() {
    return respostas.every((resposta) => resposta != null);
  }

  Future<void> _submitAnswers() async {
    if (!_isFormComplete()) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Por favor, responda todas as questões'),
          backgroundColor: Colors.orange,
        ),
      );
      return;
    }

    setState(() {
      isLoading = true;
    });

    try {
      final respostasString = respostas.join(',');
      final result = await _apiService.submeterRespostas(
        widget.prova.id,
        widget.aluno.id,
        respostasString,
      );

      if (mounted) {
        setState(() {
          isLoading = false;
        });

        if (result['success'] == true) {
          Navigator.pushNamed(
            context,
            '/result',
            arguments: {
              'aluno': widget.aluno,
              'prova': widget.prova,
              'resultado': result['resultado'],
            },
          );
        } else {
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(
              content: Text(result['message'] ?? 'Erro ao submeter respostas'),
              backgroundColor: Colors.red,
            ),
          );
        }
      }
    } catch (e) {
      if (mounted) {
        setState(() {
          isLoading = false;
        });
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('Erro: $e'),
            backgroundColor: Colors.red,
          ),
        );
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Gabarito'),
        backgroundColor: const Color(0xFF2C3E50),
        foregroundColor: Colors.white,
        actions: [
          IconButton(
            onPressed: () {
              _showCameraOption();
            },
            icon: const Icon(Icons.camera_alt),
            tooltip: 'Capturar com Câmera',
          ),
        ],
      ),
      body: Column(
        children: [
          // Header com informações
          Container(
            width: double.infinity,
            padding: const EdgeInsets.all(16),
            color: const Color(0xFFE8F4FD),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  widget.prova.titulo,
                  style: const TextStyle(
                    fontSize: 18,
                    fontWeight: FontWeight.bold,
                    color: Color(0xFF2C3E50),
                  ),
                ),
                Text('Aluno: ${widget.aluno.nome}'),
                Text('Turma: ${widget.prova.turma.nome}'),
                Text('Questões: ${widget.prova.quantidadeQuestoes}'),
              ],
            ),
          ),

          // Progress indicator
          Container(
            padding: const EdgeInsets.all(16),
            child: Column(
              children: [
                Text(
                  'Progresso: ${respostas.where((r) => r != null).length}/${widget.prova.quantidadeQuestoes}',
                  style: const TextStyle(fontWeight: FontWeight.bold),
                ),
                const SizedBox(height: 8),
                LinearProgressIndicator(
                  value: respostas.where((r) => r != null).length /
                      widget.prova.quantidadeQuestoes,
                  backgroundColor: Colors.grey[300],
                  valueColor:
                      const AlwaysStoppedAnimation<Color>(Color(0xFF27AE60)),
                ),
              ],
            ),
          ),

          // Lista de questões
          Expanded(
            child: ListView.builder(
              padding: const EdgeInsets.symmetric(horizontal: 16),
              itemCount: widget.prova.quantidadeQuestoes,
              itemBuilder: (context, index) {
                return Card(
                  margin: const EdgeInsets.only(bottom: 12),
                  child: Padding(
                    padding: const EdgeInsets.all(16),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          'Questão ${index + 1}',
                          style: const TextStyle(
                            fontSize: 16,
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                        const SizedBox(height: 12),
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                          children: ['A', 'B', 'C', 'D', 'E'].map((opcao) {
                            final isSelected = respostas[index] == opcao;
                            return GestureDetector(
                              onTap: () => _selectAnswer(index, opcao),
                              child: Container(
                                width: 50,
                                height: 50,
                                decoration: BoxDecoration(
                                  color: isSelected
                                      ? const Color(0xFF3498DB)
                                      : Colors.grey[200],
                                  borderRadius: BorderRadius.circular(25),
                                  border: Border.all(
                                    color: isSelected
                                        ? const Color(0xFF3498DB)
                                        : Colors.grey,
                                    width: 2,
                                  ),
                                ),
                                child: Center(
                                  child: Text(
                                    opcao,
                                    style: TextStyle(
                                      fontSize: 18,
                                      fontWeight: FontWeight.bold,
                                      color: isSelected
                                          ? Colors.white
                                          : Colors.black,
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

          // Botão de submeter
          Padding(
            padding: const EdgeInsets.all(16),
            child: CustomButton(
              text: 'Submeter Respostas',
              isLoading: isLoading,
              onPressed: _submitAnswers,
              backgroundColor: const Color(0xFF27AE60),
            ),
          ),
        ],
      ),
    );
  }

  void _showCameraOption() {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Capturar Gabarito'),
        content: const Text(
          'Esta funcionalidade permitiria capturar o gabarito usando a câmera. '
          'Para esta demonstração, use o preenchimento manual das respostas.',
        ),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context),
            child: const Text('OK'),
          ),
        ],
      ),
    );
  }
}
