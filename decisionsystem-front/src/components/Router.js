import React from "react";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import App from "../App";
import SignIn from "./signin/SignIn";
import NotFound from "./NotFound";
import CssBaseline from "@material-ui/core/CssBaseline";

const Router = () => (
  <React.Fragment>
    <CssBaseline />
    <BrowserRouter>
      <Switch>
        <Route exact path="/" component={SignIn} />
        <Route exact path="/my" component={App} />
        <Route component={NotFound} />
      </Switch>
    </BrowserRouter>
  </React.Fragment>
);

export default Router;
