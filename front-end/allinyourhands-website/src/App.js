import React, { Component, Suspense } from 'react';
import { properties } from './properties.js';
import axios from 'axios';
import { loadProgressBar } from 'axios-progress-bar'
import 'axios-progress-bar/dist/nprogress.css'
import Home from './components/Home';


//injectTapEventPlugin();

class App extends Component {

   constructor() {
    super();

    this.state = {
      collapsed: false,      
      songsActive: "",
      booksActive: "",
      videosActive: "",
      placesActive: "",
      weatherActive: "",
      statusApis: {}
    };
  }

  handleClick = (e) => {
    console.log('click ', e);
    this.setState({
      current: e.key,
    });
  }

  componentDidMount () {

    loadProgressBar();
    
    axios.get(properties.apiBaseUrl + `/status`)
      .then(response => {

        if (response.data.statusAPIs.book !== 1)
          this.setState({ booksActive: "none" });
      
        if (response.data.statusAPIs.audio !== 1)
          this.setState({ songsActive: "none" });        
 
        if (response.data.statusAPIs.weather !== 1)
          this.setState({ weatherActive: "none" });
       
        if (response.data.statusAPIs.directions !== 1)
          this.setState({ placesActive: "none" });
        
        if (response.data.statusAPIs.video !== 1)
          this.setState({ videosActive: "none" });
       

      });
 
  }

  convertEnabledValue = (statusApiValue) => {

    //console.log("----> convertEnabledValue called!!");

    if (statusApiValue === "1") {
      return "block";
    }
    else return "none";
  }


  render() {

    return (   

      <Home 
         booksActive={this.state.booksActive}
         videosActive={this.state.videosActive}
         songsActive={this.state.songsActive}
         weatherActive={this.state.weatherActive}
         placesActive={this.state.placesActive}
         
         />

    );
  }
}

export default App;
