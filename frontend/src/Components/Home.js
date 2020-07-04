import React, {Component} from "react";
import Button from "@material-ui/core/Button";
import "../css/Home.css";

export default class Home extends Component {

    addRedirectButton(label, redirectAddress) {
        return (<Button
            variant="outlined"
            color="primary"
            href={redirectAddress}
            className="RedirectButton"
        >
            {label}
        </Button>);
    }

    render() {
        return (
            <div className="Home">
                <div className="lander">
                    <h1>Tallink Test Assignment</h1>
                    <a href={"/"} id="homePageLink">Home Page</a>
                    <form>
                        {this.addRedirectButton("Add Conference", "/addConference")}
                        {this.addRedirectButton("View Conferences", "/viewConferences")}
                        {this.addRedirectButton("Add Participant", "/addParticipant")}
                        {this.addRedirectButton("View Participants", "/viewParticipants")}
                        {this.addRedirectButton("View Conference Rooms", "/viewConferenceRooms")}
                    </form>
                </div>
            </div>
        );
    }
}