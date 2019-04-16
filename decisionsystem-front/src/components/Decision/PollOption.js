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
    const { checked } = this.state;
    const currentIndex = checked.indexOf(value);
    const newChecked = [...checked];

    if (currentIndex === -1) {
      newChecked.push(value);
    } else {
      newChecked.splice(currentIndex, 1);
    }

    this.setState({
      checked: newChecked
    });
  };

  async componentDidMount() {}

  componentWillUnmount() {}

  render() {
    const { classes, poll, pollOption } = this.props;
    return (
      <React.Fragment>
        <ListItem
          role={undefined}
          dense
          button
          onClick={this.handleToggle(pollOption.pollOptionId)}
        >
          <Checkbox
            checked={this.state.checked.indexOf(pollOption.pollOptionId) !== -1}
            tabIndex={-1}
            disableRipple
          />
          <ListItemText primary={`${pollOption.name}`} />
          <ListItemText secondary={`${pollOption.description}`} />
          <ListItemSecondaryAction>
            <IconButton aria-label="Comments">
              <CommentIcon />
            </IconButton>
          </ListItemSecondaryAction>
        </ListItem>
      </React.Fragment>
    );
  }
}

const styles = theme => ({});

export default withStyles(styles)(PollOption);
