import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:frontendgabarito/widgets/custom_appbar.dart';
import 'package:google_mlkit_text_recognition/google_mlkit_text_recognition.dart';
import 'package:image_picker/image_picker.dart';
// import '../../datasources/remote/gabarito_remote.dart';

class GabaritoOCRPage extends StatefulWidget {
  final int alunoId;
  const GabaritoOCRPage({super.key, required this.alunoId});

  @override
  State<GabaritoOCRPage> createState() => _GabaritoOCRPageState();
}

class _GabaritoOCRPageState extends State<GabaritoOCRPage> {
  Map<int, String> respostas = {};
  bool carregando = false;
  String? erro;

  Future<void> _lerImagem() async {
    setState(() {
      carregando = true;
      erro = null;
      respostas = {};
    });

    try {
      final picker = ImagePicker();
      final XFile? image = await picker.pickImage(source: ImageSource.camera);

      if (image == null) {
        setState(() {
          carregando = false;
          erro = 'Imagem não capturada, tente novamente.';
        });
        return;
      }

      final inputImage = InputImage.fromFilePath(image.path);
      final textRecognizer = TextRecognizer();
      final RecognizedText recognizedText = await textRecognizer.processImage(
        inputImage,
      );

      final Map<int, String> detectadas = {};
      final RegExp padrao = RegExp(r'(\d{1,2})\s*[-–]\s*([A-Ea-e])');

      for (final block in recognizedText.blocks) {
        for (final line in block.lines) {
          final match = padrao.firstMatch(line.text);
          if (match != null) {
            final numero = int.tryParse(match.group(1)!);
            final letra = match.group(2)!.toUpperCase();
            if (numero != null) {
              detectadas[numero] = letra;
            }
          }
        }
      }

      setState(() {
        respostas = detectadas;
        carregando = false;
      });

      await textRecognizer.close();
    } catch (e) {
      setState(() {
        carregando = false;
        erro = 'Erro no processamento da imagem: $e';
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    final theme = Theme.of(context);

    return Scaffold(
      appBar: const CustomAppBar(title: 'Leitor de Gabarito'),
      backgroundColor: theme.scaffoldBackgroundColor,
      floatingActionButton:
          respostas.isNotEmpty
              ? FloatingActionButton.extended(
                onPressed: _enviarRespostas,
                icon: const Icon(Icons.cloud_upload_rounded),
                label: const Text('Enviar'),
                backgroundColor: theme.primaryColor,
                foregroundColor: Colors.white,
                elevation: 4,
              )
              : null,
      body: Column(
        children: [
          const SizedBox(height: 32),
          Center(
            child: ElevatedButton.icon(
              onPressed: carregando ? null : _lerImagem,
              icon: const Icon(Icons.document_scanner_rounded, size: 28),
              label: const Text('Ler Gabarito com Câmera'),
              style: ElevatedButton.styleFrom(
                backgroundColor: theme.primaryColor,
                foregroundColor: Colors.white,
                padding: const EdgeInsets.symmetric(
                  horizontal: 40,
                  vertical: 20,
                ),
                textStyle: const TextStyle(fontSize: 18),
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(16),
                ),
              ),
            ),
          ),
          const SizedBox(height: 24),

          // Loading ou erro
          if (carregando)
            const CircularProgressIndicator()
          else if (erro != null)
            Padding(
              padding: const EdgeInsets.all(16.0),
              child: Text(erro!, style: const TextStyle(color: Colors.red)),
            ),

          // Lista de respostas detectadas
          if (respostas.isNotEmpty)
            Expanded(
              child: ListView.builder(
                padding: const EdgeInsets.all(16),
                itemCount: respostas.length,
                itemBuilder: (context, index) {
                  final questao = respostas.keys.elementAt(index);
                  final resposta = respostas[questao]!;
                  return Card(
                    margin: const EdgeInsets.symmetric(vertical: 6),
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(12),
                    ),
                    elevation: 3,
                    child: ListTile(
                      leading: const Icon(
                        Icons.check_circle_outline_rounded,
                        color: Color(0xFF0055A5),
                      ),
                      title: Text(
                        'Questão $questao',
                        style: const TextStyle(fontWeight: FontWeight.bold),
                      ),
                      trailing: Text(
                        resposta,
                        style: const TextStyle(
                          color: Color(0xFF0055A5),
                          fontSize: 16,
                          fontWeight: FontWeight.w600,
                        ),
                      ),
                    ),
                  );
                },
              ),
            ),
        ],
      ),
    );
  }

  void _enviarRespostas() {
    final alunoId = widget.alunoId;
    final provaId = '456';

    final jsonSimulado = {
      'alunoId': alunoId,
      'provaId': provaId,
      'respostas': respostas.map((k, v) => MapEntry(k.toString(), v)),
    };

    final jsonFormatado = const JsonEncoder.withIndent(
      '  ',
    ).convert(jsonSimulado);

    showDialog(
      context: context,
      builder:
          (context) => AlertDialog(
            title: const Text('JSON será enviado'),
            content: SingleChildScrollView(child: Text(jsonFormatado)),
            actions: [
              TextButton(
                onPressed: () => Navigator.pop(context),
                child: const Text('Fechar'),
              ),
            ],
            scrollable: true,
          ),
    );
  }
}
