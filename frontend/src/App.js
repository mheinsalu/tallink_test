import React, {Component} from "react";
import {BrowserRouter as Router, Route} from "react-router-dom";
import Home from "./Components/Home";
import AddConference from "./Components/AddConference";
import ViewConferences from "./Components/ViewConferences";
import AddParticipant from "./Components/AddParticipant";
import ViewParticipants from "./Components/ViewParticipants";
import ViewConferenceRooms from "./Components/ViewConferenceRooms";
import Footer from "./Components/Footer";
import ConferenceDetails from "./Components/ConferenceDetails";

class App extends Component {

    render() {
        return (
            <div>
                <Home/>

                <Router>
                    <div>
                        <Route exact path="/addConference" component={AddConference}/>
                        <Route exact path="/viewConferences" component={ViewConferences}/>
                        <Route exact path="/addParticipant" component={AddParticipant}/>
                        <Route exact path="/viewParticipants" component={ViewParticipants}/>
                        <Route exact path="/viewConferenceRooms" component={ViewConferenceRooms}/>
                        <Route exact path="/viewConferences/:id" component={ConferenceDetails}/>
                    </div>
                </Router>

                <Footer>
                    Author: Märten Heinsalu. Date: 07.2020
                </Footer>
            </div>
        );
    }
}

export default App;
