import React from "react";
import { withStyles, Typography } from "@material-ui/core";
import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";
import Checkbox from "@material-ui/core/Checkbox";
import Slider, { defaultValueReducer } from "@material-ui/lab/Slider";
import Grid from "@material-ui/core/Grid";
import Tooltip from "@material-ui/core/Tooltip";
import Badge from "@material-ui/core/Badge";
import Chip from "@material-ui/core/Chip";

const initialState = {
  checked: [],
  value: 1
};

class PollOption extends React.Component {
  state = { ...initialState };

  handleToggle = value => () => {
    this.props.handleSelectOption(value);
  };

  handleScoreChange = (event, value) => {
    this.setState({ value });
  };

  async componentDidMount() {}

  componentWillUnmount() {}

  renderBySystem(classes, pollSystem, pollOption, checked) {
    const type = pollSystem.name;
    if ("Single Option" === type) {
      return this.renderChoiceSystem(classes, pollOption, checked);
    }
    if ("Multiple Option" === type) {
      return this.renderChoiceSystem(classes, pollOption, checked);
    }
    if ("Score vote" === type) {
      return this.renderScoreSystem(classes, pollOption, checked);
    }
  }

  renderChoiceSystem(classes, pollOption, checked) {
    return (
      <React.Fragment>
        <ListItem
          role={undefined}
          dense
          button
          onClick={this.handleToggle(pollOption.pollOptionId)}
        >
          <Checkbox checked={checked} tabIndex={-1} disableRipple />
          <ListItemText primary={`${pollOption.name} `} />
          <ListItemText secondary={`${pollOption.description}`} />
        </ListItem>
      </React.Fragment>
    );
  }

  renderScoreSystem(classes, pollOption, checked) {
    return (
      <React.Fragment>
        <ListItem>
          <Grid container direction="column" spacing={8}>
            <Grid item xs>
              <ListItemText primary={`${pollOption.name} `} />
              <ListItemText secondary={`${pollOption.description}`} />
            </Grid>
            <Grid item xs>
              <Slider
                classes={{ container: classes.slider }}
                value={this.state.value}
                min={1}
                max={5}
                step={1}
                onChange={this.handleScoreChange}
                thumb={
                  // <Badge badgeContent={this.state.value} color="secondary" />
                  <Chip
                    className={classes.chip}
                    label={`${this.state.value}`}
                    color="secondary"
                  />
                }
              />
            </Grid>
          </Grid>
        </ListItem>
      </React.Fragment>
    );
  }

  render() {
    const { classes, pollOption, pollSystem, checked } = this.props;
    return this.renderBySystem(classes, pollSystem, pollOption, checked);
  }
}

const styles = theme => ({
  slider: {
    padding: "22px 0px"
  },
  chip: {
    padding: "10px"
  }
});

export default withStyles(styles)(PollOption);
