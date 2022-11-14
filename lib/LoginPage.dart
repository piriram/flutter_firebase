import 'package:flutter/material.dart';
import 'package:flutter_firebase/ChatPage.dart';
import 'RegisterPage.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:modal_progress_hud_nsn/modal_progress_hud_nsn.dart';

class LoginPage extends StatelessWidget {
  const LoginPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Login'),
      ),
      body:LoginForm(),
    );
  }
}
class LoginForm extends StatefulWidget {
  const LoginForm({Key? key}) : super(key: key);

  @override
  State<LoginForm> createState() => _LoginFormState();
}

class _LoginFormState extends State<LoginForm> {
  final _authentication = FirebaseAuth.instance;
  //Form 위젯에는 globalkey가 필요하다
  final _formKey=GlobalKey<FormState>();
  bool showSpinner = false;
  String email='';
  String password='';
  @override
  //로딩할때 스피닝이 돌게하는 모달
  Widget build(BuildContext context) {
    return ModalProgressHUD(
      inAsyncCall: showSpinner,
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Form(

          key:_formKey,
          child: ListView(
            children: [
              TextFormField(
                decoration:InputDecoration(
                  labelText: 'Email'
                ),
                onChanged: (value){
                  email=value;
                  print(email);
                },

              ),
              SizedBox(
                height: 20,
              ),
              TextFormField(
                obscureText: true,
                decoration: InputDecoration(
                  labelText: 'Password'
                ),
                onChanged: (value){
                  password=value;
                  print(password);
                },

              ),
              ElevatedButton(onPressed: () async{
                try{//enter를 누르고 스피너가 돌게끔
                  setState(() {
                    showSpinner=true;
                  });
                final currentUser = await _authentication.signInWithEmailAndPassword(email: email, password: password);
                if(currentUser.user != null){
                  _formKey.currentState!.reset();
                  if(!mounted) return;
                  Navigator.push(context,MaterialPageRoute(builder: (context)=>ChatPage()));
                  setState(() {
                    showSpinner=false;
                  });
                  }
                }
                    catch(e){
                    print(e);
                    }
                }, child: const Text('Enter')),
              Row(
                mainAxisAlignment: MainAxisAlignment.end,
                children: [
                  Text('If you did not register, '),
                  TextButton(
                    child:Text('Register your email.'),
                    onPressed: (){
                      Navigator.push(context,MaterialPageRoute(builder: (context)=>RegisterPage()));
                    },
                  )
                ],
              )
            ],
          ),
        ),
      ),
    );
  }
}
