import 'package:flutter/material.dart';

class LoginPage extends StatefulWidget {
  const LoginPage({super.key});

  @override
  State<LoginPage> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  final TextEditingController _userController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();
  bool _obscurePassword = true;

  @override
  Widget build(BuildContext context) {
    final theme = Theme.of(context);

    return Scaffold(
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(24.0),
        child: Column(
          children: [
            const SizedBox(height: 60),
            Icon(Icons.cast_for_education, size: 80, color: theme.primaryColor),
            const SizedBox(height: 20),
            Text(
              'Seja bem vindo!',
              style: TextStyle(
                fontSize: 24,
                fontWeight: FontWeight.bold,
                color: theme.primaryColor,
              ),
            ),
            const SizedBox(height: 8),
            Text(
              'Faça login para acessar sua conta',
              style: TextStyle(
                fontSize: 16,
                color: theme.colorScheme.secondary,
              ),
            ),
            const SizedBox(height: 30),

            // Campo usuário
            Align(
              alignment: Alignment.centerLeft,
              child: Text(
                "Usuário",
                style: TextStyle(
                  fontWeight: FontWeight.w600,
                  color: theme.primaryColor,
                ),
              ),
            ),
            const SizedBox(height: 8),
            TextField(
              controller: _userController,
              decoration: InputDecoration(
                prefixIcon: Icon(
                  Icons.person_outline_rounded,
                  color: theme.primaryColor,
                ),
                hintText: 'Insira seu login',
              ),
            ),
            const SizedBox(height: 20),

            // Campo senha
            Align(
              alignment: Alignment.centerLeft,
              child: Text(
                "Senha",
                style: TextStyle(
                  fontWeight: FontWeight.w600,
                  color: theme.primaryColor,
                ),
              ),
            ),
            const SizedBox(height: 8),
            TextField(
              controller: _passwordController,
              obscureText: _obscurePassword,
              decoration: InputDecoration(
                prefixIcon: Icon(
                  Icons.lock_outline_rounded,
                  color: theme.primaryColor,
                ),
                suffixIcon: IconButton(
                  icon: Icon(
                    _obscurePassword ? Icons.visibility_off : Icons.visibility,
                    color: theme.primaryColor,
                  ),
                  onPressed: () {
                    setState(() {
                      _obscurePassword = !_obscurePassword;
                    });
                  },
                ),
                hintText: 'Insira sua senha',
              ),
            ),
            const SizedBox(height: 30),

            // Botão login
            SizedBox(
              width: double.infinity,
              child: ElevatedButton.icon(
                icon: const Icon(Icons.arrow_forward_rounded),
                label: const Text('Entrar no Sistema'),
                onPressed: () {
                  // Lógica do botão
                },
              ),
            ),
            const SizedBox(height: 50),

            // Institucional
            Column(
              children: [
                Image.asset('assets/logo.png', height: 60),
                const SizedBox(height: 10),
                Text(
                  'Faculdade Alfa ',
                  style: TextStyle(
                    fontSize: 18,
                    fontWeight: FontWeight.bold,
                    color: theme.primaryColor,
                  ),
                  textAlign: TextAlign.center,
                ),
                Text(
                  'na prática é superior.',
                  style: TextStyle(
                    fontSize: 14,
                    color: theme.colorScheme.secondary,
                  ),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }
}
