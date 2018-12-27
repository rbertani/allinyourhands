import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import axios from 'axios';
import { properties } from './properties.js';
import Books from './components/Books';
import { loadProgressBar } from 'axios-progress-bar'
import 'axios-progress-bar/dist/nprogress.css'
import { Layout } from 'antd';
const {
  Header, Footer, Sider, Content,
} = Layout;


class App extends Component {

  constructor() {
    super();

    this.state = {
      homeSelected: "",
      songsSelected: "",
      booksSelected: "",
      videosSelected: "",
      placesSelected: "",
      weatherSelected: "",
      statusApis: {}

    };
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

      <div>

        <Layout>
          <Sider>Sider</Sider>
          <Layout>
            <Header>Header</Header>
            <Content>Content</Content>
            <Footer>Footer</Footer>
          </Layout>
        </Layout>

      </div>

    );
  }
}

export default App;
