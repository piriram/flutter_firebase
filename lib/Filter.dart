import 'package:flutter/material.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:flutter_chat_bubble/bubble_type.dart';
import 'package:flutter_chat_bubble/chat_bubble.dart';
import 'package:flutter_chat_bubble/clippers/chat_bubble_clipper_6.dart';
import 'ChatPage.dart';
import 'main.dart';

import 'package:flutter/foundation.dart';
class NamePage extends StatefulWidget {
  const NamePage({Key? key}) : super(key: key);

  @override
  State<NamePage> createState() => _NamePageState();
}

class _NamePageState extends State<NamePage> {
  final _nameController=TextEditingController();
  String name = '';
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(''),

      ),
      body: Container(
        child: Center(

          child: Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [

              //Text('Name',textAlign: TextAlign.right,),

              Padding(
                padding: const EdgeInsets.fromLTRB(100, 0, 0, 0),
                child: TextFormField(
                    decoration: const InputDecoration(
                      border: UnderlineInputBorder(),
                      labelText: 'Name',


                    ),
                    onChanged: (value){
                      setState(() {
                        name=value!;
                      });
                    },
                    controller: _nameController,
                ),
              ),
              ElevatedButton(
                  onPressed: (){
                    print('${name}');
                    Navigator.pushNamed(context, '/list', arguments:ScreenArguments(name));
                  },
                  child: Text('Enter')),

            ],
          ),


        ),
      ),
    );
  }
}
class ListFilterPage extends StatefulWidget {
  const ListFilterPage({Key? key}) : super(key: key);

  @override
  State<ListFilterPage> createState() => _ListFilterPageState();

}

class _ListFilterPageState extends State<ListFilterPage> {
  final _authentication = FirebaseAuth.instance;
  User? loggedUser;

  @override
  void initState() {
    super.initState();
    getCurrentUser();
  }

  void getCurrentUser() {
    try {
      final user = _authentication.currentUser;
      if (user != null) {
        loggedUser = user;
      }
    } catch (e) {
      print(e);
    }
  }
  @override
  Widget build(BuildContext context) {
    final args = ModalRoute.of(context)?.settings.arguments as ScreenArguments;
    return Scaffold(
      appBar: AppBar(
        title: Text('Chat List'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(8.0),
        child: Column(
          children: [
            Expanded(
              child: StreamBuilder(//쫘라락
                stream: FirebaseFirestore.instance.collection('chat').where("userName",isEqualTo:'${args.name}').orderBy('timestamp').snapshots(),//timestamp로 정렬
                builder: (context, snapshot) {
                  if (snapshot.connectionState == ConnectionState.waiting) {
                    return const Center(child: CircularProgressIndicator());
                  }
                  final docs = snapshot.data!.docs;
                  //final ref = FirebaseFirestore.instance.collection('chat').where("userName",isEqualTo:"woozico914");
                  //final chatRef = snapshot.collection('chat').where('userName',arrayContains:name);
                  return ListView.builder(
                    itemCount: docs.length,
                    itemBuilder: (context, index) {//문서값을 리턴
                      return ListTile(
                        //isMe: docs[index]['uid'] == _authentication.currentUser!.uid,//같으면
                        //userName: docs[index]['userName'],
                        //text: docs[index]['text'],
                        title:Text('${docs[index]['text']} : by ${docs[index]['userName']}'),
                      );
                    },
                  );
                },
              ),
            ),
          ],
        ),
      ),
    );
  }
}
class ScreenArguments {
  final String name;

  ScreenArguments(this.name);
}
