import React from "react";
import PropTypes from "prop-types";
import { withStyles } from "@material-ui/core/styles";
import Drawer from "@material-ui/core/Drawer";
import Button from "@material-ui/core/Button";
import List from "@material-ui/core/List";
import Divider from "@material-ui/core/Divider";
import ListItem from "@material-ui/core/ListItem";
import ListItemIcon from "@material-ui/core/ListItemIcon";
import ListItemText from "@material-ui/core/ListItemText";
import IconButton from "@material-ui/core/IconButton";
import CloseIcon from "@material-ui/icons/Close";
import Grid from "@material-ui/core/Grid";
import Typography from "@material-ui/core/Typography";
import InputLabel from "@material-ui/core/InputLabel";
import FormControl from "@material-ui/core/FormControl";
import Select from "@material-ui/core/Select";
import PollActions from "../../actions/poll/PollActions";

const initialState = {
  loading: false,
  pollStatusOptions: [],
  pollTypeOptions: [],
  pollStatusId: 0,
  pollTypeId: 0
};

class PollFilters extends React.Component {
  state = { ...initialState };

  handleChange = event => {
    this.setState({
      [event.currentTarget.name]: event.currentTarget.value
    });
  };

  async componentDidMount() {
    this.setState({ loading: true });
    const { data: pollTypes } = await PollActions.doGetPollTypes();
    if (pollTypes) {
      const { data: pollStatuses } = await PollActions.doGetAllPollStatus();
      if (pollStatuses) {
        this.setState({
          loading: false,
          pollTypeOptions: pollTypes.content,
          pollStatusOptions: pollStatuses
        });
      }
    }
  }

  componentWillUnmount() {
    this.setState({ ...initialState });
  }

  handleClickFilter = () => {
    const { pollStatusId, pollTypeId } = this.state;
    this.props.handleFilterPolls(pollStatusId, pollTypeId);
  };

  render() {
    const { classes, open } = this.props;
    const pollStatusOptions = this.state.pollStatusOptions.map(pollStatus => {
      return (
        <option key={pollStatus.statusId} value={pollStatus.statusId}>
          {pollStatus.name}
        </option>
      );
    });
    const pollTypeOptions = this.state.pollTypeOptions.map(pollType => {
      return (
        <option key={pollType.pollTypeId} value={pollType.pollTypeId}>
          {pollType.name}
        </option>
      );
    });
    return (
      <Drawer
        anchor="right"
        open={open}
        onClose={this.props.toggleFilterDrawer(false)}
      >
        <Grid container direction="row" justify="flex-end">
          <Grid item>
            <IconButton onClick={this.props.toggleFilterDrawer(false)}>
              <CloseIcon />
            </IconButton>
          </Grid>
        </Grid>
        <Typography variant="h5" className={classes.filtersTitle} gutterBottom>
          Filters
        </Typography>
        <div className={classes.list}>
          <FormControl
            variant="outlined"
            className={classes.formControl}
            fullWidth
          >
            <InputLabel>Poll status</InputLabel>
            <Select
              native
              value={this.state.pollStatusId}
              onChange={this.handleChange}
              inputProps={{
                name: "pollStatusId",
                id: "pollStatusId"
              }}
            >
              <option value="" />
              {pollStatusOptions}
            </Select>
          </FormControl>
          <FormControl
            variant="outlined"
            className={classes.formControl}
            fullWidth
          >
            <InputLabel>Poll Type</InputLabel>
            <Select
              native
              value={this.state.pollTypeId}
              onChange={this.handleChange}
              inputProps={{
                name: "pollTypeId",
                id: "pollTypeId"
              }}
            >
              <option value="" />
              {pollTypeOptions}
            </Select>
          </FormControl>
          <Grid container justify="center">
            <Button
              variant="contained"
              color="primary"
              fullWidth
              className={classes.button}
              onClick={this.handleClickFilter}
            >
              Apply Filters
            </Button>
          </Grid>
        </div>
      </Drawer>
    );
  }
}

const styles = theme => ({
  list: {
    width: 250
  },
  button: {
    margin: theme.spacing.unit,
    marginTop: 50
  },
  formControl: {
    width: 220,
    marginTop: theme.spacing.unit * 2,
    marginLeft: theme.spacing.unit,
    marginRight: theme.spacing.unit
  },
  filtersTitle: {
    marginLeft: theme.spacing.unit * 2
  }
});

export default withStyles(styles)(PollFilters);
