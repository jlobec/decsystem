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

class PollFilters extends React.Component {
  state = {
    pollStatus: ""
  };

  handleChange = event => {
    this.setState({
      [event.currentTarget.name]: event.currentTarget.value
    });
  };

  render() {
    const { classes, open } = this.props;
    const pollStatusOptions = ["All", "Open", "Closed"].map(
      (pollStatus, index) => {
        return (
          <option key={index} value={pollStatus}>
            {pollStatus}
          </option>
        );
      }
    );
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
              value={this.state.pollStatus}
              onChange={this.handleChange}
              inputProps={{
                name: "pollStatus",
                id: "pollStatus"
              }}
            >
              <option value="" />
              {pollStatusOptions}
            </Select>
          </FormControl>
          <Grid container justify="center">
            <Button
              variant="contained"
              color="primary"
              fullWidth
              className={classes.button}
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
