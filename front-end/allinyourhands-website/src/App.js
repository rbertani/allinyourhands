import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import axios from 'axios';
import { properties } from './properties.js';
import Books from './components/Books';
import { loadProgressBar } from 'axios-progress-bar'
import 'axios-progress-bar/dist/nprogress.css'
import { Layout, Menu, Icon } from 'antd';

const { Header, Sider, Content } = Layout;

class App extends Component {

  constructor() {
    super();

    this.state = {
      collapsed: false,
      homeSelected: "",
      songsSelected: "",
      booksSelected: "",
      videosSelected: "",
      placesSelected: "",
      weatherSelected: "",
      statusApis: {},
      collapsed: false

    };
  }

  toggle = () => {
    this.setState({
      collapsed: !this.state.collapsed,
    });
  }
  componentDidMount() {

    loadProgressBar();

    /*
    axios.get(properties.apiBaseUrl + `/status`)
      .then(response => {

        if (response.data.statusAPIs.book == 1)
          this.setState({ booksSelected: "block" });
        else
          this.setState({ booksSelected: "none" });

        if (response.data.statusAPIs.audio == 1)
          this.setState({ songsSelected: "block" });
        else
          this.setState({ songsSelected: "none" });

        if (response.data.statusAPIs.weather == 1)
          this.setState({ weatherSelected: "block" });
        else
          this.setState({ weatherSelected: "none" });

        if (response.data.statusAPIs.directions == 1)
          this.setState({ placesSelected: "block" });
        else
          this.setState({ placesSelected: "none" });

        if (response.data.statusAPIs.video == 1)
          this.setState({ videosSelected: "block" });
        else
          this.setState({ videosSelected: "none" });

      });

      */
  }

  convertEnabledValue = (statusApiValue) => {

    //console.log("----> convertEnabledValue called!!");

    if (statusApiValue == "1") {
      return "block";
    }
    else return "none";
  }


  render() {
    return (

      <Layout>
        <Sider
          trigger={null}
          collapsible
          collapsed={this.state.collapsed}
        >
          <div className="logo" />
          <Menu theme="dark" mode="inline" defaultSelectedKeys={['1']}>
            <Menu.Item key="1">
              <Icon type="user" />
              <span>nav 1</span>
            </Menu.Item>
            <Menu.Item key="2">
              <Icon type="video-camera" />
              <span>nav 2</span>
            </Menu.Item>
            <Menu.Item key="3">
              <Icon type="upload" />
              <span>nav 3</span>
            </Menu.Item>
          </Menu>
        </Sider>
        <Layout>
          <Header style={{ background: '#fff', padding: 0 }}>
            <Icon
              className="trigger"
              type={this.state.collapsed ? 'menu-unfold' : 'menu-fold'}
              onClick={this.toggle}
            />
          </Header>
          <Content style={{
            margin: '24px 16px', padding: 24, background: '#fff', minHeight: 280,
          }}
          >
            Content
        </Content>
        </Layout>
      </Layout>

    );
  }
}

export default App;
