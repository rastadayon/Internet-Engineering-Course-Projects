import React, {useState} from "react";
import "./App.css";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";

import Home from "./pages/home/Home";
import Schedule from "./pages/schedule/Schedule";
import UnitSelection from "./pages/unitSelection/unitSelection";
import Login from "./pages/login/Login";
import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer } from "react-toastify";
import SignUp from "./pages/signup/Signup";
// import 'react-notifications/lib/notifications.css';
// import {NotificationContainer, NotificationManager} from 'react-notifications';


function App() {
  return (
    <Router>
      <Switch>
        <Route path="/login">
          <Login/>
        </Route>
        <Route path="/signup">
          <SignUp/>
        </Route>
        <Route exact path="/" component={Home} />

        <Route path="/courses">
          <UnitSelection />
        </Route>

        <Route path="/schedule">
          <Schedule />
        </Route>

      </Switch>
      <ToastContainer/>
    </Router>
  );
}

export default App;
