import React from "react";
import PropTypes from "prop-types";
import Avatar from "@material-ui/core/Avatar";
import Button from "@material-ui/core/Button";
import CssBaseline from "@material-ui/core/CssBaseline";
import FormControl from "@material-ui/core/FormControl";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Checkbox from "@material-ui/core/Checkbox";
import Input from "@material-ui/core/Input";
import InputLabel from "@material-ui/core/InputLabel";
import TaskIcon from "@material-ui/icons/AssignmentTurnedIn";
import Paper from "@material-ui/core/Paper";
import Typography from "@material-ui/core/Typography";
import withStyles from "@material-ui/core/styles/withStyles";

import { config } from "../../config";

class SignIn extends React.Component {
  state = {
    email: "",
    password: "",
    error: false
  };

  doSendLogin = async (userOrEmail, passwd) => {
    const signInRequest = {
      nicknameOrEmail: userOrEmail,
      password: passwd
    };
    console.log(signInRequest);
    return await fetch(config.baseUrl + "api/user/auth/signin", {
      method: "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json"
      },
      body: JSON.stringify(signInRequest)
    })
      .then(response => {
        if (!response.ok) {
          console.log("Error: " + this.state.error);
          return { success: false };
        }
        return response.json();
      })
      .then(data => {
        return {
          success: true,
          data
        };
      })
      .catch(function(error) {
        console.log(error);
      });
  };

  handleChange = event => {
    this.setState({
      [event.currentTarget.name]: event.currentTarget.value
    });
  };

  handleSubmit = async event => {
    event.preventDefault();

    // Send login user request to backend
    const fetchResult = await this.doSendLogin(
      this.state.email,
      this.state.password
    );
    console.log(fetchResult);

    this.setState({ error: !fetchResult.success });
    if (fetchResult.success) {
      // Save session details
      console.log("accessToken: " + fetchResult.data.accessToken);
      console.log("tokenType: " + fetchResult.data.tokenType);
      // TODO bear in mind "remember me option"

      this.props.history.push("/my");
    }

    event.currentTarget.reset();
  };

  render() {
    const { classes } = this.props;
    return (
      <React.Fragment>
        <CssBaseline />
        <main className={classes.layout}>
          <Paper className={classes.paper}>
            <Avatar className={classes.avatar}>
              <TaskIcon />
            </Avatar>
            <Typography component="h1" variant="h5">
              Sign in
            </Typography>
            <form className={classes.form} onSubmit={this.handleSubmit}>
              <FormControl margin="normal" required fullWidth>
                <InputLabel htmlFor="email">User or email</InputLabel>
                <Input
                  id="email"
                  name="email"
                  value={this.state.email}
                  onChange={this.handleChange}
                  autoComplete="email"
                  autoFocus
                />
              </FormControl>
              <FormControl margin="normal" required fullWidth>
                <InputLabel htmlFor="password">Password</InputLabel>
                <Input
                  name="password"
                  type="password"
                  id="password"
                  value={this.state.password}
                  onChange={this.handleChange}
                  autoComplete="current-password"
                />
              </FormControl>
              <FormControlLabel
                control={<Checkbox value="remember" color="primary" />}
                label="Remember me"
              />
              <Button
                type="submit"
                fullWidth
                variant="contained"
                color="primary"
                className={classes.submit}
              >
                Sign in
              </Button>
            </form>
            {this.state.error && (
              <Typography component="h1" variant="h2" className="error">
                User or password incorrect
              </Typography>
            )}
            <Typography
              component="h1"
              variant="body1"
              className={classes.body1}
            >
              Not registered yet?
            </Typography>
            <Button
              variant="contained"
              color="primary"
              className={classes.signup}
            >
              Sign Up
            </Button>
          </Paper>
        </main>
      </React.Fragment>
    );
  }
}

SignIn.propTypes = {
  classes: PropTypes.object.isRequired
};

const styles = theme => ({
  layout: {
    width: "auto",
    display: "block", // Fix IE 11 issue.
    marginLeft: theme.spacing.unit * 3,
    marginRight: theme.spacing.unit * 3,
    [theme.breakpoints.up(400 + theme.spacing.unit * 3 * 2)]: {
      width: 400,
      marginLeft: "auto",
      marginRight: "auto"
    }
  },
  paper: {
    marginTop: theme.spacing.unit * 8,
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
    padding: `${theme.spacing.unit * 2}px ${theme.spacing.unit * 3}px ${theme
      .spacing.unit * 3}px`
  },
  avatar: {
    margin: theme.spacing.unit,
    backgroundColor: theme.palette.secondary.main
  },
  form: {
    width: "100%", // Fix IE 11 issue.
    marginTop: theme.spacing.unit
  },
  submit: {
    marginTop: theme.spacing.unit * 3
  },
  body1: {
    marginTop: theme.spacing.unit * 2
  },
  signup: {
    marginTop: theme.spacing.unit * 2
  }
});

export default withStyles(styles)(SignIn);
