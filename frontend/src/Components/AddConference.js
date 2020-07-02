import React from "react";
import Avatar from "@material-ui/core/Avatar";
import Button from "@material-ui/core/Button";
import CssBaseline from "@material-ui/core/CssBaseline";
import TextField from "@material-ui/core/TextField";
import {Link} from "react-router-dom";
import Grid from "@material-ui/core/Grid";
import GroupIcon from "@material-ui/icons/Group";
import Typography from "@material-ui/core/Typography";
import {makeStyles} from "@material-ui/core/styles";
import Container from "@material-ui/core/Container";

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

export default function AddConference() {
    const classes = useStyles();
    const [firstLoad, setLoad] = React.useState(true);

    const [name, setName] = React.useState("");
    const [startDateTime, setStartDateTime] = React.useState("");
    const [endDateTime, setEndDateTime] = React.useState("");
    const [conferenceRoomId, setConferenceRoomId] = React.useState("");

    const handleNameChange = event => setName(event.target.value);
    const handleStartDateTimeChange = event => setStartDateTime(event.target.value);
    const handleEndDateTimeChange = event => setEndDateTime(event.target.value);
    const handleConferenceRoomIdChange = event => setConferenceRoomId(event.target.value);

    const [message, setMessage] = React.useState("Nothing saved in the session");

    async function fetchFunc(toInput) {
        alert(JSON.stringify(toInput));
        const response = await fetch("/addConference", {
            method: "POST", // *GET, POST, PUT, DELETE, etc.
            mode: "cors", // no-cors, *cors, same-origin
            cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
            credentials: "same-origin", // include, *same-origin, omit
            headers: {
                "Content-Type": "application/json"
            },
            redirect: "follow", // manual, *follow, error
            referrerPolicy: "no-referrer", // no-referrer, *client
            body: JSON.stringify(toInput) // body data type must match "Content-Type" header
        });
        let body = await response.json();
        // console.log(body.id);
        setMessage(body);
        // setMessage(body.id ? "Data successfully updated" : "Data update failed");
    }

    const handleSubmit = variables => {
        let convertedStartDateTime = convertDateTime(startDateTime);
        let convertedEndDateTime = convertDateTime(endDateTime);
        let toInput = {name, "startDateTime": convertedStartDateTime, "endDateTime": convertedEndDateTime, conferenceRoomId};
        fetchFunc(toInput);
        // resets fields
        /*setName("");
        setStartDateAndTime("");
        setEndDateAndTime("");
        setConferenceRoomId("");*/
    };

    function convertDateTime(dateTime) {
        let date = new Date(dateTime);
        const options = {
            day: '2-digit', month: '2-digit', year: 'numeric',
            hour: '2-digit', minute: '2-digit'
        };
        let resultDate = new Intl.DateTimeFormat('en-GB', options).format(date);
        let formattedDateTime = resultDate.toString()
            .replace("/", "-")
            .replace("/", "-")
            .replace(",", "");
        return formattedDateTime;
    }

    if (firstLoad) {
        setLoad(false);
    }

    return (
        <Container component="main" maxWidth="xs">
            <CssBaseline/>
            <div className={classes.paper}>
                <Avatar className={classes.avatar}>
                    <GroupIcon/>
                </Avatar>
                <Typography component="h1" variant="h5">
                    Add Conference
                </Typography>
                <form className={classes.form} noValidate>
                    <Grid container spacing={2}>
                        <Grid item xs={12}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                id="name"
                                value={name}
                                label="Name"
                                name="name"
                                autoComplete="name"
                                onChange={handleNameChange}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                autoComplete="startDateTime"
                                name="startDateTime"
                                variant="outlined"
                                type="datetime-local"
                                format="dd/MM/yyyy HH:mm"
                                InputLabelProps={{
                                    shrink: true,
                                }}
                                required
                                fullWidth
                                value={startDateTime}
                                id="startDateTime"
                                label="Start: date and time"
                                onChange={handleStartDateTimeChange}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                autoComplete="endDateTime"
                                name="endDateTime"
                                variant="outlined"
                                type="datetime-local"
                                InputLabelProps={{
                                    shrink: true,
                                }}
                                required
                                fullWidth
                                value={endDateTime}
                                id="endDateAndTime"
                                label="End: date and time"
                                onChange={handleEndDateTimeChange}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                id="conferenceRoomId"
                                value={conferenceRoomId}
                                label="Conference room ID"
                                name="conferenceRoomId"
                                autoComplete="conferenceRoomId"
                                onChange={handleConferenceRoomIdChange}
                            />
                        </Grid>
                    </Grid>
                    <Button
                        // type="submit"
                        fullWidth
                        variant="contained"
                        color="primary"
                        preventDefault
                        className={classes.submit}
                        onClick={handleSubmit}
                    >
                        Save
                    </Button>

                    <Grid container justify="center">
                        <Grid item>
                            <Link className={classes.link} to="/">
                                {" "}
                                <Typography align="left" style={{margin: "10px"}}>
                                    &#x2190; Head back Home
                                </Typography>{" "}
                            </Link>
                        </Grid>
                    </Grid>
                </form>
                <Typography style={{margin: 7}} variant="body1">
                    Status: {message}
                </Typography>
            </div>
        </Container>
    );
}
