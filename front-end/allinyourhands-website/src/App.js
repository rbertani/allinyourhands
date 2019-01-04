import React, { Component, Suspense } from 'react';
import { Redirect, Route, Switch } from 'react-router-dom';
import { properties } from './properties.js';
import axios from 'axios';
import { loadProgressBar } from 'axios-progress-bar'
import 'axios-progress-bar/dist/nprogress.css'
import injectTapEventPlugin from 'react-tap-event-plugin';


import MainLayout from './components/MainLayout';
import Books from './components/Books';

//injectTapEventPlugin();

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
      statusApis: {}

    };
  }

  handleClick = (e) => {
    console.log('click ', e);
    this.setState({
      current: e.key,
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

   
      <MainLayout></MainLayout>
    

    );
  }
}

export default App;
