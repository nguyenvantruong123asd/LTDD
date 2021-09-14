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

export default class Info extends Component {
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
    const person = this.props.navigation.state.params;
    console.log(person);
    console.log(this.props);
    return (
      <View style={{flex: 1}}>
        <View>
          <Text>Thông tin sản phẩm: </Text>
          <View
            style={{
              flexDirection: 'row',
              justifyContent: 'space-between',
              alignItems: 'center',
            }}>
            <View>
              <Text>Giá sản phẩm: </Text>
              <Text>Hình ảnh</Text>
              <Text>Nhà sản xuất</Text>
              <Text>Loại hàng</Text>
              <Text>Giảm giá</Text>
            </View>
            <View>
              <Text></Text>
              <Text></Text>
              <Text></Text>
              <Text></Text>
            </View>
          </View>
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({});
