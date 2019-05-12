import React from "react";
import { withStyles } from "@material-ui/core";
import ListItem from "@material-ui/core/ListItem";
import ListItemSecondaryAction from "@material-ui/core/ListItemSecondaryAction";
import ListItemText from "@material-ui/core/ListItemText";
import Checkbox from "@material-ui/core/Checkbox";
import IconButton from "@material-ui/core/IconButton";
import CommentIcon from "@material-ui/icons/Comment";
import axios from "axios";

import { config } from "../../config";

const initialState = {
  checked: []
};

class PollOption extends React.Component {
  state = { ...initialState };

  handleToggle = value => () => {
    this.props.handleSelectOption(value);
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

  render() {
    const { classes, pollOption, pollSystem, checked } = this.props;
    return this.renderBySystem(classes, pollSystem, pollOption, checked);
  }
}

const styles = theme => ({});

export default withStyles(styles)(PollOption);
