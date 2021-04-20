import React from "react";
import "./App.css";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";

import Home from "./pages/home/Home";
import UnitSelection from "./pages/unitSelection/UnitSelection";

function App() {
  return (
    <Router>
      <Switch>
        <Route exact path="/" component={Home} />

        <Route path="/courses">
          <UnitSelection />
        </Route>

      </Switch>
    </Router>
  );
}

export default App;
