import React, {Component} from 'react';
import {
  Text,
  View,
  TouchableOpacity,
  StyleSheet,
  Image,
  ScrollView,
} from 'react-native';
import profile from '../Assest/image/profile.png';
import IP from '../Assest/image/camIP.png';

export default class Menu extends Component {
  static navigationOptions = {
    header: null,
  };
  constructor(props) {
    super(props);

    this.state = {isLoggin: true};
  }
  render() {
    const logout = (
      <View style={{flex: 1}}>
        <View style={styles.loginBox}>
          <Image source={profile} style={styles.profile} />
          <View></View>
          <TouchableOpacity style={styles.btnStyle}>
            <Text style={styles.btnText}>Đăng nhập</Text>
          </TouchableOpacity>
        </View>
        <View style={{flex: 15}}></View>
        <View style={{flex: 4, marginTop: 70}}>
          <TouchableOpacity style={styles.btnInStyle}>
            <Text style={styles.btnInText}>Cài đặt</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.btnInStyle}>
            <Text style={styles.btnInText}>Thông tin</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.btnInStyle}>
            <Text style={styles.btnInText}>Đăng ký</Text>
          </TouchableOpacity>
        </View>
      </View>
    );
    const loggin = (
      <View style={{flex: 1}}>
        <View style={{flex: 3}}>
          <View style={styles.loginBox}>
            <Image source={profile} style={styles.profile1} />
            <TouchableOpacity style={styles.btnStyle1} >
              <Text style={styles.btnText1}>Nguyễn Phước Đức</Text>
            </TouchableOpacity>
          </View>
        </View>

        <View style={{flex: 15}}>
          <ScrollView>
            <TouchableOpacity>
              <View style={{flexDirection: 'row', paddingTop: 10}}>
                <Image
                  style={{width: 20, height: 25, paddingRight: 10}}
                  source={IP}
                />
                <Text style={styles.menuText1}>Camera IP</Text>
              </View>
            </TouchableOpacity>

            <View style={{paddingLeft: 30, paddingTop: 8}}>
              <TouchableOpacity>
                <Text style={styles.menuText2}>Carema IP HP</Text>
              </TouchableOpacity>
              <TouchableOpacity>
                <Text style={styles.menuText2}>Carema IP HP</Text>
              </TouchableOpacity>
              <TouchableOpacity>
                <Text style={styles.menuText2}>Carema IP HP</Text>
              </TouchableOpacity>
              <TouchableOpacity>
                <Text style={styles.menuText2}>Carema IP HP</Text>
              </TouchableOpacity>
            </View>

            <TouchableOpacity>
              <View style={{flexDirection: 'row', paddingTop: 10}}>
                <Image
                  style={{width: 20, height: 25, paddingRight: 10}}
                  source={IP}
                />
                <Text style={styles.menuText1}>Camera IP</Text>
              </View>
            </TouchableOpacity>

            <View style={{paddingLeft: 30, paddingTop: 8}}>
              <TouchableOpacity>
                <Text style={styles.menuText2}>Carema IP HP</Text>
              </TouchableOpacity>
              <TouchableOpacity>
                <Text style={styles.menuText2}>Carema IP HP</Text>
              </TouchableOpacity>
              <TouchableOpacity>
                <Text style={styles.menuText2}>Carema IP HP</Text>
              </TouchableOpacity>
              <TouchableOpacity>
                <Text style={styles.menuText2}>Carema IP HP</Text>
              </TouchableOpacity>
            </View>
            <TouchableOpacity>
              <View style={{flexDirection: 'row', paddingTop: 10}}>
                <Image
                  style={{width: 20, height: 25, paddingRight: 10}}
                  source={IP}
                />
                <Text style={styles.menuText1}>Camera IP</Text>
              </View>
            </TouchableOpacity>

            <View style={{paddingLeft: 30, paddingTop: 8}}>
              <TouchableOpacity>
                <Text style={styles.menuText2}>Carema IP HP</Text>
              </TouchableOpacity>
              <TouchableOpacity>
                <Text style={styles.menuText2}>Carema IP HP</Text>
              </TouchableOpacity>
              <TouchableOpacity>
                <Text style={styles.menuText2}>Carema IP HP</Text>
              </TouchableOpacity>
              <TouchableOpacity>
                <Text style={styles.menuText2}>Carema IP HP</Text>
              </TouchableOpacity>
            </View>
            <TouchableOpacity>
              <View style={{flexDirection: 'row', paddingTop: 10}}>
                <Image
                  style={{width: 20, height: 25, paddingRight: 10}}
                  source={IP}
                />
                <Text style={styles.menuText1}>Camera IP</Text>
              </View>
            </TouchableOpacity>

            <View style={{paddingLeft: 30, paddingTop: 8}}>
              <TouchableOpacity>
                <Text style={styles.menuText2}>Carema IP HP</Text>
              </TouchableOpacity>
              <TouchableOpacity>
                <Text style={styles.menuText2}>Carema IP HP</Text>
              </TouchableOpacity>
              <TouchableOpacity>
                <Text style={styles.menuText2}>Carema IP HP</Text>
              </TouchableOpacity>
              <TouchableOpacity>
                <Text style={styles.menuText2}>Carema IP HP</Text>
              </TouchableOpacity>
            </View>
          </ScrollView>
        </View>
        <View style={{flex: 4, marginTop: 70}}>
          <TouchableOpacity style={styles.btnInStyle}>
            <Text style={styles.btnInText}>Cài đặt</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.btnInStyle}>
            <Text style={styles.btnInText}>Thông tin</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.btnInStyle}>
            <Text style={styles.btnInText}>Đăng xuất</Text>
          </TouchableOpacity>
        </View>
        <View />
      </View>
    );

    const main = this.state.isLoggin ? logout : loggin;
    return <View style={styles.container}>{main}</View>;
  }
}

