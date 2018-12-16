import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import ReactHtmlParser, { processNodes, convertNodeToElement, htmlparser2 } from 'react-html-parser'

class BookReader extends React.Component {

    constructor(props) {
        super(props);   
        
        this.state = {
          htmlBookContent : this.props.htmlBookContent.replace('<html><header></header><body>','').replace('</body></html>','')       
         }
  
    }

    render() {     
      return <div>{ ReactHtmlParser(this.state.htmlBookContent) }</div>;        
    }

    componentDidMount() {
      
    }

     
}

export default BookReader;