import React, { Component } from 'react';

import { View, StyleSheet, Text, Alert, Image } from 'react-native';

import { LoginButton, AccessToken, GraphRequest, GraphRequestManager } from 'react-native-fbsdk';

export default class App extends Component {

  constructor() {
    super();
    this.state = {

      user_name: '',
      avatar_url: '',
      avatar_show: false
    }
  }

  get_Response_Info = (error, result) => {
    if (error) {
      Alert.alert('Error fetching data: ' + error.toString());
    } else {
      this.setState({ user_name: 'Welcome' + ' ' + result.name });

      this.setState({ avatar_url: result.picture.data.url });

      this.setState({ avatar_show: true })

      console.log(result);

    }
  }

  onLogout = () => {
    this.setState({ user_name: null, avatar_url: null, avatar_show: false });
  }
  
  render() {
    return (
      <View style={styles.container}>
        {this.state.avatar_url ?
          <Image
            source={{ uri: this.state.avatar_url }}
            style={styles.imageStyle} /> : null}

        <Text style={styles.text}> {this.state.user_name} </Text>

        <LoginButton
          readPermissions={['public_profile']}
          onLoginFinished={(error, result) => {
            if (error) {
              console.log(error.message);
              console.log('login has error: ' + result.error);
            } else if (result.isCancelled) {
              console.log('login is cancelled.');
            } else {
              AccessToken.getCurrentAccessToken().then(data => {
                console.log(data.accessToken.toString());

                const processRequest = new GraphRequest(
                  '/me?fields=name,picture.type(large)',
                  null,
                  this.get_Response_Info
                );
                // Start the graph request.
                new GraphRequestManager().addRequest(processRequest).start();

              });
            }
          }}
          onLogoutFinished={this.onLogout}
        />
      </View>
    );
  }
}
const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
  },

  text: {
    fontSize: 20,
    color: '#000',
    textAlign: 'center',
    padding: 20
  },

  imageStyle: {

    width: 200,
    height: 300,
    resizeMode: 'contain'
  }
});
