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

export default function AddParticipant() {
    const classes = useStyles();
    const [firstLoad, setLoad] = React.useState(true);

    const [fullName, setFullName] = React.useState("");
    const [dateOfBirth, setDateOfBirth] = React.useState("");
    const [conferenceId, setConferenceId] = React.useState("");

    const handleFullNameChange = event => setFullName(event.target.value);
    const handleDateOfBirthChange = event => setDateOfBirth(event.target.value);
    const handleConferenceIdChange = event => setConferenceId(event.target.value);

    const [message, setMessage] = React.useState("Nothing saved in the session");

    async function fetchFunc(toInput) {
        const response = await fetch("/addParticipantToConference", {
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
        console.log(body.id);
        setMessage(body.id ? "Data successfully updated" : "Data update failed");
    }

    const handleSubmit = variables => {
        const toInput = {name: fullName, dateOfBirth, conferenceId};
        fetchFunc(toInput);
        setFullName("");
        setDateOfBirth("");
        setConferenceId("");
    };

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
                    Add Participant to Conference
                </Typography>
                <form className={classes.form} noValidate>
                    <Grid container spacing={2}>
                        <Grid item xs={12}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                id="fullName"
                                value={fullName}
                                label="Full name"
                                name="fullName"
                                autoComplete="fullName"
                                onChange={handleFullNameChange}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                autoComplete="dateOfBirth"
                                name="dateOfBirth"
                                variant="outlined"
                                type="date"
                                format="dd/MM/yyyy"
                                InputLabelProps={{
                                    shrink: true,
                                }}
                                required
                                fullWidth
                                value={dateOfBirth}
                                id="dateOfBirth"
                                label="Date of birth"
                                onChange={handleDateOfBirthChange}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                autoComplete="conferenceId"
                                name="conferenceId"
                                variant="outlined"
                                required
                                fullWidth
                                value={conferenceId}
                                id="conferenceId"
                                label="Conference ID"
                                onChange={handleConferenceIdChange}
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
                            <Link to="/viewParticipants">View Participants</Link>
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
