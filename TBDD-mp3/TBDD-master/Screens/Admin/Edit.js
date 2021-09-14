import React, {Component} from 'react';
import {
  View,
  TouchableOpacity,
  Text,
  Image,
  StyleSheet,
  TextInput,
  ScrollView,
} from 'react-native';
import back from '../../Assest/image/back.png';

export default class Edit extends Component {
  static navigationOptions = {
    header: null,
  };
  constructor(props) {
    super(props);
    this.state = {
      person: [],
    };
  }
  static navigationOptions = {
    header: null,
  };
  render() {
    const {
      wrapper,
      header,
      headerTitle,
      backIconStyle,
      body,
      signInContainer,
      signInTextStyle,
      textInput,
    } = styles;

    const person = this.props.navigation.state.params;
    console.log(person)
    console.log(this.props)
    return (
      <View style={wrapper}>
        <View>
          <TouchableOpacity
            onPress={this.props.onOpen}
            onPress={() => this.props.navigation.goBack()}>
            <Image
              source={back}
              style={{width: 20, height: 20, margin: 10, marginBottom: 10}}
            />
          </TouchableOpacity>
          <Text style={styles.headerTitle}>Thay đổi thông tin sản phẩm</Text>
        </View>

        <View style={styles.container}>
          <View style={{flex: 10}}>
            <ScrollView>
              <Text style={styles.textI}>Tên sản phẩm</Text>
              <TextInput style={styles.input} placeholder="" />
              <Text style={styles.textI}>Giá sản phẩm</Text>
              <TextInput style={styles.input} placeholder="" />
              <Text style={styles.textI}>Nhà sản xuất</Text>
              <TextInput style={styles.input} placeholder="" />
              <Text style={styles.textI}>Loại hàng</Text>
              <TextInput style={styles.input} placeholder="" />
              <Text style={styles.textI}>Hình ảnh</Text>
              <TextInput style={styles.input} placeholder="" />
              <Text style={styles.textI}>Giảm giá</Text>
              <TextInput style={styles.input} placeholder="" />
            </ScrollView>
          </View>
          <View style={{alignItems: 'center', flex: 2}}>
            <TouchableOpacity style={styles.login}>
              <Text style={styles.signText}>LƯU THAY ĐỔI</Text>
            </TouchableOpacity>
            <TouchableOpacity
              onPress={this.props.onOpen}
              onPress={() => this.props.navigation.goBack()}
              style={{alignItems: 'center'}}>
              <Text style={styles.textII}>Trở lại</Text>
            </TouchableOpacity>
          </View>
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'white',
    padding: 20,
    paddingTop: 0,
    paddingRight: 0,
    justifyContent: 'space-between',
  },
  textII: {
    textDecorationLine: 'underline',
    color: '#7E8186',
    fontSize: 13,
    paddingTop: 10,
    fontFamily: 'Quicksand-SemiBold',
  },
  signText: {
    color: 'white',
    fontFamily: 'Quicksand-Bold',
  },
  login: {
    alignItems: 'center',
    borderRadius: 5,
    paddingTop: 5,
    backgroundColor: '#F13233',
    width: 150,
    height: 40,
    marginTop: 30,
    paddingTop: 10,
  },
  input: {
    height: 30,
    fontSize: 12,
    padding: 1,
    backgroundColor: 'white',
    marginBottom: 20,
    borderBottomWidth: 1,
    borderColor: '#7E8186',
    fontFamily: 'Quicksand-SemiBold',
    color: '#6D6E6A',
    marginRight: 20,
  },
  textI: {
    color: '#6D6E6A',
    fontSize: 18,
    fontFamily: 'Quicksand-SemiBold',
    marginTop: 10,
  },
  headerTitle: {
    color: '#333137',
    fontSize: 22,
    marginLeft: 10,
    marginBottom: 20,
    fontFamily: 'Quicksand-SemiBold',
  },
  wrapper: {flex: 1, backgroundColor: '#fff'},
  header: {
    flex: 1,
    backgroundColor: '#2ABB9C',
    alignItems: 'center',
    justifyContent: 'space-between',
    flexDirection: 'row',
    paddingHorizontal: 10,
  }, // eslint-disable-line
  backIconStyle: {width: 30, height: 30},
  body: {flex: 10, justifyContent: 'center'},
  textInput: {
    height: 45,
    marginHorizontal: 20,
    backgroundColor: '#FFFFFF',
    fontFamily: 'Avenir',
    paddingLeft: 20,
    borderRadius: 20,
    marginBottom: 20,
    borderColor: '#2ABB9C',
    borderWidth: 1,
  },
  signInTextStyle: {
    color: '#FFF',
    fontFamily: 'Avenir',
    fontWeight: '600',
    paddingHorizontal: 20,
  },
  signInContainer: {
    marginHorizontal: 20,
    backgroundColor: '#2ABB9C',
    borderRadius: 20,
    height: 45,
    alignItems: 'center',
    justifyContent: 'center',
    alignSelf: 'stretch',
  },
  signInStyle: {
    flex: 3,
    marginTop: 50,
  },
});

// goBackToMain() {
//     const { navigator } = this.props;
//     navigator.pop();
// }
