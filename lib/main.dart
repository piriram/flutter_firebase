import 'package:flutter/material.dart';
import 'package:firebase_core/firebase_core.dart';
import 'firebase_options.dart';
import 'LoginPage.dart';
import 'RegisterPage.dart';
import 'SuccessRegister.dart';
import 'ChatPage.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'Filter.dart';


void main() async{
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp(
    options: DefaultFirebaseOptions.currentPlatform,
  );
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,

      title: 'Flutter Demo',
      theme: ThemeData(

        primarySwatch: Colors.indigo,
      ),
      //로그인 되어있으면 page 창을 다르게함
      routes:{ '/list':(context)=>const ListFilterPage(),},
      home: StreamBuilder(
        stream: FirebaseAuth.instance.authStateChanges(),
        //타입이 스트림유저타입 로그인이 됐는지 로그아웃이 됐는지 변화를 알려주는 스트림
        //빌더라는 프로퍼티에 전달,함수를 입력받음
        builder: (context,snapshot){
          //스냅샷이 무엇일까
          //변화가 될때 스냅샷을 찍어서 전달
          if(snapshot.hasData){
            return ChatPage();
          }else{
            return const LoginPage();
          }
            //데이터가 있다면,로그인이 돼있다면
        },

      ),
    );
  }
}
