import 'package:flutter/material.dart';

class SuccessRegisterPage extends StatelessWidget {
  const SuccessRegisterPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Register'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text('You have successfuly registered.',
              style:
              TextStyle (
                fontSize: 20,
              ),
            ),
            SizedBox(
              height:20,
            ),
            ElevatedButton(onPressed: (){
              Navigator.popUntil(context, (route) => route.isFirst);
            }, child: Text('Login')),
          ],
        ),
      ),
    );
  }
}
