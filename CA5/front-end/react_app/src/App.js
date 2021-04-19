import React from "react";
import "./App.css";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";

import Home from "./pages/home/Home";
import Login from "./pages/login/Login";


function App() {
  return (
    <Router>
      <Switch>
        <Route path="/login">
          <Login/>
        </Route>
        <Route exact path="/" component={Home} />
      </Switch>
    </Router>
  );
}

export default App;
