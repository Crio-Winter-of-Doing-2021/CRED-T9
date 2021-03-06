import { Component } from "react";
import { connect } from "react-redux";
import { RootState } from '../reducers';
import { withRouter } from "react-router";
import { USER_TOKEN } from '../constants/store-constants'
import "../styles/bootstrap.min.css";

interface Prop {
  history: any
}
class App extends Component<Prop> {
  render() {
    if(!localStorage.getItem(USER_TOKEN)) {
      this.props.history.replace('/login')
    }
    return(
      <div>Dashboard</div>
    )
  }
}
const mapStateToProps = (state: RootState) => {
  return { state };
};
export default withRouter(connect(mapStateToProps)(App));