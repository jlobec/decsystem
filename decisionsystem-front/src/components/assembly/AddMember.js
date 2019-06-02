import React from "react";
import { withStyles } from "@material-ui/core";
import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogTitle from "@material-ui/core/DialogTitle";
import axios from "axios";

import { config } from "../../config";

const initialState = {
  open: false,
  fullScreen: false,
  usernameOrEmail: ""
};

class AddMember extends React.Component {
  state = { ...initialState };

  getAuth = () => {
    const token = sessionStorage.getItem("jwtToken");
    const auth = {
      headers: { Authorization: "Bearer " + token }
    };
    return auth;
  };

  getUser = async usernameOrEmail => {
    const url = config.baseUrl + "api/user?usernameOrEmail=" + usernameOrEmail;
    return axios.get(url, this.getAuth());
  };

  addUser = async (assembly, user) => {
    const url =
      config.baseUrl + "api/assembly/" + assembly.assemblyId + "/adduser";
    const body = { userId: user.userId };
    return axios.post(url, body, this.getAuth());
  };

  async componentDidMount() {
    this.setState({ loading: true });
  }

  componentWillUnmount() {
    this.setState({ ...initialState });
  }

  handleClickOpen = () => {
    this.setState({ open: true });
  };

  handleClose = () => {
    this.setState({ open: false });
  };

  handleChange = event => {
    this.setState({
      usernameOrEmail: event.currentTarget.value
    });
  };

  handleAddMember = async () => {
    let successful = false;
    const { data: userFound } = await this.getUser(this.state.usernameOrEmail);
    console.log("find user result");
    console.log(userFound);
    if (userFound) {
      // Add user
      const { data: addUserResponse } = await this.addUser(
        this.props.assembly,
        userFound
      );
      if (addUserResponse) {
        successful = true;
      }
    }
    this.props.addMember(successful, userFound);
    this.handleClose();
  };

  render() {
    const { classes } = this.props;
    return (
      <div>
        <Button
          className={classes.btnAddMember}
          variant="outlined"
          color="secondary"
          onClick={this.handleClickOpen}
        >
          Add member
        </Button>
        <Dialog
          fullScreen={this.state.fullScreen}
          open={this.state.open}
          onClose={this.handleClose}
          aria-labelledby="form-dialog-title"
        >
          <DialogTitle id="form-dialog-title">Add Member</DialogTitle>
          <DialogContent>
            <DialogContentText>
              Please enter new member's email or username here.
            </DialogContentText>
            <TextField
              autoFocus
              margin="dense"
              id="name"
              label="Email or username"
              type="email"
              value={this.state.usernameOrEmail}
              onChange={this.handleChange}
              fullWidth
            />
          </DialogContent>
          <DialogActions>
            <Button onClick={this.handleClose} color="primary">
              Cancel
            </Button>
            <Button onClick={this.handleAddMember} color="primary">
              Add member
            </Button>
          </DialogActions>
        </Dialog>
      </div>
    );
  }
}

const styles = theme => ({
  btnAddMember: {
    marginLeft: theme.spacing.unit * 2
  }
});

export default withStyles(styles)(AddMember);
