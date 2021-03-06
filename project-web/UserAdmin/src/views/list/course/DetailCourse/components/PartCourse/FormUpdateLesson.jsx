import {yupResolver} from "@hookform/resolvers/yup";
import {makeStyles} from "@material-ui/core";
import classNames from "classnames";
import {useSnackbar} from "notistack";
import React, {useState} from "react";
import {useForm} from "react-hook-form";
import courseApi from "src/api/courseApi";
import CustomButton from "src/components/CustomButton";
import CustomButtonRed from "src/components/CustomButtonRed";
import CustomDialogAction from "src/components/CustomDialogAction";
import CustomInput from "src/components/CustomInput";
import {isEmpty} from "src/Tool/Tools";
import * as yup from "yup";

const useStyles = makeStyles((theme) => ({
    formAddPart: {
        marginTop: 30,
        marginBottom: 20,
        padding: "15px",
        border: "var(--colorBlue2) 1px solid",
        borderRadius: "5px",
        "&>div": {
            display: "flex",
            alignItems: "center",
            "&>span": {
                minWidth: 150,
                fontSize: 16,
            },
        },
        "&>div:last-of-type": {
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
            "&>button": {
                margin: "15px 10px",
            },
        },
    },
    title: {
        fontWeight: 600,
    },
    videoPlay: {
        width: "50%",
    },
    buttonUpload: {
        marginLeft: 200,
    },
    divVideo: {
        "&>div": {
            display: "flex",
            alignItems: "center",
            justifyContent: "space-around",
        },
        marginTop: "30px",
        marginBottom: "30px",
    },
    hidden: {
        display: "none",
    },
    [theme.breakpoints.down("md")]: {
        divVideo: {
            "&>div": {
                display: "flex",
                flexFlow: "column",
                alignItems: "flex-start",
                justifyContent: "space-around",
            },
            marginTop: "30px",
            marginBottom: "30px",
        },
        videoPlay: {
            width: "90%",
            marginBottom: 15,
        },
    },
}));
const schema = yup.object().shape({
    // firstName: yup.string().required(),
});

function FormUpdateLesson(props) {
    const {part = {}, lesson = {}, changeDataCourse = null} = props;
    const classes = useStyles();
    const [demoVideo, setDemoVideo] = useState(lesson?.video?.urlVideo);
    const {enqueueSnackbar} = useSnackbar();
    const form = useForm({
        mode: "onBlur",
        defaultValues: {
            idCourse: part.courseId,
            idLesson: lesson.id,
            idPart: lesson.partId,
            description: lesson.description,
        },
        resolver: yupResolver(schema),
    });
    const handleOnSubmit = (values) => {
        //todo hoang todo
        console.log("Update Lesson", values);
        if (isEmpty(values?.videoCourse?.name)) {
            enqueueSnackbar("Vui l??ng t???i l??n video b??i h???c", {variant: "error"});
            return;
        }
        (async () => {
            const formData = new FormData();

            formData.append("videoCourse", values.videoCourse);
            formData.append("description", values.description);
            // formData.append("image", values.image);
            // pass param courseId, partId
            const rp = await courseApi.updateLesson(
                part.courseId,
                part.id,
                lesson.id,
                formData
            );
            if (!rp.status) {
                console.log(rp);
                if (changeDataCourse) changeDataCourse(rp);
                enqueueSnackbar("C???p nh???t th??nh c??ng", {variant: "success"});
            }
        })();
    };
    const handleChangeImg = () => {
        const inputFile = document.getElementById(
            `input-video-update-${lesson.id}`
        );
        inputFile.click();
    };
    const handleOnChangeFile = (event) => {
        const file = event.target.files[0];
        console.log(file);
        const tmpImg = URL.createObjectURL(file);
        setDemoVideo(tmpImg);
        form.setValue("videoCourse", file);
    };
    const [isOpen, setOpen] = useState(false);
    const handleOnChangeOpen = () => {
        setOpen(!isOpen);
    };
    const handleDeleteLesson = async () => {
        //console.log("Hoang TODO");
        console.log("X??a B??i H???c");

        const rp = await courseApi.deleteLesson(part.courseId, part.id, lesson.id);
        if (!rp.status) {
            console.log(rp);
        }
    };
    return (
        <form
            className={classes.formAddPart}
            onSubmit={form.handleSubmit(handleOnSubmit)}
        >
            <h3 className={classes.title}>C???p nh???t b??i h???c</h3>
            <div>
                <span>Ti??u ????? b??i h???c</span>
                <CustomInput
                    name="description"
                    title="Ti??u ????? b??i h???c"
                    label="Ti??u ????? b??i h???c"
                    form={form}
                />
            </div>
            <div className={classes.divVideo}>
                <span>Video b??i h???c</span>

                <div>
                    <input
                        accept="video/*"
                        type="file"
                        name="avatar"
                        id={`input-video-update-${lesson.id}`}
                        onChange={handleOnChangeFile}
                        hidden
                    />

                    <video
                        key={demoVideo}
                        // autoPlay
                        muted
                        loop
                        className={classNames(
                            !isEmpty(demoVideo) && classes.videoPlay,
                            isEmpty(demoVideo) && classes.hidden
                        )}
                        controls
                    >
                        <source src={demoVideo} type="video/mp4"></source>
                    </video>
                    <CustomButton
                        className={classes.buttonUpload}
                        title="Upload"
                        color="secondary"
                        variant="contained"
                        onClick={handleChangeImg}
                        css={false}
                    />
                </div>
            </div>

            <div>
                <CustomButton type="submit" title="X??c nh???n"/>
                <CustomButtonRed title="X??a b??i h???c" onClick={handleOnChangeOpen}/>
                <CustomDialogAction
                    title={`B???n c?? mu???n x??a b??i ${lesson.description}`}
                    closeDialog={handleOnChangeOpen}
                    isOpen={isOpen}
                    accepct={handleDeleteLesson}
                    id={`delete-lesson-${lesson.id}`}
                    content={
                        <div>
                            B???n c?? mu???n x??a b??i <b>{lesson.description}</b> n??y kh??ng ?
                        </div>
                    }
                />
            </div>
        </form>
    );
}

export default FormUpdateLesson;
