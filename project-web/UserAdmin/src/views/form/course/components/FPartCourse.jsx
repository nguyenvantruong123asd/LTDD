import {CCard, CCardBody, CCardFooter, CCardHeader} from "@coreui/react";
import {yupResolver} from "@hookform/resolvers/yup";
import {makeStyles} from "@material-ui/core";
import Accordion from "@material-ui/core/Accordion";
import AccordionDetails from "@material-ui/core/AccordionDetails";
import AccordionSummary from "@material-ui/core/AccordionSummary";
import Typography from "@material-ui/core/Typography";
import ExpandMoreIcon from "@material-ui/icons/ExpandMore";
import classNames from "classnames";
import {useSnackbar} from "notistack";
import React from "react";
import {useForm} from "react-hook-form";
import courseApi from "src/api/courseApi";
import CustomButton from "src/components/CustomButton";
import CustomInput from "src/components/CustomInput";
import {isEmpty} from "src/Tool/Tools";
import FormCreateLesson from "src/views/list/course/DetailCourse/components/PartCourse/FormCreateLesson";
import FormParts from "src/views/list/course/DetailCourse/components/PartCourse/FormParts";
import FormUploadLesson from "src/views/list/course/DetailCourse/components/PartCourse/FormUpdateLesson";
import * as yup from "yup";

const useStyles = makeStyles((theme) => ({
    formAddPart: {
        display: "flex",
        alignItems: "center",
        "& > span": {
            minWidth: "150px",
            fontSize: " 16px",
        },
        "& > button": {
            marginLeft: "15px",
        },
        marginBottom: "30px",
        marginTop: "30px",
    },
    contentPart: {
        "& .MuiAccordionDetails-root": {
            display: "flex",
            flexFlow: "column",
        },
        "&>.MuiPaper-elevation1": {
            boxShadow:
                "0px 2px 1px -1px rgba(44, 62, 80,1.0), 0px 1px 1px 0px rgba(44, 62, 80,1.0), 0px 1px 3px 0px rgba(44, 62, 80,1.0)",
        },
    },
    heading: {
        fontSize: "16px",
        fontWeight: 600,
        color: "var(--colorBlack1)",
    },
    accordion: {},
    footer: {
        display: "flex",
        justifyContent: "space-between",
    },
    visiable: {
        visibility: "hidden",
    },
    [theme.breakpoints.down("md")]: {},
}));
const schema = yup.object().shape({
    // firstName: yup.string().required(),
});

function FPartCourse(props) {
    const classes = useStyles();
    const {
        nextCurrentStep = null,
        step = 0,
        currentStep = 0,
        prevStep = null,
        nextStep = null,
        dataCourse = {},
        updateCourse = null,
    } = props;
    const {enqueueSnackbar} = useSnackbar();
    let indexLesson = 1;
    const formAddPart = useForm({
        mode: "onBlur",
        defaultValues: {
            namepart: "",
            //idCourse: dataCourse?.id
        },
        resolver: yupResolver(schema),
    });

    const handleOnSubmit = async (values) => {
        console.log("PartPost", values);
        try {
            const rp = await courseApi.postPart(dataCourse.id, values);
            if (!rp.status) {
                console.log(rp);
                if (updateCourse) updateCourse(rp);
                enqueueSnackbar("C???p nh???t th??nh c??ng", {variant: "success"});
                formAddPart.reset();
            } else {
                enqueueSnackbar("C???p nh???t kh??ng th??nh c??ng", {variant: "error"});
            }
        } catch (error) {
            enqueueSnackbar(error.message, {variant: "error"});
        }
    };
    const handleChangeDataCourse = (value) => {
        if (updateCourse) updateCourse(value);
    };
    return (
        <CCard>
            <CCardHeader>
                <span className="title">Ch???nh s???a kh??a h???c</span>
            </CCardHeader>
            <CCardBody>
                <div className={classes.contentPart}>
                    {!isEmpty(dataCourse.parts) &&
                    Array.from(dataCourse.parts).map((item, index) => (
                        <Accordion key={item.id} className={classes.accordion}>
                            <AccordionSummary expandIcon={<ExpandMoreIcon/>}>
                  <span className={classes.heading}>
                    Ph???n {index + 1} : {item.title}
                  </span>
                            </AccordionSummary>
                            <AccordionDetails>
                                {!isEmpty(item.lessons) &&
                                Array.from(item.lessons).map((item1, index1) => (
                                    <Accordion key={item1.id}>
                                        <AccordionSummary expandIcon={<ExpandMoreIcon/>}>
                                            <Typography className={classes.heading}>
                                                B??i {indexLesson++} : {item1.description}
                                            </Typography>
                                        </AccordionSummary>
                                        <AccordionDetails>
                                            <FormUploadLesson
                                                lesson={item1}
                                                part={item}
                                                changeDataCourse={handleChangeDataCourse}
                                            />
                                        </AccordionDetails>
                                    </Accordion>
                                ))}
                                <FormParts
                                    part={item}
                                    index={index + 1}
                                    // indexPart={indexPart}
                                    onSubmit={handleOnSubmit}
                                    changeDataCourse={handleChangeDataCourse}
                                />
                                <FormCreateLesson
                                    dataCourse={dataCourse}
                                    part={item}
                                    onSubmit={handleOnSubmit}
                                    changeDataCourse={handleChangeDataCourse}
                                />
                            </AccordionDetails>
                        </Accordion>
                    ))}
                </div>
                <form
                    className={classes.formAddPart}
                    onSubmit={formAddPart.handleSubmit(handleOnSubmit)}
                >
                    <span>Th??m ph???n kh??a h???c</span>
                    <CustomInput
                        name="namepart"
                        title="T??n ph???n h???c"
                        label="T??n ph???n h???c"
                        form={formAddPart}
                    />
                    <CustomButton type="submit" title="Th??m"/>
                </form>
            </CCardBody>
            <CCardFooter className={classes.footer}>
                <div className={classNames(step <= 0 && classes.visiable)}>
                    <CustomButton
                        title="Tr?????c"
                        onClick={() => {
                            if (prevStep) prevStep();
                        }}
                    />
                </div>
                <div className={classNames(step >= currentStep && classes.visiable)}>
                    <CustomButton
                        title="Sau"
                        onClick={() => {
                            if (nextStep) nextStep();
                        }}
                    />
                </div>
            </CCardFooter>
        </CCard>
    );
}

export default FPartCourse;
