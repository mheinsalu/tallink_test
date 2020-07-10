import React, {useEffect} from "react";
import Button from "@material-ui/core/Button";
import Typography from "@material-ui/core/Typography";
import {makeStyles} from "@material-ui/core/styles";
import TableContainer from "@material-ui/core/TableContainer";
import Paper from "@material-ui/core/Paper";
import Table from "@material-ui/core/Table";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableBody from "@material-ui/core/TableBody";

const useStyles = makeStyles(theme => ({
    paper: {
        marginTop: theme.spacing(7),
        display: "flex",
        flexDirection: "column",
        alignItems: "center"
    },
    avatar: {
        margin: theme.spacing(1),
        backgroundColor: theme.palette.secondary.main
    },
    form: {
        width: "100%", // Fix IE 11 issue.
        marginTop: theme.spacing(3)
    },
    submit: {
        margin: theme.spacing(3, 0, 2)
    },
    textField: {
        marginLeft: theme.spacing(1),
        marginRight: theme.spacing(1),
        width: "100%"
    }
}));

export default function ConferenceDetails({match}) {
    const classes = useStyles();

    const [conference, setConference] = React.useState("");
    const [conferenceParticipants, setConferenceParticipants] = React.useState([]);
    const [message, setMessage] = React.useState("Nothing done in this session");
    const [conferenceDetailsTableRow, setConferenceDetailsTableRow] = React.useState("");

    useEffect(() => {
        fetchConference();
    }, []);

    const fetchConference = async () => {
        const data = await fetch(match.url);
        const conference = await data.json();
        setConference(conference);
        setConferenceParticipants(conference.participants);
        setConferenceDetailsTableRow(createConferenceDetailsRow(conference));
    }

    const createConferenceDetailsRow = (conference) => {
        return (
            <TableRow style={{backgroundColor: "#F8F8F8"}}>
                <TableCell align="center">{conference.id}</TableCell>
                <TableCell align="center">{conference.name}</TableCell>
                <TableCell align="center">{conference.startDateTime}</TableCell>
                <TableCell align="center">{conference.endDateTime}</TableCell>
                <TableCell align="center">{conference.conferenceRoomId}</TableCell>
                <TableCell align="center">
                    <Button
                        className="RedirectButton"
                        color="secondary"
                        onClick={() => cancelConference(conference)}
                    >
                        Cancel
                    </Button>
                </TableCell>
                <TableCell align="center">
                    <Button
                        className="RedirectButton"
                        color="secondary"
                        onClick={() => checkAvailableSeats(conference)}
                    >
                        Check
                    </Button>
                </TableCell>
            </TableRow>
        );
    }

    async function sendJson(data, url, method) {
        const response = await fetch(url, {
            method: method,
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(data) // body data type must match "Content-Type" header
        });
        let body = await response.text();
        setMessage(body);
    }

    const removeParticipant = async (participant) => {
        const dataToSend = {participant: participant, conferenceId: conference.id}
        sendJson(dataToSend, "/removeParticipantFromConference", "DELETE");

        let index = conferenceParticipants.indexOf(participant);
        if (index > -1) {
            conferenceParticipants.splice(index, 1);
        }
    }

    const cancelConference = async (conference) => {
        sendJson(conference, "/cancelConference", "DELETE");

        setConference("");
        setConferenceParticipants([]);
        setConferenceDetailsTableRow("");
    }

    const checkAvailableSeats = async (conference) => {
        sendJson(conference, "/checkConferenceSeatsAvailability", "POST");
    }

    return (
        <div className={classes.paper}>

            <Typography component="h1" variant="h5">
                Conference Details
            </Typography>
            <TableContainer
                style={{width: "80%", margin: "10px 10px"}}
                component={Paper}
            >
                <Table className={classes.table} aria-label="simple table">
                    <TableHead>
                        <TableRow style={{backgroundColor: "#F8F8F8"}}>
                            <TableCell align="center">ID</TableCell>
                            <TableCell align="center">Name</TableCell>
                            <TableCell align="center">Start Date and Time</TableCell>
                            <TableCell align="center">End Date and Time</TableCell>
                            <TableCell align="center">Conference Room ID</TableCell>
                            <TableCell align="center">Cancel Conference</TableCell>
                            <TableCell align="center">Check Available Seats</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {conferenceDetailsTableRow}
                    </TableBody>
                </Table>
            </TableContainer>

            <Typography component="h1" variant="h5" style={{marginTop: "40px"}}>
                Participants' Details
            </Typography>
            <TableContainer
                style={{width: "80%", margin: "10px 10px"}}
                component={Paper}
            >
                <Table className={classes.table} aria-label="simple table">
                    <TableHead>
                        <TableRow style={{backgroundColor: "#F8F8F8"}}>
                            <TableCell align="center">ID</TableCell>
                            <TableCell align="center">Name</TableCell>
                            <TableCell align="center">Date of Birth</TableCell>
                            <TableCell align="center">Remove from Conference</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {conferenceParticipants.map(participant => (
                            <TableRow key={participant.id}>
                                <TableCell align="center">{participant.id}</TableCell>
                                <TableCell align="center">{participant.fullName}</TableCell>
                                <TableCell align="center">{participant.dateOfBirth}</TableCell>
                                <TableCell align="center">
                                    <Button
                                        className="RedirectButton"
                                        color="secondary"
                                        onClick={() => removeParticipant(participant)}
                                    >
                                        Remove
                                    </Button>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
            <Typography style={{margin: 7}} variant="body1">
                Status: {message}
            </Typography>
        </div>
    );
}