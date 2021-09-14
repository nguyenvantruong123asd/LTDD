import React, {Component} from 'react';
import {
  Text,
  View,
  Button,
  TouchableOpacity,
  StyleSheet,
  Image,
  TextInput,
} from 'react-native';

import back from '../Assest/image/back.png';
import home from '../Assest/Icon/home0.png';

export default class Authentication extends Component {
  static navigationOptions = {
    header: null,
  };

  render() {
    return (
      <View style={styles.container}>
        <View style={{flex: 4}}>
          <TouchableOpacity onPress={this.props.onOpen} onPress={() => this.props.navigation.goBack()}>
            <Image style={styles.icon} source={back} />
          </TouchableOpacity>
          <Text style={styles.styleText}>Quên mật khẩu</Text>
           
          <TouchableOpacity></TouchableOpacity>
        </View>

        <View style={{flex: 9}}>
          <Text style={styles.textI}>Email</Text>
          <TextInput style={styles.input} placeholder="Nhập email của bạn" />
          
          <View style={{alignItems: 'center'}}>
            <TouchableOpacity style={styles.login} onPress={this.props.onOpen} onPress={() => this.props.navigation.push("Verify")}>
              <Text style={styles.signText}>Tiếp tục</Text>
            </TouchableOpacity>
            <TouchableOpacity style={{alignItems: 'center'}} onPress={this.props.onOpen} onPress={() => this.props.navigation.goBack()}>
              <Text style={styles.textII}>Trở lại</Text>
            </TouchableOpacity>
          </View>
        </View>
       
      </View>
    );
  }
}
const styles = StyleSheet.create({
  textII: {
    textDecorationLine: 'underline',
    color: '#7E8186',
    fontSize: 13,
    paddingTop: 15,
    fontFamily: 'Quicksand-SemiBold',
  },
  login: {
    alignItems: 'center',
    borderRadius: 5,
    paddingTop: 5,
    backgroundColor: '#F13233',
    width: 150,
    height: 40,
    marginTop: 60,
    paddingTop: 10,
  },
  signUp: {
    alignItems: 'center',
    borderRadius: 5,
    paddingTop: 5,
    backgroundColor: '#F13233',
    width: 150,
    height: 40,
    marginTop: 20,
    paddingTop: 10,
  },
  textI: {
    color: '#6D6E6A',
    fontSize: 18,
    fontFamily: 'Quicksand-SemiBold',
    marginTop: 10,
  },
  wrapper: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  icon: {
    height: 30,
    width: 30,
  },
  input: {
    height: 40,
    fontSize: 12,
    padding: 1,
    backgroundColor: 'white',
    marginBottom: 20,
    borderBottomWidth: 1,
    borderColor: '#7E8186',
    fontFamily: 'Quicksand-SemiBold',
    color: '#6D6E6A',
  },
  styleText: {
    color: '#333137',
    fontSize: 24,
    fontFamily: 'Quicksand-Bold',
  },
  styleText1: {
    color: '#6D6E6A',
    fontSize: 14,
    fontFamily: 'Quicksand-Regular',
  },
  container: {
    flex: 1,
    backgroundColor: 'white',
    padding: 20,
    paddingTop: 10,
    justifyContent: 'space-between',
  },
  controlStyle: {
    flexDirection: 'row',
    width: 300,
  },
  signIn: {
    backgroundColor: 'white',
    alignItems: 'center',
    paddingVertical: 14,
    flex: 1,
    borderBottomLeftRadius: 20,
    borderTopLeftRadius: 20,
    margin: 1,
    borderWidth: 2,
    borderColor: '#F13233',
  },
  signIn1: {
    backgroundColor: '#F13233',
    alignItems: 'center',
    paddingVertical: 14,
    flex: 1,
    borderBottomLeftRadius: 20,
    borderTopLeftRadius: 20,
    margin: 1,
  },
  signOut: {
    backgroundColor: 'white',
    alignItems: 'center',
    flex: 1,
    paddingVertical: 14,
    borderBottomRightRadius: 20,
    borderTopRightRadius: 20,
    margin: 1,
    borderWidth: 2,
    borderColor: '#F13233',
  },
  signOut1: {
    backgroundColor: '#F13233',
    alignItems: 'center',
    flex: 1,
    paddingVertical: 14,
    borderBottomRightRadius: 20,
    borderTopRightRadius: 20,
    margin: 1,
  },
  isActive: {
    color: '#6D6E6A',
    fontFamily: 'Quicksand-Bold',
    fontSize: 15,
  },
  active: {
    color: 'white',
    fontFamily: 'Quicksand-Bold',
    fontSize: 15,
  },
  sign: {
    height: 40,
    borderRadius: 17,
    alignItems: 'center',
    justifyContent: 'center',
  },
  signText: {
    color: 'white',
    fontFamily: 'Quicksand-Bold',
  },
});
