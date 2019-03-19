import React from "react";
import { Link as RouterLink } from "react-router-dom";
import Link from "@material-ui/core/Link";
import Avatar from "@material-ui/core/Avatar";
import Button from "@material-ui/core/Button";
import CssBaseline from "@material-ui/core/CssBaseline";
import FormControl from "@material-ui/core/FormControl";
import Input from "@material-ui/core/Input";
import InputLabel from "@material-ui/core/InputLabel";
import Paper from "@material-ui/core/Paper";
import Grid from "@material-ui/core/Grid";
import Typography from "@material-ui/core/Typography";
import TaskIcon from "@material-ui/icons/AssignmentTurnedIn";
import withStyles from "@material-ui/core/styles/withStyles";
import axios from "axios";

import { config } from "../../config";

class Register extends React.Component {
  state = {
    name: "",
    lastName: "",
    nick: "",
    email: "",
    password: "",
    error: false
  };

  clearForm = () => {
    this.setState({
      name: "",
      lastName: "",
      nick: "",
      email: "",
      password: "",
      error: false
    });
  };

  handleChange = event => {
    this.setState({
      [event.currentTarget.name]: event.currentTarget.value
    });
  };

  handleSubmit = async event => {
    event.preventDefault();

    // Send signup user request to backend
    const url = config.baseUrl + "api/user/auth/signup";
    const body = {
      name: this.state.name,
      lastName: this.state.lastName,
      nickname: this.state.nick,
      email: this.state.email,
      password: this.state.password
    };
    try {
      const { data: registerResult } = await axios.post(url, body);
      console.log(registerResult);
      this.setState({ error: false });

      // If everything is OK, then sign in user
      // to get access token
      if (registerResult.success) {
        // Send login user request to backend
        const url = config.baseUrl + "api/user/auth/signin";
        const body = {
          nicknameOrEmail: this.state.email,
          password: this.state.password
        };
        try {
          const { data: signInResult } = await axios.post(url, body);
          console.log(signInResult);
          this.setState({ error: false });
          this.clearForm();
          sessionStorage.setItem("jwtToken", signInResult.accessToken);
          this.props.history.push("/my");
        } catch (error) {
          this.clearForm();
          sessionStorage.removeItem("jwtToken");
          this.setState({ error: true });
        }
      }
    } catch (error) {
      this.clearForm();
      this.setState({ error: true });
    }
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
              Register
            </Typography>
            <form className={classes.form} onSubmit={this.handleSubmit}>
              <FormControl margin="normal" required fullWidth>
                <InputLabel>Name</InputLabel>
                <Input
                  id="name"
                  name="name"
                  value={this.state.name}
                  onChange={this.handleChange}
                  autoComplete="name"
                  autoFocus
                />
              </FormControl>
              <FormControl margin="normal" required fullWidth>
                <InputLabel>Last Name</InputLabel>
                <Input
                  id="lastName"
                  name="lastName"
                  value={this.state.lastName}
                  onChange={this.handleChange}
                  autoComplete="lastName"
                  autoFocus
                />
              </FormControl>
              <FormControl margin="normal" required fullWidth>
                <InputLabel>User Name</InputLabel>
                <Input
                  id="nick"
                  name="nick"
                  value={this.state.nick}
                  onChange={this.handleChange}
                  autoComplete="nick"
                  autoFocus
                />
              </FormControl>
              <FormControl margin="normal" required fullWidth>
                <InputLabel htmlFor="email">Email</InputLabel>
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

              <Button
                className={classes.btn_submit}
                fullWidth
                type="submit"
                variant="contained"
                color="primary"
              >
                Register
              </Button>
              <Link component={RouterLink} to="/">
                <Button
                  className={classes.btn_cancel}
                  fullWidth
                  variant="contained"
                  color="default"
                >
                  Cancel
                </Button>
              </Link>
            </form>
          </Paper>
        </main>
      </React.Fragment>
    );
  }
}

const styles = theme => ({
  action_container: {
    marginTop: theme.spacing.unit * 2 * 3
  },
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
  btn_submit: {
    marginTop: theme.spacing.unit * 3 * 3
  },
  btn_cancel: {
    marginTop: theme.spacing.unit * 2,
    marginBottom: theme.spacing.unit
  }
});

export default withStyles(styles)(Register);
