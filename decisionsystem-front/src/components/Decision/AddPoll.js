import React from "react";
import { withStyles } from "@material-ui/core";
import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogTitle from "@material-ui/core/DialogTitle";
import InputLabel from "@material-ui/core/InputLabel";
import FormControl from "@material-ui/core/FormControl";
import Select from "@material-ui/core/Select";
import axios from "axios";

import { config } from "../../config";

const initialState = {
  open: false,
  fullScreen: false,
  assemblyOptions: [],
  title: "",
  description: "",
  pollType: "",
  assemblyId: 0
};

class AddPoll extends React.Component {
  state = { ...initialState };

  getStartDefaultValue = () => {
    //2017-05-24T10:30
  };

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

  getUserAssemblies = async () => {
    const url = config.baseUrl + "api/assembly/mine";
    return axios.get(url, this.getAuth());
  };

  async componentDidMount() {
    this.setState({ loading: true });
    const { data: assemblies } = await this.getUserAssemblies();
    if (assemblies) {
      this.setState({
        loading: false,
        assemblyOptions: assemblies.content
      });
    }
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
      [event.currentTarget.name]: event.currentTarget.value
    });
  };

  handleCreatePoll = async () => {
    // let successful = false;
    // const { data: userFound } = await this.getUser(this.state.usernameOrEmail);
    // console.log("find user result");
    // console.log(userFound);
    // if (userFound) {
    //   // Add user
    //   const { data: addUserResponse } = await this.addUser(
    //     this.props.assembly,
    //     userFound
    //   );
    //   if (addUserResponse) {
    //     successful = true;
    //   }
    // }
    // this.props.addMember(successful, userFound);
    this.handleClose();
  };

  render() {
    const { classes } = this.props;
    const assemblyOptions = this.state.assemblyOptions.map(assembly => {
      return (
        <option key={assembly.assemblyId} value={assembly.assemblyId}>
          {assembly.name}
        </option>
      );
    });
    return (
      <div>
        <Dialog
          fullScreen={this.state.fullScreen}
          open={this.state.open}
          onClose={this.handleClose}
          aria-labelledby="form-dialog-title"
        >
          <DialogTitle id="form-dialog-title">New Poll</DialogTitle>
          <DialogContent>
            <DialogContentText>
              Please enter new poll information.
            </DialogContentText>
            <TextField
              autoFocus
              margin="dense"
              id="title"
              name="title"
              label="Poll title"
              type="text"
              value={this.state.title}
              onChange={this.handleChange}
              fullWidth
            />
            <TextField
              autoFocus
              margin="dense"
              id="description"
              name="description"
              label="Description"
              type="text"
              value={this.state.description}
              onChange={this.handleChange}
              fullWidth
            />
            <TextField
              id="startTime"
              name="startTime"
              label="Starts at"
              type="datetime-local"
              className={classes.dateTimePicker}
              //   defaultValue={new Date()}
              InputLabelProps={{
                shrink: true
              }}
            />
            <TextField
              id="endTime"
              name="endTime"
              label="Closes at"
              type="datetime-local"
              className={classes.dateTimePicker}
              InputLabelProps={{
                shrink: true
              }}
            />
            <FormControl
              variant="outlined"
              className={classes.formControl}
              fullWidth
            >
              <InputLabel>Poll type</InputLabel>
              <Select
                native
                value={this.state.pollType}
                onChange={this.handleChange}
                inputProps={{
                  name: "pollType",
                  id: "pollType"
                }}
              >
                <option value="" />
                <option value={"simple"}>Single option</option>
              </Select>
            </FormControl>
            <FormControl
              variant="outlined"
              className={classes.formControl}
              fullWidth
            >
              <InputLabel>Assembly</InputLabel>
              <Select
                native
                value={this.state.assemblyId}
                onChange={this.handleChange}
                inputProps={{
                  name: "assemblyId",
                  id: "assemblyId"
                }}
              >
                <option value={0} />
                {assemblyOptions}
              </Select>
            </FormControl>
          </DialogContent>
          <DialogActions>
            <Button onClick={this.handleClose} color="primary">
              Cancel
            </Button>
            <Button onClick={this.handleCreatePoll} color="primary">
              Create
            </Button>
          </DialogActions>
        </Dialog>
      </div>
    );
  }
}

const styles = theme => ({
  dateTimePicker: {
    marginTop: theme.spacing.unit,
    marginRight: theme.spacing.unit * 2,
    width: 200
  },
  formControl: {
    marginTop: theme.spacing.unit * 2,
    minWidth: 200
  }
});

export default withStyles(styles)(AddPoll);
