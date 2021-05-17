import React, {useState} from "react";
import "./App.css";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";

import Home from "./pages/home/Home";
import Schedule from "./pages/schedule/Schedule";
import UnitSelection from "./pages/unitSelection/unitSelection";
import Login from "./pages/login/Login";
import ForgetPassword from "./pages/forgetPassword/ForgetPassword";
import ChangePassword from "./pages/changePassword/ChangePassword";
import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer } from "react-toastify";
// import 'react-notifications/lib/notifications.css';
// import {NotificationContainer, NotificationManager} from 'react-notifications';


function App() {
  return (
    <Router>
      <Switch>
        <Route path="/login">
          <Login/>
        </Route>
        <Route exact path="/" component={Home} />

        <Route path="/courses">
          <UnitSelection />
        </Route>

        <Route path="/schedule">
          <Schedule />
        </Route>

        <Route path="/forget">
          <ForgetPassword />
        </Route>

        <Route path="/changePassword">
          <ChangePassword />
        </Route>

      </Switch>
      <ToastContainer/>
    </Router>
  );
}

export default App;
