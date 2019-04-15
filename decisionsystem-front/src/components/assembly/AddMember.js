import React from "react";
import { withStyles } from "@material-ui/core";
import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogTitle from "@material-ui/core/DialogTitle";
import CustomizedSnackbar from "../common/CustomizedSnackbar";
import axios from "axios";

import { config } from "../../config";

const initialState = {
  open: false,
  fullScreen: false,
  snackbar: {
    open: false,
    variant: "success",
    message: ""
  }
};

class AddMember extends React.Component {
  state = { ...initialState };

  constructor(props) {
    super(props);
    this.snack = React.createRef();
  }

  async componentDidMount() {
    this.setState({ loading: true });
    // const { data: assemblies } = await this.getAssemblies();
    // if (assemblies) {
    //   // console.log(assemblies);
    //   this.setState({
    //     loading: false,
    //     assemblies: assemblies.content
    //   });
    // }
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

  handleAddMember = () => {
    // If everything is OK we show success message
    const snackbarAddedMember = {
      open: true,
      variant: "success",
      message: "User added successfully"
    };
    this.snack.openWith(snackbarAddedMember);
  };

  render() {
    const { classes } = this.props;
    return (
      <div>
        <Button
          className={classes.btnAddMember}
          variant="contained"
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
        <CustomizedSnackbar innerRef={ref => (this.snack = ref)} />
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
