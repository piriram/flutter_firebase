import 'package:flutter/material.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:cloud_firestore/cloud_firestore.dart';

import 'SuccessRegister.dart';
class RegisterPage extends StatelessWidget {
  const RegisterPage({Key? key}) : super(key: key);
//화요일 ppt 18page where equal 어쩌고 사용
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Register'),
      ),
      body: const RegisterForm(),
    );
  }
}
class RegisterForm extends StatefulWidget {
  const RegisterForm({Key? key}) : super(key: key);

  @override
  State<RegisterForm> createState() => _RegisterFormState();
}

class _RegisterFormState extends State<RegisterForm> {
  final _authentication = FirebaseAuth.instance;
  final _formKey=GlobalKey<FormState>();
  String email='';
  String password='';
  String userName='';
  @override
  Widget build(BuildContext context) {
    return Padding(
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
              },

            ),
            SizedBox(
              height: 20,
            ),
            TextFormField(
              //obscureText: true,
              decoration: InputDecoration(
                  labelText: 'User Name'
              ),
              onChanged: (value){
                userName=value;
              },

            ),
            ElevatedButton(onPressed: () async {
              try{

              final newUser=await _authentication.createUserWithEmailAndPassword(
                  email: email, password: password);
              //동기?비동기?
              await FirebaseFirestore.instance
                  .collection('user')
                  .doc(newUser.user!.uid)
                  .set({
                'userName':userName,
                'email':email,
              });
              //이메일로 아이디를 넣어도되고 random스틱으로 발급해도 된다 uid라는 것 문서의 아이디로 사용 더 안전
              //set이라는게 퓨처타입이니깐 비동기 동기로 설정해주자
              if(newUser.user!=null) {
                _formKey.currentState!.reset();//null이 아니더라면
                if (!mounted) return; //안전하지 않기때문에 state가 트리에 mount 됐다는 것을 확인한 후 push
                Navigator.push(context, MaterialPageRoute(
                    builder: (context) => const SuccessRegisterPage()));
              }
              }catch(e){
                print(e);
              }
            }, child: const Text('Enter')),
            Row(
              mainAxisAlignment: MainAxisAlignment.end,
              children: [
                Text('If you already registered your email, '),
                TextButton(
                  child:Text('Login with your email.'),
                  onPressed: (
                      ){
                    Navigator.pop(context);
                  },
                )
              ],
            )
          ],
        ),
      ),
    );
  }
}

