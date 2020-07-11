import React, {useEffect} from "react";
import Button from "@material-ui/core/Button";
import CssBaseline from "@material-ui/core/CssBaseline";
import TextField from "@material-ui/core/TextField";
import Grid from "@material-ui/core/Grid";
import Typography from "@material-ui/core/Typography";
import {makeStyles} from "@material-ui/core/styles";
import Container from "@material-ui/core/Container";
import Select from "react-select";

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

    const [name, setName] = React.useState("");
    const [startDateTime, setStartDateTime] = React.useState("");
    const [endDateTime, setEndDateTime] = React.useState("");
    const [conferenceRoomOptions, setConferenceRoomOptions] = React.useState([]);
    const [selectedValue, setSelectedValue] = React.useState("");
    const [message, setMessage] = React.useState("Nothing saved in this session");

    const handleNameChange = event => setName(event.target.value);
    const handleStartDateTimeChange = event => setStartDateTime(event.target.value);
    const handleEndDateTimeChange = event => setEndDateTime(event.target.value);
    const handleSelectedValueChange = event => setSelectedValue(event.value);

    useEffect(() => {
        fetchConferenceRooms();
    }, []);

    const fetchConferenceRooms = async () => {
        const data = await fetch("/getAllConferenceRooms");
        const items = await data.json();
        let options = items.map(conferenceRoom => ({label: conferenceRoom.name, value: conferenceRoom}));
        setConferenceRoomOptions(options);
    }

    async function sendPostJson(data) {
        const response = await fetch("/addConference", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(data) // body data type must match "Content-Type" header
        });
        let body = await response.text();
        setMessage(body);
    }

    const handleSubmit = () => {
        let convertedStartDateTime = convertDateTime(startDateTime);
        let convertedEndDateTime = convertDateTime(endDateTime);
        let toInput = {
            name,
            "startDateTime": convertedStartDateTime,
            "endDateTime": convertedEndDateTime,
            "conferenceRoomId": selectedValue.id
        };
        sendPostJson(toInput);
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

    return (
        <Container component="main" maxWidth="xs">
            <CssBaseline/>
            <div className={classes.paper}>

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
                            <Select
                                placeholder={"Select Conference Room *"}
                                value={conferenceRoomOptions.find(obj => obj.value === selectedValue)}
                                options={conferenceRoomOptions}
                                onChange={handleSelectedValueChange}
                            />
                        </Grid>
                    </Grid>
                    <Button
                        fullWidth
                        variant="contained"
                        color="primary"
                        preventDefault
                        className={classes.submit}
                        onClick={handleSubmit}
                    >
                        Save
                    </Button>

                </form>
                <Typography style={{margin: 7}} variant="body1">
                    Status: {message}
                </Typography>
            </div>
        </Container>
    );
}