const styles = StyleSheet.create({
  menuText1: {
    color: 'white',
    fontSize: 18,
    fontFamily: 'Quicksand-Bold',
    paddingLeft: 10,
  },
  menuText2: {
    color: 'white',
    fontSize: 14,
    fontFamily: 'Quicksand-SemiBold',
  },
  container: {
    flex: 1,
    backgroundColor: '#76A466',
    borderRightWidth: 3,
    borderColor: '#fff',
    alignItems: 'center',
  },
  loginBox: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: 'white',
    borderRadius: 10,
    width: 200,
    marginTop: 10,
    justifyContent: 'space-between',
  },
  profile: {
    width: 60,
    height: 60,
    borderRadius: 50,
  },
  profile1: {
    width: 40,
    height: 40,
    borderRadius: 50,
  },
  btnStyle: {
    height: 40,
    backgroundColor: '#76A466',
    alignItems: 'center',
    borderRadius: 7,
    width: 120,
    paddingTop: 3,
    marginRight: 10,
  },
  btnStyle1: {
    height: 50,
    backgroundColor: '#76A466',
    alignItems: 'center',
    width: 160,
    justifyContent: 'center',
    borderWidth: 2,
    borderColor: 'white',
    borderBottomRightRadius: 10,
    borderTopRightRadius: 10,
    padding: 4,
  },
  btnText: {
    color: 'white',
    fontSize: 20,
    fontFamily: 'Quicksand-Bold',
  },
  btnText1: {
    color: 'white',
    fontSize: 16,
    fontFamily: 'Quicksand-Bold',
  },
  btnInStyle: {
    height: 30,
    backgroundColor: '#fff',
    borderRadius: 10,
    marginBottom: 8,
    width: 200,
    paddingLeft: 20,
    paddingTop: 3,
  },
  btnInText: {
    color: '#76A466',
    fontSize: 16,
    fontFamily: 'Quicksand-Bold',
  },
});
