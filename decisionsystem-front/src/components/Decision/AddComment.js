import React from "react";
import { withStyles } from "@material-ui/core";
import Grid from "@material-ui/core/Grid";
import TextField from "@material-ui/core/TextField";
import IconButton from "@material-ui/core/IconButton";
import SendIcon from "@material-ui/icons/Send";

const initialState = {
  content: ""
};

class AddComment extends React.Component {
  state = { ...initialState };

  async componentDidMount() {}

  componentWillUnmount() {
    this.setState({ ...initialState });
  }

  handleChange = name => event => {
    this.setState({
      [name]: event.target.value
    });
  };

  render() {
    const { classes } = this.props;

    return (
      <React.Fragment>
        <Grid
          container
          spacing={8}
          direction="row"
          justify="space-between"
          alignItems="center"
        >
          <Grid item xs={10}>
            <TextField
              fullWidth
              id="outlined-multiline-flexible"
              label="Add a comment"
              multiline
              rowsMax="10"
              value={this.state.content}
              onChange={this.handleChange("content")}
              margin="normal"
              variant="outlined"
            />
          </Grid>
          <Grid item>
            <IconButton
              color="primary"
              className={classes.button}
              aria-label="Comment"
            >
              <SendIcon />
            </IconButton>
          </Grid>
        </Grid>
      </React.Fragment>
    );
  }
}

const styles = theme => ({});

export default withStyles(styles)(AddComment);
