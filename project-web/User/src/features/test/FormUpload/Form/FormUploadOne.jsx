import {yupResolver} from "@hookform/resolvers/yup";
import {makeStyles} from "@material-ui/core";
import React, {useState} from "react";
import {useForm} from "react-hook-form";
import * as yup from "yup";
import ButtonSubmit from "../../../../components/Button/ButtonSubmit";
import ButtonUploadFW from "../../../../components/Button/ButtonUploadFW";
import {
    colorBlack1,
    colorBlack2,
    colorOrange1,
} from "../../../../components/color/color";
import CustomInput from "../../../../components/Input/CustomInput";
//css
const useStyles = makeStyles((theme) => ({
    root: {
        // backgroundImage: 'url("../assets/images/noell.jpg")',
        minHeight: "100vh",

        position: "relative",

        backgroundPosition: "center",
        // backgroundRepeat: "no-repeat",
        // backgroundSize: "cover",
        background:
            'linear-gradient(rgba(0, 0, 0, 0.3), rgba(0, 0, 0, 0.3)), url("../assets/images/noell.jpg")',
    },
    cssForm: {
        background: "white",
        position: "absolute",
        left: "50%",
        top: "50%",
        transform: "translate(-50%, -50%)",
        padding: "20px",
        borderRadius: "10px",
        width: "26%",
        [theme.breakpoints.only("xs")]: {
            width: "90%",
            height: "80%",
        },
        [theme.breakpoints.only("sm")]: {
            width: "36%",
        },
    },
    title: {
        fontFamily: "Poppins",
        fontSize: "40px",
        textAlign: "center",
        display: "block",
        fontWeight: "700",
        margin: "50px 0 30px 0",
    },
    text1: {
        marginTop: "40px",
        display: "block",
        textAlign: "center",
        color: colorBlack1,
    },
    textDK: {
        display: "block",
        textAlign: "center",
        textDecoration: "none",
        color: colorBlack2,
        "&:hover": {
            color: colorOrange1,
        },
    },
    text3: {
        margin: "10px 0px 10px 0px",
        display: "block",
        textAlign: "right",
        textDecoration: "none",
        color: colorBlack2,
        "&:hover": {
            color: colorOrange1,
        },
    },
}));
//proptypes
FormUploadOne.propTypes = {};

//yup

const schema = yup.object().shape({});

//function
function FormUploadOne(props) {
    const {onSubmit} = props;
    const [dataUpload, setDataUpload] = useState({
        name: "",
        file: null,
    });
    const classes = useStyles();
    const form = useForm({
        defaultValues: {
            name: "",
            file: null,
        },
        resolver: yupResolver(schema),
    });

    function handleOnSubmit(values) {
        const tmpName = values.name;
        console.log("handleOnSubmit", tmpName);
        if (!onSubmit) return;
        const tmpDataUpload = {...dataUpload};
        tmpDataUpload.name = tmpName;
        setDataUpload(tmpDataUpload);
        onSubmit(tmpDataUpload);
    }

    function handleOnChange(data) {
        console.log(data.name);
        const tmpDataUpload = {...dataUpload};
        tmpDataUpload.file = data;
        setDataUpload(tmpDataUpload);
    }

    return (
        <div className={classes.root}>
            <form
                className={classes.cssForm}
                onSubmit={form.handleSubmit(handleOnSubmit)}
            >
                <span className={classes.title}>Form Upload</span>
                <CustomInput name="name" label="Title" form={form}/>

                <ButtonUploadFW
                    title="Upload"
                    name="file"
                    form={form}
                    onChange={handleOnChange}
                />
                <ButtonSubmit title="Submit"/>
            </form>
        </div>
    );
}

export default FormUploadOne;
