import React from "react";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import SignIn from "./signin/SignIn";
import NotFound from "./NotFound";
import CssBaseline from "@material-ui/core/CssBaseline";
import Register from "./register/Register";
import Dashboard from "./dashboard/Dashboard";

const Router = () => (
  <React.Fragment>
    <CssBaseline />
    <BrowserRouter>
      <Switch>
        <Route exact path="/" component={SignIn} />
        <Route exact path="/signin" component={SignIn} />
        <Route exact path="/register" component={Register} />
        <Route
          exact
          path="/(decisions|assemblies|settings|notifications)"
          component={Dashboard}
        />
        <Route component={NotFound} />
      </Switch>
    </BrowserRouter>
  </React.Fragment>
);

export default Router;
